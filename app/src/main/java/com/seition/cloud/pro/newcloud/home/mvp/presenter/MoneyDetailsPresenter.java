package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.money.MoneyDetail;
import com.seition.cloud.pro.newcloud.app.bean.money.MoneyDetailResponse;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MoneyContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MoneyDetailsListRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MoneyDetailsPresenter extends BasePresenter<MoneyContract.Model, MoneyContract.ListView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MoneyDetailsListRecyclerAdapter adapter;

    @Inject
    public MoneyDetailsPresenter(MoneyContract.Model model, MoneyContract.ListView rootView) {
        super(model, rootView);
    }

    private int page = 1 ,count = 10;
    boolean iscache = true;
    boolean isFirst = true;
    public void getBalanceList(boolean pull) {
        mRootView.showLoading();

        boolean isEvictCache = iscache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull ) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if(isFirst){
                isFirst = false;
                isEvictCache = false;
            }else
                isEvictCache = true;
        }else {
            isEvictCache = true;
            page++;
        }
        mModel.getBalanceList(count,page,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MoneyDetailResponse>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyDetailResponse data) {
                        MoneyDetailResponse response = data.getData();
                        ArrayList<MoneyDetail> datas = response.getList();
                        if(pull) {
                            adapter.setNewData(datas);
                            if(datas.size()>0) {
                                if (datas.size()<count) {
                                    if (adapter.getFooterViewsCount()==0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                }
                                else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            }
                            else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                        else {
                            adapter.addData(datas);
                            if (datas.size() <count) {
                                if (adapter.getFooterViewsCount()==0)
                                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }
                    }
                });
    }

    public void getSpiltList(boolean pull) {
        mRootView.showLoading();

        boolean isEvictCache = iscache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull ) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if(isFirst){
                isFirst = false;
                isEvictCache = false;
            }else
                isEvictCache = true;
        }else {
            isEvictCache = true;
            page++;
        }
        mModel.getSpiltList(count,page,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MoneyDetailResponse>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyDetailResponse data) {
                        MoneyDetailResponse response = data.getData();
                        ArrayList<MoneyDetail> datas = response.getList();
                        if(pull) {
                            adapter.setNewData(datas);
                            if(datas.size()>0) {
                                if (datas.size()<count) {
                                    if (adapter.getFooterViewsCount()==0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                }
                                else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            }
                            else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                        else {
                            adapter.addData(datas);
                            if (datas.size() <count) {
                                if (adapter.getFooterViewsCount()==0)
                                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }
                    }
                });
    }

    public void getCreditList(boolean pull) {
        mRootView.showLoading();

        boolean isEvictCache = iscache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull ) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if(isFirst){
                isFirst = false;
                isEvictCache = false;
            }else
                isEvictCache = true;
        }else {
            isEvictCache = true;
            page++;
        }
        mModel.getCreditList(count,page,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MoneyDetailResponse>(mErrorHandler) {
                    @Override
                    public void onNext(MoneyDetailResponse data) {
                        MoneyDetailResponse response = data.getData();
                        ArrayList<MoneyDetail> datas = response.getList();
                        if(pull) {
                            adapter.setNewData(datas);
                            if(datas.size()>0) {
                                if (datas.size()<count) {
                                    if (adapter.getFooterViewsCount()==0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                }
                                else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            }
                            else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                        else {
                            adapter.addData(datas);
                            if (datas.size() <count) {
                                if (adapter.getFooterViewsCount()==0)
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
