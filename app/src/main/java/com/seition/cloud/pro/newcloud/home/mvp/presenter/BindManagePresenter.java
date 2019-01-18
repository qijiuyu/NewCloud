package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class BindManagePresenter extends BasePresenter<BindManageContract.Model, BindManageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public BindManagePresenter(BindManageContract.Model model, BindManageContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @param account       银行卡号
     * @param accountmaster 姓名
     * @param accounttype   银行卡名称
     * @param bankofdeposit 开户行地址
     * @param location
     * @param province
     * @param city
     * @param area
     * @param tel_num
     */
    public void addBindBank(String account, String accountmaster, String accounttype, String bankofdeposit, String location, String province, String city, String area, String tel_num) {
        mModel.addBindBank(account, accountmaster, accounttype, bankofdeposit, location, province, city, area, tel_num)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("unbindBank ");

                        if (data.getCode() == 1) {
                            mRootView.showMessage("添加银行卡成功");
                            mRootView.killMyself();
                        } else
                            mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage("添加银行卡失败");
                    }
                });
    }

    public void loginBind() {
        mModel.getLoginBindStatus()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("unbindBank ");

                        if (data.getCode() == 1) {
                            mRootView.showMessage("添加银行卡成功");
                            mRootView.killMyself();
                        } else
                            mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage("添加银行卡失败");
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
