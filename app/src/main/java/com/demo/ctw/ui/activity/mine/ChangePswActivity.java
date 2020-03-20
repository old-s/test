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
 * 修改密码
 */
public class ChangePswActivity extends BaseMvpActivity<MinePresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.edt_psw_old)
    EditText oldPswEdt;
    @BindView(R.id.edt_psw_new)
    EditText newPswEdt;
    @BindView(R.id.edt_psw_again)
    EditText againPswEdt;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_changepsw);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("修改密码");
    }

    @OnClick({R.id.img_back, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_submit:
                String old = oldPswEdt.getText().toString();
                String newPsw = newPswEdt.getText().toString();
                String again = againPswEdt.getText().toString();
                if (CommonUtil.isEmpty(old) || CommonUtil.isEmpty(newPsw) || CommonUtil.isEmpty(again)) {
                    CommonUtil.toast("请填写完整数据");
                } else {
                    RequestObject object = new RequestObject();
                    object.setOpassword(old);
                    object.setTpassword(newPsw);
                    object.setPassword(again);
                    mvpPresenter.changePsw(object);
                    showDialogLoading();
                }
                break;
        }
    }

    @Override
    public void loadData(Object object, String type) {
        CommonUtil.toast("密码修改成功");
        finish();
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.toast(msg);
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }
}
