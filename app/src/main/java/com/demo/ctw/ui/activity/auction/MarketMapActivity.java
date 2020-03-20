package com.demo.ctw.ui.activity.auction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.MarketObject;
import com.demo.ctw.map.LocationUtil;
import com.demo.ctw.presenter.AuctionPresenter;
import com.demo.ctw.ui.activity.MarketDetailActivity;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地图
 */
public class MarketMapActivity extends BaseMvpActivity<AuctionPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.view_map)
    MapView mapView;
    @BindView(R.id.txt_name)
    TextView nameTxt;
    @BindView(R.id.txt_address)
    TextView addressTxt;
    @BindView(R.id.txt_number)
    TextView numberTxt;
    private String id;
    private MarketObject marketObject;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_market_map);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("我要竞拍");
        mvpPresenter.marketDetail(id);
        showDialogLoading();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("id"))
            id = extras.getString("id");
    }

    @Override
    public void loadData(Object object, String type) {
        showDialogDismiss();
        marketObject = (MarketObject) object;
        nameTxt.setText(marketObject.getCompanyName());
        addressTxt.setText(marketObject.getAddress());
        numberTxt.setText(marketObject.getShopcart());
        LocationUtil.getInstance().addMarketCenter(Double.parseDouble(marketObject.getLat()), Double.parseDouble(marketObject.getLng()), mapView.getMap());
//        LatLng location = new LatLng(36.669106, 117.150484);
//        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(location);
//        mapView.getMap().setMapStatus(mapStatusUpdate);
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        finish();
        CommonUtil.toast(msg);
    }

    @OnClick({R.id.img_back, R.id.txt_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_detail:
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putParcelable("data", marketObject);
                readyGo(MarketDetailActivity.class, bundle);
                break;
        }
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
    protected AuctionPresenter createPresenter() {
        return new AuctionPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
}
