package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.NewsContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.NewsModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.adapter.NewsListAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class NewsModule {
    private NewsContract.View view;
    private NewsContract.DetailsView detailsView;
    private NewsContract.FragmentView fragmentView;

    /**
     * 构建NewsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public NewsModule(NewsContract.View view) {
        this.view = view;
    }

    public NewsModule(NewsContract.DetailsView view) {
        this.detailsView = view;
    }

    public NewsModule(NewsContract.FragmentView view) {
        this.fragmentView = view;
    }

    @ActivityScope
    @Provides
    NewsContract.View provideNewsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    NewsContract.DetailsView provideNewsDetailsView() {
        return this.detailsView;
    }

    @ActivityScope
    @Provides
    NewsContract.FragmentView provideFragmentNewsView() {
        return this.fragmentView;
    }

    @ActivityScope
    @Provides
    NewsContract.Model provideNewsModel(NewsModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    NewsListAdapter provideNewsListAdapter() {
        return new NewsListAdapter();
    }
}