package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;

import android.util.Log;

import com.popularmovies.udacity.android.popularmoviesudacity.BuildConfig;
import com.popularmovies.udacity.android.popularmoviesudacity.MoviesContract;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class GridMoviesPresenter implements MoviesContract.Presenter {
    private static final String LOG_TAG = GridMoviesPresenter.class.getName();
    private Subscription subscription;
    private AppRemoteDataStore appRemoteDataStore;
    private MoviesContract.View view;

    public GridMoviesPresenter(AppRemoteDataStore appRemoteDataStore, MoviesContract.View view) {
        this.appRemoteDataStore = appRemoteDataStore;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadMovies(int page) {
        if (appRemoteDataStore == null) {
            appRemoteDataStore = new AppRemoteDataStore();
        }

        //TOOD: if else based on setting getMoviesTopRated
        subscription = appRemoteDataStore.getMoviesPopular(BuildConfig.THE_MOVIE_DB_API_KEY, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public final void onCompleted() {
                        Log.d("movie", "completed" + " page = " + page);
                        view.showComplete();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("movie", e.getMessage() + " page = " + page);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Log.d("movie", "transfer success" + " page = " + page);
                        view.showMovie(movie);
                    }
                });
    }

    @Override
    public void subscribe(int page) {
        loadMovies(page);
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}