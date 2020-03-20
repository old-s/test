package com.demo.ctw.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class RegisterObject implements Parcelable {
    private String tel;
    private String code;
    private String psw;

    public RegisterObject() {
    }

    protected RegisterObject(Parcel in) {
        tel = in.readString();
        code = in.readString();
        psw = in.readString();
    }

    public static final Creator<RegisterObject> CREATOR = new Creator<RegisterObject>() {
        @Override
        public RegisterObject createFromParcel(Parcel in) {
            return new RegisterObject(in);
        }

        @Override
        public RegisterObject[] newArray(int size) {
            return new RegisterObject[size];
        }
    };

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tel);
        dest.writeString(code);
        dest.writeString(psw);
    }
}
