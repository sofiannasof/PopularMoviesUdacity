package com.popularmovies.udacity.android.popularmoviesudacity.api;

import com.popularmovies.udacity.android.popularmoviesudacity.Movie;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by smenesid on 20-Jan-17.
 */

public interface MovieService {

    @GET("/3/movie/popular")
    Observable<Movie> getMoviesPopular(@Query("api_key") String apiKey, @Query("page") int page);

/*    @GET("movie/popular")
    Observable<Movie> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Observable<Movie> getTopRatedMovies(@Query("api_key") String apiKey);*/

}
