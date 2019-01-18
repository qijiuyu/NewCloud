package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.OrderModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.fragment.OrderFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.fragment.OrderReimburseFragment;

import dagger.Component;

@ActivityScope
@Component(modules = OrderModule.class, dependencies = AppComponent.class)
public interface OrderComponent {
    void inject(OrderFragment fragment);
    void inject(OrderReimburseFragment fragment);
}