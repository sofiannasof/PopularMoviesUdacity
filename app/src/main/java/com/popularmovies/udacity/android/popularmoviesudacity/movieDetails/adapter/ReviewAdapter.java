package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smenesid on 02-Feb-17.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder> {

    private static final String LOG_TAG = RecyclerView.class.getName();

    List<Review.Results> mReviews;
    private Context mContext;

    public ReviewAdapter() {
        super();
        mReviews = new ArrayList<>();
    }

    public void addData(List<Review.Results> review) {
        mReviews.addAll(review);
        notifyDataSetChanged();
    }

    public void clear() {
        mReviews.clear();
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view_reviews, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        Review.Results review = mReviews.get(i);
        Log.e(LOG_TAG, review.getAuthor());
        itemViewHolder.reviewTextView.setText(review.getContent());
        itemViewHolder.authorTextView.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView reviewTextView;
        TextView authorTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            reviewTextView = (TextView) itemView.findViewById(R.id.text_movie_review_content);
            authorTextView = (TextView) itemView.findViewById(R.id.text_movie_review_author);
        }
    }
}
