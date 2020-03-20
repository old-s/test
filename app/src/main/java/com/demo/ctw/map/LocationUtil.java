package com.demo.ctw.map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.demo.ctw.R;
import com.demo.ctw.entity.LocationObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;

/**
 * Created by admin on 2017/12/13.
 */

public class LocationUtil {
    private static LocationUtil util;

    public static LocationUtil getInstance() {
        if (util == null) {
            synchronized (LocationUtil.class) {
                if (util == null) {
                    util = new LocationUtil();
                }
            }
        }
        return util;
    }

    public void init(final Context context, final ILocationInterface iLocationInterface) {
        //声明locationClient类
        LocationClient locationClient = new LocationClient(context);
        //注册监听函数
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation == null)
                    return;
                int locType = bdLocation.getLocType();
                Log.i("qwj", "loc          " + locType);
                if (locType == 61 || locType == 161 || locType == 66) {
                    Log.i("qwj", "lat    " + bdLocation.getLatitude() + "\nlng        " + bdLocation.getLongitude());
                    iLocationInterface.getLocationLatLng(bdLocation);
                } else {
                    Toast.makeText(context, "定位失败 " + locType, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //配置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); //设置定位模式  Hight_Accuracy：高精度  Battery_Saving：低功耗   Device_Sensors：仅使用设备
        option.setOpenGps(true);//设置是否使用gps
        option.setIsNeedAddress(true);//是否需要地址信息
        option.setCoorType("bd09ll");//设置返回经纬度坐标类型，默认gcj02     gcj02：国测局坐标    bd0911：百度经纬度坐标   bd09：百度墨卡托坐标
        option.setScanSpan(0);//设置发起定位请求间隔    0：仅定位一次   非0：设置1000ms以上有效
        option.setIsNeedAddress(true);
        option.setLocationNotify(false); //设置是否当GPS有效时按照1s/1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//是否在stop的时候杀死这个进程
        option.disableCache(true);//禁止启用缓存定位
        locationClient.setLocOption(option);
        locationClient.start();
        locationClient.requestLocation();
    }

    /**
     * 显示定位点
     *
     * @param context
     * @param map
     */
    public void location(final Context context, final BaiduMap map) {
        map.setMyLocationEnabled(true);
        final String location = SharePrefUtil.getString(context, ShareKey.LOCATION, "");
        if (!CommonUtil.isEmpty(location)) {
            LocationObject object = GsonTools.changeGsonToBean(location, LocationObject.class);

            double lat = object.getLat();
            double lng = object.getLng();
            map.setMyLocationEnabled(true);
            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(object.getRadius())
                    .direction(100)
                    .latitude(lat).longitude(lng)
                    .build();
            //设置定位数据
            map.setMyLocationData(locationData);
            //设置中心点
            LatLng ll = new LatLng(lat, lng);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(15.0f);
            map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        } else {
            CommonUtil.toast("定位失败，请检查是否开启定位权限");
        }
    }

//    public void moveToLocation(Context context,BaiduMap map) {
//        final String location = SharePrefUtil.getString(context, ShareKey.LOCATION, "");
//        if (!CommonUtil.isEmpty(location)) {
//            LocationObject object = GsonTools.changeGsonToBean(location, LocationObject.class);
//
//            double lat = object.getLat();
//            double lng = object.getLng();
//            LatLng ll = new LatLng(lat, lng);
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.target(ll).zoom(18.0f);
//            map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//        }
//    }

    /**
     * 添加标记点
     *
     * @param latitude
     * @param longitude
     * @param map
     */
    public void addMarket(double latitude, double longitude, BaiduMap map) {
        map.clear();
        LatLng latLng = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_mark_blue);
        //构建MarkerOptions，添加marker
        map.addOverlay(new MarkerOptions().position(latLng).icon(bitmap));
    }

    /**
     * 添加标记点
     *
     * @param latitude
     * @param longitude
     * @param map
     */
    public void addMarketCenter(double latitude, double longitude, BaiduMap map) {
        map.clear();
        LatLng latLng = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_mark_blue);
        //构建MarkerOptions，添加marker
        map.addOverlay(new MarkerOptions().position(latLng).icon(bitmap));
        LatLng ll = new LatLng(latitude, longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(15.0f);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }

}
