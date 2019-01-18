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
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupTheme;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupTopicRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class GroupTopicPresenter extends BasePresenter<GroupContract.GroupTopicModel, GroupContract.GroupTopicView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    GroupTopicRecyclerAdapter adapter;
    ArrayList<GroupTheme> groupThemes;

    @Inject
    public GroupTopicPresenter(GroupContract.GroupTopicModel model, GroupContract.GroupTopicView rootView) {
        super(model, rootView);
    }


/*
    */
/**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link SupportActivity} 和 {@link Fragment} 的部分生命周期绑定
     *//*

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {

//        requestUsers(true);//打开 App 时自动加载列表
    }
*/


public  void getGroupDetails(   String  group_id, boolean cache){
    mModel.getGroupDetals(   group_id,cache)
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
            .doOnSubscribe(disposable -> {
//                    if (pullToRefresh)
//                        mRootView.showLoading();//显示下拉刷新的进度条
//                    else
//                        mRootView.loadMore();//显示上拉加载更多的进度条
            })
            .subscribeOn(AndroidSchedulers.mainThread())//Schedulers.newThread()
            .observeOn(AndroidSchedulers.mainThread())//AndroidSchedulers.mainThread()
            .doFinally(() -> {
//                    if (pullToRefresh)
//                        mRootView.showLoading();//显示下拉刷新的进度条
//                    else
//                        mRootView.loadMore();//显示上拉加载更多的进度条
            })
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
            .subscribe(new ErrorHandleSubscriber<DataBean<Group>>(mErrorHandler) {
                @Override
                public void onNext(DataBean<Group> data) {
                    System.out.println("GroupInfo ");
                    Group group = data.getData();
                    mRootView.showDetails(group);
                }
            });
}


    /*
    * */
    public  void getTopicList(   String  group_id,  int page,  int count,  String dist, String keysord,final boolean pullToRefresh, boolean cache){
        mModel.getTopicList(   group_id,   page,   count,   dist,  keysord,cache)
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
                .subscribe(new ErrorHandleSubscriber<DataBean<ArrayList<GroupTheme>>>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean<ArrayList<GroupTheme>> data) {
                        System.out.println("TopicList");
                        groupThemes = data.getData();
                        if (pullToRefresh)
                        adapter.setNewData(groupThemes);
                        else {
                            adapter.addData(groupThemes);
                            adapter.loadMoreComplete();
                        }
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }


    public  void deleteGroup(String group_id ){
        mModel.deleteGroup(group_id)
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("GroupOperation");
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }

    public  void quiteGroup(String group_id){
        mModel.quitGroup(group_id)
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("GroupCoverUpload"+data.getData());
                        String logoDataStr = data.getData().toString();
                        JSONArray array = null;
                        int logoData = 0;

                        try {
                             array = new JSONArray(logoDataStr);
                            double d = Double.parseDouble(array.get(0).toString());
                            logoData = (new Double(d).intValue());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        ArrayList<Double> logoDatas = data.getData();
//                        int logoData = (new Double(logoDatas.get(0))).intValue();
//                        mRootView.setGroupLogoData(logoData);
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }
    public  void joinGroup(String group_id){
        mModel.joinGroup(group_id)
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("GroupCoverUpload"+data.getData());
                        String logoDataStr = data.getData().toString();
                        JSONArray array = null;
                        int logoData = 0;

                        try {
                            array = new JSONArray(logoDataStr);
                            double d = Double.parseDouble(array.get(0).toString());
                            logoData = (new Double(d).intValue());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        ArrayList<Double> logoDatas = data.getData();
//                        int logoData = (new Double(logoDatas.get(0))).intValue();
//                        mRootView.setGroupLogoData(logoData);
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }
    public  void themeOperate(String tid,int action,String type){
        mModel.themeOperate(tid, action, type)
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("GroupCoverUpload"+data.getData());
                        String logoDataStr = data.getData().toString();
                        JSONArray array = null;
                        int logoData = 0;

                        try {
                            array = new JSONArray(logoDataStr);
                            double d = Double.parseDouble(array.get(0).toString());
                            logoData = (new Double(d).intValue());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        ArrayList<Double> logoDatas = data.getData();
//                        int logoData = (new Double(logoDatas.get(0))).intValue();
//                        mRootView.setGroupLogoData(logoData);
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }
    public  void themeDelete(String tid,String group_id){
        mModel.themeDelete(tid,group_id)
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("GroupCoverUpload"+data.getData());
                        String logoDataStr = data.getData().toString();
                        JSONArray array = null;
                        int logoData = 0;

                        try {
                            array = new JSONArray(logoDataStr);
                            double d = Double.parseDouble(array.get(0).toString());
                            logoData = (new Double(d).intValue());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        ArrayList<Double> logoDatas = data.getData();
//                        int logoData = (new Double(logoDatas.get(0))).intValue();
//                        mRootView.setGroupLogoData(logoData);
                        mRootView.hideLoading();//上拉加载更多
                    }
                });
    }
    public  void themeReply(String tid,String group_id){
        mModel.themeReply(tid,group_id)
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
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("GroupCoverUpload"+data.getData());
                        String logoDataStr = data.getData().toString();
                        JSONArray array = null;
                        int logoData = 0;

                        try {
                            array = new JSONArray(logoDataStr);
                            double d = Double.parseDouble(array.get(0).toString());
                            logoData = (new Double(d).intValue());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        ArrayList<Double> logoDatas = data.getData();
//                        int logoData = (new Double(logoDatas.get(0))).intValue();
//                        mRootView.setGroupLogoData(logoData);
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
