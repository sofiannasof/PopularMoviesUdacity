package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.popularmovies.udacity.android.popularmoviesudacity.MoviesContract;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsActivity extends AppCompatActivity implements MoviesContract.View {


    @Override
    public void showMovie(Movie movie) {
        Log.e("CALLED", "HERE");
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {

    }
}
