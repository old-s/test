package com.demo.ctw.presenter;

import com.demo.ctw.base.BasePresenter;
import com.demo.ctw.entity.AuctionDetailObject;
import com.demo.ctw.entity.AuctionObject;
import com.demo.ctw.entity.BrandObject;
import com.demo.ctw.entity.MarketObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.entity.SincerityMoneyObject;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import java.util.ArrayList;

public class AuctionPresenter extends BasePresenter<ILoadView> {

    public AuctionPresenter(ILoadView mvpView) {
        super(mvpView);
    }

    /**
     * 竞拍 超市列表
     *
     * @param region
     * @param brank
     * @param comType
     */
    public void auctionList(final String region, String brank, String comType) {
        RequestObject object = new RequestObject();
        object.setRegion(region);
        object.setBrank(brank);
        object.setComType(comType);
        addSubscription(AppClient.getApiService().auctionList(CommonUtil.getRequest(object)), new ObserverCallBack<ArrayList<AuctionObject>>() {
            @Override
            protected void onSuccess(ArrayList<AuctionObject> response) {
                if (mvpView != null) mvpView.loadData(response, "list");
            }

            @Override
            protected void onFail(String msg) {
                mvpView.loadFail(msg, "list");
            }
        });
    }

    /**
     * 超市品牌
     */
    public void brandList() {
        addSubscription(AppClient.getApiService().brandList(), new ObserverCallBack<ArrayList<BrandObject>>() {
            @Override
            protected void onSuccess(ArrayList<BrandObject> response) {
                if (mvpView != null) mvpView.loadData(response, "brand");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "brand");
            }
        });
    }

    /**
     * 超市详情
     *
     * @param id
     */
    public void marketDetail(String id) {
        RequestObject object = new RequestObject();
        object.setId(id);
        addSubscription(AppClient.getApiService().marketDetail(CommonUtil.getRequest(object)), new ObserverCallBack<MarketObject>() {
            @Override
            protected void onSuccess(MarketObject response) {
                if (mvpView != null) mvpView.loadData(response, "detail");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "detail");
            }
        });
    }

    /**
     * 诚意金
     *
     * @param ids
     */
    public void sincerityMoney(String ids) {
        RequestObject object = new RequestObject();
        object.setSpaceId(ids);
        addSubscription(AppClient.getApiService().sincerityMoney(CommonUtil.getRequest(object)), new ObserverCallBack<SincerityMoneyObject>() {
            @Override
            protected void onSuccess(SincerityMoneyObject response) {
                if (mvpView != null) mvpView.loadData(response, "sincerity");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "sincerity");
            }
        });
    }

    /**
     * 提交订单
     *
     * @param object
     */
    public void submitOrder(RequestObject object) {
        addSubscription(AppClient.getApiService().submitOrder(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, "submit");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "submit");
            }
        });
    }

    /**
     * 订单详情
     *
     * @param id
     */
    public void auctionDetail(String id) {
        RequestObject object = new RequestObject();
        object.setOrderno(id);
        addSubscription(AppClient.getApiService().orderDetail(CommonUtil.getRequest(object)), new ObserverCallBack<AuctionDetailObject>() {
            @Override
            protected void onSuccess(AuctionDetailObject response) {
                if (mvpView != null) mvpView.loadData(response, "detail");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "detail");
            }
        });
    }
}
