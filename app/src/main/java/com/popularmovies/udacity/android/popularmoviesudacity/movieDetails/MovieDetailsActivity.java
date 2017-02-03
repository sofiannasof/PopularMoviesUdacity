package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.data.MovieApplication;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.adapter.ReviewAdapter;
import com.popularmovies.udacity.android.popularmoviesudacity.utils.Utils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsActivity extends AppCompatActivity
        implements MovieDetailsContract.View {

    private static final String LOG_TAG = MovieDetailsActivity.class.getName();

    @Inject
    AppRemoteDataStore appRemoteDataStore;
    Gson gson = new Gson();
    TextView originalTitle;
    TextView releaseDate;
    TextView plot;
    TextView rating;
    ImageView thumb;
    CardView cardMovieReviews;
    private Movie.Results mMovie;
    private MovieDetailsContract.Presenter mPresenter;
    private Review mReview;
    private String id;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ReviewAdapter mHomeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MovieApplication.getAppComponent().inject(this);
        new MovieDetailsPresenter(appRemoteDataStore, this);

        Intent extras = getIntent();

        if (extras.hasExtra(Intent.EXTRA_TEXT)) {
            String value = extras.getStringExtra(Intent.EXTRA_TEXT);

            mMovie = gson.fromJson(value, Movie.Results.class);
            originalTitle = (TextView) findViewById(R.id.original_title);
            originalTitle.setText(mMovie.getOriginal_title());
            releaseDate = (TextView) findViewById(R.id.release_date);
            releaseDate.setText(mMovie.getRelease_date());
            plot = (TextView) findViewById(R.id.plot);
            plot.setText(mMovie.getOverview());
            rating = (TextView) findViewById(R.id.rating);
            rating.setText(Double.toString(mMovie.getVote_average()).substring(0, 3));
            thumb = (ImageView) findViewById(R.id.thumb);
            Picasso.with(getApplicationContext())
                    .load(mMovie.getPoster_path())
                    .placeholder(R.drawable.ic_posterplaceholder)
                    .error(R.drawable.ic_errorplaceholder)
                    .into(thumb);
            id = Integer.toString(mMovie.getId());

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_reviews);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mHomeAdapter = new ReviewAdapter();
            mRecyclerView.setAdapter(mHomeAdapter);
            cardMovieReviews = (CardView) findViewById(R.id.card_movie_reviews);
            fetchMovie();
            fetchReviews();
        }
    }

    private void fetchMovie() {
        if (!Utils.isOnline(this)) {
            Toast.makeText(this, R.string.message_no_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.loadMovie(mMovie);
        }
    }

    private void fetchReviews() {
        if (!Utils.isOnline(this)) {
            Toast.makeText(this, R.string.message_no_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.loadReview(1, id);
        }
    }

    @Override
    public void showMovie(Movie.Results movie) {
        this.mMovie = movie;
    }

    @Override
    public void showReview(Review review) {
        this.mReview = review;
        mHomeAdapter.addData(review.getResults());
        if (mHomeAdapter == null || mHomeAdapter.getItemCount() == 0) {
            cardMovieReviews.setVisibility(View.GONE);
        } else {
            cardMovieReviews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading movie", Toast.LENGTH_SHORT).show();
        if (mHomeAdapter == null || mHomeAdapter.getItemCount() == 0) {
            cardMovieReviews.setVisibility(View.GONE);
        } else {
            cardMovieReviews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
