package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.comment.Comments;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CommentContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.CommentFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CommentPresenter extends BasePresenter<CommentContract.Model, CommentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    CommentFragment.Comment type;
    String courseId;
    private boolean isFirst = true;


    int page = 1;
    public static int count = 10;

    @Inject
    public CommentPresenter(CommentContract.Model model, CommentContract.View rootView) {
        super(model, rootView);
    }

    public void getCommentData(boolean pull, CommentFragment.Comment type, String courseId) {
        this.type = type;
        this.courseId = courseId;
        switch (type) {
            case Video:
            case Live:
                getCommentData(1, pull, true);
                break;
            case Teacher:
                getTeacherCommentData(pull, false);
                break;
            case Offline:
                getCommentData(3, pull, true);
                break;
        }

    }

    public void getTeacherCommentData(boolean pull, boolean isCache) {
        mRootView.showLoading();
        boolean isEvictCache = pull;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

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
        mModel.getTeacherComment(page, count, courseId, isCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Comments>(mErrorHandler) {
                    @Override
                    public void onNext(Comments data) {
                        //拿到数据
                        mRootView.showComment(pull, data.getData());
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
                            switch (type) {
                                case Video:
                                    showCommentButton(config.getCourse_switch() == 0, headerView);
                                    break;
                                case Live:
                                    showCommentButton(config.getLive_switch() == 0, headerView);
                                    break;
                                case Teacher:
//                                    showCommentButton(config.getCourse_switch() == 0,headerView);
                                    break;
                                case Offline:
                                    showCommentButton(config.getCourse_line_switch() == 0, headerView);
                                    break;
                            }
                    }
                });
    }

    private void showCommentButton(boolean isVisible, View headerView) {
        if (headerView != null)
            headerView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void getCommentData(int type, boolean pull, boolean isCache) {
        mRootView.showLoading();
        boolean isEvictCache = pull;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

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
        mModel.getComment(page, count, courseId, type, isCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Comments>(mErrorHandler) {
                    @Override
                    public void onNext(Comments data) {
                        //拿到数据;
                        mRootView.showComment(pull, data.getData());
                    }
                });
    }

    public void commentCourse(String kzid, int kztype, String content, int score) {
        mRootView.showLoading();
        mModel.commentCourse(kzid, kztype, content, score)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        mRootView.showMessage(data.getMsg());
                        mRootView.hideDialog();
                        if (data.getCode() == 1)
                            getCommentData(kztype, true, true);
                        //拿到数据
//                        mRootView.showComment(data.getData());
                    }
                });
    }

    public void commentTeacher(String teacherId, int kztype, String content, int score) {
        mRootView.showLoading();
        mModel.commentTeacher(teacherId, kztype, content, score)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        mRootView.showMessage(data.getMsg());
                        mRootView.hideDialog();
                        if (data.getCode() == 1)
                            getTeacherCommentData(true, true);
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
