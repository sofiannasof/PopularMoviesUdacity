package com.popularmovies.udacity.android.popularmoviesudacity;

import android.util.Log;

import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class ListMoviePresenter implements MovieContract.Presenter {
    private Subscription subscription;
    private AppRemoteDataStore appRemoteDataStore;
    private MovieContract.View view;

    public ListMoviePresenter(AppRemoteDataStore appRemoteDataStore, MovieContract.View view) {
        this.appRemoteDataStore = appRemoteDataStore;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadMovieDetails(int page) {
        if (appRemoteDataStore == null) {
            appRemoteDataStore = new AppRemoteDataStore();
        }

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
                        view.showMovieDetails(movie);
                    }
                });
    }

    @Override
    public void subscribe(int page) {
        loadMovieDetails(page);
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}