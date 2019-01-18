package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.HomeModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.PasswordModifierFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeCourseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeOwnerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.OwnerUserInfoFragment;

import dagger.Component;

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
    void inject(HomeCourseFragment homeCourseFragment);
    void inject(HomeOwnerFragment homeOwnerFragment);
    void inject(OwnerUserInfoFragment ownerUserInfoFragment);
    void inject(PasswordModifierFragment ownerUserInfoFragment);
}