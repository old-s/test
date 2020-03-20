package com.demo.ctw.ui.activity.auction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付成功
 */
public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_money)
    TextView moneyTxt;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_pay_success);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("支付成功");
    }

    @OnClick({R.id.img_back, R.id.txt_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_home:
                finish();
                break;
        }
    }
}
