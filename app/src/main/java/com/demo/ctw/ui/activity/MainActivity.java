package com.demo.ctw.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseFragment;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.LocationObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.presenter.LoginPresenter;
import com.demo.ctw.ui.fragment.AddressChooseFragment;
import com.demo.ctw.ui.fragment.AuctionFragment;
import com.demo.ctw.ui.fragment.HomeFragment;
import com.demo.ctw.ui.fragment.MessageFragment;
import com.demo.ctw.ui.fragment.PersonFragment;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.utils.picker.AddressInitTask;
import com.demo.ctw.utils.picker.AddressPicker;
import com.demo.ctw.view.ILoadView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<LoginPresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_location)
    TextView locationTxt;
    @BindView(R.id.view_main_bootom)
    BottomNavigationBar mainBottomView;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private int curFragment = 0;
    private long exitTime = 0;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        fragments.add(new HomeFragment());
        fragments.add(new AuctionFragment());
        fragments.add(new MessageFragment());
        fragments.add(new PersonFragment());

        String jpushId = SharePrefUtil.getString(this, ShareKey.JPUSH_REGISTID, "");
        boolean isJpush = SharePrefUtil.getBoolean(this, ShareKey.IS_JPUSH, false);
        if (CommonUtil.isLogin()) {
            if (!isJpush & !CommonUtil.isEmpty(jpushId)) mvpPresenter.jpushId(jpushId);
        }

        mainBottomView.setActiveColor(R.color.app).setInActiveColor(R.color.C2).setBackgroundColor(R.color.white);
        mainBottomView.setMode(BottomNavigationBar.MODE_FIXED);
        mainBottomView.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mainBottomView
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_home_checked, "首页")
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_main_home_check)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_auction_checked, "竞拍")
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_main_auction_check)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_msg_checked, "消息")
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_main_msg_check)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_mine_checked, "我")
                        .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_main_mine_check)))
                .setFirstSelectedPosition(0)
                .initialise();
        mainBottomView.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {//这里也可以使用SimpleOnTabSelectedListener
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                setDefault(position);
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });
        setDefault(0);
    }

    /**
     * 设置默认的fragment
     *
     * @param position
     */
    private void setDefault(int position) {
        switch (position) {
            case 0:
                titleTxt.setText("物业广告竞拍");
                locationTxt.setVisibility(View.VISIBLE);
                showLocation();
                break;
            case 1:
                titleTxt.setText("广告竞拍");
                locationTxt.setVisibility(View.GONE);
                break;
            case 2:
                titleTxt.setText("消息");
                locationTxt.setVisibility(View.GONE);
                break;
            case 3:
                titleTxt.setText("我");
                locationTxt.setVisibility(View.GONE);
                break;
        }
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("" + position);
        FragmentTransaction beginTransaction = manager.beginTransaction();
        if (fragment == null) {
            if (manager.findFragmentByTag("" + curFragment) != null) {
                beginTransaction.hide(fragments.get(curFragment));
            }
            beginTransaction.add(R.id.layout_main, fragments.get(position), "" + position).show(fragments.get(position)).commit();
        } else if (curFragment != position) {
            beginTransaction.hide(fragments.get(curFragment)).show(fragments.get(position)).commit();
        }
        curFragment = position;
    }

    private void showLocation() {
        String loc = SharePrefUtil.getString(this, ShareKey.LOCATION, "");
        if (CommonUtil.isEmpty(loc)) {
            locationTxt.setText("定位");
        } else {
            LocationObject locationObject = GsonTools.changeGsonToBean(loc, LocationObject.class);
            locationTxt.setText(locationObject.getCity());
        }
    }

    @Override
    public void loadData(Object object, String type) {
        SharePrefUtil.saveBoolean(this, ShareKey.IS_JPUSH, true);
    }

    @Override
    public void loadFail(String msg, String type) {

    }

    @OnClick({R.id.txt_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_location:
                showCityPicker();
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
                locationTxt.setText(city);
//                addressTxt.setText(province.equals(city) ? province + county : province + city + county);
            }
        });
        initTask.execute();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
