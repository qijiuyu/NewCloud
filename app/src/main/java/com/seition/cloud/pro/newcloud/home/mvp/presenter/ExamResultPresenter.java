package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamRankUser;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExaminationActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamRankRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ExamResultPresenter extends BasePresenter<ExamContract.ExamModel, ExamContract.ExamRestultView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    ExamRankRecyclerAdapter adapter;

    @Inject
    public ExamResultPresenter(ExamContract.ExamModel model, ExamContract.ExamRestultView rootView) {
        super(model, rootView);
    }


    private int page = 1, count = 20;
    boolean isFirst = true;

    public void getExamRankUser(String exams_users_id, boolean pull, boolean cache) {

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
        mModel.getExamRankUser(exams_users_id, page, count, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ExamRankUser>(mErrorHandler) {
                    @Override
                    public void onNext(ExamRankUser data) {
                        ExamRankUser rankUser = data.getData();
                        ArrayList<ExamRankUser.RankUser> datas = rankUser.getList();
                        ExamRankUser.RankUser user = rankUser.getNow();
                        mRootView.showExamUser(user);
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
                            }
//                            else
//                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
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

    public void getExamInfo(String exams_users_id, int paper_id, int exams_type) {
        mRootView.showLoading();
        mModel.getExamInfo(paper_id, exams_type, exams_users_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Examination>(mErrorHandler) {
                    @Override
                    public void onNext(Examination data) {
                        mRootView.hideLoading();//上拉加载更多
                        if (data != null && data.getData() != null)
                            startExam(data.getData(), exams_type, true);
                        else
                            mRootView.showMessage("没有数据");
                    }
                });
    }

    //错题重练
    public void examinationWrongExam(String exams_users_id, int paper_id) {
        mRootView.showLoading();
        mModel.examinationWrongExam(exams_users_id, paper_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Examination>(mErrorHandler) {
                    @Override
                    public void onNext(Examination data) {
                        MExamBean bean = data.getData();
                        if (data.getCode() == 1)
                            startExam(bean, 3, true);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void getWrongExamData(String exams_users_id, int paper_id, int exams_type) {
        mRootView.showLoading();
        mModel.getWrongExamData(paper_id, exams_users_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Examination>(mErrorHandler) {
                    @Override
                    public void onNext(Examination data) {
                        mRootView.hideLoading();//上拉加载更多
                        if (data != null && data.getData() != null)
                            startExam(data.getData(), 3, false);
                        else
                            mRootView.showMessage("没有数据");
                    }
                });
    }

    public void getResult(String exams_users_id, int paper_id, int exams_type) {
        mRootView.showLoading();
        mModel.getResult(paper_id, exams_users_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Examination>(mErrorHandler) {
                    @Override
                    public void onNext(Examination data) {
                        mRootView.hideLoading();
                        if (data != null && data.getData() != null)
                            startExam(data.getData(), exams_type, false);
                        else
                            mRootView.showMessage("没有数据");
                    }
                });
    }


    private void startExam(MExamBean examBean, int exams_type, boolean isTest) {
        if (examBean == null) {
            return;
        }
        if (!isTest) examBean.setUserAnswer();
        else examBean.setUserAnswerTemp();
        mRootView.launchActivity(new Intent(mApplication, ExaminationActivity.class)
                .putExtra(MessageConfig.START_EXAMINATION, examBean)
                .putExtra(MessageConfig.START_EXAMINATION_IS_TEST, isTest)
                .putExtra(MessageConfig.START_EXAMINATION_TYPE, exams_type));
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
