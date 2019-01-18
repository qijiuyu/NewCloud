package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.SearchContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.SearchModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class SearchModule {
    private SearchContract.View view;

    /**
     * 构建SearchModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SearchModule(SearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchContract.View provideSearchView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchContract.Model provideSearchModel(SearchModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    CourseLiveRecyclerAdapter provideCourseRecyclerAdapter() {
        return  new CourseLiveRecyclerAdapter();
    }
}