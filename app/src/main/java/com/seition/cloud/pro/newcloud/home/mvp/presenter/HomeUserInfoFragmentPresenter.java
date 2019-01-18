package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.ChangeFaceResponse;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MultipartBody;


@ActivityScope
public class HomeUserInfoFragmentPresenter extends BasePresenter<HomeContract.HomeModel, HomeContract.SetInfFragmentView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomeUserInfoFragmentPresenter(HomeContract.HomeModel model, HomeContract.SetInfFragmentView rootView) {
        super(model, rootView);
    }

    public void getUserInfo(  boolean iscache){
        mModel.getUserInfo("",iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MessageUserInfo>(mErrorHandler) {
                    @Override
                    public void onNext(MessageUserInfo data) {
                        mRootView.showUserInfo(data.getData());
                    }
                });
    }

    public void setUserFace( MultipartBody.Part file){
        mModel.setUserFace(file)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ChangeFaceResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ChangeFaceResponse data) {
                        System.out.println("setUserFace"+data.getData());
                        ChangeFaceResponse changeFaceResponse = data.getData();
                        mRootView.showSetUserFace(changeFaceResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showSetUserFace(null);
                    }
                });

    }
    public void setUserInfo(String uname, int sex, String intro){
        mModel.setUserInfo( uname,sex,intro)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        if(data.getCode() ==1 ) {
                            PreferenceUtil.getInstance(mApplication).saveString("user_id", uname);
                            mRootView.showMessage("用户资料修改成功");
                            mRootView.killMyself();
                        }
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
