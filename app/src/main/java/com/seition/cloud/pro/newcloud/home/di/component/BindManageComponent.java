package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BankAddFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BankListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindAliFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindBankListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindBankManageFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindFaceChedkActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindFaceFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindManageFragment;

import dagger.Component;

@ActivityScope
@Component(modules = BindManageModule.class, dependencies = AppComponent.class)
public interface BindManageComponent {
    void inject(BindManageFragment activity);

    void inject(BindBankListFragment activity);

    void inject(BindAliFragment activity);

    void inject(BindFaceFragment activity);

    void inject(BankAddFragment activity);

    void inject(BankListFragment activity);

    void inject(BindBankManageFragment activity);

    void inject(BindFaceChedkActivity activity);

//    LoginComponent plus(LoginModule loginModule);
}