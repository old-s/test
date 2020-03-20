package com.demo.ctw.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demo.ctw.R;
import com.demo.ctw.base.HeaderRecyclerViewAdapter;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.ui.holder.CasesHolder;
import com.demo.ctw.ui.holder.NewsHolder;
import com.demo.ctw.ui.holder.NoticeHolder;

public class HomeListAdapter extends HeaderRecyclerViewAdapter {
    private String type;

    public HomeListAdapter(String type) {
        this.type = type;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        if (type.equals("1"))       //新闻
            return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
        else if (type.equals("2"))     //公告
            return new NoticeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false));
        else
            return new CasesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cases, parent, false));
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
        HomeItemObject object = (HomeItemObject) getItem(position);
        if (type.equals("1")) {
            NewsHolder newsHolder = (NewsHolder) holder;
            newsHolder.fillData(object, listener);
        } else if (type.equals("2")) {
            NoticeHolder noticeHolder = (NoticeHolder) holder;
            noticeHolder.fillData(object, listener);
        } else {
            CasesHolder casesHolder = (CasesHolder) holder;
            casesHolder.fillData(object, listener,position);
        }
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
