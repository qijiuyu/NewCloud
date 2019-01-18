package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExaminationActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamCollectRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ExamOwnerPresenter extends BasePresenter<ExamContract.ExamModel, ExamContract.ExamOwnerView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    ExamRecyclerAdapter adapter;
    @Inject
    ExamCollectRecyclerAdapter collectAdapter;

    @Inject
    public ExamOwnerPresenter(ExamContract.ExamModel model, ExamContract.ExamOwnerView rootView) {
        super(model, rootView);
    }

    private int page = 1, count = 10;
    boolean isFirst = true;

    public void getExamOwner(int log_type, boolean pull, boolean cache) {

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
        mModel.getExamOwner(log_type, page, count, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Exam>(mErrorHandler) {
                    @Override
                    public void onNext(Exam data) {
                        ArrayList<Exam> datas = data.getData();

                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                mRootView.emptyData(false);
                                if (datas.size() < count) {
//                                    if (adapter.getFooterViewsCount() == 0 && page != 1) {
//                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
//                                    }
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                mRootView.emptyData(true);
//                            adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);
                            if (datas.size() < count) {
//                                if (adapter.getFooterViewsCount() == 0)
//                                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
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
                            mRootView.emptyData(true);
//                            adapter.setEmptyView(AdapterViewUtils.getErrorViwe(mApplication));
                    }
                });
    }

    public void getCollectExam(boolean pull, boolean cache) {
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
        mModel.getCollectExam(page, count, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CollectExam>(mErrorHandler) {
                    @Override
                    public void onNext(CollectExam data) {
                        ArrayList<CollectExam> datas = data.getData();

                        if (pull) {
                            if (datas.size() > 0) {
                                mRootView.emptyData(false);
                                collectAdapter.setNewData(datas);
                                if (datas.size() < count) {
                                    if (collectAdapter.getFooterViewsCount() == 0 && page != 1) {
//                                        collectAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                        mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                    }
                                } else {
                                    collectAdapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                mRootView.emptyData(true);
//                                collectAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            collectAdapter.addData(datas);
                            if (datas.size() < count) {
//                                if (collectAdapter.getFooterViewsCount() == 0)
//                                    collectAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
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
                            collectAdapter.loadMoreFail();
                        else
                            mRootView.emptyData(true);
//                            collectAdapter.setEmptyView(AdapterViewUtils.getErrorViwe(mApplication));
                    }
                });
    }


    public void collectExam(String source_id, int action, int position) {
        mModel.collectExam(source_id, action)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
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
                        System.out.println("collectExam");
                        if (data.getCode() == 1)
                            mRootView.delete(4, position);
                        else
                            mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public void deleteExamRecord(String exams_users_id, int position) {
        mModel.deleteExamRecord(exams_users_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
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
                        System.out.println("deleteExamRecord");
                        if (data.getCode() == 1)
                            mRootView.delete(1, position);
                        else
                            mRootView.showMessage(data.getMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }


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
                            startExam(bean, 3);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public void getExamInfoAndStartExam(Exam examBean, int exams_type) {
        mRootView.showLoading();
        mModel.getExamInfo(examBean.getExams_paper_id(), exams_type, examBean.getExams_users_id().equals("0") ? "" : examBean.getExams_users_id() + "")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                 /*   if (mRootView != null)
                        mRootView.hideLoading();//上拉加载更多*/
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//上拉加载更多
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Examination>(mErrorHandler) {
                    @Override
                    public void onNext(Examination data) {
                        mRootView.hideLoading();//上拉加载更多
                        if (data != null && data.getData() != null)
                            startExam(data.getData(), exams_type);
                        else
                            mRootView.showMessage("没有数据");
                    }
                });
    }

    private void startExam(MExamBean examBean, int exams_type) {
        if (examBean == null) {
            return;
        }
        examBean.setUserAnswerTemp();
        mRootView.launchActivity(new Intent(mApplication, ExaminationActivity.class)
                .putExtra(MessageConfig.START_EXAMINATION, examBean)
                .putExtra(MessageConfig.START_EXAMINATION_IS_TEST, true)
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
