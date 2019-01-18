package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.home.mvp.contract.DetailsContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class DetailsPresenter extends BasePresenter<DetailsContract.Model, DetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DetailsPresenter(DetailsContract.Model model, DetailsContract.View rootView) {
        super(model, rootView);
    }

    public void showTeacher(int teacherId) {
        try {
            mModel.getTeacher(teacherId, false)
                    .subscribeOn(Schedulers.io())
//                    .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        if (mRootView != null)
                            mRootView.hideLoading();
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                    .subscribe(new ErrorHandleSubscriber<Lecturer>(mErrorHandler) {
                        @Override
                        public void onNext(Lecturer data) {
                            if (mRootView != null)
                                if (data.getData() != null)
                                    mRootView.showTeacher(data.getData());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
