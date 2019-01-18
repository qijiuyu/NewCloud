package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsItem;
import com.seition.cloud.pro.newcloud.app.bean.news.NewsItemBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.NewsContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.activity.NewsDetailsActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.adapter.NewsListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class NewsListPresenter extends BasePresenter<NewsContract.Model, NewsContract.FragmentView> implements BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    int page = 1;
    int count = 10;
    String cid;


    @Inject
    NewsListAdapter adapter;
    @Inject
    public NewsListPresenter(NewsContract.Model model, NewsContract.FragmentView rootView) {
        super(model, rootView);
    }


    @Override
    public void onLoadmore() {
        super.onLoadmore();
        page++;
        getNewsList(false);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        page = 1;
        getNewsList(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getNewsList(String cid, boolean pullToRefresh) {
        this.cid = cid;
        getNewsList(pullToRefresh);
    }

    public void getNewsList(boolean pull) {
        mRootView.showLoading();
        mModel.getNewsList(page, count, cid, MessageConfig.NEWS_LIST, true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null)
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ARRNewsItem>(mErrorHandler) {
                    @Override
                    public void onNext(ARRNewsItem data) {

                        ArrayList<NewsItemBean> datas = data.getData();
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //进入资讯详情页
        mRootView.launchActivity(new Intent(mApplication, NewsDetailsActivity.class)
                .putExtra("newsItemBean", (NewsItemBean) adapter.getItem(position))
        );
    }
}