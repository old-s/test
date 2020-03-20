package com.demo.ctw.ui.activity.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.RegisterObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.presenter.LoginPresenter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseMvpActivity<LoginPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.edt_phone)
    EditText phoneEdt;
    @BindView(R.id.edt_code)
    EditText codeEdt;
    @BindView(R.id.txt_code)
    TextView codeTxt;
    @BindView(R.id.edt_psw)
    EditText pswEdt;
    private RegisterObject registerObject;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("注册");
    }

    @OnClick({R.id.img_back, R.id.txt_code, R.id.txt_next, R.id.txt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_code:
                String phone = phoneEdt.getText().toString();
                if (CommonUtil.mobilePatten(phone)) {
                    RequestObject object = new RequestObject();
                    object.setTel(phone);
                    mvpPresenter.reqCode(object);
                } else {
                    CommonUtil.toast("请输入正确的手机号");
                }
                break;
            case R.id.txt_next:
                phone = phoneEdt.getText().toString();
                String code = codeEdt.getText().toString();
                String psw = pswEdt.getText().toString();
                if (CommonUtil.isEmpty(phone) || CommonUtil.isEmpty(code) || CommonUtil.isEmpty(psw)) {
                    CommonUtil.toast("请输入完整信息");
                    return;
                }
                registerObject = new RegisterObject();
                registerObject.setTel(phone);
                registerObject.setCode(code);
                registerObject.setPsw(psw);

                RequestObject object = new RequestObject();
                object.setTel(phone);
                object.setCode(code);
                mvpPresenter.telIsUse(object);
                showDialogLoading();
                break;
            case R.id.txt_login:
                readyGoThenKill(LoginActivity.class);
                break;
        }
    }

    /**
     * long millisInfuture  倒计时总时间 ms    1s=1000ms
     * long countDownInterval   倒计时时间间隔 ms
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            codeTxt.setText(millisUntilFinished / 1000 + "s后重新获取");
            codeTxt.setClickable(false);
        }

        @Override
        public void onFinish() {
            codeTxt.setText("获取验证码");
            codeTxt.setClickable(true);
        }
    };

    @Override
    public void loadData(Object object, String type) {
        showDialogDismiss();
        switch (type){
            case "code":
                timer.start();
                CommonUtil.toast("验证码发送成功");
                break;
            case "isUse":
                boolean isUsed = (boolean) object;
                if (isUsed) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("info", registerObject);
                    readyGo(ChooseRoleActivity.class, bundle);
                } else {
                    CommonUtil.toast("该账号已被占用，请去登录或更换账号注册");
                }
                break;
        }
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.toast(msg);
    }
}
