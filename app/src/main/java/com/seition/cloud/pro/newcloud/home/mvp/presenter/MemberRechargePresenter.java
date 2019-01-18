package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.member.PaySwitch;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseGridRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MemberRechargePresenter extends BasePresenter<MemberContract.Model, MemberContract.MemberRechargeView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    CourseGridRecyclerAdapter adapter;

    @Inject
    public MemberRechargePresenter(MemberContract.Model model, MemberContract.MemberRechargeView rootView) {
        super(model, rootView);
    }

    public  void getPaySwitch(){
        mModel.getPaySwitch()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PaySwitch>(mErrorHandler) {
                    @Override
                    public void onNext(PaySwitch data) {
                        ArrayList<String> datas = data.getData().getPay();

                        if(datas.size()>0)
                            mRootView.showPayView(datas);
                    }
                });
    }

    public void rechargeVip(String pay_for, int user_vip, String  vip_type_time,int  vip_time) {
        mModel.rechargeVip( pay_for,  user_vip,   vip_type_time,  vip_time)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(PayResponse data) {
                        if (data.getCode() == 1) {
                            switch (pay_for) {
                                case MyConfig.ALIPAY:
                                case MyConfig.WXPAY:
                                    PayResponse response = data.getData();
                                    mRootView.showPayResult(response);
                                    break;
                                case MyConfig.ICNPAY:
                                case MyConfig.UNIONPAY:
                                case MyConfig.SPIPAY:
                                    mRootView.showMessage(data.getMsg());
                                    mRootView.killMyself();
                                    break;
                            }

                        } else
                            mRootView.showMessage(data.getMsg());
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
