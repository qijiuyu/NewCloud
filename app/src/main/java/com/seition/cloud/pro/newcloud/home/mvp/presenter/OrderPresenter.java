package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.config.RefundConfig;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.bean.order.OrderRefund;
import com.seition.cloud.pro.newcloud.app.bean.order.Orders;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrderContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.fragment.OrderFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MultipartBody;


@ActivityScope
public class OrderPresenter extends BasePresenter<OrderContract.Model, OrderContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OrderPresenter(OrderContract.Model model, OrderContract.View rootView) {
        super(model, rootView);
    }

    public int page = 1;
    public int count = 10;
    boolean isFirst = true;

    public void getInitRefundConfig() {
        mModel.getInitRefundConfig()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<RefundConfig>(mErrorHandler) {
                    @Override
                    public void onNext(RefundConfig data) {
                        if (data.getData() != null)
                            MyConfig.isOpenRefund = data.getData().getRefund_switch() == 1;
                    }
                });
    }

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
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
                            PayResponse response = data.getData();
                            if (pay_for.equals(MyConfig.ALIPAY) || pay_for.equals(MyConfig.WXPAY))
                                mRootView.showPayResult(response);
                            else
                                mRootView.reload();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void getOrders(String type, String pay_status, String orderType, String schoolId, boolean isCache, boolean pull) {
        boolean isEvictCache = isCache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if (isFirst) {
                isFirst = false;
                isEvictCache = false;
            } else
                isEvictCache = true;
        } else {
            isEvictCache = true;
            page++;
        }

        mModel.getOrders(type, pay_status, orderType, schoolId, page, count, true)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRootView != null)
                            mRootView.hideLoading();
//                        mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Orders>(mErrorHandler) {
                    @Override
                    public void onNext(Orders data) {
                        System.out.println("getOrders ");
                        ArrayList<Order> datas = data.getData();
                        mRootView.setDatas(datas, pull);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void refundOrderInfo(int order_type, int order_id) {
        mRootView.showLoading();
        mModel.refundOrderInfo(order_type, order_id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRootView != null)
                            mRootView.hideLoading();
//                        mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<OrderRefund>(mErrorHandler) {
                    @Override
                    public void onNext(OrderRefund data) {
                        System.out.println("refundOrderInfo ");
                        OrderRefund refund = data.getData();
                        mRootView.showRefundOrderInfo(refund);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void orderRefund(int order_type, int order_id, String refund_reason, String refund_note, String voucher) {
        mRootView.showLoading();
        mModel.orderRefund(order_type, order_id, refund_reason, refund_note, voucher)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRootView != null)
                            mRootView.hideLoading();
//                        mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        if (mRootView == null) return;
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            mRootView.reload();
                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
                            mRootView.killMyself();
                        } else
                            mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void orderCancel(int order_type, int order_id) {
        mModel.orderCancel(order_type, order_id)
                .subscribeOn(Schedulers.io())
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("orderCancel ");
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            mRootView.reload();
                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void cancelApplicationForDrawbackOrder(int order_type, int order_id) {
        mModel.cancelApplicationForDrawbackOrder(order_type, order_id)
                .subscribeOn(Schedulers.io())
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("orderCancel ");
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            mRootView.reload();
                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void orderPay(int order_type, int order_id, int coupon_id, String pay_for) {
        mModel.orderPay(order_type, order_id, coupon_id, pay_for)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (mRootView != null)
                            mRootView.hideLoading();
//                        mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("orderPay ");

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }


    public void getBanlanceConfig() {
        mRootView.showLoading();
        mModel.getBanlanceConfig()
                .subscribeOn(Schedulers.io())
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
                        mRootView.showDialog(balance);
                    }
                });
    }

    public void buyCourseVideo(String vids, String pay_for, int coupon_id) {
        mModel.buyCourseVideo(vids, pay_for, coupon_id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(PayResponse data) {
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
                            PayResponse response = data.getData();
                            if (pay_for.equals(MyConfig.ALIPAY) || pay_for.equals(MyConfig.WXPAY))
                                mRootView.showPayResult(response);
                            else
                                mRootView.reload();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void buyCourseLive(String live_id, String pay_for, int coupon_id) {
        mModel.buyCourseLive(live_id, pay_for, coupon_id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(PayResponse data) {
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
                            PayResponse response = data.getData();

                            if (pay_for.equals(MyConfig.ALIPAY) || pay_for.equals(MyConfig.WXPAY))
                                mRootView.showPayResult(response);
                            else
                                mRootView.reload();
                        }
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
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<PayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(PayResponse data) {
                        System.out.println("buyCourseOffline ");
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
                            PayResponse response = data.getData();
                            mRootView.showPayResult(response);
                        }
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
                            mRootView.reload();
//                            PreferenceUtil.getInstance(mApplication).saveBoolean("OdersNeedReload", true);
//                            mRootView.killMyself();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

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
                        UploadResponse uploadResponse = data.getData();
                        mRootView.showUploadAttachId(uploadResponse.getAttach_id());
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
