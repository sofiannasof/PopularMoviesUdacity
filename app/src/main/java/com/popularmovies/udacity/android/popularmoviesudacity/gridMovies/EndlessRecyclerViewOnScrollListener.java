package com.popularmovies.udacity.android.popularmoviesudacity.gridMovies;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by smenesid on 19-Feb-17.
 */
public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    RecyclerView.LayoutManager mLayoutManager;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;

    public EndlessRecyclerViewOnScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        if (layoutManager instanceof GridLayoutManager)
            visibleThreshold = visibleThreshold * ((GridLayoutManager) layoutManager).getSpanCount();
        else if (layoutManager instanceof StaggeredGridLayoutManager)
            visibleThreshold = visibleThreshold * ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        }

        if (totalItemCount == 0)
            return;

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();

}