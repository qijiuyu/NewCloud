package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.MallModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MallGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MallRankHorRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class MallModule {
    private MallContract.View view;
    private MallContract.FragmentView fragmentView;
    private MallContract.DetailstView detailstView;


    /**
     * 构建MallModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MallModule(MallContract.View view) {
        this.view = view;
    }
    public MallModule(MallContract.FragmentView fragmentView) {
        this.fragmentView = fragmentView;
    }

    public MallModule(MallContract.DetailstView detailstView) {
        this.detailstView = detailstView;
    }
    @ActivityScope
    @Provides
    MallContract.View provideMallView() {
        return this.view;
    }


    @ActivityScope
    @Provides
    MallContract.FragmentView provideFragmentMallView() {
        return this.fragmentView;
    }

    @ActivityScope
    @Provides
    MallContract.DetailstView provideMallDetailsView() {
        return this.detailstView;
    }

    @ActivityScope
    @Provides
    MallContract.Model provideMallModel(MallModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    MallRankHorRecyclerAdapter provideMallRankadapter() {
        return new MallRankHorRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    MallGridRecyclerAdapter provideMallLisListdapter() {
        return  new MallGridRecyclerAdapter();
    }

}