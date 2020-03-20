package com.demo.ctw.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.demo.ctw.R;
import com.demo.ctw.base.ActivityManager;
import com.demo.ctw.base.BaseApplication;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.interfaces.IDialogClickInterface;
import com.demo.ctw.presenter.LoginPresenter;
import com.demo.ctw.ui.activity.login.LoginActivity;
import com.demo.ctw.ui.dialog.ConfirmCancelDialog;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.DataClearManager;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 */
public class SetActivity extends BaseMvpActivity<LoginPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_clear)
    TextView clearTxt;
    @BindView(R.id.layout_status)
    LinearLayout statusLayout;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_set);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("设置");
        if (CommonUtil.isAd()) statusLayout.setVisibility(View.GONE);
        else statusLayout.setVisibility(View.VISIBLE);
        clearTxt.setText(DataClearManager.getTotalCacheSize(this));
    }

    @OnClick({R.id.img_back, R.id.layout_status, R.id.layout_change, R.id.layout_clear, R.id.layout_about, R.id.txt_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_status:
                readyGo(MarketStatusActivity.class);
                break;
            case R.id.layout_change:
                readyGo(ChangePswActivity.class);
                break;
            case R.id.layout_clear:
                new ConfirmCancelDialog(this, "是否确认清除缓存？", new IDialogClickInterface() {
                    @Override
                    public void onEnterClick(Object object) {
                        DataClearManager.clearAllCache(SetActivity.this);
                        CommonUtil.toast("缓存已清理");
                        clearTxt.setText(DataClearManager.getTotalCacheSize(SetActivity.this));
                    }
                });
                break;
            case R.id.layout_about:
//                Bundle bundle = new Bundle();
//                bundle.putString("title", "关于我们");
//                bundle.putString("url", "http://www.baidu.com");
//                readyGo(WebActivity.class, bundle);
                readyGo(AboutActivity.class);
                break;
            case R.id.txt_cancel:
                new ConfirmCancelDialog(this, "是否退出登录？点击确认无法撤销", new IDialogClickInterface() {
                    @Override
                    public void onEnterClick(Object object) {
                        mvpPresenter.cancel();
                        showDialogLoading();
                    }
                });
                break;
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loadData(Object object, String type) {
        showDialogDismiss();
        BaseApplication.getInstance().clear();
        readyGoThenKill(LoginActivity.class);
        ActivityManager.getInstance().exit();
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.toast(msg);
    }
}
