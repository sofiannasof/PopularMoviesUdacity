package com.popularmovies.udacity.android.popularmoviesudacity.api;

import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by smenesid on 20-Jan-17.
 */

public interface MovieService {

    @GET("movie/popular")
        //TODO: remove page if not necessary
    Observable<Movie> getMoviesPopular(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Observable<Movie> getMoviesTopRated(@Query("api_key") String apiKey, @Query("page") int page);
}
