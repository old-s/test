package com.demo.ctw.ui.fragment;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;

/**
 * Created by qiu on 2018/9/7.
 */

public class TestFragment extends BaseFragment {
    @BindView(R.id.layout)
    SmartRefreshLayout layout;
    @BindView(R.id.img)
    ImageView img;

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_test;
    }

    @Override
    protected void processLogic() {
        Glide.with(getContext())
                .load("https://thumbnail0.baidupcs.com/thumbnail/7e3f9c4e43c9804e647a29fc985f4f85?fid=3930834251-250528-143385285653967&time=1536303600&rt=sh&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-FpIUopR5t2SfKyKXs9V6JS5HtyA%3D&expires=8h&chkv=0&chkbd=0&chkpc=&dp-logid=5789844363364873920&dp-callid=0&size=c710_u400&quality=100&vuk=-&ft=video")
                .into(img);
        layout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }
}
