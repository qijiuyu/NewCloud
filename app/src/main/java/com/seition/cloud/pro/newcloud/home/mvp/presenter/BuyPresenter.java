package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;
import com.seition.cloud.pro.newcloud.app.bean.member.PaySwitch;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BuyContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class BuyPresenter extends BasePresenter<BuyContract.Model, BuyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BuyPresenter(BuyContract.Model model, BuyContract.View rootView) {
        super(model, rootView);
    }

    public void buyCourseVideo(String vids, String pay_for, int coupon_id, boolean isCourseCard) {
        if (pay_for.equals(MyConfig.ALIPAY) || pay_for.equals(MyConfig.WXPAY))
            buyCourseVideoWithWxOrAli(vids, pay_for, coupon_id, isCourseCard);
        else
            buyCourseVideoWithoutWxOrAli(vids, pay_for, coupon_id, isCourseCard);
    }

    /**
     * 单课时购买
     *
     * @param vids    课程id
     * @param sid     课时id
     * @param pay_for 支付方式
     * @param vtype   1：点播 2：直播
     */
    public void buyCourseVideoItemWithWxOrAli(String vids, String sid, String pay_for, int vtype) {
        mModel.buyCourseVideoItem(vids, sid, pay_for, vtype)
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
                        payResult(data, pay_for, false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void payResult(PayResponse data, String pay_for, boolean isCourseCard) {
        if (data.getCode() == 1) {
            switch (pay_for) {
                case MyConfig.ALIPAY:
                case MyConfig.WXPAY:
                    if (isCourseCard) {
                        mRootView.showMessage(data.getMsg());
                        mRootView.killMyself();
                    } else {
                        PayResponse response = data.getData();
                        mRootView.showPayResult(response);
                    }
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

    public void buyCourseVideoWithWxOrAli(String vids, String pay_for, int coupon_id, boolean isCourseCard) {
        mModel.buyCourseVideo(vids, pay_for, coupon_id)
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
                        payResult(data, pay_for, isCourseCard);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }


    public void buyCourseVideoWithoutWxOrAli(String vids, String pay_for, int coupon_id, boolean isCourseCard) {
        mModel.buyCourseVideoNoWxOrAli(vids, pay_for, coupon_id)
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
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            mRootView.killMyself();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void buyCourseLive(String live_id, String pay_for, int coupon_id, boolean isCourseCard) {
        mModel.buyCourseLive(live_id, pay_for, coupon_id)
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
                        payResult(data, pay_for, isCourseCard);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }


    public void buyCourseOffline(String vids, String pay_for, int coupon_id) {
        if (pay_for.equals(MyConfig.ALIPAY) || pay_for.equals(MyConfig.WXPAY))
            buyCourseOfflineWithWxOrAli(vids, pay_for, coupon_id);
        else
            buyCourseOfflineWithoutWxOrAli(vids, pay_for, coupon_id);
    }

    public void buyCourseOfflineWithWxOrAli(String vids, String pay_for, int coupon_id) {
        mModel.buyCourseOffline(vids, pay_for, coupon_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(PayResponse data) {
                        payResult(data, pay_for, false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void buyCourseOfflineWithoutWxOrAli(String vids, String pay_for, int coupon_id) {
        mModel.buyCourseOffline1(vids, pay_for, coupon_id)
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
                        if (data.getCode() == 1) {
                            mRootView.killMyself();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void addFreeOrder(String vid, int vtype, String pay_for, int coupon_id) {
        mModel.addFreeOrder(vid, vtype, pay_for, coupon_id)
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
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }


    public void getUserAccount(boolean iscache) {
        mModel.getUserAccount(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
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

    public void getPaySwitch() {
        mModel.getPaySwitch()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PaySwitch>(mErrorHandler) {
                    @Override
                    public void onNext(PaySwitch data) {
                        ArrayList<String> datas = data.getData().getPay();

                        if (datas.size() > 0)
                            mRootView.showPayView(datas);
                    }
                });
    }

    public void getVideoCoupon(String id) {
        mModel.getVideoCoupon(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CouponBeans>(mErrorHandler) {
                    @Override
                    public void onNext(CouponBeans data) {
                        ArrayList<CouponBean> datas = data.getData();
                        mRootView.showCoupon(datas);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void getLiveCoupon(String live_id) {
        mModel.getLiveCoupon(live_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CouponBeans>(mErrorHandler) {
                    @Override
                    public void onNext(CouponBeans data) {
                        ArrayList<CouponBean> datas = data.getData();
                        mRootView.showCoupon(datas);
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
