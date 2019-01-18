package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.HomeModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CategoryGridAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LecturerGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LiveHorRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OfflineGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrganizationGridRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class HomeModule {
    private HomeContract.View view;
    private HomeContract.HomeView homeView;
    private HomeContract.CoursesFragmentView coursesFragmentView;
    private HomeContract.OwnerFragmentView ownerFragmentView;
    private HomeContract.SetInfFragmentView setInfFragmentView;
    private HomeContract.ModifierPwdView modifierPwdView;

    public HomeModule(HomeContract.View view) {
        this.view = view;
    }
    public HomeModule(HomeContract.HomeView homeView) {
        this.homeView = homeView;
    }

    public HomeModule(HomeContract.CoursesFragmentView homeView) {
        this.coursesFragmentView = homeView;
    }
    public HomeModule(HomeContract.OwnerFragmentView homeView) {
        this.ownerFragmentView = homeView;
    }
    public HomeModule(HomeContract.SetInfFragmentView homeView) {
        this.setInfFragmentView = homeView;
    }
    public HomeModule(HomeContract.ModifierPwdView homeView) {
        this.modifierPwdView = homeView;
    }
    @ActivityScope
    @Provides
    HomeContract.View provideHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeContract.HomeModel provideHomeActivityModel(HomeModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    HomeContract.HomeView provideHomeFragmentView() {
        return this.homeView;
    }

    @ActivityScope
    @Provides
    HomeContract.CoursesFragmentView provideCoursesFragmentView() {
        return this.coursesFragmentView;
    }
    @ActivityScope
    @Provides
    HomeContract.OwnerFragmentView provideOwnerFragmentView() {
        return this.ownerFragmentView;
    }
    @ActivityScope
    @Provides
    HomeContract.SetInfFragmentView provideUserInfoFragmentView() {
        return this.setInfFragmentView;
    }
    @ActivityScope
    @Provides
    HomeContract.ModifierPwdView provideUserModifierPwdView() {
        return this.modifierPwdView;
    }

    @ActivityScope
    @Provides
    CourseLiveRecyclerAdapter provideCourseListAdapter() {
        return  new CourseLiveRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    CategoryGridAdapter provideCategoryListAdapter() {
        return  new CategoryGridAdapter();
    }

    @ActivityScope
    @Provides
    CourseGridRecyclerAdapter provideCoursesGridAdapter() {
        return  new CourseGridRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    LiveHorRecyclerAdapter provideLiveHorAdapter() {
        return  new LiveHorRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    OfflineGridRecyclerAdapter provideOfflineGridAdapter() {
        return  new OfflineGridRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    LecturerGridRecyclerAdapter provideLecturerGridAdapter() {
        return  new LecturerGridRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    OrganizationGridRecyclerAdapter provideOrganizationGridAdapter() {
        return  new OrganizationGridRecyclerAdapter();
    }



}