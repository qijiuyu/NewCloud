package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.BindManageModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BankRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BindBankManageRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BindBankRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class BindManageModule {
    private BindManageContract.View view;
    private BindManageContract.BindBankListView bankListView;
    private BindManageContract.AliManageView aliManageView;
    private BindManageContract.FaceDetailsView faceDetailsView;
    private BindManageContract.FaceCheckView faceCheckView;

    private BindManageContract.ManageBankListView manageBankListView;

    /**
     * 构建BindManageModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BindManageModule(BindManageContract.View view) {
        this.view = view;
    }

    public BindManageModule(BindManageContract.BindBankListView view) {
        this.bankListView = view;
    }

    public BindManageModule(BindManageContract.AliManageView view) {
        this.aliManageView = view;
    }

    public BindManageModule(BindManageContract.FaceDetailsView view) {
        this.faceDetailsView = view;
    }

    public BindManageModule(BindManageContract.FaceCheckView view) {
        this.faceCheckView = view;
    }

    public BindManageModule(BindManageContract.ManageBankListView view) {
        this.manageBankListView = view;
    }

    @ActivityScope
    @Provides
    BindManageContract.View provideBindManageView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BindManageContract.BindBankListView provideBindBankListView() {
        return this.bankListView;
    }

    @ActivityScope
    @Provides
    BindManageContract.AliManageView provideAliManageView() {
        return this.aliManageView;
    }

    @ActivityScope
    @Provides
    BindManageContract.FaceDetailsView provideFaceDetailsView() {
        return this.faceDetailsView;
    }

    @ActivityScope
    @Provides
    BindManageContract.FaceCheckView provideFaceCheckView() {
        return this.faceCheckView;
    }

    @ActivityScope
    @Provides
    BindManageContract.ManageBankListView provideManageBankView() {
        return this.manageBankListView;
    }

    @ActivityScope
    @Provides
    BindManageContract.Model provideBindManageModel(BindManageModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    BindBankRecyclerAdapter provideBindBankAdapter() {
        return new BindBankRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    BankRecyclerAdapter provideBankAdapter() {
        return new BankRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    BindBankManageRecyclerAdapter provideBankManageAdapter() {
        return new BindBankManageRecyclerAdapter();
    }

}