package com.popularmovies.udacity.android.popularmoviesudacity.movieDetails.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.udacity.android.popularmoviesudacity.R;
import com.popularmovies.udacity.android.popularmoviesudacity.model.Videos;
import com.popularmovies.udacity.android.popularmoviesudacity.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smenesid on 02-Feb-17.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ItemViewHolder> {

    private static final String LOG_TAG = RecyclerView.class.getName();

    List<Videos.Results> mVideos;
    private Context mContext;

    public VideosAdapter() {
        super();
        mVideos = new ArrayList<>();
    }

    public void addData(List<Videos.Results> video) {
        mVideos.addAll(video);
        notifyDataSetChanged();
    }

    public void clear() {
        mVideos.clear();
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view_videos, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        Videos.Results video = mVideos.get(i);
        itemViewHolder.title.setText(video.getName());
        Picasso.with(mContext).load(video.getVideoThumb())
                .placeholder(R.drawable.ic_posterplaceholder)
                .error(R.drawable.ic_errorplaceholder)
                .into(itemViewHolder.thumb_video);
        itemViewHolder.thumb_video.setOnClickListener(v ->
                showTrailerinYouTube(video));
        itemViewHolder.play.setOnClickListener(v ->
                showTrailerinYouTube(video));
        itemViewHolder.share.setOnClickListener(v ->
                Utils.shareTrailer(mContext, video));
    }

    private void showTrailerinYouTube(Videos.Results video) {
        if (video != null && video.getVideoThumb() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getVideo()));
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb_video;
        TextView title;
        ImageView play;
        ImageView share;

        public ItemViewHolder(View itemView) {
            super(itemView);
            thumb_video = (ImageView) itemView.findViewById(R.id.thumb_video);
            title = (TextView) itemView.findViewById(R.id.movie_trailer_title);
            play = (ImageView) itemView.findViewById(R.id.movie_trailer_play);
            share = (ImageView) itemView.findViewById(R.id.movie_trailer_share);
        }
    }
}
