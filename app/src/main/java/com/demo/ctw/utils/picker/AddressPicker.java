package com.demo.ctw.utils.picker;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 地址选择器（包括省级、地级、县级）。
 * 地址数据见示例项目的“city.json”，来源于国家统计局官网（http://www.stats.gov.cn/tjsj/tjbz/xzqhdm）
 *
 * @author 李玉江[QQ :1032694760]
 * @version 2015 /12/15
 */
public class AddressPicker extends WheelPicker {
    private ArrayList<Province> provinceList = new ArrayList<>();
    private ArrayList<ArrayList<City>> cityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<Area>>> countyList = new ArrayList<>();
    private OnAddressPickListener onAddressPickListener;
    private Area selectedProvince, selectedCity, selectedCounty;
    private int selectedProvinceIndex = 0, selectedCityIndex = 0, selectedCountyIndex = 0;
    private boolean hideProvince = false;

    /**
     * Instantiates a new Address picker.
     *
     * @param activity the activity
     * @param data     the data
     */
    public AddressPicker(Activity activity, ArrayList<Province> data) {
        super(activity);
        int provinceSize = data.size();
        //添加省
        for (int x = 0; x < provinceSize; x++) {
            Province pro = data.get(x);
            provinceList.add(pro);
            ArrayList<City> cities = pro.getCities();
            ArrayList<City> xCities = new ArrayList<>();
            ArrayList<ArrayList<Area>> xCounties = new ArrayList<>();
            int citySize = cities.size();
            //添加地市
            for (int y = 0; y < citySize; y++) {
                City cit = cities.get(y);
                xCities.add(cit);
                ArrayList<County> counties = cit.getCounties();
                ArrayList<Area> yCounties = new ArrayList<>();
                int countySize = counties.size();
                //添加区县
                if (countySize == 0) {
                    yCounties.add(cit);
                } else {
                    for (int z = 0; z < countySize; z++) {
                        yCounties.add(counties.get(z));
                    }
                }
                xCounties.add(yCounties);
            }
            cityList.add(xCities);
            countyList.add(xCounties);
        }
    }

    /**
     * Sets selected item.
     */
    public void setSelectedItem(String provinceId, String cityId, String countyId) {
        for (int i = 0; i < provinceList.size(); i++) {
            Province pro = provinceList.get(i);
            if (pro.getAreaId().equals(provinceId)) {
                selectedProvinceIndex = i;
                break;
            }
        }
        ArrayList<City> cities = cityList.get(selectedProvinceIndex);
        for (int j = 0; j < cities.size(); j++) {
            City cit = cities.get(j);
            if (cit.getAreaId().equals(cityId)) {
                selectedCityIndex = j;
                break;
            }
        }
        ArrayList<Area> counties = countyList.get(selectedProvinceIndex).get(selectedCityIndex);
        for (int k = 0; k < counties.size(); k++) {
            Area cou = counties.get(k);
            if (cou.getAreaId().contains(countyId)) {
                selectedCountyIndex = k;
                break;
            }
        }
    }

    /**
     * 隐藏省级行政区，只显示地市级和区县级。
     * 设置为true的话，地址数据中只需要某个省份的即可
     * 参见示例中的“city2.json”
     *
     * @param hideProvince the hide province
     */
    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    /**
     * Sets on address pick listener.
     *
     * @param listener the listener
     */
    public void setOnAddressPickListener(OnAddressPickListener listener) {
        this.onAddressPickListener = listener;
    }

    @Override
    protected View initContentView() {
        if (provinceList.size() == 0) {
            throw new IllegalArgumentException("please initial options at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        int screenWidth = screen.widthPixels;
        final CalendarWheelView provinceView = new CalendarWheelView(activity);
        provinceView.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 3, WRAP_CONTENT));
        provinceView.setTextSize(textSize);
        provinceView.setTextColor(textColorNormal, textColorFocus);
        provinceView.setLineVisible(lineVisible);
        provinceView.setLineColor(lineColor);
        provinceView.setOffset(offset);
        layout.addView(provinceView);
        if (hideProvince) {
            provinceView.setVisibility(View.GONE);
        }
        final CalendarWheelView cityView = new CalendarWheelView(activity);
        cityView.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 3, WRAP_CONTENT));
        cityView.setTextSize(textSize);
        cityView.setTextColor(textColorNormal, textColorFocus);
        cityView.setLineVisible(lineVisible);
        cityView.setLineColor(lineColor);
        cityView.setOffset(offset);
        layout.addView(cityView);
        final CalendarWheelView countyView = new CalendarWheelView(activity);
        countyView.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 3, WRAP_CONTENT));
        countyView.setTextSize(textSize);
        countyView.setTextColor(textColorNormal, textColorFocus);
        countyView.setLineVisible(lineVisible);
        countyView.setLineColor(lineColor);
        countyView.setOffset(offset);
        layout.addView(countyView);
        provinceView.setItemsProvince(provinceList, selectedProvinceIndex);
        provinceView.setOnWheelViewListener(new CalendarWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, Area item) {
                selectedProvince = item;
                selectedProvinceIndex = selectedIndex;
                selectedCountyIndex = 0;
                //根据省份获取地市
                cityView.setItemsCity(cityList.get(selectedProvinceIndex), isUserScroll ? 0 : selectedCityIndex);
                //根据地市获取区县
                countyView.setItems(countyList.get(selectedProvinceIndex).get(0), isUserScroll ? 0 : selectedCountyIndex);
            }
        });
        cityView.setItemsCity(cityList.get(selectedProvinceIndex), selectedCityIndex);
        cityView.setOnWheelViewListener(new CalendarWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, Area item) {
                selectedCity = item;
                selectedCityIndex = selectedIndex;
                //根据地市获取区县
                countyView.setItems(countyList.get(selectedProvinceIndex).get(selectedCityIndex), isUserScroll ? 0 : selectedCountyIndex);
            }
        });
        countyView.setItems(countyList.get(selectedProvinceIndex).get(selectedCityIndex), selectedCountyIndex);
        countyView.setOnWheelViewListener(new CalendarWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, Area item) {
                selectedCounty = item;
                selectedCountyIndex = selectedIndex;
            }
        });
        return layout;
    }

    @Override
    protected void setContentViewAfter(View contentView) {
        super.setContentViewAfter(contentView);
        super.setOnConfirmListener(new OnConfirmListener() {
            @Override
            public void onConfirm() {
                if (onAddressPickListener != null) {
                    onAddressPickListener.onAddressPicked(selectedProvince, selectedCity, selectedCounty);
                }
            }
        });
    }

    /**
     * The type Area.
     */
    public abstract static class Area {
        /**
         * The Area id.
         */
        String areaId;
        /**
         * The Area name.
         */
        String areaName;

        /**
         * Gets area id.
         *
         * @return the area id
         */
        public String getAreaId() {
            return areaId;
        }

        /**
         * Sets area id.
         *
         * @param areaId the area id
         */
        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        /**
         * Gets area name.
         *
         * @return the area name
         */
        public String getAreaName() {
            return areaName;
        }

        /**
         * Sets area name.
         *
         * @param areaName the area name
         */
        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        @Override
        public String toString() {
            return "areaId=" + areaId + ",areaName=" + areaName;
        }

    }

    /**
     * The type Province.
     */
    public static class Province extends Area {
        /**
         * The Cities.
         */
        ArrayList<City> cities = new ArrayList<>();

        /**
         * Gets cities.
         *
         * @return the cities
         */
        public ArrayList<City> getCities() {
            return cities;
        }

        /**
         * Sets cities.
         *
         * @param cities the cities
         */
        public void setCities(ArrayList<City> cities) {
            this.cities = cities;
        }

    }

    /**
     * The type City.
     */
    public static class City extends Area {
        private ArrayList<County> counties = new ArrayList<>();

        /**
         * Gets counties.
         *
         * @return the counties
         */
        public ArrayList<County> getCounties() {
            return counties;
        }

        /**
         * Sets counties.
         *
         * @param counties the counties
         */
        public void setCounties(ArrayList<County> counties) {
            this.counties = counties;
        }

    }

    /**
     * The type County.
     */
    public static class County extends Area {
    }

    /**
     * The interface On address pick listener.
     */
    public interface OnAddressPickListener {

        /**
         * On address picked.
         *
         * @param province the province
         * @param city     the city
         * @param county   the county
         */
        void onAddressPicked(Area province, Area city, Area county);

    }

}
