package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;

import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

/**
 * Created by smenesid on 25-Jan-17.
 */

public interface OnMovieClickListener {
    void onMovieClick(Movie.Results movie);
}
