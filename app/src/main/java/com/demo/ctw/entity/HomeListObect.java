package com.demo.ctw.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeListObect implements Parcelable {
    private String title;
    private String date;
    private String address;
    private String count;
    private String time;
    private String price;
    private String img;

    public HomeListObect() {
    }

    protected HomeListObect(Parcel in) {
        title = in.readString();
        date = in.readString();
        address = in.readString();
        count = in.readString();
        time = in.readString();
        price = in.readString();
        img = in.readString();
    }

    public static final Creator<HomeListObect> CREATOR = new Creator<HomeListObect>() {
        @Override
        public HomeListObect createFromParcel(Parcel in) {
            return new HomeListObect(in);
        }

        @Override
        public HomeListObect[] newArray(int size) {
            return new HomeListObect[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(address);
        dest.writeString(count);
        dest.writeString(time);
        dest.writeString(price);
        dest.writeString(img);
    }
}
