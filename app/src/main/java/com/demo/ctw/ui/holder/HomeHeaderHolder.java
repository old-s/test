package com.demo.ctw.ui.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseViewHolder;
import com.demo.ctw.entity.HomeObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.ui.view.BannerAdView;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeHeaderHolder extends BaseViewHolder {
    @BindView(R.id.view_banner)
    BannerAdView bannerView;
    @BindView(R.id.view_filpper)
    ViewFlipper filpperView;

    public HomeHeaderHolder(View itemView, IRecyclerViewClickListener listener) {
        super(itemView, listener);
    }


    public void fillData(final HomeObject object) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        bannerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width / 5 * 2));
        bannerView.setAdList(object.getBanner());

        for (int i = 0; i < object.getGgao().size(); i++) {
            View view = View.inflate(context, R.layout.item_filpper, null);
            TextView filpperTxt = view.findViewById(R.id.txt_filpper);
            filpperTxt.setText(object.getGgao().get(i).getTitle());
            final int finalI = i;
            filpperTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClickListener(object.getGgao().get(finalI).getTitle(), "filpper");
                }
            });
            filpperView.addView(view);
        }
    }

    @OnClick({R.id.txt_more, R.id.txt_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_more:
                if (listener != null) listener.onItemClickListener(null, "more");
                break;
            case R.id.txt_check:
                if (listener != null) listener.onItemClickListener(null, "check");
                break;
        }
    }
}
