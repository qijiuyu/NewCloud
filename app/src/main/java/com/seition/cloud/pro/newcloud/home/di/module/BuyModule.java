package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.seition.cloud.pro.newcloud.home.mvp.contract.BuyContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.BuyModel;


@Module
public class BuyModule {
    private BuyContract.View view;

    /**
     * 构建BuyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BuyModule(BuyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BuyContract.View provideBuyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BuyContract.Model provideBuyModel(BuyModel model) {
        return model;
    }
}