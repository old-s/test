package com.demo.ctw.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.ctw.R;
import com.demo.ctw.base.HeaderRecyclerViewAdapter;
import com.demo.ctw.entity.AuctionRecordObject;
import com.demo.ctw.ui.holder.AuctionRecordHolder;

public class AuctionRecordAdapter extends HeaderRecyclerViewAdapter {
    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new AuctionRecordHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_record, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        AuctionRecordObject object = (AuctionRecordObject) getItem(position);
        AuctionRecordHolder recordHolder = (AuctionRecordHolder) holder;
        recordHolder.fillData(object,listener);
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
