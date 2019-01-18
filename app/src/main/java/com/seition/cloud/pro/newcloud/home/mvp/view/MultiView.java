package com.seition.cloud.pro.newcloud.home.mvp.view;

import com.jess.arms.mvp.IView;

/**
 * Created by xzw on 2018/4/28.
 */

public interface MultiView extends IView {
    void showStateViewState(int state);
    void showSpingViewFooterEnable(boolean enabled);
}
