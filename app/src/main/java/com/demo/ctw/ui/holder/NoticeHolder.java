package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;

import butterknife.BindView;

public class NoticeHolder extends BaseViewHolder {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_time)
    TextView timeTxt;

    public NoticeHolder(View view) {
        super(view);
    }

    public void fillData(final HomeItemObject object, final IRecyclerViewClickListener listener) {
        titleTxt.setText(object.getTitle());
        timeTxt.setText(object.getCreateTime());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(object, null);
            }
        });
    }
}
