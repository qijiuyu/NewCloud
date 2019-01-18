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
import com.seition.cloud.pro.newcloud.home.mvp.contract.LiveContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LiveGridRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class LivePresenter extends BasePresenter<LiveContract.Model, LiveContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    LiveGridRecyclerAdapter adapter;

    List<CourseOnline> datas;

    @Inject
    CourseLiveRecyclerAdapter courseLiveRecyclerAdapter;

    List<CourseOnline> liveDatas;

    List<CourseOnline> myCoursesDatas;


    @Inject
    public LivePresenter(LiveContract.Model model, LiveContract.View rootView) {
        super(model, rootView);
    }

    boolean isFirst = true;

    public void getLiveList(String cate_id, String keyword, String begin_time, String end_time, String order, String teacher_id, int page, int count, String status, boolean cache, boolean pull) throws Exception {

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

        mModel.getLives(cate_id, keyword, begin_time, end_time, order, teacher_id, page, count, status, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        datas = data.getData();
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
                            } else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
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

    public void getMyLiveList(int page, int count, boolean cache, boolean pull) {
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
        mModel.getMyLives(page, count)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        liveDatas = data.getData();

                        if (pull) {
                            courseLiveRecyclerAdapter.setNewData(liveDatas);
                            if (liveDatas.size() > 0) {
                                if (liveDatas.size() < count) {
                                    if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                        courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    courseLiveRecyclerAdapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                courseLiveRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));

//                            courseLiveRecyclerAdapter.setNewData(liveDatas);
                        } else {
                            courseLiveRecyclerAdapter.addData(liveDatas);
                            courseLiveRecyclerAdapter.loadMoreComplete();
                            if (liveDatas.size() < count) {
                                if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                    courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                            } else {
                                courseLiveRecyclerAdapter.removeAllFooterView();
                                mRootView.showSpingViewFooterEnable(true);//springView可上拉
                            }
                        }
                    }
                });
    }

    public void getMyTeachLiveList(int page, int count, boolean pull) throws Exception {
        if (pull) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if (isFirst) {
                isFirst = false;
            }
        } else {
            page++;
        }
        mModel.getMyTeachLiveList(page, count)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        liveDatas = data.getData();

                        if (pull) {
                            courseLiveRecyclerAdapter.setNewData(liveDatas);
                            if (liveDatas.size() > 0) {
                                if (liveDatas.size() < count) {
                                    if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                        courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    courseLiveRecyclerAdapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                courseLiveRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));

//                            courseLiveRecyclerAdapter.setNewData(liveDatas);
                        } else {
                            courseLiveRecyclerAdapter.addData(liveDatas);
                            courseLiveRecyclerAdapter.loadMoreComplete();
                            if (liveDatas.size() < count) {
                                if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                    courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                            } else {
                                courseLiveRecyclerAdapter.removeAllFooterView();
                                mRootView.showSpingViewFooterEnable(true);//springView可上拉
                            }
                        }
                    }
                });
    }

    public void getMyTeachCourseList(int page, int count, boolean pull) throws Exception {
        if (pull) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if (isFirst) {
                isFirst = false;
            }
        } else {
            page++;
        }
        mModel.getMyTeachCourseList(page, count)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        liveDatas = data.getData();

                        if (pull) {
                            courseLiveRecyclerAdapter.setNewData(liveDatas);
                            if (liveDatas.size() > 0) {
                                if (liveDatas.size() < count) {
                                    if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                        courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    courseLiveRecyclerAdapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                courseLiveRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));

//                            courseLiveRecyclerAdapter.setNewData(liveDatas);
                        } else {
                            courseLiveRecyclerAdapter.addData(liveDatas);
                            courseLiveRecyclerAdapter.loadMoreComplete();
                            if (liveDatas.size() < count) {
                                if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                    courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                            } else {
                                courseLiveRecyclerAdapter.removeAllFooterView();
                                mRootView.showSpingViewFooterEnable(true);//springView可上拉
                            }
                        }
                    }
                });
    }

    public void getMyCourses(int page, int count, boolean cache, boolean pull) throws Exception {
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
        mModel.getMyCourses(page, count, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {
                        myCoursesDatas = data.getData();

//                        LiveListData liveListData = datas.get(0);

                        if (pull) {
                            courseLiveRecyclerAdapter.setNewData(myCoursesDatas);
                            if (myCoursesDatas.size() > 0) {
                                if (myCoursesDatas.size() < count) {
                                    if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                        courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                } else {
                                    courseLiveRecyclerAdapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            } else
                                courseLiveRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            courseLiveRecyclerAdapter.addData(myCoursesDatas);
                            courseLiveRecyclerAdapter.loadMoreComplete();
                            if (myCoursesDatas.size() < count) {
                                if (courseLiveRecyclerAdapter.getFooterViewsCount() == 0)
                                    courseLiveRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                            } else {
                                courseLiveRecyclerAdapter.removeAllFooterView();
                                mRootView.showSpingViewFooterEnable(true);//springView可上拉
                            }
                        }
//                        if (pull) {
//                            courseLiveRecyclerAdapter.setNewData(myCoursesDatas);
//                            if (myCoursesDatas.size() == 0)
//                                courseLiveRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
//                        } else {
//                            courseLiveRecyclerAdapter.addData(myCoursesDatas);
//                            courseLiveRecyclerAdapter.loadMoreComplete();
//                        }
                    }
                });
    }

    public void getCollectCourse(int type, boolean cache, boolean pull) throws Exception {

        boolean isEvictCache = cache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存

        if (pull) {//默认在第一次下拉刷新时使用缓存
            if (isFirst) {
                isFirst = false;
                isEvictCache = false;
            } else
                isEvictCache = true;
        } else {
            isEvictCache = true;
        }
        mModel.getCollectCourse(type, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnlines>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnlines data) {

//                        courseLiveRecyclerAdapter.setNewData(data.getData());/...,,


                     /*   if (type == 2) {
                            courseLiveRecyclerAdapter.setNewData(data.getData());
                        }
                        else if(type == 1){
                            collectCoursesRecyclerAdapter.setNewData(data.getData());
                        }*/

                        liveDatas = data.getData();

                        if (pull) {
                            courseLiveRecyclerAdapter.setNewData(liveDatas);
                            if (liveDatas.size() == 0)
                                courseLiveRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        } else {
                            courseLiveRecyclerAdapter.addData(liveDatas);
                            courseLiveRecyclerAdapter.loadMoreComplete();
                        }
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
