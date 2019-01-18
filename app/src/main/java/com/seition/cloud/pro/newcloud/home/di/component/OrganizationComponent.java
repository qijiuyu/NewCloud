package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.OrganizationModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationApplyforFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationCommentFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationCourseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationHomeFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationOrderFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationOwnerFragment;

import dagger.Component;

@ActivityScope
@Component(modules = OrganizationModule.class, dependencies = AppComponent.class)
public interface OrganizationComponent {
    void inject(OrganizationListFragment fragment);
    void inject(OrganizationDetailsFragment fragment);
    void inject(OrganizationCourseFragment fragment);
    void inject(OrganizationHomeFragment fragment);
    void inject(OrganizationCommentFragment fragment);
    void inject(OrganizationOwnerFragment fragment);
    void inject(OrganizationOrderFragment fragment);
    void inject(OrganizationApplyforFragment fragment);
}