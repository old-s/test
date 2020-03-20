package com.demo.ctw.ui.activity.login;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.base.ActivityManager;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.BrandObject;
import com.demo.ctw.entity.ProveFormworkObject;
import com.demo.ctw.entity.RegisterObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.key.DefaultCode;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.presenter.LoginPresenter;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.ui.activity.LookPicActivity;
import com.demo.ctw.ui.adapter.AddPicGridViewAdapter;
import com.demo.ctw.ui.view.NoScrollGridView;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.MatisseGlideEngine;
import com.demo.ctw.utils.picker.AddressInitTask;
import com.demo.ctw.utils.picker.AddressPicker;
import com.demo.ctw.view.ILoadView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 超市入驻
 */
public class RegisterMarketActivity extends BaseMvpActivity<LoginPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    //基本信息
    @BindView(R.id.txt_brand)
    TextView brandTxt;
    @BindView(R.id.edt_name)
    EditText nameEdt;
    @BindView(R.id.edt_tel)
    EditText telEdt;
    @BindView(R.id.txt_address)
    TextView addressTxt;
    @BindView(R.id.edt_address)
    EditText addressEdt;
    @BindView(R.id.btn_more)
    RadioButton moreBtn;
    @BindView(R.id.edt_area)
    EditText areaEdt;
    @BindView(R.id.img_zmwj1)
    ImageView zmwj1Img;
    @BindView(R.id.edt_number)
    EditText numberEdt;
    @BindView(R.id.edt_absize)
    EditText absizeEdt;
    @BindView(R.id.edt_csize)
    EditText csizeEdt;
    @BindView(R.id.img_zmwj2)
    ImageView zmwj2Img;
    @BindView(R.id.edt_number2)
    EditText number2Edt;
    @BindView(R.id.edt_volume)
    EditText volumeEdt;
    @BindView(R.id.edt_money)
    EditText moneyEdt;

    //汇款信息
    @BindView(R.id.edt_hkname)
    EditText hknameEdt;
    @BindView(R.id.edt_bank)
    EditText bankEdt;
    @BindView(R.id.edt_account)
    EditText accountEdt;

    //证照信息
    @BindView(R.id.img_yyzz)
    ImageView yyzzImg;
    @BindView(R.id.view_dmzp)
    NoScrollGridView dmzpView;
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
    @BindView(R.id.img_zmwj3)
    ImageView zmwj3Img;

    private View clickView;
    //    private CityPickerView cityPickerView = new CityPickerView();
    private String brandId, type, userId;        //角色类型
    private String zmwj1Url, zmwj2Url, zmwj3Url, yyzzUrl, sfzzUrl, sfzfUrl;
    private RegisterObject registerObject;      //注册页面填写的信息
    private ProveFormworkObject proveFormworkObject;
    private AddPicGridViewAdapter picAdapter;
    private ArrayList<String> dmzpImgs = new ArrayList<>();

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_register_market);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        //省市区预加载
//        cityPickerView.init(this);
        titleTxt.setText("超市入驻");
        int width = (getResources().getDisplayMetrics().widthPixels - getResources().getDimensionPixelSize(R.dimen.dimen_120)) / 2;
        int height = width / 8 * 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        sfzfImg.setLayoutParams(params);
        sfzzImg.setLayoutParams(params);

        addDmzpImgs();

        mvpPresenter.proveFormwork();
    }

    private void addDmzpImgs() {
        int width = getResources().getDisplayMetrics().widthPixels;
        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_135);
        picAdapter = new AddPicGridViewAdapter(this, width - margin, new IRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(Object object, String type) {
                int position = (int) object;
                dmzpImgs.remove(position);
                picAdapter.notifyDataSetChanged();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(RegisterMarketActivity.this).choose(MimeType.ofAll()) //图片类型
                        .countable(false)   //true：选中后显示数字  false：选中后显示对号
                        .maxSelectable(4 - dmzpImgs.size())   //可选的最大数
                        .capture(true)      //是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.demo.jg.fileprovider"))
                        .imageEngine(new MatisseGlideEngine())     //图片加载引擎
                        .forResult(DefaultCode.MARKET_DMZP);
            }
        });
        dmzpView.setAdapter(picAdapter);
        picAdapter.setData(dmzpImgs);
        picAdapter.notifyDataSetChanged();
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
                proveFormworkObject = (ProveFormworkObject) object;
                break;
            case "brand":
                ArrayList<BrandObject> brandObjects = (ArrayList<BrandObject>) object;
                if (brandObjects.size() > 0) {
                    showPopView(brandObjects);
                } else {
                    CommonUtil.toast("数据为空");
                }
                break;
            case "code":
                timer.start();
                CommonUtil.toast("验证码发送成功");
                break;
            case DefaultCode.MARKET_CREDENTIALS1 + "":
                zmwj1Url = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + zmwj1Url).into(zmwj1Img);
                break;
            case DefaultCode.MARKET_CREDENTIALS2 + "":
                zmwj2Url = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + zmwj2Url).into(zmwj2Img);
                break;
            case DefaultCode.MARKET_CREDENTIALS3 + "":
                zmwj3Url = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + zmwj3Url).into(zmwj3Img);
                break;
            case DefaultCode.MARKET_YYZZ + "":
                yyzzUrl = object.toString();
                Glide.with(this).load(ApiService.API_SERVICE + yyzzUrl).into(yyzzImg);
                break;
            case "imgs":
                dmzpImgs.addAll(CommonUtil.stringToList(object.toString(), ","));
                picAdapter.notifyDataSetChanged();
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

    /**
     * 品牌列表
     *
     * @param data
     */
    public void showPopView(final ArrayList<BrandObject> data) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
        final PopupWindow popupWindow = new PopupWindow(view, clickView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView listView = view.findViewById(R.id.list);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDisplayMetrics().heightPixels / 3));
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getBname());
        }
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, list));
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(clickView);       //设置popWindow位置
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                brandId = data.get(position).getId();
                brandTxt.setText(list.get(position));
                popupWindow.dismiss();
            }
        });
    }

    @OnClick({R.id.img_back, R.id.txt_brand, R.id.txt_address, R.id.txt_code, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_brand:
                clickView = view;
                mvpPresenter.branList();
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
            case R.id.txt_submit:
                //基本信息
                String name = nameEdt.getText().toString();
                String tel = telEdt.getText().toString();
                String city = addressTxt.getText().toString();
                String address = addressEdt.getText().toString();
                String comType;
                if (moreBtn.isChecked()) comType = "1";
                else comType = "2";
                String area = areaEdt.getText().toString();
                String number = numberEdt.getText().toString();
                String absize = absizeEdt.getText().toString();
                String csize = csizeEdt.getText().toString();
                String number2 = number2Edt.getText().toString();
                String volume = volumeEdt.getText().toString();
                String money = moneyEdt.getText().toString();
                if (CommonUtil.isEmpty(brandId) || CommonUtil.isEmpty(name) || CommonUtil.isEmpty(tel) || CommonUtil.isEmpty(city) || CommonUtil.isEmpty(address)
                        || CommonUtil.isEmpty(area) || CommonUtil.isEmpty(number) || CommonUtil.isEmpty(absize) || CommonUtil.isEmpty(csize) || CommonUtil.isEmpty(number2)
                        || CommonUtil.isEmpty(volume) || CommonUtil.isEmpty(money) || CommonUtil.isEmpty(zmwj1Url) || CommonUtil.isEmpty(zmwj2Url)) {
                    CommonUtil.toast("基本信息不全，请完善");
                    return;
                }
                //汇款信息
                String hkname = hknameEdt.getText().toString();
                String bank = bankEdt.getText().toString();
                String account = accountEdt.getText().toString();
                if (CommonUtil.isEmpty(hkname) || CommonUtil.isEmpty(bank) || CommonUtil.isEmpty(account)) {
                    CommonUtil.toast("汇款信息不全，请完善");
                    return;
                }

                if (CommonUtil.isEmpty(yyzzUrl) || dmzpImgs.size() == 0 || CommonUtil.isEmpty(sfzfUrl) || CommonUtil.isEmpty(sfzzUrl)) {
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
                        || CommonUtil.isEmpty(code) || CommonUtil.isEmpty(zmwj3Url)) {
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
                object.setBrank(brandId);
                object.setCompanyName(name);
                object.setPhone(tel);
                object.setRegion(city);
                object.setAddress(address);
                object.setComType(comType);
                object.setArea(area);
                object.setAreaimg(zmwj1Url);
                object.setShopcart(number);
                object.setAarea(absize);
                object.setCarea(csize);
                object.setShopcartimg(zmwj2Url);
                object.setAdvScreen(number2);
                object.setFootfall(volume);
                object.setTurnover(money);
                //汇款信息
                object.setCompanyBank(hkname);
                object.setBank(bank);
                object.setBankNum(account);
                //证照信息
                object.setBusinessLicense(yyzzUrl);
                object.setCompanyImg(CommonUtil.listToString(dmzpImgs));
                object.setCardimgo(sfzzUrl);
                object.setCardimgt(sfzfUrl);
                //公司指定联系人
                object.setPersonName(nickname);
                object.setEmail(email);
                object.setAdminTel(phone);
                object.setWechat(wechat);
                object.setTelCodet(code);
                object.setPersonImg(zmwj3Url);
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

    @OnClick({R.id.img_zmwj1, R.id.img_zmwj2, R.id.img_zmwj3, R.id.img_yyzz, R.id.img_sfz_f, R.id.img_sfz_z})
    public void onAddPic(View view) {
        int code = 0;
        switch (view.getId()) {
            case R.id.img_zmwj1:
                code = DefaultCode.MARKET_CREDENTIALS1;
                break;
            case R.id.img_zmwj2:
                code = DefaultCode.MARKET_CREDENTIALS2;
                break;
            case R.id.img_zmwj3:
                code = DefaultCode.MARKET_CREDENTIALS3;
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

    @OnClick({R.id.txt_look_zmwj1, R.id.txt_look_zmwj2, R.id.txt_look_zmwj3})
    public void onLookClick(View view) {
        if (proveFormworkObject == null) {
            CommonUtil.toast("数据为空，无法查看模板文件");
            return;
        }
        Bundle bundle = new Bundle();
        String imgUrl = "", name = "";
        switch (view.getId()) {
            case R.id.txt_look_zmwj1:
                name = "超市营业面积证明模板";
                imgUrl = proveFormworkObject.getYymj();
                break;
            case R.id.txt_look_zmwj2:
                name = "推车数量证明模板";
                imgUrl = proveFormworkObject.getTczm();
                break;
            case R.id.txt_look_zmwj3:
                name = "管理人证明模板";
                imgUrl = proveFormworkObject.getLxrzm();
                break;
        }
        if (CommonUtil.isEmpty(imgUrl)) {
            CommonUtil.toast("照片地址为空，无法查看");
        }
        bundle.putString("imgUrl", imgUrl);
        bundle.putString("title", name);
        readyGo(LookPicActivity.class, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DefaultCode.MARKET_DMZP) {
                ArrayList<String> pics = (ArrayList<String>) Matisse.obtainPathResult(data);
                mvpPresenter.updateImg(pics);
            } else {
                mvpPresenter.updateImg(Matisse.obtainPathResult(data).get(0), requestCode + "");
            }
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }
}
