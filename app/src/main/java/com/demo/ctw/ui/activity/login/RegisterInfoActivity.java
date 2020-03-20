package com.demo.ctw.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册信息
 */
public class RegisterInfoActivity extends BaseActivity {
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

    @OnClick({R.id.img_back, R.id.layout_yyzz, R.id.layout_sfz, R.id.layout_khh, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_yyzz:
                Bundle bundle = new Bundle();
                bundle.putString("type", "yyzz");
                readyGo(UploadImgActivity.class, bundle);
                break;
            case R.id.layout_sfz:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", "sfz");
                readyGo(UploadImgActivity.class, bundle1);
                break;
            case R.id.layout_khh:

                break;
            case R.id.txt_submit:
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", "info");
                readyGoThenKill(ShowStatusActivity.class, bundle2);
                break;
        }
    }
}
