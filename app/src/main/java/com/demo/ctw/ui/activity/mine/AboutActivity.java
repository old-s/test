package com.demo.ctw.ui.activity.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutActivity extends BaseMvpActivity<MinePresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_version)
    TextView versionTxt;
    @BindView(R.id.txt_content)
    TextView contentTxt;

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("关于我们");
        versionTxt.setText("智慧云" + CommonUtil.getVersion(this));
        mvpPresenter.about();
    }

    @Override
    public void loadData(Object object, String type) {

    }

    @Override
    public void loadFail(String msg, String type) {

    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
