package com.seition.cloud.pro.newcloud.home.mvp.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.app.utils.M;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by addis on 2018/9/19.
 */
public class ShowAgreement {

    public static void showAgreement(Context context, LayoutInflater layoutInflater, CheckBox agree, String key) {
        if (context == null || layoutInflater == null || agree == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogVview = layoutInflater.inflate(R.layout.dialog_webview, null);
        WebView webView = (WebView) dialogVview.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        Map<String, String> header = new HashMap<>();
        try {
            header.put("en-params", M.getEncryptData(MApplication.getCodedLock(), M.getMapString("key", key)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        webView.loadUrl(Service.DOMAIN_NAME + "basic.single", header);
        builder.setView(dialogVview);
        builder.setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agree.setChecked(true);
            }
        });
        builder.setNegativeButton(R.string.unagree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agree.setChecked(false);
            }
        });
        builder.show();
    }
}
