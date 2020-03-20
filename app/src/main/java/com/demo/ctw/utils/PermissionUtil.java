package com.demo.ctw.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by qiu on 2018/8/22.
 */

public class PermissionUtil {
    private static boolean ENABLE = true;
    private static int CODE = -1;

    public static boolean hasBasePhoneAuth(Activity activity, String authBaseArr[]) {
        // TODO Auto-generated method stub
        PackageManager pm = activity.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, activity.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void req(Activity activity, String permission[], int requestCode) {
        if (ENABLE) {
            CODE = requestCode;
            ActivityCompat.requestPermissions(activity, permission, CODE);
        }
    }

    public interface ResultListener {
        void onAgree();

        void onRefuse();
    }


    public static void onResult(int requestCode, int[] grantResults, ResultListener listener) {
        boolean ALL = true;
        if (ENABLE) {
            if (requestCode == CODE) {
                for (int auth : grantResults) {
                    if (auth != PackageManager.PERMISSION_GRANTED) {
                        ALL = false;
                    }
                }
                if (listener != null) {
                    if (ALL) listener.onAgree();
                    else listener.onRefuse();
                }
            }
        }
    }
}
