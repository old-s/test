package com.demo.ctw.ui.activity.auction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.AuctionObject;
import com.demo.ctw.entity.MarketObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.entity.SincerityMoneyObject;
import com.demo.ctw.map.LocationUtil;
import com.demo.ctw.presenter.AuctionPresenter;
import com.demo.ctw.rx.Notice;
import com.demo.ctw.rx.NoticeCode;
import com.demo.ctw.ui.adapter.GridCheckAdapter;
import com.demo.ctw.ui.dialog.ChooseInfoDialog;
import com.demo.ctw.ui.view.NoScrollGridView;
import com.demo.ctw.ui.view.flowlayout.FlowObject;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.DateUtils;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.view.ILoadView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 我要竞拍
 */
public class AffirmAuctionActivity extends BaseMvpActivity<AuctionPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.scrollview)
    ScrollView scrollView;
    @BindView(R.id.view_map)
    MapView mapView;
    //基本信息
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
    //选择投放信息
    @BindView(R.id.txt_year)
    TextView yearTxt;
    @BindView(R.id.view_month)
    NoScrollGridView monthView;
    @BindView(R.id.txt_shopnum_real)
    TextView shopnumRealTxt;
    @BindView(R.id.txt_advnum_real)
    TextView advnumRealTxt;
    @BindView(R.id.view_tfwz)
    NoScrollGridView tfwzView;
    @BindView(R.id.img_tfwz)
    ImageView tfwzImg;
    @BindView(R.id.txt_finishtime)
    TextView finishtimeTxt;
    //我要出价
    @BindView(R.id.txt_price)
    TextView priceTxt;
    @BindView(R.id.txt_total)
    TextView totalTxt;
    @BindView(R.id.txt_money)
    TextView moneyTxt;

    private String months[] = {"1-2月", "3-4月", "5-6月", "7-8月", "9-10月", "11-12月"};
    private String tfwzs[] = {"A面", "B面", "C面"};
    private int imgIds[] = {R.mipmap.ic_tfwz_a, R.mipmap.ic_tfwz_b, R.mipmap.ic_tfwz_c};
    private GridCheckAdapter monthAdapter = new GridCheckAdapter();
    private GridCheckAdapter tfwzAdapter = new GridCheckAdapter();
    private ArrayList<AuctionObject> auctionObjects;
    private ArrayList<FlowObject> monthList = new ArrayList<>();
    private ArrayList<FlowObject> tfwzList = new ArrayList<>();
    private int areas = 0, advnums = 0, shopnums = 0;
    private int advnum = 0, shopnum = 0;
    private String ids, area, month = "1-2月", location = "A面", money = "0";

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_affirm_auction);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("我要竞拍");

        mvpPresenter.sincerityMoney(ids);
        showLoading();

        if (auctionObjects == null)
            finish();
        for (int i = 0; i < auctionObjects.size(); i++) {
            AuctionObject object = auctionObjects.get(i);
            areas += Integer.parseInt(object.getArea());
            advnums += Integer.parseInt(object.getAdvScreen());
            shopnums += Integer.parseInt(object.getShopcart());
        }
//        absizeTxt.setText(marketObject.getAarea());
//        csizeTxt.setText(marketObject.getCarea());
        areaTxt.setText(areas + "㎡");
        shopnumTxt.setText(shopnums + "个");
        advnumTxt.setText(advnums + "个");
        yearTxt.setText("2019年");

        monthList = getData(months);
        monthAdapter.setData(monthList);
        monthView.setAdapter(monthAdapter);

        tfwzList = getData(tfwzs);
        tfwzAdapter.setData(tfwzList);
        tfwzView.setAdapter(tfwzAdapter);

        shopnum = shopnums;
        advnum = advnums;
        shopnumRealTxt.setText(shopnum + "个");
        advnumRealTxt.setText(advnum + "个");
        total();
    }

    private ArrayList<FlowObject> getData(String[] data) {
        ArrayList<FlowObject> objects = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            FlowObject object = new FlowObject();
            if (i == 0) object.setCheck(true);
            else object.setCheck(false);
            object.setId(i + "");
            object.setName(data[i]);
            objects.add(object);
        }
        return objects;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("data"))
            auctionObjects = extras.getParcelableArrayList("data");

        if (extras.containsKey("ids"))
            ids = extras.getString("ids");

        if (extras.containsKey("area"))
            area = extras.getString("area");
    }

    @Override
    public void loadData(Object object, String type) {
        switch (type) {
            case "sincerity":
                hideLoading();
                SincerityMoneyObject moneyObject = (SincerityMoneyObject) object;
                money = moneyObject.getCztime();
                moneyTxt.setText("诚意金：¥" + money);
                ArrayList<MarketObject> locations = moneyObject.getLocations();
                for (int i = 0; i < locations.size(); i++) {
                    MarketObject marketObject = locations.get(i);
                    if (CommonUtil.isEmpty(marketObject.getLat()) & CommonUtil.isEmpty(marketObject.getLng()))
                        return;
                    LocationUtil.getInstance().addMarketCenter(Double.valueOf(marketObject.getLat()), Double.valueOf(marketObject.getLng()), mapView.getMap());
                }
                break;
            case "submit":
                showDialogDismiss();
                post(new Notice(NoticeCode.UPDATE_ORDER));
                CommonUtil.toast("订单提交成功，请选择支付方式");
                Bundle bundle = new Bundle();
                bundle.putString("price", money);
                bundle.putString("content", location + "  " + shopnumRealTxt.getText().toString());
                readyGoThenKill(PrepaymentActivity.class, bundle);
                break;
        }
    }

    @Override
    public void loadFail(String msg, String type) {
        switch (type) {
            case "sincerity":
                CommonUtil.toast(msg);
                showError(msg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mvpPresenter.sincerityMoney(ids);
                        showLoading();
                    }
                });
                break;
            case "submit":
                showDialogDismiss();
                CommonUtil.toast(msg);
                break;
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCheckStates(monthList, position, "1");
                monthAdapter.notifyDataSetChanged();
                if (position == 0 || position == 4) {
                    shopnum = shopnums;
                    advnum = advnums;
                } else {
                    shopnum = shopnums / 2;
                    advnum = advnums / 2;
                }
                shopnumRealTxt.setText(shopnum + "个");
                advnumRealTxt.setText(advnum + "个");
                total();
            }
        });
        tfwzView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCheckStates(tfwzList, position, "2");
                tfwzAdapter.notifyDataSetChanged();
                tfwzImg.setImageResource(imgIds[position]);
            }
        });
    }

    /**
     * 改变选择状态
     *
     * @param data
     * @param position
     * @param type
     */
    private ArrayList<FlowObject> changeCheckStates(ArrayList<FlowObject> data, int position, String type) {
        if (!data.get(position).isCheck()) {
            data.get(position).setCheck(true);
            if (type.equals("1")) month = data.get(position).getName();
            else location = data.get(position).getName();
            for (int i = 0; i < data.size(); i++) {
                if (i != position) data.get(i).setCheck(false);
            }
        }
        return data;
    }

    @OnClick({R.id.img_back, R.id.layout_years, R.id.img_minus, R.id.img_add, R.id.txt_affirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_years:
                int year = DateUtils.getYear();
                String years[] = new String[30];
                for (int i = 0; i < 30; i++) {
                    years[i] = year + i + "年";
                }
                new ChooseInfoDialog(this, "选择投放年份", years, new ChooseInfoDialog.IChooseInfoListener() {
                    @Override
                    public void getChooseInfo(String reason) {
                        yearTxt.setText(reason);
                    }
                });
                break;
            case R.id.img_minus:
                int price1 = Integer.parseInt(priceTxt.getText().toString());
                if (price1 == 1) {
                    CommonUtil.toast("当前已是最低价");
                    return;
                }
                price1--;
                priceTxt.setText(price1 + "");
                break;
            case R.id.img_add:
                int price2 = Integer.parseInt(priceTxt.getText().toString());
                price2++;
                priceTxt.setText(price2 + "");
                break;
            case R.id.txt_affirm:
                RequestObject object = new RequestObject();
                object.setSpaceId(ids);
                object.setArea(area);
                object.setCashPledge(money);
                object.setYear(yearTxt.getText().toString().substring(0, 4));
                object.setMonth(month.substring(0, month.length() - 1));
                object.setLocation(location.substring(0, 1));
                object.setPrice(priceTxt.getText().toString());
                object.setTotalPrice(totalTxt.getText().toString());
                object.setNum(shopnum + "");
                object.setZaras(areas + "");
                object.setGwknum(advnum + "");
                mvpPresenter.submitOrder(object);
                showDialogLoading();
                break;
        }
    }

    @OnTextChanged(R.id.txt_price)
    public void onClick() {
        total();
    }

    private void total() {
        String price = priceTxt.getText().toString();
        double total = Double.valueOf(price) * shopnum;
        totalTxt.setText(total + "");
    }

    @Override
    protected AuctionPresenter createPresenter() {
        return new AuctionPresenter(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return scrollView;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
}
