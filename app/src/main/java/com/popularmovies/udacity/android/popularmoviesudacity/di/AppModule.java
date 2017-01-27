package com.popularmovies.udacity.android.popularmoviesudacity.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by smenesid on 23-Jan-17.
 */

@Module
public class AppModule {

    private static final String LOG_TAG = AppModule.class.getName();

    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}