package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.model.MovieResults;
import com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smenesid on 23-Jan-17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ItemViewHolder> {

    private static final String LOG_TAG = MoviesAdapter.class.getName();

    List<MovieResults> mMovies;
    private Context mContext;

    public MoviesAdapter() {
        super();
        mMovies = new ArrayList<>();
    }

    public void addData(List<MovieResults> movie) {
        mMovies.addAll(movie);
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
        final MovieResults movie = mMovies.get(i);
        Picasso.with(mContext).load(movie.getPoster_path())
                .placeholder(R.drawable.ic_posterplaceholder)
                .error(R.drawable.ic_errorplaceholder)
                .into(itemViewHolder.imageView);
        itemViewHolder.itemView.setOnClickListener(v ->
                startMovieDetailsActivity(movie));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void startMovieDetailsActivity(MovieResults movie) {
        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, new Gson().toJson(movie));
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