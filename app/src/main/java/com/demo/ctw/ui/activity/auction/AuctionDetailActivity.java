package com.demo.ctw.ui.activity.auction;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.AuctionDetailObject;
import com.demo.ctw.entity.AuctionInfoObject;
import com.demo.ctw.presenter.AuctionPresenter;
import com.demo.ctw.ui.adapter.AuctionDetailAdapter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 竞拍详情
 */
public class AuctionDetailActivity extends BaseMvpActivity<AuctionPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.txt_pay)
    TextView payTxt;
    private AuctionDetailAdapter adapter = new AuctionDetailAdapter();
    private String orderNo;
    private AuctionInfoObject infoObject;

    @Override
    protected AuctionPresenter createPresenter() {
        return new AuctionPresenter(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("订单详情");
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        if (CommonUtil.isEmpty(orderNo)) {
            finish();
            return;
        }
        mvpPresenter.auctionDetail(orderNo);
        showLoading();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("orderNo")) {
            orderNo = extras.getString("orderNo");
        }
    }

    @Override
    public void loadData(Object object, String type) {
        hideLoading();
        AuctionDetailObject detailObject = (AuctionDetailObject) object;
        infoObject = detailObject.getModel();
        if (infoObject.getCompleteStatus().equals("nopay"))
            payTxt.setVisibility(View.VISIBLE);
        else payTxt.setVisibility(View.GONE);
        adapter.setHeader(detailObject.getModel());
        adapter.setItems(detailObject.getDlist());
        adapter.setFooter(detailObject.getModel());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg, String type) {
        CommonUtil.toast(msg);
        finish();
    }

    @OnClick({R.id.img_back, R.id.txt_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_pay:
                Bundle bundle = new Bundle();
                bundle.putString("price", infoObject.getTotalPrice());
                bundle.putString("content", infoObject.getNum() + "个");
                readyGo(PrepaymentActivity.class, bundle);
                break;
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return list;
    }
}
