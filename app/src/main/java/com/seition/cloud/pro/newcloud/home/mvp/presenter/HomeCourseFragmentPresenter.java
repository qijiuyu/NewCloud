package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.jess.arms.widget.statue.MultiStateView;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSearch;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class HomeCourseFragmentPresenter extends BasePresenter<HomeContract.HomeModel, HomeContract.CoursesFragmentView> {
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
    public HomeCourseFragmentPresenter(HomeContract.HomeModel model, HomeContract.CoursesFragmentView rootView) {
        super(model, rootView);
    }
    public void searchCourses(String keyword, String location, int type, String cate_id, String order,String vip_id,boolean pull, boolean cache) throws Exception {
        boolean isEvictCache = cache;//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存
        if (pull ) {//默认在第一次下拉刷新时使用缓存
            page = 1;
            if(isFirst){
                isFirst = false;
                isEvictCache = false;
            }else
                isEvictCache = true;
        }else {
            isEvictCache = true;
            page++;
        }

        mModel.searchCourses(keyword,location,type,page,count,cate_id,order,vip_id,isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null) {
                        mRootView.hideLoading();
                        mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseSearch>(mErrorHandler) {
                    @Override
                    public void onNext(CourseSearch data) {
                        ArrayList<CourseOnline> datas = data.getData().get(0).getList();

                        if(pull) {
                            adapter.setNewData(datas);
                            if(datas.size()>0) {
                                if (datas.size()<count) {
                                    if (adapter.getFooterViewsCount()==0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                }
                                else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            }
                            else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                        else {
                            adapter.addData(datas);
                            if (datas.size() <count) {
                                if (adapter.getFooterViewsCount()==0)
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

    private int page =1 ,count =10;
    boolean isFirst = true;

    public void getCommonCategory(boolean iscache) throws Exception {
        mModel.getCommonCategory(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CommonCategory>(mErrorHandler) {
                    @Override
                    public void onNext(CommonCategory data) {
                        ArrayList<CommonCategory> categories = data.getData();
                        mRootView.showCategoryWindows(categories);
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
