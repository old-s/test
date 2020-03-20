package com.demo.ctw.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.key.DefaultCode;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.rx.Notice;
import com.demo.ctw.rx.NoticeCode;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.MatisseGlideEngine;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.view.ILoadView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人资料
 */
public class CompileUserActivity extends BaseMvpActivity<MinePresenter> implements ILoadView {
    @BindView(R.id.edt_nickname)
    EditText nicknameEdt;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_tel)
    TextView telTxt;
    @BindView(R.id.txt_companyname)
    TextView compantnameTxt;
    @BindView(R.id.txt_gname)
    TextView gnameTxt;
    @BindView(R.id.txt_banknum)
    TextView banknumTxt;
    @BindView(R.id.txt_bank)
    TextView bankTxt;
    @BindView(R.id.txt_phone)
    TextView phoneTxt;
    @BindView(R.id.txt_email)
    TextView emailTxt;
    @BindView(R.id.txt_wechat)
    TextView wechatTxt;
    private String imgUrl, imgurl, name;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_compile_user);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String info = SharePrefUtil.getString(this, ShareKey.USERINFO, "");
        if (CommonUtil.isEmpty(info)) {
            finish();
            return;
        }
        UserObject userObject = GsonTools.changeGsonToBean(info, UserObject.class);
        if (!CommonUtil.isEmpty(userObject.getNickName()))
            nicknameEdt.setText(userObject.getNickName());
        if (!CommonUtil.isEmpty(userObject.getImg()))
            Glide.with(this).load(ApiService.API_SERVICE + userObject.getImg())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(img);

        telTxt.setText(userObject.getTel());
        compantnameTxt.setText(userObject.getCompanyName());
        gnameTxt.setText(userObject.getPersonName());
        banknumTxt.setText(userObject.getBankNum());
        bankTxt.setText(userObject.getBank());
        phoneTxt.setText(userObject.getAdminTel());
        emailTxt.setText(userObject.getEmail());
        wechatTxt.setText(userObject.getWechat());
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("name")) name = extras.getString("name");
        if (extras.containsKey("img")) imgurl = extras.getString("img");
    }

    @Override
    public void loadData(Object object, String type) {
        if (type.equals("img")) {
            imgUrl = object.toString();
            Glide.with(this).load(ApiService.API_SERVICE + imgUrl)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(img);
        } else if (type.equals("update")) {
            showDialogDismiss();
            CommonUtil.toast("个人信息修改成功");
            finish();
            post(new Notice(NoticeCode.UPDATE_USERINFO));
        }
    }

    @Override
    public void loadFail(String msg, String type) {

    }

    @OnClick({R.id.img_back, R.id.txt_save, R.id.img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_save:
                String nickname = nicknameEdt.getText().toString();
                if (CommonUtil.isEmpty(nickname)) {
                    CommonUtil.toast("名称为空，无法保存");
                    return;
                }
                if (CommonUtil.isEmpty(imgUrl) & nickname.equals(name)) {
                    CommonUtil.toast("您未修改个人信息，无法保存");
                    return;
                }

                RequestObject object = new RequestObject();
                object.setNickName(nickname);
                object.setImg(imgUrl);
                mvpPresenter.updateUser(object);
                showDialogLoading();
                break;
            case R.id.img:
                Matisse.from(this).choose(MimeType.ofAll()) //图片类型
                        .countable(false)   //true：选中后显示数字  false：选中后显示对号
                        .maxSelectable(1)   //可选的最大数
                        .capture(true)      //是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.demo.ctw.fileprovider"))
                        .imageEngine(new MatisseGlideEngine())     //图片加载引擎
                        .forResult(DefaultCode.USER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DefaultCode.USER)
                mvpPresenter.updateImg(Matisse.obtainPathResult(data).get(0), "img");
        }
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }
}
