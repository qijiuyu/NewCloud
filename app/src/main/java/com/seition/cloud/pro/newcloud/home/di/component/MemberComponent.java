package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.MemberModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.member.fragment.MemberCenterFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.member.fragment.MemberCoursesListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.member.fragment.MemberRechargeFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MemberModule.class, dependencies = AppComponent.class)
public interface MemberComponent {


    void inject(MemberCenterFragment fragment);
    void inject(MemberCoursesListFragment fragment);
    void inject(MemberRechargeFragment fragment);
}