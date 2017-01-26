package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.popularmovies.udacity.android.popularmoviesudacity.MovieDetailsContract;
import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.data.MovieApplication;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsActivity extends AppCompatActivity
        implements MovieDetailsContract.View {

    @Inject
    AppRemoteDataStore appRemoteDataStore;
    Gson gson = new Gson();
    TextView originalTitle;
    TextView releaseDate;
    TextView plot;
    TextView rating;
    ImageView thumb;
    private Movie.Results mMovie;
    private MovieDetailsContract.Presenter mPresenter;

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
                    .into(thumb);

        }
    }

    @Override
    public void showMovie(Movie.Results movie) {
        this.mMovie = movie;
        mPresenter.loadMovie(movie);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading movie", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete() {
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
