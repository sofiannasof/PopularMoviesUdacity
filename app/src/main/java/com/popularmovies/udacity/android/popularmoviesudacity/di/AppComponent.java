package com.popularmovies.udacity.android.popularmoviesudacity.di;

import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.gridMovies.GridMoviesActivity;
import com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.MovieDetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by smenesid on 23-Jan-17.
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject(GridMoviesActivity activity);
    void inject(MovieDetailsActivity activity);
    void inject(AppRemoteDataStore appRemoteDataStore);
}
