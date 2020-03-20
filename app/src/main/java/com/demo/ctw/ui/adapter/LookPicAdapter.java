package com.demo.ctw.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.ui.activity.LookPicActivity;

import java.util.ArrayList;

public class LookPicAdapter extends BaseAdapter {
    private int count, width;
    private ArrayList<String> imgs;

    public LookPicAdapter(int count, int width, ArrayList<String> imgs) {
        this.count = count;
        this.width = width;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs == null ? 0 : imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs == null ? null : imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return imgs == null ? null : position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic, parent, false);
            int mWidth = width / count;
            convertView.setLayoutParams(new ViewGroup.LayoutParams(mWidth, mWidth / 3 * 2));
        }
        ImageView pic = convertView.findViewById(R.id.img);
        Glide.with(parent.getContext()).load(ApiService.API_SERVICE + imgs.get(position)).into(pic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("imgUrl", imgs.get(position));
                Intent intent = new Intent(parent.getContext(), LookPicActivity.class);
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
