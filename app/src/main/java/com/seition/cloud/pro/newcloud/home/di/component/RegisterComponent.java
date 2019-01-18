package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.seition.cloud.pro.newcloud.home.di.module.RegisterModule;

import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.EmailRegisterActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.PhoneRegisterActivity;

@ActivityScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {
    void inject(PhoneRegisterActivity activity);

    void inject(EmailRegisterActivity activity);
}