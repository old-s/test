package com.demo.ctw.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.demo.ctw.R;

/**
 * Created by qiu on 2018/9/5.
 */

public class LoadAlertDialog extends Dialog {
    public LoadAlertDialog(Context context) {
        super(context, R.style.alert_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_load_dialog);
    }
}
