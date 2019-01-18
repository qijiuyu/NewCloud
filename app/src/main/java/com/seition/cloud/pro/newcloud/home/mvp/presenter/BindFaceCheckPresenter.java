package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MultipartBody;


@ActivityScope
public class BindFaceCheckPresenter extends BasePresenter<BindManageContract.Model, BindManageContract.FaceCheckView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public BindFaceCheckPresenter(BindManageContract.Model model, BindManageContract.FaceCheckView rootView) {
        super(model, rootView);
    }

    /*public void getFaceSaveStatus(   ){
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

//                        ArrayList<String> bankNames = data.getData();
//                        bankRecyclerAdapter.setNewData(bankNames);
//                        if(bankRecyclerAdapter.getItemCount()>0)
//                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
//                        else
//                            mRootView.showStateViewState(MultiStateView.STATE_EMPTY);
                    }
                });
    }*/

    public void uploadFile(MultipartBody.Part file) {
        mModel.uploadFile(file)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<UploadResponse>(mErrorHandler) {
                    @Override
                    public void onNext(UploadResponse data) {
                        System.out.println("uploadFile" + data.getData());
                        UploadResponse uploadResponse = data.getData();
                        mRootView.showUploadAttachId(uploadResponse);
                    }
                });
    }

    public void faceLogin(String attach_id) {
        mModel.faceLogin(attach_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<User>(mErrorHandler) {
                    @Override
                    public void onNext(User data) {
                        System.out.println("faceLogin" + data.getData());

                        if (data.getCode() == 1) {
                            User user = data.getData();
                            PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(mApplication);
                            preferenceUtil.saveLoginUser(user);
//                            preferenceUtil.saveString("oauth_token_secret", user.getOauth_token_secret());
//                            preferenceUtil.saveString("oauth_token", user.getOauth_token());
//                            preferenceUtil.saveString("user_id", user.getUid()+"");
//                            preferenceUtil.saveString("uname", user.getUname());
//                            preferenceUtil.saveString("user_avatar", user.getUserface());
                            mRootView.faceLogin(true);
                        } else {
                            mRootView.showMessage(data.getMsg());
                            mRootView.faceLogin(false);
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.faceLogin(false);
                    }
                });
    }

    public void faceVerify(String attach_id) {
        mModel.faceVerify(attach_id)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("faceVerify" + data.getData());
                        if (data.getCode() == 1) {
                            mRootView.faceVerify(true);
                            mRootView.hideLoading();
                        } else
                            mRootView.faceVerify(false);
                        mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.faceVerify(false);
                    }
                });
    }

    public void faceAdd(String attach_id) {
        mModel.faceAdd(attach_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("faceAdd" + data.getData());
                        if (data.getCode() == 1) {
                            mRootView.faceAdd(true);
                            mRootView.killMyself();
                        } else
                            mRootView.faceAdd(false);
                        mRootView.hideLoading();
                        mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.faceAdd(false);
                        mRootView.hideLoading();
                    }
                });
    }

    public void faceCreate(String attach_id) {
        mModel.faceCreate(attach_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("faceCreate" + data.getData());


                        if (data.getCode() == 1) {
                            mRootView.faceCreated(true);
                        } else
                            mRootView.faceCreated(false);
                        mRootView.hideLoading();
                        mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.faceCreated(false);
                        mRootView.hideLoading();
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
