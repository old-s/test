package com.demo.ctw.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class OptionActivity extends BaseMvpActivity<MinePresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.edt_option)
    EditText optionEdt;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_option);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("意见反馈");
    }

    @Override
    public void loadData(Object object, String type) {
        showDialogDismiss();
        CommonUtil.toast("提交成功");
        finish();
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.toast(type);
    }

    @OnClick({R.id.img_back, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_submit:
                String option = optionEdt.getText().toString();
                if (CommonUtil.isEmpty(option)) {
                    CommonUtil.toast("请输入内容");
                    return;
                }
                RequestObject object = new RequestObject();
                object.setContent(option);
                mvpPresenter.option(object);
                showDialogLoading();
                break;
        }
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }
}
