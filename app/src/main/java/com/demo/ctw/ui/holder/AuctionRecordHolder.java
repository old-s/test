package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.AuctionRecordObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.ui.view.BorderTextView;

import butterknife.BindView;

public class AuctionRecordHolder extends BaseViewHolder {
    @BindView(R.id.txt_status1)
    TextView status1Txt;
    @BindView(R.id.txt_date)
    TextView dateTxt;
    @BindView(R.id.txt_status2)
    BorderTextView status2Txt;
    @BindView(R.id.txt_title)
    TextView titleTXt;
    @BindView(R.id.txt_money)
    TextView moneyTxt;
    @BindView(R.id.txt_price)
    TextView priceTxt;

    public AuctionRecordHolder(View view) {
        super(view);
    }

    public void fillData(final AuctionRecordObject object, final IRecyclerViewClickListener listener) {
        String status = object.getCompleteStatus();
        switch (status) {
            case "doing":
                status1Txt.setText("进行中");
                status2Txt.setText("进行中");
                status2Txt.setContentColorResource(R.color.C5);
                status2Txt.setTextColor(context.getResources().getColor(R.color.C2));
                break;
            case "lose":
                status1Txt.setText("已结束");
                status2Txt.setText("未中标");
                status2Txt.setContentColorResource(R.color.C5);
                status2Txt.setTextColor(context.getResources().getColor(R.color.C2));
                break;
            case "win":
                status1Txt.setText("已结束");
                status2Txt.setText("已中标");
                status2Txt.setContentColorResource(R.color.app);
                status2Txt.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "nopay":
                status1Txt.setText("进行中");
                status2Txt.setText("未付款");
                status2Txt.setContentColorResource(R.color.C5);
                status2Txt.setTextColor(context.getResources().getColor(R.color.C2));
                break;
        }

        dateTxt.setText("(" + object.getEndTime() + ")");
        titleTXt.setText(object.getName());
        moneyTxt.setText(object.getPrice());
        priceTxt.setText("（押金¥" + object.getCashPledge() + "）");

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(object, "item");
            }
        });
    }
}
