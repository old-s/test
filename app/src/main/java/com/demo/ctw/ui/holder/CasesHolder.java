package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.rx.ApiService;

import butterknife.BindView;

public class CasesHolder extends BaseViewHolder {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.img)
    ImageView img;

    public CasesHolder(View view) {
        super(view);
    }

    public void fillData(final HomeItemObject object, final IRecyclerViewClickListener listener, int position) {
        int width = (context.getResources().getDisplayMetrics().widthPixels - context.getResources().getDimensionPixelSize(R.dimen.dimen_30))/2;
        int heigth = width / 2;
        if (position % 2 == 0) img.setPadding(20, 15, 5, 5);
        else img.setPadding(5, 15, 20, 5);
        img.setLayoutParams(new LinearLayout.LayoutParams(width, heigth));

        titleTxt.setText(object.getName());
        Glide.with(context).load(ApiService.API_SERVICE + object.getPic()).apply(new RequestOptions().centerCrop()).into(img);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(object, null);
            }
        });
    }
}
