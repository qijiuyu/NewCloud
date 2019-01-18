package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupMember;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupApplyMemberHorizontalListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupMemberHorizontalListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class GroupDetailsPresenter extends BasePresenter<GroupContract.GroupTopicModel, GroupContract.GroupMemberView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    GroupMemberHorizontalListAdapter adapter;
    ArrayList<GroupMember> groupMembers;
//    @Inject
//    GroupMemberHorizontalListAdapter applyAdapter;
    @Inject
    GroupApplyMemberHorizontalListAdapter applyAdapter;
    ArrayList<GroupMember> applyGroupMembers;

    @Inject
    public GroupDetailsPresenter(GroupContract.GroupTopicModel model, GroupContract.GroupMemberView rootView) {
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



    public  void getGroupMemberList(   String  group_id,  int page,  int count,  String type, boolean cache){
        mModel.getGroupMemberList(   group_id,   page,   count,   type,cache)
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
                .subscribe(new ErrorHandleSubscriber<DataBean<ArrayList<GroupMember>>>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean<ArrayList<GroupMember>> data) {
                        System.out.println("GroupMemberList0 type ="+type );
                        groupMembers = data.getData();
                        adapter.setData(groupMembers);
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }

    public  void getGroupApplyMemberList(   String  group_id,  int page,  int count,  String type, boolean cache){
        mModel.getApplyGroupMemberList(   group_id,   page,   count,   type,cache)
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
                .subscribe(new ErrorHandleSubscriber<DataBean<ArrayList<GroupMember>>>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean<ArrayList<GroupMember>> data) {
                        System.out.println("GroupMemberList1 type ="+type);
                        applyGroupMembers = data.getData();
                        applyAdapter.setData(applyGroupMembers);
                        mRootView.hideLoading();//上拉加载更多
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
