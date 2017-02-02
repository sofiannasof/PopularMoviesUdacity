package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import com.popularmovies.udacity.android.popularmoviesudacity.base.BasePresenter;
import com.popularmovies.udacity.android.popularmoviesudacity.base.BaseView;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsContract {

    public interface View extends BaseView<MovieDetailsContract.Presenter> {
        void showMovie(Movie.Results movie);

        void showReview(Review review);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter extends BasePresenter {
        void loadMovie(Movie.Results movie);

        void loadReview(int page, String id);
    }
}