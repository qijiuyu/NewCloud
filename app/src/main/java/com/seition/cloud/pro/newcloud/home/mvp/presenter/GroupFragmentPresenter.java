package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupData;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class GroupFragmentPresenter extends BasePresenter<GroupContract.fModel, GroupContract.fView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

//    List<Group> groups;

    @Inject
    GroupRecyclerAdapter adapter;

    @Inject
    public GroupFragmentPresenter(GroupContract.fModel model, GroupContract.fView rootView) {
        super(model, rootView);
    }



    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link SupportActivity} 和 {@link Fragment} 的部分生命周期绑定
     *//*

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {

//        requestUsers(true);//打开 App 时自动加载列表
    }
    */

    /*@GET(DOMAIN_NAME_DAFENGCHE + "&mod=Group&act=getList")
    Observable<DataBean<ArrayList<GroupDataOld>>> getGroupList(@Query("page") int page, @Query("count") int count, @Query("cate_id") String cate_id);
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<DataBean<ArrayList<GroupDataOld>>> getGroupList(Observable<DataBean<ArrayList<GroupDataOld>>> cates
            , int page, int count, String id, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    public  void getGroupList(int vPage,int page,int count,String id,final boolean pullToRefresh,boolean updates){
        mModel.getGroupList(vPage,page,count,id, updates)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean<ArrayList<GroupDataOld>>>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean<ArrayList<GroupDataOld>> data) {
                        ArrayList<GroupDataOld> groupDatas = data.getData();
                        GroupDataOld groupData = groupDatas.get(0);
                        groups = groupData.getGroup_list();

                        mRootView.setGroupCategory(data.getData().get(0).getTitle());
                        if (pullToRefresh) {
                            adapter.setNewData(groups);
                        }
                        else {
                            adapter.addData(groups);
                            adapter.loadMoreComplete();
                        }
                        System.out.println("::::::::"+groups.size());
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }
    */

    private int page = 1;
    private int count = 6;
    boolean isFirst = true;
    /**
     *
     * @param vPage
     * @param id
     * @param pull
     * @param cache
     */
    public  void getGroupList(int vPage,String id,final boolean pull,boolean cache){
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

        mModel.getGroupListNew(vPage,page,count,id, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
//                    if (pullToRefresh)
//                        mRootView.showLoading();//显示下拉刷新的进度条
//                    else
//                        mRootView.loadMore();//显示上拉加载更多的进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    if (pullToRefresh)
//                        mRootView.showLoading();//显示下拉刷新的进度条
//                    else
//                        mRootView.loadMore();//显示上拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GroupData>(mErrorHandler) {
                    @Override
                    public void onNext(GroupData data) {
                        ArrayList<GroupData> groupData = data.getData(); //null;//
                        List<Group> groups = groupData.get(0).getGroup_list();

                        mRootView.setGroupCategory(groupData.get(0).getTitle());
                        if(groups.size()<count)
                            mRootView.setSpringViewLoader(false);
                        if (pull) {
                            page = 1;
                            adapter.setNewData(groups);
                        }
                        else {

                            adapter.addData(groups);

                            mRootView.setSpringViewLoader(true);
                            page++;

//                            adapter.loadMoreComplete();
                        }
                        System.out.println("::::::::"+groups.size());
                        mRootView.hideLoading();
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
