package com.demo.ctw.utils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.demo.ctw.base.ActivityManager;
import com.demo.ctw.base.BaseApplication;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.ui.activity.login.LoginActivity;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by admin on 2017/12/11.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            SharePrefUtil.saveString(context, ShareKey.JPUSH_REGISTID, regId);
            Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
            Toast.makeText(context, bundle.getString(JPushInterface.EXTRA_MESSAGE), Toast.LENGTH_SHORT).show();
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.i(TAG, "message        " + message + "\nextra         " + extra + "\nalert        " + alert);
            ReceiverObject object = GsonTools.changeGsonToBean(extra, ReceiverObject.class);
            if (object != null & object.getType().equals("3")) {
                readyGoLogin(context, alert);
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 用户点击打开了通知");
            ReceiverObject object = GsonTools.changeGsonToBean(bundle.getString(JPushInterface.EXTRA_EXTRA), ReceiverObject.class);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.i(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void readyGoLogin(Context context, String alert) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
        BaseApplication.getInstance().clear();
        ActivityManager.getInstance().exit();
        CommonUtil.toast(alert);
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        return "";
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
    }
}
