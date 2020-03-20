package com.demo.ctw.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.presenter.HomePresenter;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.ui.activity.WebActivity;
import com.demo.ctw.ui.adapter.HomeListAdapter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新闻
 */
public class HomeListActivity extends BaseMvpActivity<HomePresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.list)
    RecyclerView list;
    private HomeListAdapter adapter;
    private String type, title;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_bga);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText(title);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mvpPresenter.isMore()) {
                    if (type.equals("3")) mvpPresenter.caseLists();
                    else mvpPresenter.moreLists(type);
                } else refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (type.equals("3")) mvpPresenter.caseLists();
                else mvpPresenter.lists(type);
            }
        });
        if (type.equals("3")) list.setLayoutManager(new GridLayoutManager(this, 2));
        else list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeListAdapter(type);
        list.setAdapter(adapter);
        if (type.equals("3")) mvpPresenter.caseLists();
        else mvpPresenter.lists(type);
        showLoading();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("title"))
            title = extras.getString("title");
        if (extras.containsKey("type"))
            type = extras.getString("type");
    }

    @Override
    public void loadData(Object object, String type) {
        CommonUtil.endRefresh(refreshLayout);
        ArrayList<HomeItemObject> objects = (ArrayList<HomeItemObject>) object;
        if (objects.size() == 0 || objects == null) showEmpty();
        else hideLoading();
        adapter.setItems(objects);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void loadFail(String msg, final String type) {
        CommonUtil.endRefresh(refreshLayout);
        showError(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.lists(type);
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnItemClicktListener(new IRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(Object object, String type1) {
                HomeItemObject itemObject = (HomeItemObject) object;
                if (type.equals("3"))
                    CommonUtil.toast(itemObject.getName());
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", ApiService.API_SERVICE_URL + itemObject.getId());
                    bundle.putString("title", itemObject.getTitle());
                    readyGo(WebActivity.class, bundle);
                }
            }
        });
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }

    @Override
    protected View getLoadingTargetView() {
        return list;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }
}
