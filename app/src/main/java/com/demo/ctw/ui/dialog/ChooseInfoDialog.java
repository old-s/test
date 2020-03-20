package com.demo.ctw.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.demo.ctw.R;

import java.lang.reflect.Field;

/**
 * Created by qiu on 2018/7/4.
 */

public class ChooseInfoDialog extends Dialog {
    private Dialog dialog;

    public ChooseInfoDialog(Context context, String title, String reasons[], IChooseInfoListener listener) {
        super(context);
        setDialogView(context, title, reasons, listener);
    }

    private void setDialogView(Context context, String title, final String[] reasons, final IChooseInfoListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_info, null);
        final NumberPicker picker = (NumberPicker) view.findViewById(R.id.picker_info);
        TextView titleTxt = view.findViewById(R.id.txt_title);
        TextView submitTxt = (TextView) view.findViewById(R.id.txt_submit);
        TextView cancelTxt = (TextView) view.findViewById(R.id.txt_cancel);
        titleTxt.setText(title);
        submitTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) listener.getChooseInfo(reasons[picker.getValue()]);
            }
        });
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        picker.setDisplayedValues(reasons);
        picker.setMinValue(0);
        picker.setMaxValue(reasons.length - 1);
        picker.setWrapSelectorWheel(false);
//        if (reasons.length > 2) {
            picker.setValue(0);
//        }
        Field[] fields = NumberPicker.class.getDeclaredFields();
        for (Field f : fields)
            if (f.getName().equals("mSelectionDivider")) {
                f.setAccessible(true);
                try {
                    f.set(picker, new ColorDrawable(context.getResources().getColor(R.color.transparent)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });

        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = context.getResources().getDisplayMetrics().widthPixels;
        params.dimAmount = 0.5f;
        window.setAttributes(params);
    }

    public interface IChooseInfoListener {
        void getChooseInfo(String reason);
    }
}

