package com.demo.ctw.ui.activity.auction;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseActivity;
import com.demo.ctw.entity.HomeListObect;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 竞拍详情
 */
public class AuctionActivity extends BaseActivity {
    @BindView(R.id.web)
    WebView webView;
    @BindView(R.id.txt_price)
    TextView priceTxt;

    private HomeListObect object;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_auction_detail);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        webSettings();
        webView.loadUrl("http://172.167.40.121:8081/static/index.html");

        if (object != null) priceTxt.setText("保证金¥3000");
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("data")) {
            object = extras.getParcelable("data");
        }
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

    @OnClick({R.id.img_back, R.id.txt_auction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_auction:
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", object);
                readyGo(AffirmAuctionActivity.class, bundle);
                break;
        }
    }
}
