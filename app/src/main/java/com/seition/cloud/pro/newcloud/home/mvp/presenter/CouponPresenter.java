package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CouponContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment.CouponFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CouponRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CouponPresenter extends BasePresenter<CouponContract.Model, CouponContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    CouponRecyclerAdapter adapter;

    @Inject
    public CouponPresenter(CouponContract.Model model, CouponContract.View rootView) {
        super(model, rootView);
    }

    public void showCouponFragment(boolean isOrganization, String organizationId) {
        ArrayList<FragmentBean> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentBean("优惠券", CouponFragment.newInstance(1, isOrganization, organizationId)));
        fragmentList.add(new FragmentBean("打折卡", CouponFragment.newInstance(2, isOrganization, organizationId)));
        if (!isOrganization) {
            fragmentList.add(new FragmentBean("课程卡", CouponFragment.newInstance(5, isOrganization, organizationId)));
            fragmentList.add(new FragmentBean("会员卡", CouponFragment.newInstance(3, isOrganization, organizationId)));
        }
        mRootView.showFragment(fragmentList);
    }

    public void getMyCouponList(boolean isOrganizattion, int type, int status, boolean pull, boolean cache) {
        boolean isEvictCache = cache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull) {//默认在第一次下拉刷新时使用缓存
            if (isFirst) {
                isFirst = false;
                isEvictCache = false;
            } else
                isEvictCache = true;
        } else {
            isEvictCache = true;
        }

        mModel.getMyCoupons(type, status, true)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CouponBeans>(mErrorHandler) {
                    @Override
                    public void onNext(CouponBeans data) {
                        ArrayList<CouponBean> datas = data.getData();
                        adapter.setIsMy(!isOrganizattion);
                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() == 0)
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (!pull)
                            adapter.loadMoreFail();
                        else
                            adapter.setEmptyView(AdapterViewUtils.getErrorViwe(mApplication));
                    }
                });
    }

    boolean isFirst = true;

    public void getOrganizationCouponList(boolean isOrganizattion, int type, String organizationId, boolean pull, boolean cache) {
        boolean isEvictCache = cache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull) {//默认在第一次下拉刷新时使用缓存
            if (isFirst) {
                isFirst = false;
                isEvictCache = false;
            } else
                isEvictCache = true;
        } else {
            isEvictCache = true;
        }

        mModel.getOrganizationCoupons(type, organizationId, true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CouponBeans>(mErrorHandler) {
                    @Override
                    public void onNext(CouponBeans data) {
                        System.out.println("getOrganizationList");
                        ArrayList<CouponBean> datas = data.getData();
                        adapter.setIsMy(!isOrganizattion);
                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() == 0)
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (!pull)
                            adapter.loadMoreFail();
                        else
                            adapter.setEmptyView(AdapterViewUtils.getErrorViwe(mApplication));
                    }
                });
    }

    public void getCanUseCouponWithMe(int courseType, String courseId, boolean pull) {
        (courseType == 1 ?
                mModel.getCanUseCourseCouponWithMe(courseId)
                : mModel.getCanUseLiveCouponWithMe(courseId))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CouponBeans>(mErrorHandler) {
                    @Override
                    public void onNext(CouponBeans data) {
                        ArrayList<CouponBean> datas = data.getData();
                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() == 0)
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (!pull)
                            adapter.loadMoreFail();
                        else
                            adapter.setEmptyView(AdapterViewUtils.getErrorViwe(mApplication));
                    }
                });
    }

    /**
     * 领取优惠券
     *
     * @param code
     */
    public void grantCoupon(String code) {
        mModel.grantCoupon(code)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
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
                        mRootView.refresh();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void useCoupon(String coupon_id) {
        mModel.useCoupon(coupon_id)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
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
                        mRootView.refresh();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void useVipCoupon(String coupon_id) {
        mModel.useVipCoupon(coupon_id)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
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
                        mRootView.refresh();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void getCoupon(int courseType, int type, String courseId) {
        (courseType == 1 ? mModel.getCourseCoupon(type, courseId) : mModel.getLiveCoupon(type, courseId))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CouponBeans>(mErrorHandler) {
                    @Override
                    public void onNext(CouponBeans data) {
                        mRootView.setData(data.getData());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
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
