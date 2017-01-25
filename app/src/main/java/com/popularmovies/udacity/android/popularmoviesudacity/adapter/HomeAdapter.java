package com.popularmovies.udacity.android.popularmoviesudacity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.gridMovies.LoadListener;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Movie;
import com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder> {

    List<Movie.Results> mMovies;
    private LoadListener mLoadListener;
    private Context mContext;
    private boolean mLoading;
    private int mLastMoviesCount = 0;

    public HomeAdapter() {
        super();
        mMovies = new ArrayList<>();
    }

    public void addData(List<Movie.Results> movie) {
        mMovies.addAll(movie);
        mLoading = false;
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {

        if (canLoadMoreMovies(i)) {
            if (mLoadListener != null) {
                mLoadListener.onLoadMoreData();
                mLastMoviesCount = mMovies.size();
                mLoading = true;
            }
        } else {
            final Movie.Results movie = mMovies.get(i);
            // TODO: deal with null images
            Picasso.with(mContext).load(movie.getPoster_path()).into(itemViewHolder.imageView);
            itemViewHolder.itemView.setOnClickListener(v -> startMovieDetailsActivity(1)); //TODO: get id
        }
    }

    private boolean canLoadMoreMovies(int currentPosition) {
        return !mLoading && currentPosition == mMovies.size() - 1 &&
                mLastMoviesCount != mMovies.size();
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setOnMovieClickedListener(LoadListener loadListener) {
        this.mLoadListener = loadListener;
    }

    public void startMovieDetailsActivity(int movieId) {
        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieId);
        mContext.startActivity(intent);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.movie_image_view);
        }
    }
}