package com.demo.ctw.ui.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.base.BaseFragment;
import com.demo.ctw.ui.adapter.ViewPagerAdapter;
import com.demo.ctw.ui.fragment.AuctionRecordFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 竞拍记录
 */
public class AuctionRecordActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.tab)
    public TabLayout tab;
    @BindView(R.id.pager)
    public ViewPager pager;
    private String[] titles = {"全部", "进行中", "已中标", "未中标"};
    private String[] types = {"", "doing", "win", "lose"};
    private ViewPagerAdapter adapter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_auction_record);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("竞拍记录");
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tab.setupWithViewPager(pager);
        pager.setAdapter(adapter);
        initTitles();
    }

    private void initTitles() {
        ArrayList<String> data = new ArrayList<>();
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            data.add(titles[i]);
            AuctionRecordFragment fragment = new AuctionRecordFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", types[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        adapter.initData(data, fragments);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
