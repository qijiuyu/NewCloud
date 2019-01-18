package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.OfflineModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment.OfflineDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment.OfflineListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment.OfflineOwnerFragment;

import dagger.Component;

@ActivityScope
@Component(modules = OfflineModule.class, dependencies = AppComponent.class)
public interface OfflineComponent {
    void inject(OfflineDetailsFragment fragment);
    void inject(OfflineListFragment offlineListFragment);
    void inject(OfflineOwnerFragment offlineOwnerFragment);

}