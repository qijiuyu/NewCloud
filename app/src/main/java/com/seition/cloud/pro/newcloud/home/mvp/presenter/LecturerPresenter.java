package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LecturerContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LectureListRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class LecturerPresenter extends BasePresenter<LecturerContract.Model, LecturerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    LectureListRecyclerAdapter adapter;

    @Inject
    CourseLiveRecyclerAdapter courseAdapter;

    Teacher teacher;
    private int teacher_id = 0;

    @Inject
    public LecturerPresenter(LecturerContract.Model model, LecturerContract.View rootView) {
        super(model, rootView);
    }

    public void getLecturerDetails(int teacher_id, boolean isCache){
        this.teacher_id = teacher_id;
        mModel.getLectureDetails(teacher_id, isCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Lecturer>(mErrorHandler) {
                    @Override
                    public void onNext(Lecturer data) {
                        mRootView.showTeacher(teacher = data.getData());
                    }
                });
    }

    private int page = 1 ,count = 10;
    boolean isFirst = true;
    public void getTeacherCourse(int teacher_id,boolean pull,boolean cache){
        boolean isEvictCache = cache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull ) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if(isFirst){
                isFirst = false;
                isEvictCache = false;
            }
            else
                isEvictCache = true;
        }else {
            isEvictCache = true;
            page++;
        }
        mModel.getTeacherCourse(page, count,teacher_id,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        ArrayList<CourseOnline> datas = data.getData()  ;
                        if(pull) {
                            if(datas.size()>0) {
                                courseAdapter.setNewData(datas);
                                if (datas.size()<count) {
                                    if (courseAdapter.getFooterViewsCount()==0&&page ==1)
                                        courseAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                }
                                else {
                                    courseAdapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            }
                            else
                                courseAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                        else {
                            courseAdapter.addData(datas);
                            if (datas.size() <count) {
                                if (courseAdapter.getFooterViewsCount()==0)
                                    courseAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }


                    }
                });
    }


    public void doOrganizationFollow(String user_id) {
        mModel.doTeacherFollow(user_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FollowState>(mErrorHandler) {
                    @Override
                    public void onNext(FollowState data) {
                        mRootView.showMessage(data.getMsg());
                        getLecturerDetails(teacher_id,true);
                    }
                });
    }

    public void cancelOrganizationFollow(String user_id) {
        mModel.cancelTeacherFollow(user_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FollowState>(mErrorHandler) {
                    @Override
                    public void onNext(FollowState data) {
                        mRootView.showMessage(data.getMsg());
                        getLecturerDetails(teacher_id,true);
                    }
                });
    }

    public void attentionTeacher() {
        switch (teacher.getFollow_state().getFollowing()) {
            case 0:
                doOrganizationFollow(teacher.getUid());
                break;
            case 1:
                cancelOrganizationFollow(teacher.getUid());
                break;
        }
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
