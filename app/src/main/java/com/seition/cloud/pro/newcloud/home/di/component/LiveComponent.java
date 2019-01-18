package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.LiveModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.live.fragment.LiveListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.live.fragment.LiveListMainFragment;

import dagger.Component;

@ActivityScope
@Component(modules = LiveModule.class, dependencies = AppComponent.class)
public interface LiveComponent {

    void inject(LiveListFragment listFragment);

    void inject(LiveListMainFragment mainFragment);
}