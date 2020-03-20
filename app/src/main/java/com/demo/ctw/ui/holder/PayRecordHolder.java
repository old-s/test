package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.PayRecordObject;

import butterknife.BindView;

public class PayRecordHolder extends BaseViewHolder {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_money)
    TextView moneyTxt;
    @BindView(R.id.txt_date)
    TextView dateTxt;

    public PayRecordHolder(View view) {
        super(view);
    }

    public void fillData(PayRecordObject object) {
        if (object.getType().equals("pay")) {
            titleTxt.setText("保证金");
            moneyTxt.setText("-" + object.getPaymoney());
        } else {
            titleTxt.setText("保证金退还");
            moneyTxt.setText("+" + object.getPaymoney());
        }
        dateTxt.setText(object.getCreateTime());
    }
}
