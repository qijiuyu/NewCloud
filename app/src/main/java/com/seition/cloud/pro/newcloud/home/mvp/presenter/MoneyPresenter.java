package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_SpiltDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.SpiltDetails;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.WXPayUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MoneyContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MoneyPresenter extends BasePresenter<MoneyContract.Model, MoneyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MoneyPresenter(MoneyContract.Model model, MoneyContract.View rootView) {
        super(model, rootView);
    }

    public void pay() {

    }

    public final static int TypeJifen = 1;
    public final static int TypeIncome = 2;
    public final static int TypeBanlance = 3;

    public void getDetails(int type) {
        switch (type) {
            case TypeBanlance:
                //余额
                getBanlanceDetails();
                break;
            case TypeIncome:
                //收入
                getIncomeDetails();
                break;
            case TypeJifen:
                //积分
                getCountDetails();
                break;
        }
    }

    public void getBanlanceDetails() {
        mRootView.showLoading();
        mModel.getBanlanceConfig(true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Data_BalanceDetails>(mErrorHandler) {
                    @Override
                    public void onNext(Data_BalanceDetails data) {
                        BalanceDetails balance = data.getData();
                        mRootView.showBalance(balance);
                    }
                });
    }

    public void getIncomeDetails() {//收入
        mRootView.showLoading();
        mModel.getIncomeConfig(true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Data_SpiltDetails>(mErrorHandler) {
                    @Override
                    public void onNext(Data_SpiltDetails data) {
                        SpiltDetails spilt = data.getData();
                        mRootView.showSpilt(spilt);
                    }
                });
    }

    public void getCountDetails() {//积分
        mRootView.showLoading();
        mModel.getCountConfig(true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Data_CreditDetails>(mErrorHandler) {
                    @Override
                    public void onNext(Data_CreditDetails data) {
                        CreditDetails credit = data.getData();
                        mRootView.showCredit(credit);
                    }
                });
    }

    public void rechargeBanlance(String pay_for, String money) {
        mRootView.showLoading();
        mModel.rechargeBanlance(pay_for, money)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(PayResponse payResponse) {
                        PayResponse data = payResponse.getData();
                        if (data.getAlipay() != null) {
                            mRootView.toAliPay(data.getAlipay().getBasic());
                        } else if (data.getWxpay() != null) {
                            WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                            builder.setAppId(data.getWxpay().getBasic().getAppid())
                                    .setPartnerId(data.getWxpay().getBasic().getPartnerid())
                                    .setPrepayId(data.getWxpay().getBasic().getPrepayid())
                                    .setPackageValue(data.getWxpay().getBasic().getPackages())
                                    .setNonceStr(data.getWxpay().getBasic().getNoncestr())
                                    .setTimeStamp(data.getWxpay().getBasic().getTimestamp())
                                    .setSign(data.getWxpay().getBasic().getSign())
                                    .build().toWXPayNotSign(mApplication, MyConfig.APP_ID);
                        }
                    }
                });
    }

    public void recharge(String type, String exchange_score) {
        mRootView.showLoading();
        mModel.recharge(type, exchange_score)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1)
                            mRootView.killMyself();
                    }
                });
    }

    public void incomeToWithdraw(String exchange_balance, String type, int card_id) {
        mRootView.showLoading();
        mModel.incomeToWithdraw(exchange_balance, type, card_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1)
                            mRootView.killMyself();
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
