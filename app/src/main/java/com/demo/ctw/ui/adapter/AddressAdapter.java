package com.demo.ctw.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.entity.AddressObject;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {
    private ArrayList<AddressObject.Area> data;
    private int pos;

    public void setData(ArrayList<AddressObject.Area> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        }
        TextView titleTxt = convertView.findViewById(R.id.txt);
        titleTxt.setText(data.get(position).getAreaName());
        if (position == pos)
            titleTxt.setTextColor(parent.getContext().getResources().getColor(R.color.app));
        else titleTxt.setTextColor(parent.getContext().getResources().getColor(R.color.C3));
        return convertView;
    }

    public void setClickPos(int pos) {
        this.pos = pos;
    }
}
