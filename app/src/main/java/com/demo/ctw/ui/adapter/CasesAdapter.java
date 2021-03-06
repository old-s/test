package com.demo.ctw.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ctw.R;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.rx.ApiService;

import java.util.ArrayList;

public class CasesAdapter extends BaseAdapter {
    private ArrayList<HomeItemObject> data = new ArrayList<>();

    public void setData(ArrayList<HomeItemObject> data) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cases, parent, false);
        }
        TextView titleTxt = convertView.findViewById(R.id.txt_title);
        ImageView img = convertView.findViewById(R.id.img);
        int width = (parent.getContext().getResources().getDisplayMetrics().widthPixels - parent.getContext().getResources().getDimensionPixelSize(R.dimen.dimen_40)) / 2;
        int heigth = width / 2;
        img.setLayoutParams(new LinearLayout.LayoutParams(width, heigth));

        HomeItemObject object = data.get(position);
        titleTxt.setText(object.getName());
        Glide.with(parent.getContext()).load(ApiService.API_SERVICE + object.getPic()).apply(new RequestOptions().centerCrop()).into(img);
        return convertView;
    }
}