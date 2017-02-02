package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import com.popularmovies.udacity.android.popularmoviesudacity.utils.Utils;

import javax.inject.Inject;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class GridMoviesActivity extends AppCompatActivity
        implements MoviesContract.View, LoadListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = GridMoviesActivity.class.getName();
    private static final String SCROLL_POSITION_KEY = "scroll";

    @Inject
    AppRemoteDataStore appRemoteDataStore;

    private Movie movie;
    private MoviesContract.Presenter mPresenter;
    private MoviesAdapter mHomeAdapter;
    private int mCurrentMoviePageNumber = 1;
    private ProgressBar mProgressBar;
    private SharedPreferences prefs;
    private String mode = "popular";
    private SwipeRefreshLayout refreshLayout;
    private GridLayoutManager mLayoutManager;
    private int scrollPosition = 0;
    private RecyclerView mRecyclerView;

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieApplication.getAppComponent().inject(this);
        new GridMoviesPresenter(appRemoteDataStore, this);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(() -> refresh());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mHomeAdapter = new MoviesAdapter();
        mHomeAdapter.setOnMovieClickedListener(() -> mRecyclerView.post(() -> onLoadMoreData()));

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(getApplicationContext()));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mHomeAdapter);

        setupSharedPreferences();
        fetchPage();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SCROLL_POSITION_KEY)) {
                scrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY, 0);
                mLayoutManager.scrollToPosition(scrollPosition);
            }
        }
    }

    private void setupSharedPreferences() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mode = prefs.getString(getString(R.string.pref_order_by),
                getResources().getString(R.string.pref_default_value));
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    private void refresh() {
        mCurrentMoviePageNumber = 1;
        mHomeAdapter.clear();
        scrollPosition = 0;
        fetchPage();
        refreshLayout.setRefreshing(false);
    }

    private void fetchPage() {
        if (!Utils.isOnline(this)) {
            Toast.makeText(this, R.string.message_no_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.loadMovies(mCurrentMoviePageNumber, mode);
            mCurrentMoviePageNumber++;
        }
    }

    @Override
    public void showMovie(Movie movie) {
        mProgressBar.setVisibility(View.VISIBLE);
        this.movie = movie;
        mHomeAdapter.addData(movie.getResults());
        mRecyclerView.scrollToPosition(scrollPosition);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCROLL_POSITION_KEY, mLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_order_by))) {
            mode = sharedPreferences.getString(key, getResources().getString(R.string.pref_order));
            refresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}

