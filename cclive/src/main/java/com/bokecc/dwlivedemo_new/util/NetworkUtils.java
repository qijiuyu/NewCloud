package com.bokecc.dwlivedemo_new.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.bokecc.dwlivedemo_new.DWApplication;

/**
 * 网络工具类
 * Created by renhui on 2017/8/17.
 */
public class NetworkUtils {

    public static boolean isFastMobileNetwork(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            default:
                return true;
        }
    }

    private static boolean isNetworkAvailable(boolean isFast, Context context) {
        if (context == null) return false;
//        Context ctx = DWApplication.getContext();
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //  如果设备不支持此类型的网络链接会返回null
        NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isFiltered = true;
        if (isFast && mobile != null) {
            int subType = mobile.getSubtype();
            isFiltered = isFastMobileNetwork(subType);
        }
        if (mobile == null && wifi == null) {
            return false;
        } else if (mobile != null && wifi == null) {
            return mobile.isConnected() && isFiltered;
        } else if (wifi != null && mobile == null) {
            return wifi.isConnected();
        } else {
            return wifi.isConnected() || (mobile.isConnected() && isFiltered);
        }
    }

    public static boolean isWifiNetorkAvailable() {
        Context ctx = DWApplication.getContext();
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifi != null && wifi.isConnected();
    }

    public static boolean isFastNetworkAvailable(Context context) {
        return isNetworkAvailable(true, context);
    }

    public static boolean isNetworkAvailable(Context context) {
        return isNetworkAvailable(false, context);
    }

}
