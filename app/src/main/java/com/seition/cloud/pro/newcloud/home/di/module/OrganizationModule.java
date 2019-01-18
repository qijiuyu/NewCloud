package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.OrganizationModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LectureListRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrderListRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrganizationHomeRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrganizationRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class OrganizationModule {
    private OrganizationContract.DetailsView view;
    private OrganizationContract.ListView listView;
    private OrganizationContract.FragmentView fragmentView;
    private OrganizationContract.OwnerView ownerView;
    private OrganizationContract.HomeView homeView;

    /**
     * 构建OrganizationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrganizationModule(OrganizationContract.ListView view) {
        this.listView = view;
    }

    public OrganizationModule(OrganizationContract.DetailsView view) {
        this.view = view;
    }

    public OrganizationModule(OrganizationContract.FragmentView view) {
        this.fragmentView = view;
    }

    public OrganizationModule(OrganizationContract.OwnerView view) {
        this.ownerView = view;
    }

    public OrganizationModule(OrganizationContract.HomeView view) {
        this.homeView = view;
    }

    @ActivityScope
    @Provides
    OrganizationContract.DetailsView provideOrganizationDView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrganizationContract.ListView provideOrganizationLView() {
        return this.listView;
    }

    @ActivityScope
    @Provides
    OrganizationContract.FragmentView provideOrganizationFView() {
        return this.fragmentView;
    }

    @ActivityScope
    @Provides
    OrganizationContract.OwnerView provideOrganizationOView() {
        return this.ownerView;
    }

    @ActivityScope
    @Provides
    OrganizationContract.HomeView provideOrganizationHomeView() {
        return this.homeView;
    }

    @ActivityScope
    @Provides
    OrganizationContract.Model provideOrganizationModel(OrganizationModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    OrganizationRecyclerAdapter provideOrganizationListAdapter() {
        return new OrganizationRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    OrganizationHomeRecyclerAdapter provideOrganizationHomeListAdapter() {
        return new OrganizationHomeRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    CourseLiveRecyclerAdapter provideOrganizationCourseListAdapter() {
        return new CourseLiveRecyclerAdapter();
    }

    @Provides
    LectureListRecyclerAdapter provideOrganizationTeacherListAdapter() {
        return new LectureListRecyclerAdapter();
    }

    @Provides
    OrderListRecyclerAdapter provideOrganizationOrderListAdapter() {
        return new OrderListRecyclerAdapter();
    }

}