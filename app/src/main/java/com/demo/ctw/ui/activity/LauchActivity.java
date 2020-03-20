package com.demo.ctw.ui.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.entity.LocationObject;
import com.demo.ctw.interfaces.IDialogClickInterface;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.map.ILocationInterface;
import com.demo.ctw.map.LocationUtil;
import com.demo.ctw.ui.activity.login.LoginActivity;
import com.demo.ctw.ui.dialog.ReqPermissionDialog;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.PermissionUtil;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.utils.TestData;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 启动页
 */
public class LauchActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView img;
    private final static String authBaseArr[] = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final static int authBaseRequestCode = 1;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_lauch);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (!PermissionUtil.hasBasePhoneAuth(this, authBaseArr)) {
            PermissionUtil.req(this, authBaseArr, authBaseRequestCode);
        } else {
            agree();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onResult(requestCode, grantResults, new PermissionUtil.ResultListener() {
            @Override
            public void onAgree() {
                agree();
            }

            @Override
            public void onRefuse() {
                new ReqPermissionDialog(LauchActivity.this, new IDialogClickInterface() {
                    @Override
                    public void onEnterClick(Object object) {
                        PermissionUtil.req(LauchActivity.this, authBaseArr, authBaseRequestCode);
                    }
                });
            }
        });
    }

    private void agree() {
        Observable.timer(0, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                if (CommonUtil.isLogin()) readyGoThenKill(MainActivity.class);
                else readyGoThenKill(LoginActivity.class);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
        location();
    }

    private void location() {
        new LocationUtil().init(this, new ILocationInterface() {
            @Override
            public void getLocationLatLng(BDLocation bdLocation) {
                LocationObject object = new LocationObject();
                object.setRadius(bdLocation.getRadius());
                object.setDir(bdLocation.getDirection());
                object.setLat(bdLocation.getLatitude());
                object.setLng(bdLocation.getLongitude());
                object.setCity(bdLocation.getCity());
                SharePrefUtil.saveString(LauchActivity.this, ShareKey.LOCATION, GsonTools.createGsonString(object));
                Log.i("data", "loc       " + GsonTools.createGsonString(object));
            }
        });
    }
}
