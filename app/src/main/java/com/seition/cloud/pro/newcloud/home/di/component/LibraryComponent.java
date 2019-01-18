package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.seition.cloud.pro.newcloud.home.di.module.LibraryModule;

import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.activity.ArticleLibraryListActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.fragment.ArticleLibraryOwnerFragment;

@ActivityScope
@Component(modules = LibraryModule.class, dependencies = AppComponent.class)
public interface LibraryComponent {
    void inject(ArticleLibraryListActivity activity);

    void inject(ArticleLibraryOwnerFragment activity);

}