package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.InitApp;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LoginContract;
import com.seition.cloud.pro.newcloud.home.api.service.BindService;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.LoginService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;

import java.net.URLEncoder;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<User> login(String uname, String password) throws Exception {
        String content = M.getEncryptData(MApplication.getCodedLock()
                , M.getMapString(
                        "uname", URLEncoder.encode(uname, "utf-8")
                        , "upwd", URLEncoder.encode(password, "utf-8")
                ));
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .login(content);
    }

    @Override
    public Observable<InitApp> getMcryptKey() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getMcryptKey(hex, token);
    }

    @Override
    public Observable<RegisterTypeInit> getInitRegisterType() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getInitRegisterType(hex, token);
    }

    @Override
    public Observable<DataBean> feedBack(String content, String way) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("content", content, "way", way));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .feedBack(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> getUnionId(String access_token) {

        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUnionId(access_token);
    }

    @Override
    public Observable<User> loginSync(String app_token, String app_login_type) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(),
                    M.getMapString("app_token", app_token, "app_login_type", app_login_type));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .loginSync(en_params);
    }

    @Override
    public Observable<FaceStatus> getFaceSaveStatus() {
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getFaceSaveStatus(Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<FaceSence> getFaceSence() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("hextime", hex, "token", token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = Utils.getAouthToken(mApplication);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getFaceSence(en_params, Utils.getAouthToken(mApplication));

    }
}