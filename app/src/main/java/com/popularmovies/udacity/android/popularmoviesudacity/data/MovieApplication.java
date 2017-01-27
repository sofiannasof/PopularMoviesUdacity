package com.popularmovies.udacity.android.popularmoviesudacity.data;

import android.app.Application;

import com.popularmovies.udacity.android.popularmoviesudacity.di.AppComponent;
import com.popularmovies.udacity.android.popularmoviesudacity.di.AppModule;
import com.popularmovies.udacity.android.popularmoviesudacity.di.DaggerAppComponent;
import com.popularmovies.udacity.android.popularmoviesudacity.di.DataModule;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class MovieApplication extends Application {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String LOG_TAG = MovieApplication.class.getName();
    private static AppComponent mAppComponent;

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule(BASE_URL))
                .build();
    }
}
