package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.seition.cloud.pro.newcloud.home.mvp.contract.LibraryContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.LibraryModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.adapter.LibraryListAdapter;


@Module
public class LibraryModule {
    private LibraryContract.View view;

    /**
     * 构建LibraryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public LibraryModule(LibraryContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LibraryContract.View provideLibraryView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LibraryContract.Model provideLibraryModel(LibraryModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    LibraryListAdapter provideLibraryListAdapterModel() {
        return new LibraryListAdapter();
    }
}