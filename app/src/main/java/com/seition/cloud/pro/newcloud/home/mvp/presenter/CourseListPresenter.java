package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class CourseListPresenter extends BasePresenter<CourseContract.Model, CourseContract.CourseListView>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    CourseLiveRecyclerAdapter adapter;

    @Inject
    public CourseListPresenter(CourseContract.Model model, CourseContract.CourseListView rootView) {
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
}
