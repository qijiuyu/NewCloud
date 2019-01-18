package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.CourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity.EntityCardUseActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity.RechargeCardUseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment.CourseOwnerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment.CourseSeitionFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment.LiveSeitionFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.activity.CourseDownloadActivity;

import dagger.Component;


@ActivityScope
@Component(modules = CourseModule.class, dependencies = AppComponent.class)
public interface CourseComponent {

    void inject(CourseDetailsFragment activity);

    void inject(CourseDownloadActivity activity);

    void inject(CourseSeitionFragment fragment);

    void inject(CourseOwnerFragment fragment);

    void inject(LiveSeitionFragment fragment);

    void inject(EntityCardUseActivity activity);

    void inject(RechargeCardUseFragment activity);

    void inject(CourseDetailsActivity activity);
}