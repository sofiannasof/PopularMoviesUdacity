package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.data.AppRemoteDataStore;
import com.popularmovies.udacity.android.popularmoviesudacity.data.MovieApplication;
import com.popularmovies.udacity.android.popularmoviesudacity.gridMovies.adapter.MoviesAdapter;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

import javax.inject.Inject;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class GridMoviesActivity extends AppCompatActivity
        implements MoviesContract.View, LoadListener {

    @Inject
    AppRemoteDataStore appRemoteDataStore;
    private Movie movie;
    private MoviesContract.Presenter mPresenter;
    private MoviesAdapter mHomeAdapter;
    private int mCurrentMoviePageNumber = 0;
    private ProgressBar mProgressBar;
    private int GRID_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieApplication.getAppComponent().inject(this);
        new GridMoviesPresenter(appRemoteDataStore, this);

        fetchPage();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mHomeAdapter = new MoviesAdapter();

        //Calling loadMore function in Runnable to fix the
        // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
        mHomeAdapter.setOnMovieClickedListener(() -> mRecyclerView.post(() -> fetchPage()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    private void fetchPage() {
        mCurrentMoviePageNumber++;
        mPresenter.loadMovies(mCurrentMoviePageNumber);
        if (movie != null) {
            mHomeAdapter.addData(movie.getResults());
        }
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
        fetchPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}

