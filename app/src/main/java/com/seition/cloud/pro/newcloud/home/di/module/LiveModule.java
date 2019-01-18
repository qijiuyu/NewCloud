package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LiveContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.LiveModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LiveGridRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class LiveModule {
    private LiveContract.MainView mainView;
    private LiveContract.View view;

    /**
     * 构建LiveModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */

    public LiveModule(LiveContract.MainView view) {
        this.mainView = view;
    }
    public LiveModule(LiveContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LiveContract.MainView provideLiveMainView() {
        return this.mainView;
    }

    @ActivityScope
    @Provides
    LiveContract.View provideLiveView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LiveContract.Model provideLiveModel(LiveModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    LiveGridRecyclerAdapter provideLiveListAdapter(  ){
        return new LiveGridRecyclerAdapter();
    }
    @ActivityScope
    @Provides
    CourseLiveRecyclerAdapter provideLiveOwnerListAdapter(  ){
        return new CourseLiveRecyclerAdapter();
    }

}