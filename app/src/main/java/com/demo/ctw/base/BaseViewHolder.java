package com.demo.ctw.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.ctw.listener.IRecyclerViewClickListener;

import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public Context context;
    public IRecyclerViewClickListener listener;

    public BaseViewHolder(View itemView, IRecyclerViewClickListener listener) {
        super(itemView);
        context = itemView.getContext();
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(View view) {
        super(view);
        context = itemView.getContext();
        ButterKnife.bind(this, view);
    }
}
