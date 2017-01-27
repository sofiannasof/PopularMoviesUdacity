package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;

import android.util.Log;

import com.popularmovies.udacity.android.popularmoviesudacity.BuildConfig;
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

    public GridMoviesPresenter(AppRemoteDataStore appRemoteDataStore,
                               MoviesContract.View view) {
        this.appRemoteDataStore = appRemoteDataStore;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadMovies(int page, String order) {
        if (appRemoteDataStore == null) {
            appRemoteDataStore = new AppRemoteDataStore();
        }

        if (order.equals("popular")) {

            subscription = appRemoteDataStore.getMoviesPopular(BuildConfig.THE_MOVIE_DB_API_KEY, page)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Movie>() {
                        @Override
                        public final void onCompleted() {
                            Log.d(LOG_TAG, "popular completed" + " page = " + page);
                            view.showComplete();
                        }

                        @Override
                        public final void onError(Throwable e) {
                            Log.e(LOG_TAG, e.getMessage() + " page = " + page);
                            view.showError(e.toString());
                        }

                        @Override
                        public void onNext(Movie movie) {
                            Log.d(LOG_TAG, "popular transfer success" + " page = " + page);
                            view.showMovie(movie);
                        }
                    });
        } else if (order.equals("top_rated")) {

            subscription = appRemoteDataStore.getMoviesTopRated(BuildConfig.THE_MOVIE_DB_API_KEY)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Movie>() {
                        @Override
                        public final void onCompleted() {
                            Log.d(LOG_TAG, "top_rated completed" + " page = " + page);
                            view.showComplete();
                        }

                        @Override
                        public final void onError(Throwable e) {
                            Log.e(LOG_TAG, e.getMessage() + " page = " + page);
                            view.showError(e.toString());
                        }

                        @Override
                        public void onNext(Movie movie) {
                            Log.d(LOG_TAG, "top_rated transfer success" + " page = " + page);
                            view.showMovie(movie);
                        }
                    });
        }
    }

    @Override
    public void subscribe(int page, String order) {
        loadMovies(page, order);
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}