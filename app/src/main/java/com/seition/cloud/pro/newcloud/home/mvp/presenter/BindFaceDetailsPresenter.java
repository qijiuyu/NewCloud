package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class BindFaceDetailsPresenter extends BasePresenter<BindManageContract.Model, BindManageContract.FaceDetailsView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public BindFaceDetailsPresenter(BindManageContract.Model model, BindManageContract.FaceDetailsView rootView) {
        super(model, rootView);
    }

    public void getFaceSaveStatus(   ){
        mModel.getFaceSaveStatus()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FaceStatus>(mErrorHandler) {
                    @Override
                    public void onNext(FaceStatus data) {
                        System.out.println("getFaceSaveStatus ");
                        FaceStatus faceStatus = data.getData();
                        if(data.getCode() ==1)
                        mRootView.showFaceSaveStatus(faceStatus);
                        else
                            mRootView.showMessage("人脸储存状态获取失败");

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
