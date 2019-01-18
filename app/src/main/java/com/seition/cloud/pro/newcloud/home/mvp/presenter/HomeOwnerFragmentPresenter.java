package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserCount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserMember;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class HomeOwnerFragmentPresenter extends BasePresenter<HomeContract.HomeModel, HomeContract.OwnerFragmentView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomeOwnerFragmentPresenter(HomeContract.HomeModel model, HomeContract.OwnerFragmentView rootView) {
        super(model, rootView);
    }

    private int requestCount = 0;

    private void setRequestCount() {
        requestCount++;
        LogUtils.debugInfo("add requestCount  = " + requestCount);
    }

    private void loadDataSuccess() {
        requestCount--;
        LogUtils.debugInfo("reduce requestCount  = " + requestCount);
        if (requestCount == 0) ;
        mRootView.hideLoading();
    }


    public void getUserInfo(boolean iscache) {
        setRequestCount();
        mModel.getUserInfo("", iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MessageUserInfo>(mErrorHandler) {
                    @Override
                    public void onNext(MessageUserInfo data) {
                        mRootView.showUserInfo(data.getData());
                    }
                });
    }

    public void getUserAccount(boolean iscache) {
        setRequestCount();
        mModel.getUserAccount(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<UserAccount>(mErrorHandler) {
                    @Override
                    public void onNext(UserAccount data) {
                        UserAccount userAccount = data.getData();
                        mRootView.showUserAccount(userAccount);
                    }
                });
    }

    public void getUserCount(boolean iscache) {
        setRequestCount();
        mModel.getUserCount(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<UserCount>(mErrorHandler) {
                    @Override
                    public void onNext(UserCount data) {

                        UserCount userCount = data.getData();
                        mRootView.showUserCount(userCount);
                    }
                });
    }

    public void getUserVip(String time, boolean iscache) {
        setRequestCount();
        mModel.getUserVip(time, iscache)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<UserMember>(mErrorHandler) {
                    @Override
                    public void onNext(UserMember data) {
                        UserMember userMember = data.getData();
                        mRootView.showUserMember(userMember);
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
