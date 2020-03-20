package com.demo.ctw.ui.holder;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.HomeListObect;
import com.demo.ctw.listener.IRecyclerViewClickListener;

import butterknife.BindView;

public class HomeItemHolder extends BaseViewHolder {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_date)
    TextView dateTxt;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_address)
    TextView addressTxt;
    @BindView(R.id.txt_info)
    TextView infoTxt;
    @BindView(R.id.txt_price)
    TextView priceTxt;

    public HomeItemHolder(View itemView) {
        super(itemView);
    }

    public void fillData(final HomeListObect object, final IRecyclerViewClickListener listener) {
        titleTxt.setText(object.getTitle());
        dateTxt.setText(object.getDate());
        Glide.with(context).load(object.getImg()).into(img);
        addressTxt.setText(object.getAddress());
        infoTxt.setText(object.getCount() + "个  |  " + object.getTime() + "月");
        String price = "竞拍价" + object.getPrice() + "万";
        SpannableString spannableString = new SpannableString(price);
        //设置颜色
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.app)), 3, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小，true表示前面的字体大小20单位为dip
        spannableString.setSpan(new AbsoluteSizeSpan(18, true), 3, price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        priceTxt.setText(spannableString);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(object, "item");
            }
        });
    }
}
