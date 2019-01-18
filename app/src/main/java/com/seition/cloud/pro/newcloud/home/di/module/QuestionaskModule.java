package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.QuestionaskModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QuestionAnswerRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QuestionAskRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QuestionClassifyRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QuestionHonorRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class QuestionaskModule {
    private QuestionaskContract.View view;
    private QuestionaskContract.PublishView publishView;
    private QuestionaskContract.DetailsView detailsView;

    /**
     * 构建QuestionaskModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public QuestionaskModule(QuestionaskContract.View view) {
        this.view = view;
    }

    public QuestionaskModule(QuestionaskContract.PublishView view) {
        this.publishView = view;
    }

    public QuestionaskModule(QuestionaskContract.DetailsView view) {
        this.detailsView = view;
    }

    @ActivityScope
    @Provides
    QuestionaskContract.View provideQuestionaskView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    QuestionaskContract.PublishView providePublishView() {
        return this.publishView;
    }

    @ActivityScope
    @Provides
    QuestionaskContract.DetailsView provideDetailsView() {
        return this.detailsView;
    }

    @ActivityScope
    @Provides
    QuestionaskContract.Model provideQuestionaskModel(QuestionaskModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    QuestionAskRecyclerAdapter provideQuestionaskListAdapter() {
        return new QuestionAskRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    QuestionAnswerRecyclerAdapter provideQuestionAnswerListAdapter() {
        return new QuestionAnswerRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    QuestionHonorRecyclerAdapter provideQuestionHonorListAdapter() {
        return new QuestionHonorRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    QuestionClassifyRecyclerAdapter provideQuestionClassifyListAdapter() {
        return new QuestionClassifyRecyclerAdapter();
    }

}