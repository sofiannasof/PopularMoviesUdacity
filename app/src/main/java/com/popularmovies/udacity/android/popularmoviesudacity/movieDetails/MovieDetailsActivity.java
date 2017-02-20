package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.data.MovieApplication;
import com.popularmovies.udacity.android.popularmoviesudacity.data.local.AppLocalDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResults;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;
import com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.adapter.ReviewAdapter;
import com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.adapter.VideosAdapter;
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
    @Inject
    AppLocalDataStore appLocalDataStore;

    Gson gson = new Gson();
    TextView originalTitle;
    TextView releaseDate;
    TextView plot;
    TextView rating;
    ImageView thumb;
    ImageView backdrop;
    CardView cardMovieReviews;
    CardView cardMovieVideos;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private MovieResults mMovie;
    private MovieDetailsContract.Presenter mPresenter;
    private String id;
    private Review mReview;
    private Videos mVideos;
    private RecyclerView mRecyclerViewReviews;
    private RecyclerView mRecyclerViewVideos;
    private LinearLayoutManager mLayoutManagerReviews;
    private LinearLayoutManager mLayoutManagerVideos;
    private ReviewAdapter mReviewsAdapter;
    private VideosAdapter mVideosAdapter;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MovieApplication.getAppComponent().inject(this);
        new MovieDetailsPresenter(appRemoteDataStore, this);

        Intent extras = getIntent();

        if (extras.hasExtra(Intent.EXTRA_TEXT)) {
            String value = extras.getStringExtra(Intent.EXTRA_TEXT);

            mMovie = gson.fromJson(value, MovieResults.class);
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
            backdrop = (ImageView) findViewById(R.id.backdrop);
            toolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                toolbar.setNavigationOnClickListener(view -> onBackPressed());
            }

            Picasso.with(getApplicationContext())
                    .load(mMovie.getBackdrop_path())
                    .placeholder(R.drawable.ic_posterplaceholder)
                    .error(R.drawable.ic_errorplaceholder)
                    .into(backdrop);
            id = Integer.toString(mMovie.getId());

            mRecyclerViewReviews = (RecyclerView) findViewById(R.id.recycler_view_reviews);
            mRecyclerViewReviews.setHasFixedSize(true);
            mLayoutManagerReviews = new LinearLayoutManager(this);
            mRecyclerViewReviews.setLayoutManager(mLayoutManagerReviews);
            mRecyclerViewReviews.setItemAnimator(new DefaultItemAnimator());
            mReviewsAdapter = new ReviewAdapter();
            cardMovieReviews = (CardView) findViewById(R.id.card_movie_reviews);
            mRecyclerViewReviews.setAdapter(mReviewsAdapter);

            mRecyclerViewVideos = (RecyclerView) findViewById(R.id.recycler_view_videos);
            mRecyclerViewVideos.setHasFixedSize(true);
            mLayoutManagerVideos = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewVideos.setLayoutManager(mLayoutManagerVideos);
            mRecyclerViewVideos.setItemAnimator(new DefaultItemAnimator());
            mVideosAdapter = new VideosAdapter();
            cardMovieVideos = (CardView) findViewById(R.id.card_movie_videos);
            mRecyclerViewVideos.setAdapter(mVideosAdapter);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(v -> mPresenter.saveOrRemoveFavorite());

            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            collapsingToolbarLayout.setTitle(mMovie.getTitle());
            collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
            setTitle("");

            fetchMovie();
            fetchReviews();
            fetchVideos();
            fetchFav();
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

    private void fetchVideos() {
        if (!Utils.isOnline(this)) {
            Toast.makeText(this, R.string.message_no_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.loadVideo(1, id);
        }
    }

    private void fetchFav() {
        if (appLocalDataStore.isFavorite(mMovie)) {
            fab.setImageResource(R.drawable.ic_favorite_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_24dp);
        }
    }

    @Override
    public void showMovie(MovieResults movie) {
        this.mMovie = movie;
    }

    @Override
    public void showReview(Review review) {
        this.mReview = review;
        mReviewsAdapter.addData(review.getResults());
        if (mReviewsAdapter == null || mReviewsAdapter.getItemCount() == 0) {
            cardMovieReviews.setVisibility(View.GONE);
        } else {
            cardMovieReviews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showVideos(Videos videos) {
        this.mVideos = videos;
        mVideosAdapter.addData(videos.getResults());
        if (mVideosAdapter == null || mVideosAdapter.getItemCount() == 0) {
            cardMovieVideos.setVisibility(View.GONE);
        } else {
            cardMovieVideos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading movie", Toast.LENGTH_SHORT).show();
        if (mReviewsAdapter == null || mReviewsAdapter.getItemCount() == 0) {
            cardMovieReviews.setVisibility(View.GONE);
        } else {
            cardMovieReviews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void setIconFavorite() {
        if (!appLocalDataStore.isFavorite(mMovie)) {
            fab.setImageResource(R.drawable.ic_favorite_24dp);
            appLocalDataStore.saveFieldsToDatabase(mMovie);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_24dp);
            appLocalDataStore.deleteFieldsFromDatabase(mMovie);
        }
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
