package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.seition.cloud.pro.newcloud.home.mvp.contract.RegisterContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.RegisterModel;


@Module
public class RegisterModule {
    private RegisterContract.View view;

    /**
     * 构建RegisterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RegisterModule(RegisterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RegisterContract.View provideRegisterView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RegisterContract.Model provideRegisterModel(RegisterModel model) {
        return model;
    }
}