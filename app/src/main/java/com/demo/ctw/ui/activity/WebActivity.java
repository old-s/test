package com.demo.ctw.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网页
 */
public class WebActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.webview)
    WebView webView;
    private String title, url;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText(title);
        webSettings();
        webView.loadUrl(url);
    }

    private void webSettings() {
        //设置WebView的客户端
        webView.setWebViewClient(new WebViewClient());
//        webView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
        WebSettings webSettings = webView.getSettings();
        //执行JavaScript
        webSettings.setJavaScriptEnabled(true);
        //JavaScript自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持缩放
        webSettings.setSupportZoom(true);
        //调整图片大小
        webSettings.setUseWideViewPort(true);
        //默认字体
        webSettings.setDefaultFixedFontSize(12);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("title"))
            title = extras.getString("title");
        if (extras.containsKey("url"))
            url = extras.getString("url");
    }

    @OnClick({R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}