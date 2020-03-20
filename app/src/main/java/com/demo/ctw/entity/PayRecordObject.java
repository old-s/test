package com.demo.ctw.entity;

public class PayRecordObject {
    /**
     * status 交易状态：0交易生成，1交易成功, 2交易失败
     * paytype 支付方式0未选择  1支付宝2 微信
     * type 类型pay缴费 ref退款
     */
    private String paymoney;
    private String createTime;
    private String type;

    public String getPaymoney() {
        return paymoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getType() {
        return type;
    }
}
