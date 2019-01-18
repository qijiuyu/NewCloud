package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.seition.cloud.pro.newcloud.home.mvp.contract.DetailsContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.DetailsModel;


@Module
public class DetailsModule {
    private DetailsContract.View view;

    /**
     * 构建DetailsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DetailsModule(DetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DetailsContract.View provideDetailsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DetailsContract.Model provideDetailsModel(DetailsModel model) {
        return model;
    }
}