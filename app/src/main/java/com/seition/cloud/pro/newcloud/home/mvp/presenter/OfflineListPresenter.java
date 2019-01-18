package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchool;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchoolResponse;
import com.seition.cloud.pro.newcloud.app.popupwindow.LoadMoreListPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OfflineContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OfflineListRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class OfflineListPresenter extends BasePresenter<OfflineContract.Model, OfflineContract.OfflineListView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    OfflineListRecyclerAdapter adapter;

    ArrayList<CourseOffline> offlineCourses;

    @Inject
    public OfflineListPresenter(OfflineContract.Model model, OfflineContract.OfflineListView rootView) {
        super(model, rootView);
    }

    private boolean isFirst = true;

    private int page = 1, count = 10;

    public void getOfflineCourses(String cateId, String orderBy, String school_id, String time, boolean pull, boolean cache) throws Exception {

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

        mModel.getOfflineCourses(page, count, cateId, orderBy, school_id, time, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOfflines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOfflines data) {
                        ArrayList<CourseOffline> datas = data.getData();

                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                if (datas.size() < count) {
                                    adapter.loadMoreEnd(true);
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.loadMoreComplete();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);

                            if (datas.size() < count) {
                                adapter.loadMoreEnd(false);
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                adapter.loadMoreComplete();
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }

                    }
                });
    }


    public void getMyCollectOfflineCourses(boolean pull, boolean cache) throws Exception {

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

        mModel.getMyCollectOfflineCourses(page, count)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOfflines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOfflines data) {
                        ArrayList<CourseOffline> datas = data.getData();

                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                if (datas.size() < count) {
                                    adapter.loadMoreEnd(true);
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.loadMoreComplete();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);

                            if (datas.size() < count) {
                                adapter.loadMoreEnd(false);
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                adapter.loadMoreComplete();
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }
                    }
                });
    }
    public void getMyTeachOfflineCourses(boolean pull){

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

        mModel.getMyTeachOfflineCourses(page, count)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOfflines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOfflines data) {
                        ArrayList<CourseOffline> datas = data.getData();

                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                if (datas.size() < count) {
                                    adapter.loadMoreEnd(true);
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.loadMoreComplete();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);

                            if (datas.size() < count) {
                                adapter.loadMoreEnd(false);
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                adapter.loadMoreComplete();
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }
                    }
                });
    }

    public void getMyOfflineCourses(boolean pull, boolean cache) throws Exception {

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

        mModel.getMyOfflineCourses(page, count, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOfflines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOfflines data) {
//                        System.out.println("vPage = "+vPage+"  - - -- - size() == " +data.getData().size()+data.getData());
                        ArrayList<CourseOffline> datas = data.getData();

                        if (pull) {
                            adapter.setNewData(datas);
                            if (datas.size() > 0) {
                                if (datas.size() < count) {
                                    adapter.loadMoreEnd(true);
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    adapter.loadMoreComplete();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            adapter.addData(datas);

                            if (datas.size() < count) {
                                adapter.loadMoreEnd(false);
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                adapter.loadMoreComplete();
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }
                    }
                });
    }

    int offlinePage = 1;

    public void getOfflineSchools(boolean pull, boolean cache) throws Exception {

        boolean isEvictCache = pull;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull) {//默认在第一次下拉刷新时使用缓存
            offlinePage = 1;
            if (isFirst) {
                isFirst = false;
                isEvictCache = false;
            } else
                isEvictCache = true;
        } else {
            isEvictCache = true;
            offlinePage++;
        }
        mModel.getOfflineSchools(offlinePage, LoadMoreListPopWindow.Count, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();//上拉加载更多
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<OfflineSchoolResponse>(mErrorHandler) {
                    @Override
                    public void onNext(OfflineSchoolResponse data) {
                        ArrayList<OfflineSchool> datas = data.getData().getSchool();
                        mRootView.setDialogData(datas, pull);
                    }
                });
    }


    public void getOfflineCategory(boolean cache) {

        mModel.getOfflineCategory(cache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonCategory>(mErrorHandler) {
                    @Override
                    public void onNext(CommonCategory data) {
                        ArrayList<CommonCategory> commonCategories = data.getData();
                        mRootView.showCategoryWindows(commonCategories);
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
