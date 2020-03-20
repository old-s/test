package com.demo.ctw.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.interfaces.IDialogClickInterface;

/**
 * Created by admin on 2017/11/23.
 */

public class ConfirmCancelDialog {
    private Dialog dialog;
    private Context context;

    public ConfirmCancelDialog(Context context, String title, IDialogClickInterface listener) {
        this.context = context;
        etContentView(title, listener);
    }

    private void etContentView(String title, final IDialogClickInterface listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_cancel, null);
        TextView titleTxt = (TextView) view.findViewById(R.id.txt_title);
        titleTxt.setText(title);
        TextView affirmTxt = (TextView) view.findViewById(R.id.txt_affirm);
        TextView cancelTxt = (TextView) view.findViewById(R.id.txt_cancel);
        affirmTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEnterClick(null);
                dialog.dismiss();
            }
        });

        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        int mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        layoutParams.width = mScreenWidth/4*3;
        dialog.getWindow().setAttributes(layoutParams);
    }
}
