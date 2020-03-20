package com.demo.ctw.base;

/**
 * Created by qiu on 2018/9/6.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    protected void processLogic() {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
    }

    @Override
    protected void onFirstUserVisible() {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null)
            mvpPresenter.detachView();
    }
}
