package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.AuctionInfoObject;

import butterknife.BindView;

public class AuctionFooterHolder extends BaseViewHolder {
    @BindView(R.id.txt_totalcount)
    TextView totalCountTxt;
    @BindView(R.id.txt_total)
    TextView totalTxt;
    @BindView(R.id.txt_money)
    TextView moneyTxt;

    public AuctionFooterHolder(View view) {
        super(view);
    }

    public void fillData(AuctionInfoObject object) {
        totalCountTxt.setText(object.getNum()+"个");
        totalTxt.setText("¥"+object.getTotalPrice());
        moneyTxt.setText("¥"+object.getCashPledge());
    }
}
