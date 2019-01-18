package com.seition.cloud.pro.newcloud.home.mvp.view;


import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.seition.cloud.pro.newcloud.R;

/**
 * Created by BlingBling on 2016/10/15.
 */

public final class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more_adapter;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
