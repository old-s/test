package com.demo.ctw.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpFragment;
import com.demo.ctw.entity.AuctionRecordObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.ui.activity.auction.AuctionDetailActivity;
import com.demo.ctw.ui.adapter.AuctionRecordAdapter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 竞拍记录
 */
public class AuctionRecordFragment extends BaseMvpFragment<MinePresenter> implements ILoadView {
    @BindView(R.id.layout)
    SmartRefreshLayout layout;
    @BindView(R.id.list)
    RecyclerView list;
    private AuctionRecordAdapter adapter = new AuctionRecordAdapter();
    private String statusType;

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_auction_record;
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        layout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mvpPresenter.isMore()) mvpPresenter.moreAuctionRecords(statusType);
                else layout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.auctionRecords(statusType);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);
        mvpPresenter.auctionRecords(statusType);
        showLoading();
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {
        super.getBundleExtras(bundle);
        if (bundle.containsKey("type")) {
            statusType = bundle.getString("type");
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnItemClicktListener(new IRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(Object object, String type) {
                AuctionRecordObject recordObject = (AuctionRecordObject) object;
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", recordObject.getOrderno());
                readyGo(AuctionDetailActivity.class, bundle);
            }
        });
    }

    @Override
    public void loadData(Object object, String type) {
        CommonUtil.endRefresh(layout);
        ArrayList<AuctionRecordObject> data = (ArrayList<AuctionRecordObject>) object;
        if (data.size() == 0 || data == null) showEmpty();
        else hideLoading();
        adapter.setItems(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg, final String type) {
        CommonUtil.endRefresh(layout);
        CommonUtil.toast(msg);
        showError(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.auctionRecords(statusType);
            }
        });
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return list;
    }
}
