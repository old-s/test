package com.demo.ctw.view;

/**
 * Created by qiu on 2018/9/6.
 */

public interface ILoadView {
    void loadData(Object object, String type);

    void loadFail(String msg, String type);
}
