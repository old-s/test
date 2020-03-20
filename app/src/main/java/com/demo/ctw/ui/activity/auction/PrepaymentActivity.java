package com.demo.ctw.ui.activity.auction;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.pay.AlipayHelper;
import com.demo.ctw.pay.PayCallBack;
import com.demo.ctw.pay.WechatHelper;
import com.demo.ctw.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付诚意金
 */
public class PrepaymentActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_money)
    TextView moneyTxt;
    @BindView(R.id.txt_detail)
    TextView detailTxt;
    @BindView(R.id.view_wechat)
    RadioButton wechatView;
    @BindView(R.id.view_alipay)
    RadioButton alipayView;
    @BindView(R.id.txt_intro)
    TextView introTxt;
    private String price, content;


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_prepayment);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("支付诚意金");
        moneyTxt.setText(Html.fromHtml("¥<font><big><big>" + price + "</big></big></font>"));
        detailTxt.setText(content);
        String info = "本次拍卖需缴纳诚意金<font color='#016bfa'>" + price + "</font>元";
        introTxt.setText(Html.fromHtml(info));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("price")) {
            price = extras.getString("price");
        }
        if (extras.containsKey("content"))
            content = extras.getString("content");
    }

    @OnClick({R.id.img_back, R.id.layout_wechat, R.id.layout_alipay, R.id.txt_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_wechat:
                if (wechatView.isChecked()) {
                    wechatView.setChecked(false);
                } else {
                    wechatView.setChecked(true);
                    alipayView.setChecked(false);
                }
                break;
            case R.id.layout_alipay:
                if (alipayView.isChecked()) {
                    alipayView.setChecked(false);
                } else {
                    alipayView.setChecked(true);
                    wechatView.setChecked(false);
                }
                break;
            case R.id.txt_pay:
                RequestObject object = new RequestObject();
                if (wechatView.isChecked()) {
                    alipay(object);
                } else if (alipayView.isChecked()) {
                    wechatPay(object);
                } else {
                    CommonUtil.toast("请选择支付方式");
                }
                break;
        }
    }

    /**
     * 支付宝支付
     *
     * @param object
     */
    private void alipay(RequestObject object) {
        AlipayHelper helper = new AlipayHelper(this, new PayCallBack() {
            @Override
            public void onPaySuccess() {
                CommonUtil.toast("支付成功");
                readyGoThenKill(PaySuccessActivity.class);
            }

            @Override
            public void onPayFailed(String resultCode, String resultInfo) {
                CommonUtil.toast(resultInfo);
            }
        });
        helper.alipayInfo(object, this);
    }

    /**
     * 微信支付
     *
     * @param object
     */
    private void wechatPay(RequestObject object) {
        WechatHelper helper = new WechatHelper(this, this, new PayCallBack() {
            @Override
            public void onPaySuccess() {
                CommonUtil.toast("支付成功");
                readyGoThenKill(PaySuccessActivity.class);
            }

            @Override
            public void onPayFailed(String resultCode, String resultInfo) {
                CommonUtil.toast(resultInfo);
            }
        });
        helper.wechatInfo(object);
    }
}
