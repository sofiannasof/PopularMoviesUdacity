package com.popularmovies.udacity.android.popularmoviesudacity;

import com.popularmovies.udacity.android.popularmoviesudacity.base.BasePresenter;
import com.popularmovies.udacity.android.popularmoviesudacity.base.BaseView;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsContract {

    public interface View extends BaseView<MovieDetailsContract.Presenter> {
        void showMovie(Movie.Results movie);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter extends BasePresenter {
        void loadMovie(Movie.Results movie);
    }
}