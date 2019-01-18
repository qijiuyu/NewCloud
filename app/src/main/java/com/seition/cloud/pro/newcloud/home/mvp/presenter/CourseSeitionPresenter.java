package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.download.InitDownloadBean;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CourseSeitionPresenter extends BasePresenter<CourseContract.Model, CourseContract.SeitionView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private boolean isCourseSeitionListCache = true;
    ArrayList<CourseSeition> seitions;

    @Inject
    public CourseSeitionPresenter(CourseContract.Model model, CourseContract.SeitionView rootView) {
        super(model, rootView);
    }

    public void getCourseSeitionList(String courseId) throws Exception {
        mModel.getCourseSeitionList(courseId, false)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseSeition>(mErrorHandler) {
                    @Override
                    public void onNext(CourseSeition data) {
                        //拿到数据
                        mRootView.showSeition(InitDownloadBean.seitionDataToExpandableData(seitions = data.getData(), mApplication, false));
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
