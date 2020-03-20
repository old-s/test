package com.demo.ctw.ui.fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseFragment;
import com.demo.ctw.entity.AddressObject;
import com.demo.ctw.ui.activity.auction.AuctionListActivity;
import com.demo.ctw.ui.adapter.AddressAdapter;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.picker.AssetsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 省市区选择
 */
public class AddressChooseFragment extends BaseFragment {
    @BindView(R.id.list_pro)
    ListView proList;
    @BindView(R.id.list_city)
    ListView cityList;
    @BindView(R.id.list_area)
    ListView areaList;

    private AddressAdapter proAdapter = new AddressAdapter();
    private AddressAdapter cityAdapter = new AddressAdapter();
    private AddressAdapter areaAdapter = new AddressAdapter();
    private List<AddressObject> list;
    private ArrayList<AddressObject.City> cityLists;

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_address_choose;
    }

    @Override
    protected void processLogic() {
        String json = AssetsUtils.readText(getActivity(), "city.json");
        list = GsonTools.changeGsonToList(json, AddressObject.class);
        Log.i("data", "list:      " + list + "\n" + json);

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
    }

    @Override
    protected void setListener() {
        super.setListener();

        proList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                proAdapter.setClickPos(position);
                proAdapter.notifyDataSetChanged();

                if (position == 0)
                    return;
                cityLists = list.get(position - 1).getCities();
                ArrayList<AddressObject.Area> citys = new ArrayList<>();
                citys.add(new AddressObject.Area("", "全部"));
                for (int i = 0; i < cityLists.size(); i++) {
                    AddressObject.City city = cityLists.get(i);
                    citys.add(new AddressObject.Area(city.getAreaId(), city.getAreaName()));
                }
                cityAdapter.setData(citys);
                cityAdapter.setClickPos(0);
                cityAdapter.notifyDataSetChanged();
                areaAdapter.setData(new ArrayList<AddressObject.Area>());
                areaAdapter.notifyDataSetChanged();
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setClickPos(position);
                cityAdapter.notifyDataSetChanged();

                if (position == 0)
                    return;
                ArrayList<AddressObject.Area> counties = cityLists.get(position - 1).getCounties();
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
            }
        });
    }

    @OnClick({R.id.txt_cancel, R.id.txt_affirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:

                break;
            case R.id.txt_affirm:
                readyGo(AuctionListActivity.class);
                break;
        }
    }
}
