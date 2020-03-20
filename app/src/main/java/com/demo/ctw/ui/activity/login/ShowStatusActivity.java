package com.demo.ctw.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 审核状态以及登录状态显示
 */
public class ShowStatusActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.img_status)
    ImageView statusImg;
    @BindView(R.id.txt_status)
    TextView statusTxt;
    @BindView(R.id.txt_info)
    TextView infoTxt;
    @BindView(R.id.txt_know)
    TextView knowTxt;
    private String type;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_show_status);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (type.equals("login")) {
            titleTxt.setText("登录成功");
            statusImg.setImageResource(R.mipmap.ic_success);
            statusTxt.setText("注册成功");
            infoTxt.setText("恭喜您成功注册，为了更好的体验我们的平台，我们建议您去完善资料！");
            knowTxt.setText("完善资料");
        } else {
            titleTxt.setText("注册成功");
            statusImg.setImageResource(R.mipmap.ic_wait);
            statusTxt.setText("等待审核");
            infoTxt.setText("您已提交物业客户审核资料，审核大概需要等待1-2天，审核通过我们会短信提醒您，请耐心等待！");
            knowTxt.setText("知道了");
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("type")) {
            type = extras.getString("type");
        }
    }

    @OnClick({R.id.img_back, R.id.txt_know})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_know:
                if (type.equals("login")) readyGoThenKill(ImprovementInfoActivity.class);
                else readyGoThenKill(LoginActivity.class);
                break;
        }
    }
}
