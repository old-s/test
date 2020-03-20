package com.demo.ctw.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.entity.BannerObject;
import com.demo.ctw.entity.MarketObject;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.ui.adapter.LookPicAdapter;
import com.demo.ctw.ui.view.BannerAdView;
import com.demo.ctw.ui.view.NoScrollGridView;
import com.demo.ctw.utils.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 超市详情
 */
public class MarketDetailActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.layout)
    ScrollView layout;
    @BindView(R.id.view_banner)
    BannerAdView bannerView;
    @BindView(R.id.txt_mName)
    TextView mNameTxt;
    //基本信息
    @BindView(R.id.txt_brand)
    TextView branTxt;
    @BindView(R.id.txt_name)
    TextView nameTxt;
    @BindView(R.id.txt_region)
    TextView regionTxt;
    @BindView(R.id.txt_address)
    TextView addressTxt;
    @BindView(R.id.txt_type)
    TextView typeTxt;
    @BindView(R.id.txt_area)
    TextView areaTxt;
    @BindView(R.id.txt_shopnum)
    TextView shopnumTxt;
    @BindView(R.id.txt_absize)
    TextView absizeTxt;
    @BindView(R.id.txt_csize)
    TextView csizeTxt;
    @BindView(R.id.txt_advnum)
    TextView advnumTxt;
    @BindView(R.id.txt_kll)
    TextView kllTxt;
    @BindView(R.id.txt_money)
    TextView moneyTxt;
    //证照信息
    @BindView(R.id.img_yyzz)
    ImageView yyzzImg;
    @BindView(R.id.view_dmzp)
    NoScrollGridView dmzpView;

    private String id;
    private MarketObject marketObject;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_market_detail);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("超市详情");
        if (marketObject == null) {
            finish();
            return;
        }
        loadData();
    }

    private void loadData() {
        showComImgs(marketObject.getCompanyImg());
        mNameTxt.setText(marketObject.getCompanyName());
        //基本信息
        branTxt.setText(marketObject.getBrank());
        nameTxt.setText(marketObject.getCompanyName());
        regionTxt.setText(marketObject.getRegion());
        addressTxt.setText(marketObject.getAddress());
        typeTxt.setText(marketObject.getComType().equals("1") ? "连锁" : "单店");
        areaTxt.setText(marketObject.getArea() + "㎡");
        shopnumTxt.setText(marketObject.getShopcart() + "个");
        absizeTxt.setText(marketObject.getAarea());
        csizeTxt.setText(marketObject.getCarea());
        advnumTxt.setText(marketObject.getAdvScreen() + "个");
        kllTxt.setText(marketObject.getFootfall() + "个人");
        moneyTxt.setText(marketObject.getTurnover() + "万元");
        //证照信息
        Glide.with(this).load(ApiService.API_SERVICE + marketObject.getBusinessLicense()).into(yyzzImg);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("id")) {
            id = extras.getString("id");
        }
        if (extras.containsKey("data"))
            marketObject = extras.getParcelable("data");
    }

    /**
     * header banner,店面照片
     *
     * @param companyImg
     */
    private void showComImgs(String companyImg) {
        if (CommonUtil.isEmpty(companyImg))
            return;
        ArrayList<BannerObject> bannerObjects = new ArrayList<>();
        ArrayList<String> imgs = CommonUtil.stringToList(companyImg, ",");
        for (int i = 0; i < imgs.size(); i++) {
            BannerObject object = new BannerObject();
            object.setBanner_path(imgs.get(i));
            object.setLink(i);
            bannerObjects.add(object);
        }
        bannerView.setAdList(bannerObjects);

        int widht = getResources().getDisplayMetrics().widthPixels - getResources().getDimensionPixelSize(R.dimen.dimen_130);
        dmzpView.setAdapter(new LookPicAdapter(2, widht, imgs));
    }

    @OnClick({R.id.txt_look_area, R.id.txt_look_number, R.id.img_yyzz})
    public void onClick(View view) {
        String imgUrl = "", title = "";
        switch (view.getId()) {
            case R.id.txt_look_area:
                imgUrl = marketObject.getAreaimg();
                title = "营业面积证明文件";
                break;
            case R.id.txt_look_number:
                imgUrl = marketObject.getShopcartimg();
                title = "推车数量证明文件";
                break;
            case R.id.img_yyzz:
                imgUrl = marketObject.getBusinessLicense();
                title = "营业执照证明文件";
                break;
        }
        if (CommonUtil.isEmpty(imgUrl)) {
            CommonUtil.toast("图片地址为空，无法查看");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("imgUrl", imgUrl);
        bundle.putString("title", title);
        readyGo(LookPicActivity.class, bundle);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
