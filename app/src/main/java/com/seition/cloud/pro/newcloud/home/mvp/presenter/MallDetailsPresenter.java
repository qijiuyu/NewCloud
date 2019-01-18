package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.config.CredPayConfig;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_CreditDetails;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MallDetailsPresenter extends BasePresenter<MallContract.Model, MallContract.DetailstView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MallDetailsPresenter(MallContract.Model model, MallContract.DetailstView rootView) {
        super(model, rootView);
    }

    public void getInitCredpayConfig() {
        mModel.getInitCredpayConfig()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CredPayConfig>(mErrorHandler) {
                    @Override
                    public void onNext(CredPayConfig data) {
                        if (data.getData() != null) {
                            MyConfig.isOpenCredPay = data.getData().getStatus() == 1;
                            MyConfig.credRatio = data.getData().getSplit_score();
                        }
                        mRootView.showPrice(data.getData());
                    }
                });
    }

    public void getCountDetails() {//积分
        mRootView.showLoading();
        mModel.getCountConfig()
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
                        mRootView.showDialog(credit);
                    }
                });
    }

    public void useAliPayOrWxPay(Mall mall, int count, String addressId, String payStyle) {//积分
        mRootView.showLoading();
        mModel.useAliPayOrWxPay(mall.getGoods_id(), count, addressId, ViewContentSettingUtils.getMallPrice(mall.getPrice() + mall.getFare()), payStyle)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(PayResponse data) {
                        if (data.getCode() == 1) {
                            PayResponse response = data.getData();
                            if (payStyle.equals(MyConfig.ALIPAY) || payStyle.equals(MyConfig.WXPAY))
                                mRootView.showPayResult(response);
                        }
                    }
                });
    }

    public void exchangeMallGood(int goods_id, int num, String address_id) {
        mModel.exchangeMallGood(goods_id, num, address_id)
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
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1)
                            mRootView.killMyself();
//                            mRootView.reLoad(num);

                    }
                });
    }

    public void getReceiveAddresses() {
        mModel.getReceiveAddress(true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRootView != null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ReceiveGoodsAddresss>(mErrorHandler) {
                    @Override
                    public void onNext(ReceiveGoodsAddresss data) {
                        ArrayList<ReceiveGoodsAddress> studyRecords = data.getData();
                        ReceiveGoodsAddress address = null;
                        if (data.getCode() == 1 && studyRecords.size() == 1) {
                            address = studyRecords.get(0);//.getProvince() + receiveGoodsAddress.getCity() + receiveGoodsAddress.getArea() + receiveGoodsAddress.getAddress();

                        } else if (data.getCode() == 1 && studyRecords.size() > 1) {
                            for (ReceiveGoodsAddress receiveGoodsAddress : studyRecords) {
                                if (receiveGoodsAddress.getIs_default().equals("1"))
                                    address = receiveGoodsAddress;//.getProvince() + receiveGoodsAddress.getCity() + receiveGoodsAddress.getArea() + receiveGoodsAddress.getAddress();
                            }
                        }
                        mRootView.setReceiveAddress(address);
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
