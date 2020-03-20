package com.demo.ctw.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.rx.Notice;
import com.demo.ctw.rx.NoticeCode;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 超市状态
 */
public class MarketStatusActivity extends BaseMvpActivity<MinePresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.view_normal)
    RadioButton normalView;
    @BindView(R.id.view_stop)
    RadioButton stopView;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_market_status);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("超市状态");
        String user = SharePrefUtil.getString(this, ShareKey.USERINFO, "");
        UserObject userObject = GsonTools.changeGsonToBean(user, UserObject.class);
        if (userObject.getStatus().equals("1")) {
            normalView.setChecked(true);
            stopView.setChecked(false);
        } else {
            normalView.setChecked(false);
            stopView.setChecked(true);
        }
    }

    @Override
    public void loadData(Object object, String type) {
        showDialogDismiss();
        switch (type) {
            case "1":
                normalView.setChecked(true);
                stopView.setChecked(false);
                break;
            case "2":
                stopView.setChecked(true);
                normalView.setChecked(false);
                break;
        }
        post(new Notice(NoticeCode.CHANGE_MARKET_STATUS));
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.toast(msg);
    }

    @OnClick({R.id.img_back, R.id.layout_normal, R.id.layout_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_normal:
                if (!normalView.isChecked()) {
                    mvpPresenter.changeStatus("1");
                    showDialogLoading();
                }
                break;
            case R.id.layout_stop:
                if (!stopView.isChecked()) {
                    mvpPresenter.changeStatus("2");
                    showDialogLoading();
                }
                break;
        }
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }
}
