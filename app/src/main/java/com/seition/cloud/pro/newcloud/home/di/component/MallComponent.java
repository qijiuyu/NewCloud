package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.MallModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent.MallDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent.MallFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent.MallListFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MallModule.class, dependencies = AppComponent.class)
public interface MallComponent {
//    void inject(MallActivity activity);
    void inject(MallFragment mallFragment);
    void inject(MallListFragment mallListFragment);
    void inject(MallDetailsFragment mallDetailsFragment);
//    void inject(MallDetailsActivity detailsActivity);
}