package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.jess.arms.widget.statue.MultiStateView;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ReceiveAddressRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class UserReceiveAddressListPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    ReceiveAddressRecyclerAdapter adapter;

    @Inject
    public UserReceiveAddressListPresenter(UserContract.Model model, UserContract.View rootView) {
        super(model, rootView);
    }


    public void getReceiveAddresses(boolean iscache) {
        mModel.getReceiveAddress(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ReceiveGoodsAddresss>(mErrorHandler) {
                    @Override
                    public void onNext(ReceiveGoodsAddresss data) {
                        ArrayList<ReceiveGoodsAddress> studyRecords = data.getData();
                        adapter.setNewData(studyRecords);

                        if (adapter.getItemCount() > 0)
                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                        else
                            mRootView.showStateViewState(MultiStateView.STATE_EMPTY);
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void addReceiveAddress(String province, String city, String area, String location, String address, String name, String phone, String is_default) {
        mModel.addReceiveAddress(province, city, area, location, address, name, phone, is_default)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ReceiveGoodsAddress>(mErrorHandler) {
                    @Override
                    public void onNext(ReceiveGoodsAddress data) {

                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1)
                            mRootView.killMyself();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void changeReceiveAddress(String province, String city, String area, String location, String address, String name, String phone,String address_id, String is_default) {
        mModel.changeReceiveAddress(province, city, area, location, address, name, phone,address_id, is_default)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ReceiveGoodsAddress>(mErrorHandler) {
                    @Override
                    public void onNext(ReceiveGoodsAddress data) {
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1)
                            mRootView.killMyself();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void deleteReceiveAddress(String address_id) {
        mModel.deleteReceiveAddress(address_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ReceiveGoodsAddress>(mErrorHandler) {
                    @Override
                    public void onNext(ReceiveGoodsAddress data) {
                        if (data.getCode() == 1)
                            getReceiveAddresses(true);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void setDefaultReceiveAddress(String address_id) {
        mModel.setDefaultReceiveAddress(address_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ReceiveGoodsAddress>(mErrorHandler) {
                    @Override
                    public void onNext(ReceiveGoodsAddress data) {
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
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
