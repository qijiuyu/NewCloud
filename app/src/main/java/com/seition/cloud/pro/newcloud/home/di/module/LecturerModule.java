package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LecturerContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.LecturerModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LectureListRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class LecturerModule {
    private LecturerContract.View view;
    private LecturerContract.ListContainerView containerView;


    /**
     * 构建LecturerModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LecturerModule(LecturerContract.View view) {
        this.view = view;
    }
    public LecturerModule(LecturerContract.ListContainerView view) {
        this.containerView = view;
    }

    @ActivityScope
    @Provides
    LecturerContract.View provideLecturerView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LecturerContract.ListContainerView provideListContainerView() {
        return this.containerView;
    }

    @ActivityScope
    @Provides
    LecturerContract.Model provideLecturerModel(LecturerModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    LectureListRecyclerAdapter provideMessageCommentAdapter() {
        return  new LectureListRecyclerAdapter();
    }
    @ActivityScope
    @Provides
    CourseLiveRecyclerAdapter provideLectureCourseAdapter() {
        return  new CourseLiveRecyclerAdapter();
    }


}