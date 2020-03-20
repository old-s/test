package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.MessageObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.utils.TestData;


import butterknife.BindView;

public class MessageHolder extends BaseViewHolder {
    @BindView(R.id.txt_time)
    TextView timeTxt;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_type)
    TextView typeTxt;
    @BindView(R.id.txt_info)
    TextView infoTxt;

    public MessageHolder(View view) {
        super(view);
    }

    public void fillData(final MessageObject object, final IRecyclerViewClickListener listener) {
        timeTxt.setText(object.getCreatetime());
        Glide.with(context).load(TestData.imgurl2)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).centerCrop())
                .into(img);
        typeTxt.setText("竞标消息");
        infoTxt.setText(object.getContent());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(object, "");
            }
        });
    }
}
