package com.popularmovies.udacity.android.popularmoviesudacity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by smenesid on 02-Feb-17.
 */
public class Videos {
    @SerializedName("id")
    public int id;
    @SerializedName("results")
    public List<Results> results;

    public List<Videos.Results> getResults() {
        return results;
    }

    public static class Results {
        @SerializedName("id")
        public String id;
        @SerializedName("iso_639_1")
        public String iso_639_1;
        @SerializedName("iso_3166_1")
        public String iso_3166_1;
        @SerializedName("key")
        public String key;
        @SerializedName("name")
        public String name;
        @SerializedName("site")
        public String site;
        @SerializedName("size")
        public int size;
        @SerializedName("type")
        public String type;

        public String getKey() {
            return "https://img.youtube.com/vi/" + key + "/mqdefault.jpg";
        }
    }
}