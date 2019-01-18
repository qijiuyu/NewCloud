package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindAliAccount;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BankRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BindBankManageRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BindBankRecyclerAdapter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class BindAlipayManagePresenter extends BasePresenter<BindManageContract.Model, BindManageContract.AliManageView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    BindBankRecyclerAdapter adapter;
    @Inject
    BankRecyclerAdapter bankRecyclerAdapter;
    @Inject
    BindBankManageRecyclerAdapter manageRecyclerAdapter;

    @Inject
    public BindAlipayManagePresenter(BindManageContract.Model model, BindManageContract.AliManageView rootView) {
        super(model, rootView);
    }

    public void getAlipayInfo() {
        mModel.getAlipayInfo(true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BindAliAccount>(mErrorHandler) {
                    @Override
                    public void onNext(BindAliAccount data) {
                        System.out.println("getAlipayInfo ");
                        BindAliAccount aliAccount = data.getData();
                        String id = aliAccount.getId();
                        if (aliAccount.getId() != null)
                            mRootView.showAlipayInfo(aliAccount);
                        else
                            mRootView.showAlipayInfo(null);

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void setAlipay(String real_name, String alipay_account) {
        mModel.setAlipay(real_name, alipay_account)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BindAliAccount>(mErrorHandler) {
                    @Override
                    public void onNext(BindAliAccount data) {
                        BindAliAccount aliAccount = data.getData();
                        if (data.getCode() == 1)
                            getAlipayInfo();
//                            mRootView.showAlipayInfo(aliAccount);
                        else
                            mRootView.showMessage(data.getMsg());
                    }
                });
    }

    public void unbindAlipay(String id) {
        mModel.unbindAlipay(id)
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
                        System.out.println("unbindAlipay ");
                        if (data.getCode() == 1)
                            mRootView.showAlipayInfo(null);

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
