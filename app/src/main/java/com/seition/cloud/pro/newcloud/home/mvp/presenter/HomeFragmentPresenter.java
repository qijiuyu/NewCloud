package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.InitApp;
import com.seition.cloud.pro.newcloud.app.bean.MarketStatus;
import com.seition.cloud.pro.newcloud.app.bean.McryptKey;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveBean;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveCourse;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organizations;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CategoryGridAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LecturerGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LiveHorRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OfflineGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrganizationGridRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class HomeFragmentPresenter extends BasePresenter<HomeContract.HomeModel, HomeContract.HomeView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    ArrayList<AdvertBean> advertBeans;

    @Inject
    CategoryGridAdapter categoryGridAdapter;

    @Inject
    LiveHorRecyclerAdapter liveHorRecyclerAdapter;

    @Inject
    OfflineGridRecyclerAdapter offlineGridRecyclerAdapter;
    @Inject
    LecturerGridRecyclerAdapter lecturerGridRecyclerAdapter;
    @Inject
    OrganizationGridRecyclerAdapter organizationGridRecyclerAdapter;

//    @Named("new")
//    @Inject
//    CourseGridRecyclerAdapter newCourseAdapter;
//    @Named("hot")
//    @Inject
//    CourseGridRecyclerAdapter hotCourseAdapter;

    @Inject
    public HomeFragmentPresenter(HomeContract.HomeModel model, HomeContract.HomeView rootView) {
        super(model, rootView);
    }

    private int requestCount = 0;

    private void setRequestCount() {
        requestCount++;
        LogUtils.debugInfo("add requestCount  = " + requestCount);
    }

    private void loadDataSuccess() {
        requestCount--;
        LogUtils.debugInfo("reduce requestCount  = " + requestCount);
        mRootView.hideLoading();
    }

    public void getBanners(String place, boolean iscache) {
        setRequestCount();
        mModel.getHomeBanner(place, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<AdvertBean>(mErrorHandler) {
                    @Override
                    public void onNext(AdvertBean data) {
                        advertBeans = data.getData();
                        mRootView.setBanners(advertBeans);
                    }
                });
    }

    private int count = 20;

    public void getHomeCategory(boolean iscache) throws Exception {
        setRequestCount();
        mModel.getHomeCategory(count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean<ArrayList<CommonCategory>>>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean<ArrayList<CommonCategory>> data) {
                        ArrayList<CommonCategory> categories = data.getData();
                        categoryGridAdapter.setDatas(categories);

                    }
                });
    }

    public void getHomeLive(boolean iscache) {
        setRequestCount();
        mModel.getHomeLive(count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<HomeLiveBean>(mErrorHandler) {
                    @Override
                    public void onNext(HomeLiveBean data) {
                        data.getData().setTime();
                        ArrayList<HomeLiveCourse> liveCourses = data.getData().getLive_list();
                        liveHorRecyclerAdapter.setNewData(liveCourses);
                    }
                });
    }

    public void getHomePerCourses(boolean iscache) {
        setRequestCount();
        mModel.getHomePerfectCourses(count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        ArrayList<CourseOnline> courses = data.getData();
                        mRootView.setHotCourse(courses);
                    }
                });
    }

    public void getHomeNewCourses(boolean iscache) {
        setRequestCount();
        mModel.getHomeNewCourses(count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {

                        ArrayList<CourseOnline> courses = data.getData();
                        mRootView.setNewCourse(courses);
                    }
                });
    }

    public void getHomeOfflineCourses(int page, int count, String cateId, String orderBy, String school_id, String time, boolean iscache) {
        setRequestCount();
        mModel.getHomeOffline(page, count, cateId, orderBy, null, time, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOfflines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOfflines data) {
                        ArrayList<CourseOffline> offlineCourses = data.getData();
                        offlineGridRecyclerAdapter.setNewData(offlineCourses);
                    }
                });
    }

    public void getHomeLectures(boolean iscache) {
        setRequestCount();
        mModel.getHomeLectures(count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Lecturers>(mErrorHandler) {
                    @Override
                    public void onNext(Lecturers data) {
                        ArrayList<Teacher> lecturers = data.getData();
                        lecturerGridRecyclerAdapter.setNewData(lecturers);
                    }
                });
    }

    public void getHomeOrganization(boolean iscache) throws Exception {
        setRequestCount();
        mModel.getHomeOrganization(count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Organizations>(mErrorHandler) {
                    @Override
                    public void onNext(Organizations data) {
                        ArrayList<Organization> organizations = data.getData();
                        organizationGridRecyclerAdapter.setNewData(organizations);
                    }
                });
    }

    public void getMcryptKey() {
        setRequestCount();
        mModel.getMcryptKey()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<InitApp>(mErrorHandler) {
                    @Override
                    public void onNext(InitApp data) {
                        McryptKey key = data.getData().getMcryptKey();
                        if (data.getCode() == 1) {
                            PreferenceUtil.getInstance(mApplication).saveString(MyConfig.Config_McryptKey, key.getMcrypt_key());
                            getMarketStatus();
                            getBanners("app_home", true);
//            mPresenter.getHomeCategory(false);
                            getHomeLive(false);
                            getHomePerCourses(false);
                            getHomeNewCourses(false);
                            String time = TimeUtils.dataToStamp(TimeUtils.getCurrentTime(TimeUtils.Format_TIME1), TimeUtils.Format_TIME1) + ",";
                            getHomeOfflineCourses(1, 4, "", "new", "", time, true);
                            getHomeLectures(false);
                        }
                    }
                });
    }

    public void getMarketStatus() {
        setRequestCount();
        mModel.getMarketStatus()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    loadDataSuccess();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MarketStatus>(mErrorHandler) {
                    @Override
                    public void onNext(MarketStatus data) {
                        MarketStatus key = data.getData();
                        if (data.getCode() == 1)
                            PreferenceUtil.getInstance(mApplication).saveInt(MyConfig.Config_MarketStatus, key.getOrder_switch());
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
