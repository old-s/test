package com.demo.ctw.ui.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.demo.ctw.R;
import com.demo.ctw.entity.AuctionCheckObject;

import java.util.ArrayList;

public class AuctionCheckAdapter extends BaseAdapter {
    private ArrayList<AuctionCheckObject> data;

    public AuctionCheckAdapter(ArrayList<AuctionCheckObject> data) {
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
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_acution_check, null);
            int height = parent.getContext().getResources().getDimensionPixelSize(R.dimen.dimen_30);
            int width = (parent.getContext().getResources().getDisplayMetrics().widthPixels - height * 2) / 4;
            convertView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        }
        final CheckBox infoBtn = convertView.findViewById(R.id.txt_info);
        final AuctionCheckObject object = data.get(position);
        infoBtn.setChecked(object.isCheck());
        infoBtn.setText(object.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setCheck(infoBtn.isChecked());
            }
        });
        return convertView;
    }
}
