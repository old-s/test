package com.demo.ctw.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.demo.ctw.R;
import com.demo.ctw.ui.view.BorderTextView;
import com.demo.ctw.ui.view.flowlayout.FlowObject;

import java.util.ArrayList;

public class GridCheckAdapter extends BaseAdapter {
    private ArrayList<FlowObject> data;

    public void setData(ArrayList<FlowObject> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data == null ? 0 : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_check, parent, false);
        BorderTextView nameTxt = convertView.findViewById(R.id.txt_name);
        ImageView img = convertView.findViewById(R.id.img_check);
        final FlowObject flowObject = data.get(position);
        nameTxt.setText(flowObject.getName());
        if (flowObject.isCheck()) {
            nameTxt.setStrokeColor(R.color.app);
            nameTxt.setTextColor(parent.getResources().getColor(R.color.app));
            img.setVisibility(View.VISIBLE);
        } else {
            nameTxt.setStrokeColor(R.color.C5);
            nameTxt.setStrokeColor(parent.getResources().getColor(R.color.C4));
            nameTxt.setTextColor(parent.getResources().getColor(R.color.C2));
            img.setVisibility(View.GONE);
        }
        return convertView;
    }
}
