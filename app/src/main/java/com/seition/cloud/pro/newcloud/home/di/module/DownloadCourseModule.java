package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.DownloadCourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.DownloadCourseModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter.DownloadAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter.DownloadCourseAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class DownloadCourseModule {
    private DownloadCourseContract.View view;

    /**
     * 构建DownloadCourseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public DownloadCourseModule(DownloadCourseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    DownloadCourseContract.View provideDownloadCourseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    DownloadCourseContract.Model provideDownloadCourseModel(DownloadCourseModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    DownloadAdapter provideDownloadAdapter() {
        return new DownloadAdapter();
    }

    @ActivityScope
    @Provides
    DownloadCourseAdapter provideDownloadCourseAdapter() {
        return new DownloadCourseAdapter();
    }
}