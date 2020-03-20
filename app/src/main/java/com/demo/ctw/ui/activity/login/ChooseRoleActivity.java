package com.demo.ctw.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.entity.RegisterObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择身份
 */
public class ChooseRoleActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
//    @BindView(R.id.btn_cs)
//    RadioButton csBtn;
//    @BindView(R.id.btn_cszb)
//    RadioButton cszbBtn;

    @BindView(R.id.view_cs)
    CheckBox csView;
    @BindView(R.id.view_cszb)
    CheckBox cszbView;
    @BindView(R.id.view_ggz)
    CheckBox ggzView;

    private RegisterObject object;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_choose_role);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("选择角色");
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("info"))
            object = extras.getParcelable("info");
    }

    @OnClick({R.id.img_back, R.id.layout_cs, R.id.layout_cszb, R.id.layout_ggz, R.id.txt_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_cs:
                if (!csView.isChecked()) {
                    csView.setChecked(true);
                    cszbView.setChecked(false);
                    ggzView.setChecked(false);
                }
                break;
            case R.id.layout_cszb:
                if (!cszbView.isChecked()) {
                    csView.setChecked(false);
                    cszbView.setChecked(true);
                    ggzView.setChecked(false);
                }
                break;
            case R.id.layout_ggz:
                if (!ggzView.isChecked()) {
                    csView.setChecked(false);
                    cszbView.setChecked(false);
                    ggzView.setChecked(true);
                }
                break;
            case R.id.txt_next:
                Bundle bundle = new Bundle();
                bundle.putParcelable("info", object);
                if (csView.isChecked()) {
                    bundle.putString("type", "1");
                    readyGo(RegisterMarketActivity.class, bundle);
                } else if (cszbView.isChecked()) {
                    bundle.putString("type", "3");
                    readyGo(RegisterMarketActivity.class, bundle);
                } else {
                    bundle.putString("type", "2");
                    readyGo(RegisterAdActivity.class, bundle);
                }
//                if (csBtn.isChecked()) {
//                    bundle.putString("type", "1");
//                    readyGo(RegisterMarketActivity.class, bundle);
//                } else if (cszbBtn.isChecked()) {
//                    bundle.putString("type", "3");
//                    readyGo(RegisterMarketActivity.class, bundle);
//                } else {
//                    bundle.putString("type", "2");
//                    readyGo(RegisterAdActivity.class, bundle);
//                }
                break;
        }
    }
}
