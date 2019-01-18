package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.ExamModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.AnswerSheetAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.ExamListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.ExamMoudleListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.ExamMyAnswerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamCollectRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamRankRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class ExamModule {
    private ExamContract.ExaminationView examinationView;
    private ExamContract.ExamListView examListView;
    private ExamContract.ExamOwnerView examOwnerView;
    private ExamContract.ExamRestultView examResultView;
    private ExamContract.ExamTypeMoudleView examTypeListView;
    private ExamContract.ExamCollectView examCollectView;

    /**
     * 构建ExamModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param examListView
     */
    public ExamModule(ExamContract.ExamListView examListView) {
        this.examListView = examListView;
    }

    @ActivityScope
    @Provides
    ExamContract.ExamListView provideExamView() {
        return this.examListView;
    }

    /**
     * 构建ExamCollectModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param examCollectView
     */
    public ExamModule(ExamContract.ExamCollectView examCollectView) {
        this.examCollectView = examCollectView;
    }

    @ActivityScope
    @Provides
    ExamContract.ExamCollectView provideExamCollectView() {
        return this.examCollectView;
    }


    /**
     * 构建ExaminationModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param examinationView
     */
    public ExamModule(ExamContract.ExaminationView examinationView) {
        this.examinationView = examinationView;
    }

    @ActivityScope
    @Provides
    ExamContract.ExaminationView provideExaminationView() {
        return this.examinationView;
    }

    /**
     * 构建ExamOwnerModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param examOwnerView
     */
//    private int type;
    public ExamModule(ExamContract.ExamOwnerView examOwnerView/*,int type*/) {
        this.examOwnerView = examOwnerView;
//        this.type = type;
    }

    @ActivityScope
    @Provides
    ExamContract.ExamOwnerView provideExamOwnerView() {
        return this.examOwnerView;
    }


    /**
     * 构建ExamResultModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param examResultView
     */
    public ExamModule(ExamContract.ExamRestultView examResultView) {
        this.examResultView = examResultView;
    }

    @ActivityScope
    @Provides
    ExamContract.ExamRestultView provideExamResultView() {
        return this.examResultView;
    }

    /**
     * 构建ExamTypeListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param examTypeListView
     */
    public ExamModule(ExamContract.ExamTypeMoudleView examTypeListView) {
        this.examTypeListView = examTypeListView;
    }

    @ActivityScope
    @Provides
    ExamContract.ExamTypeMoudleView provideExamTypeListView() {
        return this.examTypeListView;
    }

    @ActivityScope
    @Provides
    ExamContract.ExamModel provideExamModel(ExamModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    ExamMoudleListAdapter provideExamMoudleAdapter() {
        return new ExamMoudleListAdapter();
    }

    @ActivityScope
    @Provides
    ExamListAdapter provideExamAdapter() {
        return new ExamListAdapter();
    }

    @ActivityScope
    @Provides
    ExamMyAnswerAdapter provideExamAnswerAdapter() {
        return new ExamMyAnswerAdapter();
    }

    @ActivityScope
    @Provides
    AnswerSheetAdapter provideAnswerSheetAdapter() {
        return new AnswerSheetAdapter();
    }

    @ActivityScope
    @Provides
    ExamRecyclerAdapter provideExamOwnerAdapter() {
        return new ExamRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    ExamCollectRecyclerAdapter provideCollectExamOwnerAdapter() {
        return new ExamCollectRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    ExamRankRecyclerAdapter provideExamRankAdapter() {
        return new ExamRankRecyclerAdapter();
    }


/*

    @ActivityScope
    @Provides
    ExamListRecyclerAdapter provideExamListAdapter() {
        return new ExamListRecyclerAdapter();
    }
*/

}