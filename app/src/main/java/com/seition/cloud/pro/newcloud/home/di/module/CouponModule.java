package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CouponContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.CouponModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CouponRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class CouponModule {
    private CouponContract.View view;

    /**
     * 构建BindManageModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CouponModule(CouponContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CouponContract.View provideBindManageView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CouponContract.Model provideBindManageModel(CouponModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    CouponRecyclerAdapter provideCouponListAdapter() {
        return new CouponRecyclerAdapter();
    }
}