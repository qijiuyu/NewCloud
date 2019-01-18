package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.DownloadCourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.fragment.DownloadCourseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.fragment.DownloadLibraryFragment;

import dagger.Component;

@ActivityScope
@Component(modules = DownloadCourseModule.class, dependencies = AppComponent.class)
public interface DownloadCourseComponent {
    void inject(DownloadCourseFragment fragment);

    void inject(DownloadLibraryFragment fragment);
}