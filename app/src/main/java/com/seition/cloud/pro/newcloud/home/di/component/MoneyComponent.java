package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.MoneyModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.activity.MoneyDetailsListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.activity.OwnerMoneyFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MoneyModule.class, dependencies = AppComponent.class)
public interface MoneyComponent {
    void inject(OwnerMoneyFragment moneyFragment);
    void inject(MoneyDetailsListFragment moneyDetailsListFragment);
}