package com.demo.ctw.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.OrderOptionObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.ui.adapter.LookPicAdapter;
import com.demo.ctw.ui.view.NoScrollGridView;
import com.demo.ctw.utils.CommonUtil;

public class OrderOptionHolder extends BaseViewHolder {
    public OrderOptionHolder(View view) {
        super(view);
    }

    public void fillFinishData(OrderOptionObject orderOptionObject) {
        TextView timeTxt = itemView.findViewById(R.id.txt_time);
        TextView titleTxt = itemView.findViewById(R.id.txt_title);
        NoScrollGridView gridView = itemView.findViewById(R.id.view_grid);
        TextView introTxt = itemView.findViewById(R.id.txt_intro);
        timeTxt.setText(orderOptionObject.getInstall());
        titleTxt.setText(orderOptionObject.getMark());
        String proveVideo = orderOptionObject.getProveVideo();
        if (!CommonUtil.isEmpty(proveVideo)) {
            int width = context.getResources().getDisplayMetrics().widthPixels - context.getResources().getDimensionPixelSize(R.dimen.dimen_40);
            gridView.setAdapter(new LookPicAdapter(3, width, CommonUtil.stringToList(proveVideo, ",")));
        }
        introTxt.setText(orderOptionObject.getCompanyName() + "  " + orderOptionObject.getRmark());
    }


    public void fillIngData(final OrderOptionObject orderOptionObject, final IRecyclerViewClickListener listener) {
        TextView titleTxt = itemView.findViewById(R.id.txt_title);
        TextView introTxt = itemView.findViewById(R.id.txt_intro);

        titleTxt.setText(orderOptionObject.getCompanyName());
        introTxt.setText(orderOptionObject.getRmark());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClickListener(orderOptionObject, "ing");
            }
        });
    }
}
