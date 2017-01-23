package com.popularmovies.udacity.android.popularmoviesudacity.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;


/**
 * Created by smenesid on 23-Jan-17.
 */

@Module
public class DataModule {
    String mBaseUrl;

    public DataModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(mBaseUrl)
                .setClient(new OkClient(new OkHttpClient()))
                .build();
    }

    @Provides
    @Singleton
    AppRemoteDataStore providesRepository() {
        return new AppRemoteDataStore();
    }

}