package com.demo.ctw.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.ui.view.ZoomImageView;
import com.demo.ctw.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查看大图
 */
public class LookPicActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.img)
    ZoomImageView img;
    private String title, imgUrl;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_look_pic);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (CommonUtil.isEmpty(title)) titleTxt.setText("查看大图");
        else titleTxt.setText(title);
        Log.i("data", "url         " + imgUrl);
        Glide.with(this).load(ApiService.API_SERVICE + imgUrl).into(img);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("title"))
            title = extras.getString("title");
        if (extras.containsKey("imgUrl")) {
            imgUrl = extras.getString("imgUrl");
        }
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }
}
