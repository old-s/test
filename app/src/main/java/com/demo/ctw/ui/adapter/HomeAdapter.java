package com.demo.ctw.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.ctw.R;
import com.demo.ctw.base.HeaderRecyclerViewAdapter;
import com.demo.ctw.entity.HomeListObect;
import com.demo.ctw.entity.HomeObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.ui.holder.HomeHeaderHolder;
import com.demo.ctw.ui.holder.HomeItemHolder;

public class HomeAdapter extends HeaderRecyclerViewAdapter {
    private IRecyclerViewClickListener listener;

    public void setOnItemListener(IRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HomeHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_header, parent, false), listener);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new HomeItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeHeaderHolder homeHeaderHolder = (HomeHeaderHolder) holder;
        HomeObject object = (HomeObject) getHeader();
        homeHeaderHolder.fillData(object);
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeItemHolder itemHolder = (HomeItemHolder) holder;
        HomeListObect object = (HomeListObect) getItem(position);
        itemHolder.fillData(object,listener);
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
