package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.seition.cloud.pro.newcloud.home.di.module.DownloadModule;

import com.seition.cloud.pro.newcloud.home.mvp.ui.download.activity.DownloadActivity;

@ActivityScope
@Component(modules = DownloadModule.class, dependencies = AppComponent.class)
public interface DownloadComponent {
    void inject(DownloadActivity activity);
}