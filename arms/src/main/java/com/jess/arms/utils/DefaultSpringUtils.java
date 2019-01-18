package com.jess.arms.utils;

import android.content.Context;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * Created by addis on 2018/3/26.
 */

public class DefaultSpringUtils {
    public static SpringView.DragHander getRefreshHeaderView(Context context) {
        if (context != null)
            return new DefaultHeader(context);
        return null;
    }

    public static SpringView.DragHander getLoadMoreFooterView(Context context) {
        if (context != null)
            return new DefaultFooter(context);
        return null;
    }
}
