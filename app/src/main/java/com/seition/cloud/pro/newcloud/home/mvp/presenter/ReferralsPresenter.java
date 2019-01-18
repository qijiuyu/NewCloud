package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.bean.referrals.Arr_Referrals;
import com.seition.cloud.pro.newcloud.app.bean.referrals.OwnerQRCode;
import com.seition.cloud.pro.newcloud.app.bean.referrals.ReferralsBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ReferralsContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.fragment.MainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals.OwnerReferralsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals.adapter.ReferralsListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Created by addis on 2018/11/23.
 */
@ActivityScope
public class ReferralsPresenter extends BasePresenter<ReferralsContract.Model, ReferralsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    ReferralsListAdapter adapter;
    boolean isFirst = true;
    private int count = 20;
    private int page = 1;
    String uid;

    @Inject
    public ReferralsPresenter(ReferralsContract.Model model, ReferralsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onLoadmore() {
        super.onLoadmore();
        page++;
        getReferralsList(false, uid);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        getReferralsList(true, uid);
    }

    public void getOwnerQRCode() {
        mRootView.showLoading();
        mModel.getOwnerQRCode()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<OwnerQRCode>(mErrorHandler) {
                    @Override
                    public void onNext(OwnerQRCode data) {
                        mRootView.showMyQRCode(data.getData());
                    }
                });
    }

    public void getReferralsList(boolean pull, String uid) {
        mRootView.showLoading();
        this.uid = uid;
        boolean isEvictCache = true;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull) {//默认在第一次下拉刷新时使用缓存
//            page = 1;
            if (isFirst) {
                isFirst = false;
                isEvictCache = false;
            } else
                isEvictCache = true;
        } else {
            isEvictCache = true;
//            page++;
        }
        mModel.getReferralsList(page, count, uid)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Arr_Referrals>(mErrorHandler) {
                    @Override
                    public void onNext(Arr_Referrals data) {
                        ArrayList<ReferralsBean> datas = data.getData();
                        datas.add(0, null);
                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                if (datas.size() < count) {
                                    if (adapter.getFooterViewsCount() == 0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);
                            if (datas.size() < count) {
                                if (adapter.getFooterViewsCount() == 0)
                                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }

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