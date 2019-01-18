package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.bean.user.RegistResp;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.home.mvp.contract.RegisterContract;
import com.seition.cloud.pro.newcloud.home.api.service.LoginService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<RegistResp> getVerifyCode(String en_params) {
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .getRegVerifyCode(en_params);

    }

    @Override
    public Observable<User> register(String en_params) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .register(en_params))
                .flatMap(new Function<Observable<User>, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(Observable<User> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}