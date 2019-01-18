package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.CourseModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.LiveSeitionAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter.DownloadVideoAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;


@Module
public class CourseModule {
    private CourseContract.View view;
    private CourseContract.LiveSeitionView liveSeitionview;
    private CourseContract.SeitionView seitionView;
    private CourseContract.CourseListView courseListView;
    private CourseContract.CourseDownloadView courseDownloadView;
    private CourseContract.CourseCardView courseCardView;


    /**
     * 构建CourseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CourseModule(CourseContract.View view) {
        this.view = view;
    }

    public CourseModule(CourseContract.LiveSeitionView liveSeitionview) {
        this.liveSeitionview = liveSeitionview;
    }

    public CourseModule(CourseContract.CourseListView courseListView) {
        this.courseListView = courseListView;
    }

    /**
     * 构建CourseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param courseDownloadView
     */
    public CourseModule(CourseContract.CourseDownloadView courseDownloadView) {
        this.courseDownloadView = courseDownloadView;
    }

    /**
     * 构建CourseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param seitionView
     */
    public CourseModule(CourseContract.SeitionView seitionView) {
        this.seitionView = seitionView;
    }

    public CourseModule(CourseContract.CourseCardView courseCardView) {
        this.courseCardView = courseCardView;
    }

    @ActivityScope
    @Provides
    CourseContract.View provideCourseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CourseContract.CourseListView provideCourseListView() {
        return this.courseListView;
    }

    @ActivityScope
    @Provides
    CourseContract.Model provideCourseModel(CourseModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    CourseContract.SeitionView provideCourseSeitionView() {
        return this.seitionView;
    }

    @ActivityScope
    @Provides
    CourseContract.LiveSeitionView provideLiveSeitionView() {
        return this.liveSeitionview;
    }

    @ActivityScope
    @Provides
    CourseContract.CourseDownloadView provideCourseDownloadView() {
        return this.courseDownloadView;
    }

    @ActivityScope
    @Provides
    CourseContract.CourseCardView provideCourseCardView() {
        return this.courseCardView;
    }

    @ActivityScope
    @Provides
    CourseSeitionAdapter provideCourseSeitionAdapter() {
        return new CourseSeitionAdapter(new ArrayList<>());
    }

    @ActivityScope
    @Provides
    DownloadVideoAdapter provideDownloadVideoAdapter() {
        return new DownloadVideoAdapter(new ArrayList<>());
    }

    @ActivityScope
    @Provides
    CourseLiveRecyclerAdapter provideCourseOwnerListAdapter() {
        return new CourseLiveRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    LiveSeitionAdapter provideLiveSeitionAdapter() {
        return new LiveSeitionAdapter();
    }

}