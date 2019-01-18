package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.live.LiveTeacher;
import com.seition.cloud.pro.newcloud.app.popupwindow.LoadMoreListPopWindow;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LiveContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class LiveMainPresenter extends BasePresenter<LiveContract.Model, LiveContract.MainView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LiveMainPresenter(LiveContract.Model model, LiveContract.MainView rootView) {
        super(model, rootView);
    }
    boolean isFirst = true;

    public void getCommonCategory(boolean iscache) throws Exception {
        mModel.getCommonCategory(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonCategory>(mErrorHandler) {
                    @Override
                    public void onNext(CommonCategory data) {
                        ArrayList<CommonCategory> categories = data.getData();
                        mRootView.showCategory(categories);
                    }
                });
    }

    int teacherPage = 1;
    public void getLiveScreenTeacher(  boolean pull) throws Exception {

        boolean isEvictCache = true;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull ) {//默认在第一次下拉刷新时使用缓存
            teacherPage = 1;
            if(isFirst){
                isFirst = false;
                isEvictCache = false;
            }else
                isEvictCache = true;
        }else {
            isEvictCache = true;
            teacherPage++;
        }


        mModel.getLiveScreenTeacher(teacherPage, LoadMoreListPopWindow.Count,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LiveTeacher>(mErrorHandler) {
                    @Override
                    public void onNext(LiveTeacher data) {
                        ArrayList<LiveTeacher> datas = data.getData();
                        mRootView.setDialogData(datas,pull);
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
