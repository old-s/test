package com.demo.ctw.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.ctw.R;
import com.demo.ctw.base.HeaderRecyclerViewAdapter;
import com.demo.ctw.entity.OrderOptionObject;
import com.demo.ctw.ui.holder.OrderOptionHolder;

public class OrderOptionAdapter extends HeaderRecyclerViewAdapter {
    private String status;

    public OrderOptionAdapter(String status) {
        this.status = status;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        if (status.equals("0")) return new OrderOptionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option_finish,parent,false));
        else return new OrderOptionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option_ing,parent,false));
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
        OrderOptionHolder orderOptionHolder = (OrderOptionHolder) holder;
        OrderOptionObject orderOptionObject = (OrderOptionObject) getItem(position);
        if (status.equals("0")){
            orderOptionHolder.fillFinishData(orderOptionObject);
        }else {
            orderOptionHolder.fillIngData(orderOptionObject,listener);
        }
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
