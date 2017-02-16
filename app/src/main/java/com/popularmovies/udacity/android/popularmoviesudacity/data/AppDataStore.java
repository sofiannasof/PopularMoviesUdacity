package com.popularmovies.udacity.android.popularmoviesudacity.data;

import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;

import rx.Observable;

/**
 * Created by smenesid on 13-Feb-17.
 */

public interface AppDataStore {

    Observable<Movie> getMoviesPopular(String apiKey, int page);

    Observable<Movie> getMoviesTopRated(String apiKey, int page);

    Observable<Videos> getMovieTrailer(String id, String api_key, int page);

    Observable<Review> getMovieReviews(String id, String api_key, int page);

    Observable<Movie> getMoviesFavorite();
}
