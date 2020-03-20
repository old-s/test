package com.demo.ctw.pay;

public class WechatInfoObject {
    private String timeStamp;
    private String appId;
    private String outTradeNo;
    private String sign;
    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String spackage;
    private String packageValue;

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getAppId() {
        return appId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getSign() {
        return sign;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSpackage() {
        return spackage;
    }

    public String getPackageValue() {
        return packageValue;
    }
}
