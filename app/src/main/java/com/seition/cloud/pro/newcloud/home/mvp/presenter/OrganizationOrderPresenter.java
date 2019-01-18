package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.bean.order.OrganizationOrder;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class OrganizationOrderPresenter extends BasePresenter<OrganizationContract.Model, OrganizationContract.FragmentView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


   /* @Inject
    OrderListRecyclerAdapter adapter;*/


    @Inject
    public OrganizationOrderPresenter(OrganizationContract.Model model, OrganizationContract.FragmentView rootView) {
        super(model, rootView);
    }

    public int page = 1, count = 10;
    boolean isFirst = true;
    public void getOrganizationOrders(String school_id, String pay_status, boolean pull, boolean cache) {
        boolean isEvictCache = cache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

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
        mModel.getOrganizationOrderList(page, count, school_id, pay_status, isEvictCache)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<OrganizationOrder>(mErrorHandler) {
                    @Override
                    public void onNext(OrganizationOrder data) {
                        OrganizationOrder organizationOrder = data.getData();
                        ArrayList<Order> datas = organizationOrder.getList();
                        mRootView.setDatas(datas, pull);
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
