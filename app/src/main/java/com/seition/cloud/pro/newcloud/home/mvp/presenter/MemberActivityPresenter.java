package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.bean.member.VipUser;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberTypeRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberUserRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MemberActivityPresenter extends BasePresenter<MemberContract.Model, MemberContract.MemberView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MemberTypeRecyclerAdapter memberAdapter;
    @Inject
    MemberUserRecyclerAdapter memberUserAdapter;


    @Inject
    public MemberActivityPresenter(MemberContract.Model model, MemberContract.MemberView rootView) {
        super(model, rootView);
    }

    public void getVipGrades( boolean iscache){
        mModel.getVipGrades(iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Member>(mErrorHandler) {
                    @Override
                    public void onNext(Member data) {
                        ArrayList<Member> members = data.getData();
                        if(members != null) {
                            memberAdapter.setNewData(members);
                            mRootView.setFragment(members);
                        }
                    }
                });
    }


    public void getNewMembers( int limit,boolean iscache){
        mModel.getNewMembers(limit,iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<VipUser>(mErrorHandler) {
                    @Override
                    public void onNext(VipUser data) {
                        ArrayList<VipUser> vipUsers =  data.getData();
                        memberUserAdapter.setNewData(vipUsers);
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
