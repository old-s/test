package com.demo.ctw.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.demo.ctw.base.BaseApplication;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.interfaces.IDialogClickInterface;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.ui.dialog.ConfirmCancelDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class CommonUtil {
    /**
     * 弹出toast信息
     *
     * @param info
     */
    public static void toast(String info) {
        if (!isEmpty(info))
            toast(info, false);
    }

    public static void toast(String info, boolean isLong) {
        if (isLong) {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), info, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(BaseApplication.getInstance().getApplicationContext(), info, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("");
    }


    /**
     * 手机匹配
     *
     * @param mobile
     * @return
     */
    public static boolean mobilePatten(String mobile) {
        String regExp = "^((11[0-9])|(13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.find();
    }


    public static RequestObject getRequest(RequestObject object) {
        Log.i("qwj", "data        " + GsonTools.createGsonString(object));
        String data = Base64.encodeToString(GsonTools.createGsonString(object).getBytes(), Base64.NO_WRAP).trim();
        String randomkey = SharePrefUtil.getString(BaseApplication.getInstance(), ShareKey.RANDOMKEY, "");
        RequestObject requestObject = new RequestObject();
        requestObject.setSign(MD5.encrypt(data + randomkey));
        requestObject.setObject(data);
        return requestObject;
    }

    public static boolean isLogin() {
        String userId = BaseApplication.getInstance().getUserId();
        return !isEmpty(userId);
    }

    public static void endRefresh(SmartRefreshLayout layout) {
        layout.finishLoadMore();
        layout.finishRefresh();
    }

    public static ArrayList<String> stringToList(String data, String split) {
        ArrayList<String> list = new ArrayList<>();
        String str[] = data.split(split);
        for (int i = 0; i < str.length; i++) {
            if (!isEmpty(str[i]))
                list.add(str[i]);
        }
        return list;
    }

    public static String listToString(List<String> list) {
        if (list.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                builder.append(list.get(i)).append(",");
            }
            return builder.toString().substring(0, builder.toString().length() - 1);
        } else {
            return "";
        }
    }

    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return bitmap;
    }

    public static void tel(final Context context, final String tel) {
        new ConfirmCancelDialog(context, tel, new IDialogClickInterface() {
            @Override
            public void onEnterClick(Object object) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                context.startActivity(intent);
            }
        });
    }

    public static boolean isAd() {
        String type = BaseApplication.getInstance().getType();
        return type.equals("2");
    }

    public static String getVersion(Context context) {
        String name = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0);
            name = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "v" + name;
    }

    public static String isDataEmpty(String info){
        return CommonUtil.isEmpty(info) ? "0" : info;
    }
}
