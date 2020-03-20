package com.demo.ctw.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.entity.AddressObject;
import com.demo.ctw.interfaces.IDialogClickInterface;
import com.demo.ctw.ui.activity.auction.AuctionListActivity;
import com.demo.ctw.ui.adapter.AddressAdapter;
import com.demo.ctw.ui.view.flowlayout.FlowAdapter;
import com.demo.ctw.ui.view.flowlayout.FlowLayout;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.picker.AssetsUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baidu.mapapi.BMapManager.getContext;

public class AddressChooseDialog extends PopupWindow {
    private ListView proList, cityList, areaList;
    private AddressAdapter proAdapter = new AddressAdapter();
    private AddressAdapter cityAdapter = new AddressAdapter();
    private AddressAdapter areaAdapter = new AddressAdapter();
    private List<AddressObject> list;
    private ArrayList<AddressObject.City> cityLists;
    private Context context;
    private PopupWindow popupWindow;
    private String pro = "", city = "", area = "";
    private ArrayList<AddressObject.Area> counties;

    public AddressChooseDialog(Context context, View parentView, IDialogClickInterface listener) {
        super(context);
        this.context = context;
        setContentview(parentView, listener);
    }

    private void setContentview(View parentView, final IDialogClickInterface listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_address_choose, null);
        proList = view.findViewById(R.id.list_pro);
        cityList = view.findViewById(R.id.list_city);
        areaList = view.findViewById(R.id.list_area);
        view.findViewById(R.id.txt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.txt_affirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEnterClick(pro + city + area);
                }
                dismiss();
            }
        });
        view.findViewById(R.id.txt_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        loadData();

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        int height = context.getResources().getDisplayMetrics().heightPixels - context.getResources().getDimensionPixelSize(R.dimen.dimen_80) - result;
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, height);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parentView);       //设置popWindow以下拉的方式展现
    }

    private void loadData() {
        String json = AssetsUtils.readText(context, "city.json");
        list = GsonTools.changeGsonToList(json, AddressObject.class);

        ArrayList<AddressObject.Area> pros = new ArrayList<>();
        pros.add(new AddressObject.Area("", "全国"));
        for (int i = 0; i < list.size(); i++) {
            pros.add(new AddressObject.Area(list.get(i).getAreaId(), list.get(i).getAreaName()));
        }
        proAdapter.setData(pros);
        proAdapter.setClickPos(0);
        proList.setAdapter(proAdapter);

        cityList.setAdapter(cityAdapter);
        areaList.setAdapter(areaAdapter);

        setListener();
    }

    private void setListener() {
        proList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                proAdapter.setClickPos(position);
                proAdapter.notifyDataSetChanged();

                if (position == 0) {
                    pro = "";
                    city = "";
                    area = "";
                    cityAdapter.setData(new ArrayList<AddressObject.Area>());
                    cityAdapter.notifyDataSetChanged();
                    areaAdapter.setData(new ArrayList<AddressObject.Area>());
                    areaAdapter.notifyDataSetChanged();
                    return;
                }

                cityLists = list.get(position - 1).getCities();
                pro = list.get(position - 1).getAreaName();
                ArrayList<AddressObject.Area> citys = new ArrayList<>();
                citys.add(new AddressObject.Area("", "全部"));
                for (int i = 0; i < cityLists.size(); i++) {
                    AddressObject.City city = cityLists.get(i);
                    citys.add(new AddressObject.Area(city.getAreaId(), city.getAreaName()));
                }
                cityAdapter.setData(citys);
                cityAdapter.setClickPos(0);
                cityAdapter.notifyDataSetChanged();
                area = "";
                areaAdapter.setData(new ArrayList<AddressObject.Area>());
                areaAdapter.notifyDataSetChanged();
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setClickPos(position);
                cityAdapter.notifyDataSetChanged();

                if (position == 0) {
                    city = "";
                    area = "";
                    areaAdapter.setData(new ArrayList<AddressObject.Area>());
                    areaAdapter.notifyDataSetChanged();
                    return;
                }
                counties = cityLists.get(position - 1).getCounties();
                city = cityLists.get(position - 1).getAreaName();
                ArrayList<AddressObject.Area> areas = new ArrayList<>();
                areas.add(new AddressObject.Area("", "全部"));
                for (int i = 0; i < counties.size(); i++) {
                    AddressObject.Area area = counties.get(i);
                    areas.add(new AddressObject.Area(area.getAreaId(), area.getAreaName()));
                }
                areaAdapter.setData(areas);
                areaAdapter.setClickPos(0);
                areaAdapter.notifyDataSetChanged();
            }
        });

        areaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setClickPos(position);
                areaAdapter.notifyDataSetChanged();
                if (position == 0) {
                    area = "";
                    return;
                }
                area = counties.get(position - 1).getAreaName();
            }
        });
    }

//    @OnClick({R.id.txt_cancel, R.id.txt_affirm})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.txt_cancel:
//                dismiss();
//                break;
//            case R.id.txt_affirm:
//                if (listener != null) listener.onEnterClick(pro + city + area);
//                dismiss();
//                break;
//        }
//    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
    }
}

