package com.seition.cloud.pro.newcloud.app.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.seition.cloud.pro.newcloud.R;

/**
 * Created by xzw on 2018/6/7.
 */

public class AdapterViewUtils {

    public static View getEmptyViwe(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_empty_view, null, false);
    }

    public static View getEmptyViwesmall(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_empty_view_small, null, false);
    }

    public static View getErrorViwe(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.view_nonet, null);
    }

    public static View getNoDataViwe(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.footer_with_no_data, null);
    }
}
