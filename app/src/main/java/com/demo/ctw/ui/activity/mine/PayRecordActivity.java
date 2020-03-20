package com.demo.ctw.ui.activity.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.PayRecordObject;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.ui.adapter.PayRecordAdapter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 缴费记录
 */
public class PayRecordActivity extends BaseMvpActivity<MinePresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_date)
    TextView dateTxt;
    @BindView(R.id.txt_pay)
    TextView payTxt;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.list)
    RecyclerView list;
    private PayRecordAdapter adapter = new PayRecordAdapter();
    private String date;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_pay_record);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("缴费记录");
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mvpPresenter.isMore()) mvpPresenter.morePayRecord(date);
                else refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.payRecord(date);
            }
        });
        mvpPresenter.payRecord(date);
        showLoading();
    }

    @Override
    public void loadData(Object object, String type) {
        CommonUtil.endRefresh(refreshLayout);
        showDialogDismiss();
        ArrayList<PayRecordObject> recordObjects = (ArrayList<PayRecordObject>) object;
        if (recordObjects.size() == 0 || recordObjects == null) showEmpty();
        else hideLoading();
        adapter.setItems(recordObjects);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.endRefresh(refreshLayout);
        CommonUtil.toast(msg);
        showError(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.payRecord(date);
                showLoading();
            }
        });
    }

    @OnClick({R.id.img_back, R.id.txt_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_date:
                TimePickerView timePickerView;
                timePickerView = new TimePickerBuilder(PayRecordActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date d, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                        String dateStr = format.format(d);
                        date = dateStr;
                        dateTxt.setText(dateStr);
                        mvpPresenter.payRecord(date);
                        showDialogLoading();
                    }
                }).setType(new boolean[]{true, true, false, false, false, false})
                        .build();
                timePickerView.show();
                break;
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return list;
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }
}
