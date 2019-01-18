package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.seition.cloud.pro.newcloud.home.di.module.DetailsModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.DetailsFragment;


@ActivityScope
@Component(modules = DetailsModule.class, dependencies = AppComponent.class)
public interface DetailsComponent {
    void inject(DetailsFragment fragment);
}