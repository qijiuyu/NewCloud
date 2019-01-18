package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.config.CredPayConfig;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallRankData;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MallRankHorRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MallPresenter extends BasePresenter<MallContract.Model, MallContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MallRankHorRecyclerAdapter adapter;

    @Inject
    public MallPresenter(MallContract.Model model, MallContract.View rootView) {
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
                            mRootView.showListData();
                        }
                    }
                });
    }

    public void getMallCategory(int goods_category_id, boolean iscache) {
        mModel.getMallCategory(goods_category_id, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MallCategory>(mErrorHandler) {
                    @Override
                    public void onNext(MallCategory data) {
                        System.out.println("getMallCategory");
                        ArrayList<MallCategory> commonCategories = data.getData();
                        mRootView.setFragmentList(commonCategories);
                    }
                });
    }

    public void getMallBanner(String place, boolean iscache) {
        mModel.getMallBanner(place, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean<ArrayList<AdvertBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean<ArrayList<AdvertBean>> data) {
                        ArrayList<AdvertBean> advertBeans = data.getData();
                        mRootView.setBanner(advertBeans);
                    }
                });
    }

    public void getMallRankListData(String type, String keyword, int page, int count, boolean iscache) {
        mModel.getMallRankDatas(type, keyword, page, count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MallRankData>(mErrorHandler) {
                    @Override
                    public void onNext(MallRankData data) {
                        MallRankData mallRankData = data.getData();
                        ArrayList<Mall> mallRanks = mallRankData.getRank();
                        adapter.setNewData(mallRanks);
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
