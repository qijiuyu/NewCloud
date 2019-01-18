package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseCacheBean;
import com.seition.cloud.pro.newcloud.app.bean.download.InitDownloadBean;
import com.seition.cloud.pro.newcloud.app.utils.download.DBUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.widget.DownProgressView;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CourseDownloadPresenter extends BasePresenter<CourseContract.Model
        , CourseContract.CourseDownloadView> implements BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemChildClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    RecyclerView recycle_view;
    private boolean isCourseSeitionListCache = true;
    ArrayList<CourseSeition> seitions;
    CourseCacheBean mCourse;

    @Inject
    public CourseDownloadPresenter(CourseContract.Model model, CourseContract.CourseDownloadView rootView) {
        super(model, rootView);
    }

    public void setRecycle_view(RecyclerView recycle_view) {
        this.recycle_view = recycle_view;
    }

    public void getCourseSeitionList(CourseCacheBean course) throws Exception{
        mCourse = course;
        if (mCourse != null)
            mModel.getCourseSeitionList(mCourse.getCourseId(), isCourseSeitionListCache)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        //错误
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                    .subscribe(new ErrorHandleSubscriber<DataBean<ArrayList<CourseSeition>>>(mErrorHandler) {
                        @Override
                        public void onNext(DataBean<ArrayList<CourseSeition>> data) {
                            //拿到数据
                            mRootView.showSeition(InitDownloadBean.seitionDataToExpandableData(seitions = data.getData(), mApplication, true));
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.download) {
            //已兑换，执行下载操作
            if (recycle_view != null) {
                DownProgressView progressView = (DownProgressView) adapter.getViewByPosition(recycle_view, position, R.id.download_progress);
                progressView.start();
                DBUtils.init(mApplication).saveCourse(mCourse);
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //播放视频
    }
}
