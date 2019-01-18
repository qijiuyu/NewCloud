package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OfflineContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.OfflineModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OfflineListRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class OfflineModule {
    private OfflineContract.OfflineListView view;
    private OfflineContract.OfflineDetailsView detailsView;
    /**
     * 构建OfflineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OfflineModule(OfflineContract.OfflineListView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OfflineContract.OfflineListView provideOfflineListView() {
        return this.view;
    }



    public OfflineModule(OfflineContract.OfflineDetailsView view) {
        this.detailsView = view;
    }

    @ActivityScope
    @Provides
    OfflineContract.OfflineDetailsView provideOfflineDetailsView() {
        return this.detailsView;
    }

    @ActivityScope
    @Provides
    OfflineContract.Model provideOfflineModel(OfflineModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    OfflineListRecyclerAdapter provideOfflineRecylerAapter(OfflineModel model) {
        return new OfflineListRecyclerAdapter();
    }

}