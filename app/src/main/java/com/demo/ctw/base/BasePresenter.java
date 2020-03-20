package com.demo.ctw.base;


import com.demo.ctw.rx.ApiService;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qiu on 2018/9/6.
 */

public class BasePresenter<V> {
    public V mvpView;
    private final ApiService apiService;

    public BasePresenter(V mvpView) {
        this.mvpView = mvpView;
        apiService = AppClient.getApiService();
    }

    public void detachView() {
        this.mvpView = null;
    }


    public void addSubscription(Observable observable, ObserverCallBack subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
