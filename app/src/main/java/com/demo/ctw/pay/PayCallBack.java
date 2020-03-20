package com.demo.ctw.pay;

public interface PayCallBack {

    void onPaySuccess();

    void onPayFailed(String resultCode, String resultInfo);
}
