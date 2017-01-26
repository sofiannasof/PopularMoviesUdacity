package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import com.popularmovies.udacity.android.popularmoviesudacity.MovieDetailsContract;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

import rx.Subscription;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {
    private Subscription subscription;
    private AppRemoteDataStore appRemoteDataStore;
    private MovieDetailsContract.View view;

    public MovieDetailsPresenter(AppRemoteDataStore appRemoteDataStore,
                                 MovieDetailsContract.View view) {
        this.appRemoteDataStore = appRemoteDataStore;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadMovie(Movie.Results movie) {
        view.showMovie(movie);
    }

    @Override
    public void subscribe(int page) {

    }

    @Override
    public void unsubscribe() {

        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
