package com.popularmovies.udacity.android.popularmoviesudacity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by smenesid on 02-Feb-17.
 */
public class Review {
    @SerializedName("id")
    public int id;
    @SerializedName("page")
    public int page;
    @SerializedName("results")
    public List<Review.Results> results;
    @SerializedName("total_pages")
    public int total_pages;
    @SerializedName("total_results")
    public int total_results;

    public List<Review.Results> getResults() {
        return results;
    }

    public static class Results {
        @SerializedName("id")
        public String id;
        @SerializedName("author")
        public String author;
        @SerializedName("content")
        public String content;
        @SerializedName("url")
        public String url;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
