package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionasks;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QuestionAnswerRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class QuestionaskDetailsPresenter extends BasePresenter<QuestionaskContract.Model, QuestionaskContract.DetailsView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    QuestionAnswerRecyclerAdapter adapter;


    @Inject
    public QuestionaskDetailsPresenter(QuestionaskContract.Model model, QuestionaskContract.DetailsView rootView) {
        super(model, rootView);
    }

    private int page = 1, count = 10;
    boolean isFirst = true;

    public void getQuestionDetails(int wid, boolean pull, boolean iscache) {
        mModel.getQuestionDetails(wid, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Questionask>(mErrorHandler) {
                    @Override
                    public void onNext(Questionask data) {

                        Questionask questionask = data.getData();

                        if (data.getCode() == 1)
                            mRootView.showDetails(questionask);
                    }
                });

    }

    public void getInitReviewConfig(View headerView) {
        if (headerView != null) headerView.setVisibility(View.GONE);//默认隐藏
        mModel.getInitReviewConfig()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CommentConfig>(mErrorHandler) {
                    @Override
                    public void onNext(CommentConfig data) {
                        CommentConfig config = data.getData();//0为开启，1为关闭
                        if (config != null)
                            showCommentButton(config.getWenda_switch() == 0, headerView);
                    }
                });
    }

    private void showCommentButton(boolean isVisible, View headerView) {
        if (headerView != null)
            headerView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void getQuestionAnswerList(int wid, boolean pull, boolean cache) {
        boolean isEvictCache = cache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

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
        mModel.getQuestionAnswerList(page, count, wid, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
//                    mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Questionasks>(mErrorHandler) {
                    @Override
                    public void onNext(Questionasks data) {
                        //拿到数据
                        ArrayList<Questionask> datas = data.getData();
                        if (pull) {
                            if (datas.size() > 0) {
                                adapter.setNewData(datas);
                                if (datas.size() < count) {
                                    if (adapter.getFooterViewsCount() == 0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            }
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
