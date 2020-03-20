package com.demo.ctw.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.base.ActivityManager;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.ProveFormworkObject;
import com.demo.ctw.entity.RegisterObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.key.DefaultCode;
import com.demo.ctw.presenter.LoginPresenter;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.ui.activity.LookPicActivity;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.MatisseGlideEngine;
import com.demo.ctw.utils.picker.AddressInitTask;
import com.demo.ctw.utils.picker.AddressPicker;
import com.demo.ctw.view.ILoadView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告主入驻
 */
public class RegisterAdActivity extends BaseMvpActivity<LoginPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    //基本信息
    @BindView(R.id.edt_company)
    EditText companyEdt;
    @BindView(R.id.edt_brand)
    EditText brandEdt;
    @BindView(R.id.edt_tel)
    EditText telEdt;
    @BindView(R.id.txt_address)
    TextView addressTxt;
    @BindView(R.id.edt_address)
    EditText addressEdt;

    //收款信息
    @BindView(R.id.edt_skname)
    EditText sknameEdt;
    @BindView(R.id.edt_bank)
    EditText bankEdt;
    @BindView(R.id.edt_account)
    EditText accountEdt;

    //证照信息
    @BindView(R.id.img_yyzz)
    ImageView yyzzImg;
    @BindView(R.id.img_sfz_z)
    ImageView sfzzImg;
    @BindView(R.id.img_sfz_f)
    ImageView sfzfImg;

    //公司指定联系（管理）人
    @BindView(R.id.edt_nickname)
    EditText nicknameEdt;
    @BindView(R.id.edt_email)
    EditText emailEdt;
    @BindView(R.id.edt_phone)
    EditText phoneEdt;
    @BindView(R.id.edt_wechat)
    EditText wechatEdt;
    @BindView(R.id.edt_code)
    EditText codeEdt;
    @BindView(R.id.txt_code)
    TextView codeTxt;
    @BindView(R.id.img_zmwj)
    ImageView zmwjImg;

    private String zmwjUrl, yyzzUrl, sfzzUrl, sfzfUrl;
    private String type, userId;        //角色类型,用户id
    private RegisterObject registerObject;      //注册页面填写的信息
    private ProveFormworkObject proveFormwork;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_register_ad);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("广告主入驻");
        int width = (getResources().getDisplayMetrics().widthPixels - getResources().getDimensionPixelSize(R.dimen.dimen_120)) / 2;
        int height = width / 8 * 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        sfzfImg.setLayoutParams(params);
        sfzzImg.setLayoutParams(params);
        mvpPresenter.proveFormwork();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("type"))
            type = extras.getString("type");
        if (extras.containsKey("info"))
            registerObject = extras.getParcelable("info");
        if (extras.containsKey("userId"))
            userId = extras.getString("userId");
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
        switch (type) {
            case "proveFormwork":
                proveFormwork = (ProveFormworkObject) object;
                break;
            case "code":
                timer.start();
                CommonUtil.toast("验证码发送成功");
                break;
            case DefaultCode.MARKET_CREDENTIALS + "":
                zmwjUrl = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + zmwjUrl).into(zmwjImg);
                break;
            case DefaultCode.MARKET_YYZZ + "":
                yyzzUrl = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + yyzzUrl).into(yyzzImg);
                break;
            case DefaultCode.MARKET_SFZF + "":
                sfzfUrl = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + sfzfUrl).into(sfzfImg);
                break;
            case DefaultCode.MARKET_SFZZ + "":
                sfzzUrl = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + sfzzUrl).into(sfzzImg);
                break;
            case "register":
                showDialogDismiss();
                Bundle bundle = new Bundle();
                bundle.putString("type", "register");
                readyGo(ShowStatusActivity.class, bundle);
                ActivityManager.getInstance().exit();
                break;
        }
    }

    @Override
    public void loadFail(String msg, String type) {
        CommonUtil.toast(msg);
        showDialogDismiss();
    }

    @OnClick({R.id.img_back, R.id.txt_address, R.id.txt_code, R.id.txt_look_zmwj, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_address:
                showCityPicker();
                break;
            case R.id.txt_code:
                String telC = phoneEdt.getText().toString();
                if (CommonUtil.mobilePatten(telC)) {
                    RequestObject object = new RequestObject();
                    object.setTel(telC);
                    mvpPresenter.reqCode(object);
                } else {
                    CommonUtil.toast("请输入正确的手机号");
                }
                break;
            case R.id.txt_look_zmwj:
                if (proveFormwork == null) {
                    CommonUtil.toast("数据为空，无法查看模板文件");
                    return;
                }
                Bundle bundle = new Bundle();
                String imgUrl = proveFormwork.getLxrzm();
                if (CommonUtil.isEmpty(imgUrl)) {
                    CommonUtil.toast("照片地址为空，无法查看");
                }
                bundle.putString("imgUrl", imgUrl);
                bundle.putString("title", "管理人证明模板");
                readyGo(LookPicActivity.class, bundle);
                break;
            case R.id.txt_submit:
                //基本信息
                String name = companyEdt.getText().toString();
                String brand = brandEdt.getText().toString();
                String tel = telEdt.getText().toString();
                String city = addressTxt.getText().toString();
                String address = addressEdt.getText().toString();
                if (CommonUtil.isEmpty(name) || CommonUtil.isEmpty(brand) || CommonUtil.isEmpty(tel) || CommonUtil.isEmpty(city) ||
                        CommonUtil.isEmpty(address)) {
                    CommonUtil.toast("基本信息不全，请完善");
                    return;
                }
                //收款信息
                String skname = sknameEdt.getText().toString();
                String bank = bankEdt.getText().toString();
                String account = accountEdt.getText().toString();
                if (CommonUtil.isEmpty(skname) || CommonUtil.isEmpty(bank) || CommonUtil.isEmpty(account)) {
                    CommonUtil.toast("收款信息不全，请完善");
                    return;
                }

                if (CommonUtil.isEmpty(yyzzUrl) || CommonUtil.isEmpty(sfzfUrl) || CommonUtil.isEmpty(sfzzUrl)) {
                    CommonUtil.toast("证照信息不全，请完善");
                    return;
                }
                //公司指定联系（管理）人
                String nickname = nicknameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                String wechat = wechatEdt.getText().toString();
                String code = codeEdt.getText().toString();
                if (CommonUtil.isEmpty(nickname) || CommonUtil.isEmpty(email) || CommonUtil.isEmpty(phone) || CommonUtil.isEmpty(wechat)
                        || CommonUtil.isEmpty(code) || CommonUtil.isEmpty(zmwjUrl)) {
                    CommonUtil.toast("公司指定联系（管理）人信息不全，请完善");
                    return;
                }

                RequestObject object = new RequestObject();
                object.setUserId(userId);
                object.setTel(registerObject.getTel());
                object.setTelCode(registerObject.getCode());
                object.setPassword(registerObject.getPsw());
                object.setType(type);
                //基本信息
                object.setCompanyName(name);
                object.setBrank(brand);
                object.setPhone(tel);
                object.setRegion(city);
                object.setAddress(address);
                //收款信息
                object.setCompanyBank(skname);
                object.setBank(bank);
                object.setBankNum(account);
                //证照信息
                object.setBusinessLicense(yyzzUrl);
                object.setCardimgo(sfzzUrl);
                object.setCardimgt(sfzfUrl);
                //公司指定联系人
                object.setPersonName(nickname);
                object.setEmail(email);
                object.setAdminTel(phone);
                object.setWechat(wechat);
                object.setTelCodet(code);
                object.setPersonImg(zmwjUrl);
                mvpPresenter.register(object);
                showDialogLoading();
                break;
        }
    }

    /**
     * 省市区选择
     */
    private void showCityPicker() {
        AddressInitTask initTask = new AddressInitTask(this);
        initTask.setOnAddressResultListener(new AddressInitTask.OnAddressResultListener() {
            @Override
            public void onResult(AddressPicker.Area area1, AddressPicker.Area area2, AddressPicker.Area area3) {
                String province = area1.getAreaName();
                String city = area2.getAreaName();
                String county = area3.getAreaName();
                addressTxt.setText(province.equals(city) ? province + county : province + city + county);
            }
        });
        initTask.execute();
    }

    @OnClick({R.id.img_zmwj, R.id.img_yyzz, R.id.img_sfz_f, R.id.img_sfz_z})
    public void onAddPic(View view) {
        int code = 0;
        switch (view.getId()) {
            case R.id.img_zmwj:
                code = DefaultCode.MARKET_CREDENTIALS;
                break;
            case R.id.img_yyzz:
                code = DefaultCode.MARKET_YYZZ;
                break;
            case R.id.img_sfz_f:
                code = DefaultCode.MARKET_SFZF;
                break;
            case R.id.img_sfz_z:
                code = DefaultCode.MARKET_SFZZ;
                break;
        }
        Matisse.from(this).choose(MimeType.ofAll()) //图片类型
                .countable(false)   //true：选中后显示数字  false：选中后显示对号
                .maxSelectable(1)   //可选的最大数
                .capture(true)      //是否显示拍照
                .captureStrategy(new CaptureStrategy(true, "com.demo.ctw.fileprovider"))
                .imageEngine(new MatisseGlideEngine())     //图片加载引擎
                .forResult(code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mvpPresenter.updateImg(Matisse.obtainPathResult(data).get(0), requestCode + "");
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }
}
