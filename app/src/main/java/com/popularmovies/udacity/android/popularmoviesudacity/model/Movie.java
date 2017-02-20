package com.popularmovies.udacity.android.popularmoviesudacity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by smenesid on 21-Jan-17.
 */

public class Movie {

    @SerializedName("page")
    public int page;
    @SerializedName("results")
    public List<MovieResults> results;
    @SerializedName("total_results")
    public int total_results;
    @SerializedName("total_pages")
    public int total_pages;

    public Movie(List<MovieResults> results, int total_results) {
        this.page = 1;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = 1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieResults> getResults() {
        return results;
    }

    public void setResults(List<MovieResults> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

}