package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;

import com.popularmovies.udacity.android.popularmoviesudacity.base.BasePresenter;
import com.popularmovies.udacity.android.popularmoviesudacity.base.BaseView;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;

/**
 * Created by smenesid on 20-Jan-17.
 */

public class MoviesContract {

    public interface View extends BaseView<MoviesContract.Presenter> {
        void showMovie(Movie movie);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter extends BasePresenter {
        void loadMovies(int page, String order);
    }
}
