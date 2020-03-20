package com.demo.ctw.pay;

import android.content.Context;

import com.demo.ctw.base.BasePresenter;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;
import com.demo.ctw.utils.CommonUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信支付
 */

public class WechatHelper extends BasePresenter {
    private PayCallBack payCallBack;
    private String outTradeNo;

    public WechatHelper(Object mvpView) {
        super(mvpView);
    }

    private IWXAPI api;

    public WechatHelper(Object mvpView, Context context, PayCallBack payCallBack) {
        super(mvpView);
        api = WXAPIFactory.createWXAPI(context, "wxef7689dc38c0173e");
        this.payCallBack = payCallBack;
    }

    public void wechatInfo(final RequestObject object) {
        addSubscription(AppClient.getApiService().wechatInfo(object), new ObserverCallBack<WechatInfoObject>() {
            @Override
            protected void onSuccess(final WechatInfoObject response) {
                outTradeNo = response.getOutTradeNo();
                PayReq req = new PayReq();
                req.appId = response.getAppId();
                req.partnerId = response.getPartnerId();
                req.prepayId = response.getPrepayId();
                req.nonceStr = response.getNonceStr();
                req.timeStamp = response.getTimeStamp();
                req.packageValue = response.getPackageValue();
                req.sign = response.getSign();
                req.extData = object.getOrderid();
                boolean isSend = api.sendReq(req);
                if (isSend) {
                    payCallBack.onPaySuccess();
                }
            }

            @Override
            protected void onFail(String msg) {
                payCallBack.onPayFailed("", msg);
            }
        });
    }

    private void wechatResult() {
        RequestObject object = new RequestObject();
        object.setOutTradeNo(outTradeNo);
        addSubscription(AppClient.getApiService().wechatResult(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                payCallBack.onPaySuccess();
            }

            @Override
            protected void onFail(String msg) {
                payCallBack.onPayFailed("", msg);
            }
        });
    }
}
