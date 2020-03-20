package com.demo.ctw.presenter;

import com.demo.ctw.base.BasePresenter;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.entity.HomeObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import java.util.ArrayList;

public class HomePresenter extends BasePresenter<ILoadView> {
    private ArrayList<HomeItemObject> newsObjects = new ArrayList<>();
    private int PAGESIZE = 20;
    private int offset = 0;
    private boolean isMore = false;


    public HomePresenter(ILoadView mvpView) {
        super(mvpView);
    }

    public boolean isMore() {
        return isMore;
    }

    /**
     * 首页列表
     */
    public void homeList() {
        addSubscription(AppClient.getApiService().homeList(CommonUtil.getRequest(new RequestObject())), new ObserverCallBack<HomeObject>() {
            @Override
            protected void onSuccess(HomeObject response) {
                if (mvpView != null) mvpView.loadData(response, "list");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "list");
            }
        });
    }

    /**
     * 新闻 案例
     */
    public void lists(String type) {
        offset = 0;
        lists(type, offset);
    }

    public void moreLists(String type) {
        offset += PAGESIZE;
        lists(type, offset);
    }

    private void lists(String type, final int offset) {
        RequestObject object = new RequestObject();
        object.setType(type);
        object.setLimit(PAGESIZE + "");
        object.setOffset(offset + "");
        addSubscription(AppClient.getApiService().lists(CommonUtil.getRequest(object)), new ObserverCallBack<ArrayList<HomeItemObject>>() {
            @Override
            protected void onSuccess(ArrayList<HomeItemObject> response) {
                if (offset == 0)
                    newsObjects.clear();
                newsObjects.addAll(response);
                if (mvpView != null) mvpView.loadData(newsObjects, "list");
                if (newsObjects != null) isMore = offset * PAGESIZE == newsObjects.size();
            }

            @Override
            protected void onFail(String msg) {
                mvpView.loadFail(msg, "list");
            }
        });
    }

    /**
     * 合作案例
     */
    public void caseLists() {
        offset = 0;
        caseLists(offset);
    }

    public void moreCaseLists() {
        offset += PAGESIZE;
        caseLists(offset);
    }

    private void caseLists(final int offset) {
        RequestObject object = new RequestObject();
        object.setLimit(PAGESIZE + "");
        object.setOffset(offset + "");
        addSubscription(AppClient.getApiService().caseLists(CommonUtil.getRequest(object)), new ObserverCallBack<ArrayList<HomeItemObject>>() {
            @Override
            protected void onSuccess(ArrayList<HomeItemObject> response) {
                if (offset == 0)
                    newsObjects.clear();
                newsObjects.addAll(response);
                if (mvpView != null) mvpView.loadData(newsObjects, "list");
                if (newsObjects != null) isMore = offset * PAGESIZE == newsObjects.size();
            }

            @Override
            protected void onFail(String msg) {
                mvpView.loadFail(msg, "list");
            }
        });
    }

    /**
     * 新闻公告详情页
     *
     * @param id
     */
    public void itemDetail(String id) {
        final RequestObject object = new RequestObject();
        object.setId(id);
        addSubscription(AppClient.getApiService().itemDetail(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, "detail");
            }

            @Override
            protected void onFail(String msg) {
                mvpView.loadFail(msg, "detail");
            }
        });
    }
}
