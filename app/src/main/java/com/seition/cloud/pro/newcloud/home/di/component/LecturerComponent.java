package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.LecturerModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LectureCourseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LectureHomeFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LecturerDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LecturerListFragment;

import dagger.Component;

@ActivityScope
@Component(modules = LecturerModule.class, dependencies = AppComponent.class)
public interface LecturerComponent {
    void inject(LecturerDetailsFragment activity);
    void inject(LectureHomeFragment fragment);
    void inject(LecturerListFragment activity);
    void inject(LectureCourseFragment courseFragment);
}