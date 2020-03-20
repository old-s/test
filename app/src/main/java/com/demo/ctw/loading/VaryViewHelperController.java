package com.demo.ctw.loading;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.demo.ctw.R;


public class VaryViewHelperController {
    private VaryViewHelper helper;
    private boolean isShow = false;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(VaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    /**
     * 网络异常
     *
     * @param onClickListener
     */
    public void showNetError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.layout_net_error);
        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }
        helper.showLayout(layout);
        isShow = true;
    }

    /**
     * 显示异常
     *
     * @param errorMsg
     * @param onClickListener
     */
    public void showError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.layout_show_error);
        TextView txt = layout.findViewById(R.id.txt_show_info);
        if (!TextUtils.isEmpty(errorMsg)) {
            txt.setText(errorMsg);
        } else {
            txt.setText(helper.getContext().getResources().getString(R.string.show_error));
        }
        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }
        helper.showLayout(layout);
        isShow = true;
    }

    /**
     * 显示为空
     *
     * @param emptyMsg
     */
    public void showEmpty(String emptyMsg) {
        View layout = helper.inflate(R.layout.layout_show_empty);
        TextView txt = layout.findViewById(R.id.txt_show_info);
        if (!TextUtils.isEmpty(emptyMsg)) {
            txt.setText(emptyMsg);
        }
        helper.showLayout(layout);
        isShow = true;
    }

    /**
     * 加载中……
     *
     * @param msg
     */
    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.layout_loading);
        TextView textView = layout.findViewById(R.id.txt_loading);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        helper.showLayout(layout);
        isShow = true;
    }

    public void restore() {
        if (isShow) {
            helper.restoreView();
            isShow = false;
        }
    }

    public boolean isShow() {
        return isShow;
    }
}
