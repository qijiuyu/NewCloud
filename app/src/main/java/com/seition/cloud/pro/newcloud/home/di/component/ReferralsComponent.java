package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.di.module.ReferralsModule;
import com.seition.cloud.pro.newcloud.home.di.module.RegisterModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.EmailRegisterActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.PhoneRegisterActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals.OwnerReferralsFragment;

import dagger.Component;

/**
 * Created by addis on 2018/11/23.
 */

@ActivityScope
@Component(modules = ReferralsModule.class, dependencies = AppComponent.class)
public interface ReferralsComponent {
    void inject(OwnerReferralsFragment activity);
}