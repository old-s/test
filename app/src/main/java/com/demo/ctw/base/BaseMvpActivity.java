package com.demo.ctw.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by qiu on 2018/9/6.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) mvpPresenter.detachView();
    }
}
