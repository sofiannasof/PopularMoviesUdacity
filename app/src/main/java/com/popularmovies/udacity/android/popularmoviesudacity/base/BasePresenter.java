package com.popularmovies.udacity.android.popularmoviesudacity.base;

/**
 * Created by smenesid on 20-Jan-17.
 */
public interface BasePresenter {

    void subscribe(int page, String order);

    void unsubscribe();
}
