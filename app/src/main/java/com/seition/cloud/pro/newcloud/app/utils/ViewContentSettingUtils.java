package com.seition.cloud.pro.newcloud.app.utils;

import android.content.Context;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;

/**
 * Created by addis on 2018/7/6.
 */
public class ViewContentSettingUtils {
    public static void priceSetting(Context mContext, TextView priceTv, double price) {
        if (price == 0.0D) {
            priceTv.setTextColor(mContext.getResources().getColor(R.color.price_green_color));
            priceTv.setText(R.string.free);
        } else {
            priceTv.setTextColor(mContext.getResources().getColor(R.color.price_red_color));
            priceCheck(priceTv, price);
        }
    }

    public static void priceCheck(TextView priceTv, double price) {
        if (price == (int) price)
            priceTv.setText("¥" + (int) price);
        else
            priceTv.setText("¥" + price);
    }
//    public static void showCoverImg_Glide() {
//
//    }

    public static double getMallPrice(int price) {
        String[] array = MyConfig.credRatio.split(":");
        double newPrice = price;
        if (array.length > 1) {
            try {
                newPrice = price * Double.parseDouble(array[0]) / Double.parseDouble(array[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newPrice;
    }

    public static String getMallPrice(int price, Context mContext, boolean isUseDefault, boolean isCredPay) {
        String str = "";
        if (mContext != null)
            if (isUseDefault) {
                if (MyConfig.isOpenCredPay)
                    str = price + "积分";
                else {
                    str = mContext.getString(R.string.price_symbol) + getMallPrice(price);
                }
            } else if (isCredPay) {
                str = price + "积分";
            } else str = mContext.getString(R.string.price_symbol) + getMallPrice(price);
        return str;
    }
}
