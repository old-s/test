package com.demo.ctw.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpFragment;
import com.demo.ctw.entity.OrderOptionObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.rx.Notice;
import com.demo.ctw.rx.NoticeCode;
import com.demo.ctw.ui.activity.mine.OrderResponseActivity;
import com.demo.ctw.ui.adapter.OrderOptionAdapter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 订单反馈
 */
public class OrderListFragment extends BaseMvpFragment<MinePresenter> implements ILoadView {
    @BindView(R.id.layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.list)
    RecyclerView list;
    private OrderOptionAdapter adapter;
    private String status;

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_bga;
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mvpPresenter.isMore()) mvpPresenter.moreOrderOption(status);
                else refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.orderOption(status);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderOptionAdapter(status);
        list.setAdapter(adapter);

        mvpPresenter.orderOption(status);
        showLoading();
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {
        super.getBundleExtras(bundle);
        if (bundle.containsKey("status")) {
            status = bundle.getString("status");
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnItemClicktListener(new IRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(Object object, String type) {
                OrderOptionObject optionObject = (OrderOptionObject) object;
                Bundle bundle = new Bundle();
                bundle.putString("id", optionObject.getId());
                readyGo(OrderResponseActivity.class, bundle);
            }
        });
    }

    @Override
    public void loadData(Object object, String type) {
        CommonUtil.endRefresh(refreshLayout);
        ArrayList<OrderOptionObject> data = (ArrayList<OrderOptionObject>) object;
        if (data.size() == 0 || data == null) showEmpty();
        else hideLoading();
        adapter.setItems(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg, String type) {
        CommonUtil.endRefresh(refreshLayout);
        showError(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.orderOption(status);
                showLoading();
            }
        });
    }

    @Override
    protected boolean isBindRxBusHere() {
        return true;
    }

    @Override
    protected void dealRxbus(Notice notice) {
        super.dealRxbus(notice);
        if (notice.type == NoticeCode.ORDER_RESPONSE) {
            refreshLayout.autoRefresh();
        }
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
