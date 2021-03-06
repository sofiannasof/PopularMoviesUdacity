package com.popularmovies.udacity.android.popularmoviesudacity.api;

import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by smenesid on 20-Jan-17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<Movie> getMoviesPopular(@Query("api_key") String apiKey,
                                       @Query("page") int page);

    @GET("movie/top_rated")
    Observable<Movie> getMoviesTopRated(@Query("api_key") String apiKey,
                                        @Query("page") int page);

    @GET("movie/{id}/videos")
    Observable<Videos> getMovieTrailer(@Path("id") String id,
                                       @Query("api_key") String api_key,
                                       @Query("page") int page);

    @GET("movie/{id}/reviews")
    Observable<Review> getMovieReviews(@Path("id") String id,
                                       @Query("api_key") String api_key,
                                       @Query("page") int page);
}
