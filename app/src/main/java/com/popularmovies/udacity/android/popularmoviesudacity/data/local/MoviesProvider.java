package com.popularmovies.udacity.android.popularmoviesudacity.data.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by smenesid on 13-Feb-17.
 */

public class MoviesProvider extends ContentProvider {

    static final int FAV_MOVIE_ID = 100;
    static final int FAV_MOVIE_LIST = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDdHelper mMoviesDBHelper;

    static UriMatcher buildUriMatcher() {
        final String authority = DatabaseContract.CONTENT_AUTHORITY;
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authority, DatabaseContract.PATH_FAV_MOVIES + "/#", FAV_MOVIE_ID);
        uriMatcher.addURI(authority, DatabaseContract.PATH_FAV_MOVIES, FAV_MOVIE_LIST);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMoviesDBHelper = new MoviesDdHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case FAV_MOVIE_ID:
                retCursor = mMoviesDBHelper.getReadableDatabase().query(
                        DatabaseContract.Movies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                Log.e("PROVIDER ", "FAV_MOVIE_ID");

                break;
            case FAV_MOVIE_LIST:
                retCursor = mMoviesDBHelper.getReadableDatabase().query(
                        DatabaseContract.Movies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                Log.e("PROVIDER ", "FAV_MOVIE_LIST");

                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.e("PROVIDER COUNT", Integer.toString(retCursor.getCount()));

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mMoviesDBHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case FAV_MOVIE_LIST:
                long _id = db.insert(DatabaseContract.Movies.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DatabaseContract.Movies.buildUserUri(_id);
                else
                    throw new SQLException("Failed to insert row " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
        Log.e("PROVIDER ", "insert");

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMoviesDBHelper.getWritableDatabase();
        int rowsDeleted;
        switch (sUriMatcher.match(uri)) {
            case FAV_MOVIE_LIST:
                rowsDeleted = db.delete(DatabaseContract.Movies.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
        if (selection == null || 0 != rowsDeleted)
            getContext().getContentResolver().notifyChange(uri, null);

        Log.e("PROVIDER ", "deleted rows" + rowsDeleted);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMoviesDBHelper.getWritableDatabase();
        int update;
        switch (sUriMatcher.match(uri)) {
            //Case for User
            case FAV_MOVIE_LIST:
                update = db.update(DatabaseContract.Movies.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
        if (update > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return update;
    }
}
