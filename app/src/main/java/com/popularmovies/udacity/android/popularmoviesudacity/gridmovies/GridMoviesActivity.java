package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.data.MovieApplication;
import com.popularmovies.udacity.android.popularmoviesudacity.gridMovies.adapter.MoviesAdapter;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.settings.MyPreferences;
import com.popularmovies.udacity.android.popularmoviesudacity.utils.SettingsUtils;

import javax.inject.Inject;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class GridMoviesActivity extends AppCompatActivity
        implements MoviesContract.View, LoadListener {

    private static final String LOG_TAG = GridMoviesActivity.class.getName();

    @Inject
    AppRemoteDataStore appRemoteDataStore;

    private Movie movie;
    private MoviesContract.Presenter mPresenter;
    private MoviesAdapter mHomeAdapter;
    private int mCurrentMoviePageNumber = 1;
    private ProgressBar mProgressBar;
    private SharedPreferences prefs;
    private int GRID_COLUMNS = 2;
    private String mode = "popular";
    private boolean previouslyStarted;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieApplication.getAppComponent().inject(this);
        new GridMoviesPresenter(appRemoteDataStore, this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        previouslyStarted = prefs.getBoolean(getString(R.string.prefs_isFirstLaunch), false);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(() -> refresh());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mHomeAdapter = new MoviesAdapter();

        mHomeAdapter.setOnMovieClickedListener(() -> mRecyclerView.post(() -> onLoadMoreData()));
        fetchPage();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    private void refresh() {
        mCurrentMoviePageNumber = 1;
        mHomeAdapter.clear();
        fetchPage();
        refreshLayout.setRefreshing(false);
    }

    private void fetchPage() {
        if(!previouslyStarted) {
            SettingsUtils.isFirstRunProcessComplete(getApplicationContext());
            prefs.edit().putBoolean(getString(R.string.prefs_isFirstLaunch), true).commit();
            prefs.edit().putString(getString(R.string.pref_order_by),mode).commit();
        } else {
            if (prefs.getAll().get(getString(R.string.pref_order_by)).equals("popular")) {
                mode = "popular";
            } else if (prefs.getAll().get(getString(R.string.pref_order_by)).equals("top_rated")) {
                mode = "top_rated";
            }
        }

        mPresenter.loadMovies(mCurrentMoviePageNumber, mode);
        mCurrentMoviePageNumber++;
    }

    @Override
    public void showMovie(Movie movie) {
        mProgressBar.setVisibility(View.VISIBLE);
        this.movie = movie;
        mHomeAdapter.addData(movie.getResults());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading movies", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showComplete() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadMoreData() {
        mProgressBar.setVisibility(View.VISIBLE);
        if (mCurrentMoviePageNumber != 1) {
            fetchPage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startSettingsActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startSettingsActivity() {
        Intent intent = new Intent(GridMoviesActivity.this, MyPreferences.class);
        startActivity(intent);
    }
}

