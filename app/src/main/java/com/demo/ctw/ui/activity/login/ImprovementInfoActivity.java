package com.demo.ctw.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 完善资料 -物业
 */
public class ImprovementInfoActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_register_info);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("注册信息");
    }

    @OnClick({R.id.img_back, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_submit:
                CommonUtil.toast("提交");
                break;
        }
    }
}
