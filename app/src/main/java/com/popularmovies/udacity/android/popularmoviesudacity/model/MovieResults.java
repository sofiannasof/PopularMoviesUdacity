package com.popularmovies.udacity.android.popularmoviesudacity.model;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.udacity.android.popularmoviesudacity.data.local.DatabaseContract;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverColumn;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverType;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by smenesid on 14-Feb-17.
 */
@StorIOSQLiteType(table = DatabaseContract.Movies.TABLE_NAME)
@StorIOContentResolverType(uri = DatabaseContract.Movies.CONTENT_URI_STRING)
public class MovieResults {

    @SerializedName("poster_path")
    public String poster_path;
    @SerializedName("adult")
    public boolean adult;
    @SerializedName("overview")
    public String overview;
    @SerializedName("release_date")
    public String release_date;
    @StorIOSQLiteColumn(name = DatabaseContract.Movies.COLUMN_MOVIE_ID, key = true)
    @StorIOContentResolverColumn(name = DatabaseContract.Movies.COLUMN_MOVIE_ID, key = true)
    @SerializedName("id")
    public int id;
    @SerializedName("original_title")
    public String original_title;
    @SerializedName("original_language")
    public String original_language;
    @StorIOSQLiteColumn(name = DatabaseContract.Movies.COLUMN_TITLE)
    @StorIOContentResolverColumn(name = DatabaseContract.Movies.COLUMN_TITLE)
    @SerializedName("title")
    public String title;
    @SerializedName("backdrop_path")
    public String backdrop_path;
    @SerializedName("popularity")
    public double popularity;
    @SerializedName("vote_count")
    public int vote_count;
    @SerializedName("video")
    public boolean video;
    @SerializedName("vote_average")
    public double vote_average;

    public String getPoster_path() {
        return "http://image.tmdb.org/t/p/w185" + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return "http://image.tmdb.org/t/p/w780" + backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

}