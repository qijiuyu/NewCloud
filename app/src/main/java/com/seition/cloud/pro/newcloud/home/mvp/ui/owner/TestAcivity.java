package com.seition.cloud.pro.newcloud.home.mvp.ui.owner;

import android.os.Bundle;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.kennyc.view.MultiStateView;
import com.seition.cloud.pro.newcloud.R;

import butterknife.BindView;

/**
 * Created by xzw on 2018/4/26.
 */

public class TestAcivity extends BaseActivity {
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }



    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_test;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        setTitle("测试");
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }
}
