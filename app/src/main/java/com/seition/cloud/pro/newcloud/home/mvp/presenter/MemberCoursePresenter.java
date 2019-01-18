package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseGridRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MemberCoursePresenter extends BasePresenter<MemberContract.Model, MemberContract.MemberCourseView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    CourseGridRecyclerAdapter adapter;

    @Inject
    public MemberCoursePresenter(MemberContract.Model model, MemberContract.MemberCourseView rootView) {
        super(model, rootView);
    }

    public void getVipCourse(int vip_id, boolean iscache) {
        mModel.getVipCourses(vip_id, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        ArrayList<CourseOnline> datas = data.getData();

                        if (datas.size() > 0) {
                            adapter.setNewData(datas);
                            if (mRootView != null)
                                mRootView.addFooter();
                        }
                        else
                         adapter.setEmptyView(AdapterViewUtils.getEmptyViwesmall(mApplication));


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
