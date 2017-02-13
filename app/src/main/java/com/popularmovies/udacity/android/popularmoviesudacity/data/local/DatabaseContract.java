package com.popularmovies.udacity.android.popularmoviesudacity.data.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by smenesid on 13-Feb-17.
 */

public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "com.popularmovies.udacity.android.popularmoviesudacity";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class Movies implements BaseColumns {
        public static final String CONTENT_URI_STRING = "content://" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "movie_title";
        public static final String COLUMN_OVERVIEW = "movie_overview";
        public static final String COLUMN_VOTE_COUNT = "movie_vote_count";
        public static final String COLUMN_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_LANGUAGE = "movie_language";
        public static final String COLUMN_VOTE_AVERAGE = "movie_vote_avg";

        public static Uri buildMovieUri(int movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

        public static int getMovieIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
