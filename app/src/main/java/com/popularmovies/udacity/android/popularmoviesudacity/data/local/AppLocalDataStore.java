package com.popularmovies.udacity.android.popularmoviesudacity.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.popularmovies.udacity.android.popularmoviesudacity.data.AppDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResults;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResultsStorIOContentResolverDeleteResolver;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResultsStorIOContentResolverGetResolver;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResultsStorIOContentResolverPutResolver;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;
import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by smenesid on 12-Feb-17.
 */

public class AppLocalDataStore implements AppDataStore {

    private StorIOContentResolver mStorIOContentResolver;

    @Inject
    public AppLocalDataStore(@NonNull Context context) {
        mStorIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(context.getContentResolver())
                .addTypeMapping(MovieResults.class, ContentResolverTypeMapping.<MovieResults>builder()
                        .putResolver(new MovieResultsStorIOContentResolverPutResolver())
                        .getResolver(new MovieResultsStorIOContentResolverGetResolver())
                        .deleteResolver(new MovieResultsStorIOContentResolverDeleteResolver()).build()
                ).build();
    }

    @Override
    public Observable<Movie> getMoviesPopular(String apiKey, int page) {
        return null;
    }

    @Override
    public Observable<Movie> getMoviesTopRated(String apiKey, int page) {
        return null;
    }

    @Override
    public Observable<Videos> getMovieTrailer(String id, String api_key, int page) {
        return null;
    }

    @Override
    public Observable<Review> getMovieReviews(String id, String api_key, int page) {
        return null;
    }

    @Override
    public Observable<Movie> getMoviesFavorite() {
        return mStorIOContentResolver.get()
                .object(Movie.class)
                .withQuery(Query.builder()
                        .uri(DatabaseContract.Movies.CONTENT_URI)
                        .build())
                .prepare()
                .asRxObservable();
    }

    public void saveFieldsToDatabase(MovieResults results) {
        mStorIOContentResolver.put()
                .object(results)
                .prepare()
                .executeAsBlocking();
    }

    public void deleteFieldsFromDatabase(MovieResults results) {
        mStorIOContentResolver.delete()
                .object(results)
                .prepare()
                .executeAsBlocking();
    }

    public boolean isFavorite(MovieResults results) {
        boolean favorite = false;

        MovieResults mResult = mStorIOContentResolver.get()
                .object(MovieResults.class)
                .withQuery(com.pushtorefresh.storio.contentresolver.queries.Query.builder()
                        .uri(DatabaseContract.Movies.CONTENT_URI)
                        .where(DatabaseContract.Movies.COLUMN_MOVIE_ID + " = ? ")
                        .whereArgs(results.getId()).build()
                ).prepare().executeAsBlocking();

        if (mResult != null)
            favorite = true;

        Log.e("isFavorite", Boolean.toString(favorite));
        return favorite;
    }
}
