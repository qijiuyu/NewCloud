package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseCacheBean;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.download.DBUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.DownloadCourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.activity.CourseDownloadActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter.DownloadAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter.DownloadCourseAdapter;
import com.seition.cloud.pro.newcloud.widget.DownProgressView;

import java.io.File;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class DownloadCoursePresenter extends BasePresenter<DownloadCourseContract.Model, DownloadCourseContract.View> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    RecyclerView recycle_view;

    @Inject
    public DownloadCoursePresenter(DownloadCourseContract.Model model, DownloadCourseContract.View rootView) {
        super(model, rootView);
    }

    public void setRecycle_view(RecyclerView recycle_view) {
        this.recycle_view = recycle_view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 获取我的下载中文库列表
     */
    public void getCacheBooksList() {
        mRootView.setData(DBUtils.init(mApplication).queryDownloadBookList());
    }

    /**
     * 获取我的下载中课程列表
     */
    public void getCacheVideoList() {
        mRootView.setCourseData(DBUtils.init(mApplication).queryCourseList());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.download) {
            DownloadBean item = (DownloadBean) adapter.getItem(position);
            if (recycle_view != null) {
                DownProgressView progressView = (DownProgressView) adapter.getViewByPosition(recycle_view, position, R.id.download_progress);
                progressView.start();
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof DownloadCourseAdapter)
            //进入课程下载列表
            mRootView.launchActivity(
                    new Intent(
                            mApplication, CourseDownloadActivity.class)
                            .putExtra("course", (CourseCacheBean) adapter.getItem(position))
                            .putExtra("from", MessageConfig.FROM_DOWNLOAD)
            );
        else {
            //打开文库
            DownloadBean bean = ((DownloadAdapter) adapter).getItem(position);
            File path = new File(bean.getFileSavePath());
            if (path.exists())
                mRootView.launchActivity(getFileIntent(path, bean.getExtension()));
        }
    }

    public Intent getFileIntent(File path, String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);//Intent.ACTION_VIEW = "android.intent.action.VIEW"
        intent.addCategory(Intent.CATEGORY_DEFAULT);//Intent.CATEGORY_DEFAULT = "android.intent.category.DEFAULT"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(path);
        intent.setDataAndType(uri, "application/" + type);
        return intent;
    }
}