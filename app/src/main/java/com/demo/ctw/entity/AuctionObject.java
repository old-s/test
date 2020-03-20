package com.demo.ctw.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.demo.ctw.utils.CommonUtil;

public class AuctionObject implements Parcelable {
    private String address;
    private String companyName;
    private String shopcart;
    private String id;
    private String area;
    private String advScreen;
    private boolean isCheck = true;

    protected AuctionObject(Parcel in) {
        address = in.readString();
        companyName = in.readString();
        shopcart = in.readString();
        id = in.readString();
        area = in.readString();
        advScreen = in.readString();
        isCheck = in.readByte() != 0;
    }

    public static final Creator<AuctionObject> CREATOR = new Creator<AuctionObject>() {
        @Override
        public AuctionObject createFromParcel(Parcel in) {
            return new AuctionObject(in);
        }

        @Override
        public AuctionObject[] newArray(int size) {
            return new AuctionObject[size];
        }
    };

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getShopcart() {
        return CommonUtil.isEmpty(shopcart) ? "0" : shopcart;
    }

    public String getId() {
        return id;
    }

    public String getArea() {
        return CommonUtil.isEmpty(area) ? "0" : area;
    }

    public String getAdvScreen() {
        return CommonUtil.isEmpty(advScreen) ? "0" : advScreen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(companyName);
        dest.writeString(shopcart);
        dest.writeString(id);
        dest.writeString(area);
        dest.writeString(advScreen);
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }
}
