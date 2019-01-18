package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CommentContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.CommentModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseCommentAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class CommentModule {
    private CommentContract.View view;

    /**
     * 构建CommentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommentModule(CommentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommentContract.View provideCommentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommentContract.Model provideCommentModel(CommentModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    CourseCommentAdapter provideCourseCommentAdapter() {
        return new CourseCommentAdapter();
    }
}