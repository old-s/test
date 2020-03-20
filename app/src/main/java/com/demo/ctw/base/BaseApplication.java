package com.demo.ctw.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.demo.ctw.R;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by qiu on 2018/9/5.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private String token = "";
    private String userId = "";
    private String type = "";

    public static BaseApplication getInstance() {
        return instance;
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.C3, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        String userinfo = SharePrefUtil.getString(this, ShareKey.USERINFO, "");
        if (!CommonUtil.isEmpty(userinfo)) {
            UserObject userObject = GsonTools.changeGsonToBean(userinfo, UserObject.class);
            if (userObject != null) {
                userId = userObject.getId();
                type = userObject.getType();
            }
        }
        token = SharePrefUtil.getString(this, ShareKey.TOKEN, "");
        initThird();
    }

    /**
     * 初始化第三方
     */
    private void initThird() {
        /**
         * 极光推送
         */
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        String registrationID = JPushInterface.getRegistrationID(this);
        if (!CommonUtil.isEmpty(registrationID))
            SharePrefUtil.saveString(this, ShareKey.JPUSH_REGISTID, registrationID);
        Log.i("JPush", registrationID);
        /**
         * 百度地图
         */
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharePrefUtil.saveString(this, ShareKey.TOKEN, token);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void clear() {
        SharePrefUtil.saveString(this, ShareKey.TOKEN, "");
        SharePrefUtil.saveString(this, ShareKey.RANDOMKEY, "");
        SharePrefUtil.saveString(this, ShareKey.USERINFO, "");
        setUserId("");
        setToken("");
        setType("");
        SharePrefUtil.saveBoolean(this, ShareKey.IS_JPUSH, false);
    }

    private Context context;

    public void setActivityContext(Context context) {
        this.context = context;
    }

    public Context getActivityContext() {
        return context;
    }
}
