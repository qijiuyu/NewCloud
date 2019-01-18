package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.seition.cloud.pro.newcloud.home.di.module.NewsModule;

import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.activity.NewsActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.activity.NewsDetailsActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.fragment.NewsListFragment;

@ActivityScope
@Component(modules = NewsModule.class, dependencies = AppComponent.class)
public interface NewsComponent {
    void inject(NewsActivity activity);

    void inject(NewsDetailsActivity activity);

    void inject(NewsListFragment fragment);
}