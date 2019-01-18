package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.ExamModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExamActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExamResultActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExamTypeListActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExaminationActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.fragment.ExamCollectFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.fragment.ExamOwnerFragment;

import dagger.Component;

@ActivityScope
@Component(modules = ExamModule.class, dependencies = AppComponent.class)
public interface ExamComponent {
    void inject(ExamActivity activity);

//    void inject(ExamOwnerActivity activity);

    void inject(ExaminationActivity activity);

    void inject(ExamResultActivity activity);

    void inject(ExamTypeListActivity activity);

    void inject(ExamCollectFragment fragment);

    void inject(ExamOwnerFragment fragment);
}