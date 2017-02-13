package com.popularmovies.udacity.android.popularmoviesudacity.data;

import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;

import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by smenesid on 13-Feb-17.
 */

public interface AppDataStore {

    Observable<Movie> getMoviesPopular(@Query("api_key") String apiKey,
                                       @Query("page") int page);

    Observable<Movie> getMoviesTopRated(@Query("api_key") String apiKey,
                                        @Query("page") int page);

    Observable<Videos> getMovieTrailer(@Path("id") String id,
                                       @Query("api_key") String api_key,
                                       @Query("page") int page);

    Observable<Review> getMovieReviews(@Path("id") String id,
                                       @Query("api_key") String api_key,
                                       @Query("page") int page);
}
