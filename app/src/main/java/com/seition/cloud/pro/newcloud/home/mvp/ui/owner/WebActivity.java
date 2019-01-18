package com.seition.cloud.pro.newcloud.home.mvp.ui.owner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.seition.cloud.pro.newcloud.R;


import butterknife.BindView;

/**
 * Created by addis on 2016/12/15.
 */

public class WebActivity extends BaseActivity /*implements View.OnClickListener */{

    @BindView(R.id.webView)
    WebView webview;

     private String title_str, url;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_web;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        initWebView();
        loadUrl();
        setTitle(title_str);
    }


    private void initWebView() {
        webview.setHorizontalScrollBarEnabled(false);// 设置滑动条水平不显示
        webview.setVerticalScrollBarEnabled(false); // 设置滑动条垂直不显示
        webview.getSettings().setJavaScriptEnabled(true);// 支持javascript
        webview.getSettings().setSupportZoom(true);// 设置可以支持缩放
        webview.getSettings().setBuiltInZoomControls(true);// 设置出现缩放工具
        webview.getSettings().setUseWideViewPort(true);// 扩大比例的缩放
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 自适应屏幕
        webview.getSettings().setLoadWithOverviewMode(true);
//		webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setDomStorageEnabled(true);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 结束
                super.onPageFinished(view, url);
//                setRefresh(false);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 开始
                super.onPageStarted(view, url, favicon);
//                setRefresh(true);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void loadUrl() {
        Intent intent = getIntent();
        title_str = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        webview.loadUrl(url);
    }
}
