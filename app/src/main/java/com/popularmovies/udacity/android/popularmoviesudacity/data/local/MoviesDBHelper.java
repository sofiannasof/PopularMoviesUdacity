package com.popularmovies.udacity.android.popularmoviesudacity.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by smenesid on 13-Feb-17.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " + DatabaseContract.Movies.TABLE_NAME + " (" +
                        DatabaseContract.Movies.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        DatabaseContract.Movies.COLUMN_TITLE + " TEXT NOT NULL, " +
         /*               DatabaseContract.Movies.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                        DatabaseContract.Movies.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                        DatabaseContract.Movies.COLUMN_LANGUAGE + " TEXT NOT NULL, " +
                        DatabaseContract.Movies.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                        DatabaseContract.Movies.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +*/
                        "UNIQUE ( " + DatabaseContract.Movies.COLUMN_MOVIE_ID + " ) ON CONFLICT REPLACE )";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Movies.TABLE_NAME);
        onCreate(db);
    }
}
