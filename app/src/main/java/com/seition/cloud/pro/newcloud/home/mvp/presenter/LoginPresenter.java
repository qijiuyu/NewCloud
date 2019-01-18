package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.InitApp;
import com.seition.cloud.pro.newcloud.app.bean.McryptKey;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LoginContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void getMcryptKey() {
        mModel.getMcryptKey()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<InitApp>(mErrorHandler) {
                    @Override
                    public void onNext(InitApp data) {
                        McryptKey key = data.getData().getMcryptKey();
                        if (data.getCode() == 1) {
                            PreferenceUtil.getInstance(mApplication).saveString(MyConfig.Config_McryptKey, key.getMcrypt_key());
                            if (MyConfig.isOpenFaceMoudle) {
                                getFaceSence(false, "login");
                            }
                        }
                    }
                });
    }

    public void getInitRegisterType() {
        mModel.getInitRegisterType()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<RegisterTypeInit>(mErrorHandler) {
                    @Override
                    public void onNext(RegisterTypeInit data) {
                        RegisterTypeInit registerType = data.getData();
                        mRootView.showRegisterType(registerType.getAccount_type());
                    }
                });
    }

    public void login(String uname, String password) throws Exception {
        mModel.login(uname, password)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<User>(mErrorHandler) {
                    @Override
                    public void onNext(User data) {
                        if (data.getCode() == 1) {
                            User user = data.getData();
                            PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(mApplication);
                            preferenceUtil.saveLoginUser(user);
                            if (isOpenFaceMoudleLoginCompulsiveFaceCheck)
                                checkFace(true);
                            else
                                mRootView.toHome();
                        } else mRootView.showMessage(data.getMsg());
                    }
                });
    }

    public void feedBack(String content, String way) {
        mModel.feedBack(content, way)
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
                        if (data.getCode() == 1) {
                            mRootView.showMessage("");
                            mRootView.killMyself();
                        } else mRootView.showMessage(data.getMsg());
                    }
                });
    }

    public void getUnionId(String access_token) {
        mModel.getUnionId(access_token)
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
                        if (data.getCode() == 1) {
                            mRootView.showMessage("");
//                            mRootView.killMyself();
                        } else mRootView.showMessage(data.getMsg());
                    }
                });
    }

    public void loginSync(String app_token, String app_login_type) {
        mModel.loginSync(app_token, app_login_type)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<User>(mErrorHandler) {
                    @Override
                    public void onNext(User data) {
                        if (mRootView == null) return;
                        if (data.getCode() == 1) {
                            User user = data.getData();
                            PreferenceUtil preferenceUtil = PreferenceUtil.getInstance(mApplication);
                            preferenceUtil.saveLoginUser(user);
                            if (isOpenFaceMoudleLoginCompulsiveFaceCheck)
                                checkFace(true);
                            else
                                mRootView.toHome();
                        } else {
                            mRootView.showMessage(data.getMsg());
                            mRootView.toRegister(app_token, app_login_type);
                        }
                    }
                });
    }

    public void getFaceSaveStatus() {
        mModel.getFaceSaveStatus()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FaceStatus>(mErrorHandler) {
                    @Override
                    public void onNext(FaceStatus data) {
                        System.out.println("getFaceSaveStatus ");
                        FaceStatus faceStatus = data.getData();
                        if (data.getCode() == 1) {
                            mRootView.showFaceSaveStatus(faceStatus);
                        } else
                            mRootView.showMessage("人脸储存状态获取失败");

                    }
                });
    }


    public void getFaceSence(boolean isOpen, String face) {//{"data":{"is_open":0,"open_scene":[]},"code":1,"msg":"获取成功"}
        isOpenFaceMoudle = false;
        isOpenFaceMoudleLoginCompulsiveFaceCheck = false;
        mModel.getFaceSence()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FaceSence>(mErrorHandler) {
                    @Override
                    public void onNext(FaceSence data) {
                        FaceSence faceSence = data.getData();
                        //拿到数据
//                        if (data.getCode() == 1) {
                        isOpenFaceMoudle = faceSence.getIs_open() == 1 ? true : false;
                        boolean canFaceLogin = false;
                        PreferenceUtil.getInstance(mApplication).saveInt("SenceOpen", faceSence.getIs_open());

                        if (faceSence != null && faceSence.getIs_open() == 1) {
                            for (int i = 0; i < faceSence.getOpen_scene().size(); i++) {
                                if (faceSence.getOpen_scene().get(i).equals(face))
                                    canFaceLogin = true;
//                                        break;
                                if (faceSence.getOpen_scene().get(i).equals("login_force_verify"))
                                    isOpenFaceMoudleLoginCompulsiveFaceCheck = true;

                            }
                        }
                        mRootView.setFaceLoginTextVisibiliity(canFaceLogin);

//                        } else mRootView.showMessage(data.getMsg());
                    }
                });
    }

    public boolean isOpenFaceMoudle = false;//是否开启人脸功能
    public boolean isOpenFaceMoudleLoginCompulsiveFaceCheck = false;//是否开启登录后强制人脸验证

    private void checkFace(boolean isCheckOk) {

        if (isCheckOk) {
            if (isOpenFaceMoudle && isOpenFaceMoudleLoginCompulsiveFaceCheck)
                getFaceSaveStatus();
//                else
//                    writeUser();
        }

//                Net.checkFaceScene(mContext, "login_force_verify", faceHandler, false);
//        else writeUser();
    }


    public void moreLogin(int id) {
        switch (id) {

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
