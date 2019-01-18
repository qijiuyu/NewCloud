package com.seition.cloud.pro.newcloud.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.seition.cloud.pro.newcloud.app.config.MyConfig;

/**
 * Created by addis on 2018/10/18.
 */
public abstract class WXPAYReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case MyConfig.WXPAY_SUCCESS:
                exit();
                break;
        }
    }

    public abstract void exit();
}
