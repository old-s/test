package com.demo.ctw.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.ctw.R;
import com.demo.ctw.base.HeaderRecyclerViewAdapter;
import com.demo.ctw.entity.AuctionInfoObject;
import com.demo.ctw.entity.AuctionRecordObject;
import com.demo.ctw.ui.holder.AuctionFooterHolder;
import com.demo.ctw.ui.holder.AuctionHeaderHolder;
import com.demo.ctw.ui.holder.AuctionItemHolder;

public class AuctionDetailAdapter extends HeaderRecyclerViewAdapter {
    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new AuctionHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new AuctionItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return new AuctionFooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction_footer, parent, false));
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        AuctionInfoObject object = (AuctionInfoObject) getHeader();
        AuctionHeaderHolder headerHolder = (AuctionHeaderHolder) holder;
        headerHolder.fillData(object);
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        AuctionRecordObject object = (AuctionRecordObject) getItem(position);
        AuctionItemHolder auctionItemHolder = (AuctionItemHolder) holder;
        auctionItemHolder.fillData(object);
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        AuctionInfoObject object = (AuctionInfoObject) getFooter();
        AuctionFooterHolder footerHolder = (AuctionFooterHolder) holder;
        footerHolder.fillData(object);
    }
}
