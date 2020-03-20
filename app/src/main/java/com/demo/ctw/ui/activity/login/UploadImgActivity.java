package com.demo.ctw.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 上传图片
 */
public class UploadImgActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.txt_info)
    TextView infoTxt;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    private String type;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_upload_img);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (type.equals("yyzz")) {
            titleTxt.setText("上传营业执照");
            infoTxt.setText("拍摄/上传公司营业执照");
            img1.setImageResource(R.mipmap.ic_yyzz);
        } else {
            titleTxt.setText("上传证件");
            infoTxt.setText("拍摄/上传您的二代身份证");
            img1.setImageResource(R.mipmap.ic_sfz_z);
            img2.setImageResource(R.mipmap.ic_sfz_f);
        }
        int width = getResources().getDisplayMetrics().widthPixels - getResources().getDimensionPixelSize(R.dimen.dimen_130);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width / 8 * 5);
        img1.setLayoutParams(params);
        img2.setLayoutParams(params);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("type")) {
            type = extras.getString("type");
        }
    }

    @OnClick({R.id.img_back, R.id.img1, R.id.img2, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img1:
                break;
            case R.id.img2:

                break;
            case R.id.txt_submit:
                finish();
                break;
        }
    }
}
