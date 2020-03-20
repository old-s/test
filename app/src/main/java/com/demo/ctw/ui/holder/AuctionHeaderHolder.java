package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.AuctionInfoObject;
import com.demo.ctw.ui.view.BorderTextView;

import butterknife.BindView;

public class AuctionHeaderHolder extends BaseViewHolder {
    @BindView(R.id.txt_number)
    TextView numberTxt;
    @BindView(R.id.txt_date)
    TextView dateTxt;
    @BindView(R.id.txt_status)
    BorderTextView statusTxt;

    public AuctionHeaderHolder(View view) {
        super(view);
    }


    public void fillData(AuctionInfoObject object) {
        numberTxt.setText("订单编号：" + object.getOrderno());
        dateTxt.setText("下单时间：" + object.getAddTime());
        String status = object.getCompleteStatus();
        switch (status) {
            case "doing":
                statusTxt.setText("进行中");
                statusTxt.setContentColorResource(R.color.C5);
                statusTxt.setTextColor(context.getResources().getColor(R.color.C2));
                break;
            case "lose":
                statusTxt.setText("未中标");
                statusTxt.setContentColorResource(R.color.C5);
                statusTxt.setTextColor(context.getResources().getColor(R.color.C2));
                break;
            case "win":
                statusTxt.setText("已中标");
                statusTxt.setContentColorResource(R.color.app);
                statusTxt.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "nopay":
                statusTxt.setText("未付款");
                statusTxt.setContentColorResource(R.color.C5);
                statusTxt.setTextColor(context.getResources().getColor(R.color.C2));
                break;
        }
    }
}
