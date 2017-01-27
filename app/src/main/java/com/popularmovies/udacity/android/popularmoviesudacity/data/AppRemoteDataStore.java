package com.popularmovies.udacity.android.popularmoviesudacity.data;

import android.util.Log;

import com.popularmovies.udacity.android.popularmoviesudacity.api.MovieService;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class AppRemoteDataStore implements MovieService {

    private static final String LOG_TAG = AppRemoteDataStore.class.getName();

    @Inject
    Retrofit retrofit;

    public AppRemoteDataStore() {
        MovieApplication.getAppComponent().inject(this);
    }

    @Override
    public Observable<Movie> getMoviesPopular(@Query("api_key") String apiKey,
                                              @Query("page") int page) {
        Log.d(LOG_TAG, "Loaded popular movies");
        Observable<Movie> call = null;
        if (retrofit != null) {
            MovieService apiService = retrofit.create(MovieService.class);

            call = apiService.getMoviesPopular(apiKey, page);
        }
        return call;
    }

    @Override
    public Observable<Movie> getMoviesTopRated(@Query("api_key") String apiKey) {
        Log.d(LOG_TAG, "Loaded top rated movies");
        Observable<Movie> call = null;
        if (retrofit != null) {
            MovieService apiService = retrofit.create(MovieService.class);

            call = apiService.getMoviesTopRated(apiKey);
        }
        return call;
    }
}

