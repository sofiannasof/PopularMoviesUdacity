package com.popularmovies.udacity.android.popularmoviesudacity;

import com.popularmovies.udacity.android.popularmoviesudacity.base.BasePresenter;
import com.popularmovies.udacity.android.popularmoviesudacity.base.BaseView;

/**
 * Created by smenesid on 20-Jan-17.
 */

public class MovieContract {

    public interface View extends BaseView<Presenter> {
        void showMovieDetails(Movie movie);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter extends BasePresenter {
        void loadMovieDetails(int page);
    }
}
