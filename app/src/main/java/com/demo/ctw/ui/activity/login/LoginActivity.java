package com.demo.ctw.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseApplication;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.RegisterObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.presenter.LoginPresenter;
import com.demo.ctw.ui.activity.MainActivity;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements ILoadView {
    @BindView(R.id.edt_phone)
    EditText phoneEdt;
    @BindView(R.id.edt_psw)
    EditText pswEdt;
    private RegisterObject registerObject;
    private RequestObject requestObject;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mvpPresenter.token();
        String tel = SharePrefUtil.getString(this, ShareKey.TEL, "");
        if (!CommonUtil.isEmpty(tel)) phoneEdt.setText(tel);
    }

    @OnClick({R.id.txt_login, R.id.txt_forget, R.id.txt_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login:
                String tel = phoneEdt.getText().toString();
                String psw = pswEdt.getText().toString();
                if (CommonUtil.isEmpty(tel) || CommonUtil.isEmpty(psw)) {
                    CommonUtil.toast("请填写完整数据");
                    return;
                }
                registerObject = new RegisterObject();
                registerObject.setPsw(psw);
                registerObject.setTel(tel);
                requestObject = new RequestObject();
                requestObject.setTel(tel);
                requestObject.setPassword(psw);
                SharePrefUtil.saveString(this, ShareKey.TEL, tel);

                if (CommonUtil.isEmpty(SharePrefUtil.getString(this, ShareKey.TOKEN, ""))) {
                    mvpPresenter.token();
                } else {
                    mvpPresenter.login(requestObject);
                    showDialogLoading();
                }
                break;
            case R.id.txt_forget:
                readyGo(ForgetPswActivity.class);
                break;
            case R.id.txt_register:
                readyGo(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void loadData(Object object, String type) {
        switch (type) {
            case "token":
//                mvpPresenter.login(requestObject);
//                showDialogLoading();
                break;
            case "login":
                showDialogDismiss();
                UserObject userObject = (UserObject) object;
                switch (userObject.getAuditStatus()) {
                    case "wait":
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("type", "register");
                        readyGoThenKill(ShowStatusActivity.class, bundle1);
                        break;
                    case "audity":
                        CommonUtil.toast("登录成功");
                        SharePrefUtil.saveString(this, ShareKey.USERINFO, GsonTools.createGsonString(userObject));
                        BaseApplication.getInstance().setUserId(userObject.getId());
                        BaseApplication.getInstance().setType(userObject.getType());
                        readyGoThenKill(MainActivity.class);
                        break;
                    case "auditn":
                        CommonUtil.toast("您提交的信息审核未通过，请重新提交");
                        String userType = userObject.getType();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("userId", userObject.getId());
                        bundle2.putString("type", userType);
                        bundle2.putParcelable("info", registerObject);
                        if (userType.equals("2")) readyGo(RegisterAdActivity.class, bundle2);
                        else readyGo(RegisterMarketActivity.class, bundle2);
                        break;
                }
                break;
        }
    }

    @Override
    public void loadFail(String msg, String type) {
        switch (type) {
            case "token":
                CommonUtil.toast("token获取失败");
                break;
            case "login":
                showDialogDismiss();
                CommonUtil.toast(msg);
                break;
        }
    }
}
