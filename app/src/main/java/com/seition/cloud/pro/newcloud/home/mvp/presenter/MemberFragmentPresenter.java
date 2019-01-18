package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberTypeRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberUserRecyclerAdapter;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class MemberFragmentPresenter extends BasePresenter<MemberContract.Model, MemberContract.View> {
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
    public MemberFragmentPresenter(MemberContract.Model model, MemberContract.View rootView) {
        super(model, rootView);
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
