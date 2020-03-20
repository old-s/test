package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.AuctionRecordObject;

import butterknife.BindView;

public class AuctionItemHolder extends BaseViewHolder {
    @BindView(R.id.txt_name)
    TextView nameTxt;
    @BindView(R.id.txt_count)
    TextView countTxt;

    public AuctionItemHolder(View view) {
        super(view);
    }

    public void fillData(AuctionRecordObject object) {
        nameTxt.setText(object.getName());
    }
}
