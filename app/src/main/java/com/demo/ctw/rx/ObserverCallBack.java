package com.demo.ctw.rx;

import android.content.Intent;

import com.demo.ctw.base.ActivityManager;
import com.demo.ctw.base.BaseApplication;
import com.demo.ctw.ui.activity.login.LoginActivity;
import com.demo.ctw.utils.CommonUtil;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by qiu on 2018/9/6.
 */

public abstract class ObserverCallBack<T> implements Observer<ResultResponse<T>> {
    private String error;
    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onError(final Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            error = "网络连接超时，请重试";
        } else if (e instanceof SocketException) {
            error = "网络未连接，请连接网络";
        } else {
            error = "数据请求出错，请重试";
        }
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        onFail(error);
    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void onNext(ResultResponse<T> response) {
        if (response.status == 200) {
            onSuccess(response.object);
        } else if (response.status == 700) {
            CommonUtil.toast("token失效，请重新登录");
            Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.getInstance().startActivity(intent);
            ActivityManager.getInstance().exit();
        } else {
            onFail(response.message);
        }
    }

    protected abstract void onSuccess(T response);

    protected abstract void onFail(String msg);
}
