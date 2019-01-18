package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.seition.cloud.pro.newcloud.home.di.module.BuyModule;

import com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity.BuyFragment;

@ActivityScope
@Component(modules = BuyModule.class, dependencies = AppComponent.class)
public interface BuyComponent {
    void inject(BuyFragment activity);

}