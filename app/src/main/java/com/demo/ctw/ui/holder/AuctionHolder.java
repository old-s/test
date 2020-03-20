package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.AuctionObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;

import butterknife.BindView;

public class AuctionHolder extends BaseViewHolder {
    @BindView(R.id.view_check)
    RadioButton checkView;
    @BindView(R.id.txt_name)
    TextView nameTxt;
    @BindView(R.id.txt_address)
    TextView addressTxt;
    @BindView(R.id.txt_number)
    TextView numberTxt;

    public AuctionHolder(View itemView) {
        super(itemView);
    }

    public void fillData(final AuctionObject object, final IRecyclerViewClickListener listener) {
        object.setCheck(true);
        checkView.setChecked(object.isCheck());
        nameTxt.setText(object.getCompanyName());
        addressTxt.setText(object.getAddress());
        numberTxt.setText(object.getShopcart());
        checkView.setChecked(true);

//        checkView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkView.setChecked(!object.isCheck());
//                object.setCheck(checkView.isChecked());
//            }
//        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(object, "item");
            }
        });
    }
}
