package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails;

import com.popularmovies.udacity.android.popularmoviesudacity.base.BasePresenter;
import com.popularmovies.udacity.android.popularmoviesudacity.base.BaseView;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResults;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;

/**
 * Created by smenesid on 25-Jan-17.
 */

public class MovieDetailsContract {

    public interface View extends BaseView<MovieDetailsContract.Presenter> {
        void showMovie(MovieResults movie);

        void showReview(Review review);

        void showVideos(Videos videos);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter extends BasePresenter {
        void loadMovie(MovieResults movie);

        void loadReview(int page, String id);

        void loadVideo(int page, String id);
    }
}