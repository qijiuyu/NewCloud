package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsClassify;
import com.seition.cloud.pro.newcloud.app.bean.news.NewsClassifyBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.home.mvp.contract.NewsContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.fragment.NewsListFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class NewsPresenter extends BasePresenter<NewsContract.Model, NewsContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public NewsPresenter(NewsContract.Model model, NewsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getNewsClassify() {
        mRootView.showLoading();
        mModel.getNewsClassifyList(MessageConfig.NEWS_CLASSIFY_LIST, true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ARRNewsClassify>(mErrorHandler) {
                    @Override
                    public void onNext(ARRNewsClassify data) {
                        ArrayList<NewsClassifyBean> list = data.getData();
                        ArrayList<FragmentBean> fragmentList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++)
                            fragmentList.add(new FragmentBean(list.get(i).getTitle()
                                    , NewsListFragment.getNewsListFragment(list.get(i))));
                        mRootView.showFragment(fragmentList);
                        mRootView.hideLoading();
                    }
                });
    }
}
