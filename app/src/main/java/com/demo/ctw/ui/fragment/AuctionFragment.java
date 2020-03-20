package com.demo.ctw.ui.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpFragment;
import com.demo.ctw.entity.AuctionObject;
import com.demo.ctw.entity.BrandObject;
import com.demo.ctw.interfaces.IDialogClickInterface;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.presenter.AuctionPresenter;
import com.demo.ctw.ui.activity.auction.AffirmAuctionActivity;
import com.demo.ctw.ui.activity.auction.MarketMapActivity;
import com.demo.ctw.ui.adapter.AuctionAdapter;
import com.demo.ctw.ui.dialog.AddressChooseDialog;
import com.demo.ctw.ui.dialog.AddressChoosePopDialog;
import com.demo.ctw.ui.view.flowlayout.FlowAdapter;
import com.demo.ctw.ui.view.flowlayout.FlowLayout;
import com.demo.ctw.ui.view.flowlayout.FlowObject;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.picker.AddressInitTask;
import com.demo.ctw.utils.picker.AddressPicker;
import com.demo.ctw.view.ILoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 竞拍
 */
public class AuctionFragment extends BaseMvpFragment<AuctionPresenter> implements ILoadView {
    @BindView(R.id.layout)
    SmartRefreshLayout layout;
    @BindView(R.id.list)
    RecyclerView list;
    private AuctionAdapter adapter = new AuctionAdapter();
    private String region, brank, comType;
    private View parentView;
    private ArrayList<AuctionObject> auctionObjects;
    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_auction;
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        layout.setEnableLoadMore(false);
        layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                region = "";
                brank = "";
                comType = "";
                mvpPresenter.auctionList(region, brank, comType);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        mvpPresenter.auctionList(region, brank, comType);
        showLoading();
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnItemClicktListener(new IRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(Object object, String type) {
                AuctionObject auctionObject = (AuctionObject) object;
                Bundle bundle = new Bundle();
                bundle.putString("id", auctionObject.getId());
                readyGo(MarketMapActivity.class, bundle);
            }
        });
    }

    @Override
    public void loadData(Object object, String type) {
        showDialogDismiss();
        switch (type) {
            case "brand":
                ArrayList<BrandObject> brandObjects = (ArrayList<BrandObject>) object;
                if (brandObjects.size() == 0 || brandObjects == null) {
                    CommonUtil.toast("品牌列表为空");
                    return;
                }
                ArrayList<FlowObject> data = new ArrayList<>();
                FlowObject object1 = new FlowObject();
                object1.setCheck(true);
                object1.setName("全部");
                object1.setId("0");
                data.add(object1);
                for (int i = 0; i < brandObjects.size(); i++) {
                    BrandObject brandObject = brandObjects.get(i);
                    FlowObject flowObject = new FlowObject();
                    flowObject.setCheck(false);
                    flowObject.setId(brandObject.getId());
                    flowObject.setName(brandObject.getBname());
                    data.add(flowObject);
                }
                showBrandDialog(data, "brand");
                break;
            case "list":
                layout.finishRefresh();
                auctionObjects = (ArrayList<AuctionObject>) object;
                if (auctionObjects.size() == 0 || auctionObjects == null) showEmpty();
                else hideLoading();
                adapter.setItems(auctionObjects);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void loadFail(String msg, String type) {
        CommonUtil.toast(msg);
        layout.finishRefresh();
        if (type.equals("list")) {
            showError(msg, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mvpPresenter.auctionList(region, brank, comType);
                    showLoading();
                }
            });
        }
    }

    @OnClick({R.id.layout_address, R.id.layout_brand, R.id.layout_type, R.id.txt_auction})
    public void onClikc(View view) {
        switch (view.getId()) {
            case R.id.layout_address:
                new AddressChooseDialog(getContext(), view, new IDialogClickInterface() {
                    @Override
                    public void onEnterClick(Object object) {
                        region = object.toString();
                        Log.i("data", "region        " + region);
                        mvpPresenter.auctionList(region, brank, comType);
                        showDialogLoading();
                    }
                });
                break;
            case R.id.layout_brand:
                parentView = view;
                mvpPresenter.brandList();
                break;
            case R.id.layout_type:
                ArrayList<FlowObject> data = new ArrayList<>();
                String tags[] = {"全部", "连锁", "单店"};
                for (int i = 0; i < 3; i++) {
                    FlowObject object = new FlowObject();
                    object.setId(i + "");
                    object.setName(tags[i]);
                    if (i == 0) object.setCheck(true);
                    else object.setCheck(false);
                    data.add(object);
                }
                showBrandDialog(data, "type");
                break;
            case R.id.txt_auction:
                ArrayList<AuctionObject> checks = new ArrayList<>();
                ArrayList<String> marketIds = new ArrayList<>();
                for (int i = 0; i < auctionObjects.size(); i++) {
                    AuctionObject object = auctionObjects.get(i);
                    if (object.isCheck()) {
                        checks.add(object);
                        marketIds.add(object.getId());
                    }
                }
                if (checks.size() == 0 || checks == null) {
                    CommonUtil.toast("请先选择要竞拍的超市");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data", checks);
                bundle.putString("area",region);
                bundle.putString("ids", CommonUtil.listToString(marketIds));
                readyGo(AffirmAuctionActivity.class, bundle);
                break;
        }
    }

    /**
     * 对话框
     */
    private void showBrandDialog(final ArrayList<FlowObject> data, final String type) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_brand, null);
        FlowLayout classifyView = view.findViewById(R.id.view_classify);
        TextView cancelTxt = view.findViewById(R.id.txt_cancel);
        TextView affirmTxt = view.findViewById(R.id.txt_affirm);
        TextView bgTxt = view.findViewById(R.id.txt_bg);

        final FlowAdapter flowAdapter = new FlowAdapter(getContext(), data);
        classifyView.setAdapter(flowAdapter);

        int height = getResources().getDisplayMetrics().heightPixels - getResources().getDimensionPixelSize(R.dimen.dimen_100);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, height);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parentView);       //设置popWindow以下拉的方式展现

        classifyView.setItemClickListener(new FlowLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                changeCheckStates(data, position);
                flowAdapter.notifyDataSetChanged();
            }
        });
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        affirmTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> ids = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isCheck()) ids.add(data.get(i).getId());
                }
                if (ids.size() == 0 || ids == null) {
                    popupWindow.dismiss();
                    return;
                }
                switch (type) {
                    case "brand":
                        brank = CommonUtil.listToString(ids);
                        break;
                    case "type":
                        comType = CommonUtil.listToString(ids);
                        break;
                }
                mvpPresenter.auctionList(region, brank, comType);
                popupWindow.dismiss();
                showDialogLoading();
            }
        });
        bgTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 改变选择状态
     *
     * @param data
     * @param position
     */
    private void changeCheckStates(ArrayList<FlowObject> data, int position) {
        if (data.get(position).isCheck()) {
            data.get(position).setCheck(false);
        } else {
            if (position == 0) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setCheck(false);
                }
                data.get(0).setCheck(true);
            } else {
                data.get(0).setCheck(false);
                data.get(position).setCheck(true);
            }
        }
    }

    @Override
    protected AuctionPresenter createPresenter() {
        return new AuctionPresenter(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return list;
    }
}
