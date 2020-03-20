package com.demo.ctw.ui.view.flowlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.demo.ctw.R;
import com.demo.ctw.ui.view.BorderTextView;

import java.util.ArrayList;

public class FlowAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FlowObject> mList;

    public FlowAdapter(Context context, ArrayList<FlowObject> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public FlowObject getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_flow, null);
            holder = new ViewHolder();
            holder.tagTxt = convertView.findViewById(R.id.txt_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String text = getItem(position).getName();
        holder.tagTxt.setText(text);
        Boolean state = getItem(position).isCheck();
        if (state) {
            holder.tagTxt.setContentColorResource(R.color.app);
            holder.tagTxt.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.tagTxt.setTextColor(mContext.getResources().getColor(R.color.C3));
        }
        return convertView;
    }

    static class ViewHolder {
        BorderTextView tagTxt;
    }
}