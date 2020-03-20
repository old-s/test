package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.rx.ApiService;

import butterknife.BindView;

public class NewsHolder extends BaseViewHolder {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_info)
    TextView infoTxt;
    @BindView(R.id.txt_time)
    TextView timeTxt;
    @BindView(R.id.img)
    ImageView img;

    public NewsHolder(View view) {
        super(view);
    }

    public void fillData(final HomeItemObject object, final IRecyclerViewClickListener listener) {
        titleTxt.setText(object.getTitle());
        infoTxt.setText(object.getMark());
        timeTxt.setText(object.getCreateTime());
        Glide.with(context).load(ApiService.API_SERVICE + object.getImgs()).apply(new RequestOptions().centerCrop()).into(img);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(object, null);
            }
        });
    }
}
