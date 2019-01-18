package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class OrganizationDetailsPresenter extends BasePresenter<OrganizationContract.Model, OrganizationContract.DetailsView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public OrganizationDetailsPresenter(OrganizationContract.Model model, OrganizationContract.DetailsView rootView) {
        super(model, rootView);
    }


    public void getOrganizationDetails(String school_id,boolean iscache){
        mModel.getOrganizationDetails(school_id, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Organization>(mErrorHandler) {
                    @Override
                    public void onNext(Organization data) {

                        Organization organization = data.getData();
                        mRootView.setFragmenList(organization);
                    }
                });
    }

    public void doOrganizationFollow(String user_id){
        mModel.doOrganizationFollow(user_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FollowState>(mErrorHandler) {
                    @Override
                    public void onNext(FollowState data) {

                        mRootView.showMessage(data.getMsg());
                        FollowState followState =data.getData();
                        if (data.getCode() == 1)
                            mRootView.setAttention(followState.getFollowing());

                    }
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage("关注失败");
                    }
                });
    }
    public void cancelOrganizationFollow(String user_id){
        mModel.cancelOrganizationFollow(user_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FollowState>(mErrorHandler) {
                    @Override
                    public void onNext(FollowState data) {
                        mRootView.showMessage(data.getMsg());
                        FollowState followState =data.getData();
                        if (data.getCode() == 1)
                            mRootView.setAttention(followState.getFollowing());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage("取消关注失败");
                    }
                });
    }


    public  void getShareUrl(String type, String  vid, String mhm_id){
        mModel.getShare(type,vid,mhm_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Share>(mErrorHandler) {
                    @Override
                    public void onNext(Share data) {
                        System.out.println("collectOffine"+data.getData());
                        Share share = data.getData();
                        if(data.getCode() ==1 )
                            mRootView.share(share);
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
