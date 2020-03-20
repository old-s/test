package com.demo.ctw.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.base.BaseFragment;
import com.demo.ctw.ui.adapter.FragPagerAdapter;
import com.demo.ctw.ui.fragment.OrderListFragment;
import com.demo.ctw.utils.ViewAnimationHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单反馈
 */
public class OrderOptionActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.view_pager)
    ViewPager pagerView;
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private ViewAnimationHelper helper;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_order_option);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("订单反馈");
        initView();
    }

    private void initView() {
        for (int i = 0; i < 2; i++) {
            OrderListFragment fragment = new OrderListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("status", i + "");
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        helper = new ViewAnimationHelper(this, img, 2, 70);
        helper.startAnimation(0);
        FragPagerAdapter adapter = new FragPagerAdapter(getSupportFragmentManager(), fragments);
        pagerView.setAdapter(adapter);
        pagerView.setOffscreenPageLimit(2);
        pagerView.setCurrentItem(0);
    }

    @Override
    protected void setListener() {
        super.setListener();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_finish:
                        helper.startAnimation(0);
                        pagerView.setCurrentItem(0);
                        break;
                    case R.id.btn_ing:
                        helper.startAnimation(1);
                        pagerView.setCurrentItem(1);
                        break;
                }
            }
        });
        pagerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                helper.startAnimation(position);
                RadioButton btn = (RadioButton) group.getChildAt(position);
                btn.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
