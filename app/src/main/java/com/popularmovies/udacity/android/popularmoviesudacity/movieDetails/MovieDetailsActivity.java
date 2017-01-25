package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.popularmovies.udacity.android.popularmoviesudacity.MovieDetailsContract;
import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.data.MovieApplication;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

import javax.inject.Inject;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsActivity extends AppCompatActivity
        implements MovieDetailsContract.View {

    @Inject
    AppRemoteDataStore appRemoteDataStore;
    Gson gson = new Gson();
    TextView tvTitle;
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
            tvTitle = (TextView) findViewById(R.id.movie_title);
            tvTitle.setText(mMovie.getOriginal_title());
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
