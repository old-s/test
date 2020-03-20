package com.demo.ctw.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.demo.ctw.base.BasePresenter;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;
import com.demo.ctw.utils.CommonUtil;

import java.util.Map;

/**
 * @description 支付宝支付
 */
public class AlipayHelper extends BasePresenter {
    private static final int ALIPAY_REPLY = 0;
    private PayCallBack payCallBack;
    private String outTradeNo;

    public AlipayHelper(Object mvpView, PayCallBack payCallBack) {
        super(mvpView);
        this.payCallBack = payCallBack;
    }

    public void alipayInfo(RequestObject object, final Activity activity) {
        addSubscription(AppClient.getApiService().payInfo(object), new ObserverCallBack<AlipyInfoObject>() {
            @Override
            protected void onSuccess(final AlipyInfoObject response) {
                outTradeNo = response.getOutTradeNo();
                //开启线程发送支付请求
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask payTask = new PayTask(activity);
                        Map<String, String> result = payTask.payV2(response.getOrderString(), true);
                        handler.obtainMessage(ALIPAY_REPLY, result).sendToTarget();
                    }
                };
                new Thread(payRunnable).start();
            }

            @Override
            protected void onFail(String msg) {
                payCallBack.onPayFailed("", msg);
            }
        });
    }

    private void alipyResult() {
        RequestObject object = new RequestObject();
        object.setOutTradeNo(outTradeNo);
        addSubscription(AppClient.getApiService().alipayResult(CommonUtil.getRequest(object)), new ObserverCallBack() {
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

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            Log.i("data", "msg         " + msg.what);
            switch (msg.what) {
                case ALIPAY_REPLY:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        payCallBack.onPaySuccess();
//                        alipyResult();
                    } else {
                        Log.i("data", "sta        " + resultStatus);
                        if (TextUtils.equals(resultStatus, "8000")) {
                            payCallBack.onPayFailed("", "订单正在处理中");
                        } else if (TextUtils.equals(resultStatus, "4000")) {
                            payCallBack.onPayFailed("", "订单支付失败");
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            payCallBack.onPayFailed("", "用户中途取消");
                        } else if (TextUtils.equals(resultStatus, "6002")) {
                            payCallBack.onPayFailed("", "网络连接出错");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            payCallBack.onPayFailed(resultStatus, resultInfo);
                        }
                    }
                    break;
            }
        }
    };
}
