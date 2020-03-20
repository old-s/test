package com.demo.ctw.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.interfaces.IDialogClickInterface;


/**
 * Created by qiu on 2018/8/22.
 * 权限请求对话框
 */

public class ReqPermissionDialog {
    private Dialog dialog;
    private Context context;

    public ReqPermissionDialog(Context context, IDialogClickInterface listener) {
        this.context = context;
        etContentView(listener);
    }

    private void etContentView(final IDialogClickInterface listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_permission, null);

        TextView agreeTxt = (TextView) view.findViewById(R.id.txt_agree);
        agreeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onEnterClick(null);
                    dialog.dismiss();
                }
            }
        });

        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels - context.getResources().getDimensionPixelSize(R.dimen.dimen_60);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}
