package com.demo.ctw.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.demo.ctw.utils.CommonUtil;

public class MarketObject implements Parcelable {
    private String brank;        //品牌
    private String companyName;        //名称
    private String region;        //省市区
    private String address;        //地址
    private String comType;        //超市类型 1连锁 2单店
    private String area;            //营业面积
    private String areaimg;            //营业面积证明文件
    private String shopcart;            //推车数量
    private String shopcartimg;            //推车数量证明文件
    private String advScreen;            //购物筐数量
    private String footfall;            //日客流量
    private String turnover;            //年营业额
    private String businessLicense;            //营业执照
    private String companyImg;            //店面照片
    private String lat;
    private String lng;
    private String aarea;
    private String carea;

    protected MarketObject(Parcel in) {
        brank = in.readString();
        companyName = in.readString();
        region = in.readString();
        address = in.readString();
        comType = in.readString();
        area = in.readString();
        areaimg = in.readString();
        shopcart = in.readString();
        shopcartimg = in.readString();
        advScreen = in.readString();
        footfall = in.readString();
        turnover = in.readString();
        businessLicense = in.readString();
        companyImg = in.readString();
        lat = in.readString();
        lng = in.readString();
        aarea = in.readString();
        carea = in.readString();
    }

    public static final Creator<MarketObject> CREATOR = new Creator<MarketObject>() {
        @Override
        public MarketObject createFromParcel(Parcel in) {
            return new MarketObject(in);
        }

        @Override
        public MarketObject[] newArray(int size) {
            return new MarketObject[size];
        }
    };

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getBrank() {
        return brank;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRegion() {
        return region;
    }

    public String getAddress() {
        return address;
    }

    public String getComType() {
        return comType;
    }

    public String getArea() {
        return CommonUtil.isDataEmpty(area);
    }

    public String getAreaimg() {
        return areaimg;
    }

    public String getShopcart() {
        return CommonUtil.isDataEmpty(shopcart);
    }

    public String getShopcartimg() {
        return shopcartimg;
    }

    public String getAdvScreen() {
        return CommonUtil.isDataEmpty(advScreen);
    }

    public String getFootfall() {
        return CommonUtil.isDataEmpty(footfall);
    }

    public String getTurnover() {
        return CommonUtil.isDataEmpty(turnover);
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public String getCompanyImg() {
        return companyImg;
    }

    public String getAarea() {
        return aarea;
    }

    public String getCarea() {
        return carea;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brank);
        dest.writeString(companyName);
        dest.writeString(region);
        dest.writeString(address);
        dest.writeString(comType);
        dest.writeString(area);
        dest.writeString(areaimg);
        dest.writeString(shopcart);
        dest.writeString(shopcartimg);
        dest.writeString(advScreen);
        dest.writeString(footfall);
        dest.writeString(turnover);
        dest.writeString(businessLicense);
        dest.writeString(companyImg);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(aarea);
        dest.writeString(carea);
    }
}
