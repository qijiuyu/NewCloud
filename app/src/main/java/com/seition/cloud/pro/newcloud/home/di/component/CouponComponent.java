package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.CouponModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment.SelectCouponFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.activity.SelectCouponMainActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationCouponMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment.CouponFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment.OwnerCouponMainFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CouponModule.class, dependencies = AppComponent.class)
public interface CouponComponent {
    void inject(OwnerCouponMainFragment fragment);
    void inject(OrganizationCouponMainFragment fragment);
    void inject(CouponFragment fragment);
    void inject(SelectCouponFragment fragment);
    void inject(SelectCouponMainActivity fragment);
}