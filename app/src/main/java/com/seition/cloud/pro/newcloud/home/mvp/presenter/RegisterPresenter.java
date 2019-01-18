package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.user.RegistResp;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.home.mvp.contract.RegisterContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView) {
        super(model, rootView);
    }

    public void getVerifyCode(String en_params) {//{"interfacetime":"1530845535","data":{"status":1},"code":1,"msg":"短信发送成功,请注意查收"}
        mRootView.showLoading();
        mModel.getVerifyCode(en_params)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<RegistResp>(mErrorHandler) {
                    @Override
                    public void onNext(RegistResp data) {
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1)
                            mRootView.showVerifyTime(60);
                    }
                });
    }

    public void phoneRegister(String en_params) {//{"interfacetime":"1530845655","data":{"oauth_token":"81052b90f34a6891394db8b1f8b3043c","oauth_token_secret":"f74695c2786e28f7581482b77db57242","type":"location","type_uid":"","uid":39},"code":1,"msg":"注册成功"}
        mRootView.showLoading();
        mModel.register(en_params)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<User>(mErrorHandler) {
                    @Override
                    public void onNext(User data) {
                        if (data.getCode() == 1)
                            mRootView.regeisterResult();
                        else
                            mRootView.showMessage(data.getMsg());
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
