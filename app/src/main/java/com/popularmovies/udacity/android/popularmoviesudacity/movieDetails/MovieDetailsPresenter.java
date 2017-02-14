package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import android.util.Log;

import com.popularmovies.udacity.android.popularmoviesudacity.BuildConfig;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResults;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private static final String LOG_TAG = MovieDetailsContract.class.getName();

    private Subscription subscription;
    private AppRemoteDataStore appRemoteDataStore;
    private MovieDetailsContract.View view;
    private Boolean mIsFavorite = false;

    public MovieDetailsPresenter(AppRemoteDataStore appRemoteDataStore,
                                 MovieDetailsContract.View view) {
        this.appRemoteDataStore = appRemoteDataStore;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadMovie(MovieResults movie) {
        if (appRemoteDataStore == null) {
            appRemoteDataStore = new AppRemoteDataStore();
        }

        view.showMovie(movie);
    }

    @Override
    public void loadReview(int page, String id) {
        if (appRemoteDataStore == null) {
            appRemoteDataStore = new AppRemoteDataStore();
        }

        subscription = appRemoteDataStore.getMovieReviews(id, BuildConfig.THE_MOVIE_DB_API_KEY, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Review>() {
                    @Override
                    public final void onCompleted() {
                        Log.d(LOG_TAG, "review completed" + " page = " + page);
                        view.showComplete();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage() + " page = " + page);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(Review review) {
                        Log.d(LOG_TAG, "review transfer success" + " page = " + page);
                        view.showReview(review);
                    }
                });
    }

    @Override
    public void loadVideo(int page, String id) {
        if (appRemoteDataStore == null) {
            appRemoteDataStore = new AppRemoteDataStore();
        }

        subscription = appRemoteDataStore.getMovieTrailer(id, BuildConfig.THE_MOVIE_DB_API_KEY, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Videos>() {
                    @Override
                    public final void onCompleted() {
                        Log.d(LOG_TAG, "video completed" + " page = " + page);
                        view.showComplete();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage() + " page = " + page);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(Videos videos) {
                        Log.d(LOG_TAG, "video transfer success" + " page = " + page);
                        view.showVideos(videos);
                    }
                });
    }

    @Override
    public void saveOrRemoveFavorite() {
        //mIsFavorite = mMovieRepository.isFavorite(mMovie.id);
        Log.d("HERE", "HERE");

        if (mIsFavorite) {
            //mMovieRepository.removeFavorite(mMovie.id);
            view.setIconFavorite(false);
            Log.d("HERE1", "false");
        } else {
            //mMovieRepository.insertFavorite(mMovie);
            view.setIconFavorite(true);
            Log.d("HERE1", "true");
        }
    }

    @Override
    public void subscribe(int page, String order) {

    }

    @Override
    public void unsubscribe() {

        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
