package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.seition.cloud.pro.newcloud.home.mvp.contract.DownloadContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.DownloadModel;


@Module
public class DownloadModule {
    private DownloadContract.View view;

    /**
     * 构建DownloadModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DownloadModule(DownloadContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DownloadContract.View provideDownloadView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DownloadContract.Model provideDownloadModel(DownloadModel model) {
        return model;
    }
}