package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.jess.arms.widget.statue.MultiStateView;
import com.seition.cloud.pro.newcloud.app.bean.bind.Banks;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindBank;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BankRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BindBankManageRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BindBankRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class BindBankListPresenter extends BasePresenter<BindManageContract.Model, BindManageContract.BindBankListView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    BindBankRecyclerAdapter adapter;
    @Inject
    BankRecyclerAdapter bankRecyclerAdapter;
    @Inject
    BindBankManageRecyclerAdapter manageRecyclerAdapter;

    @Inject
    public BindBankListPresenter(BindManageContract.Model model, BindManageContract.BindBankListView rootView) {
        super(model, rootView);
    }

    public void getBindBanks(int type, boolean iscache){
        mModel.getBindBanks(10, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null)
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BindBank>(mErrorHandler) {
                    @Override
                    public void onNext(BindBank data) {
                        System.out.println("getBindBanks ");
                        ArrayList<BindBank> studyRecords = data.getData();

                        if(type == 1) {
                            adapter.setNewData(studyRecords);
                            if (adapter.getItemCount() > 0)
                                mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                            else
                                mRootView.showStateViewState(MultiStateView.STATE_EMPTY);
                        }else {
                            manageRecyclerAdapter.setNewData(studyRecords);
                            if (manageRecyclerAdapter.getItemCount() > 0)
                                mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                            else
                                mRootView.showStateViewState(MultiStateView.STATE_EMPTY);
                        }
                    }
                });
    }

    public void getBanks( boolean iscache){
        mModel.getBanks(-1, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Banks>(mErrorHandler) {
                    @Override
                    public void onNext(Banks data) {
                        ArrayList<String> bankNames = data.getData();
                        bankRecyclerAdapter.setNewData(bankNames);
                        if(bankRecyclerAdapter.getItemCount()>0)
                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                        else
                            mRootView.showStateViewState(MultiStateView.STATE_EMPTY);
                    }
                });
    }

    private void refresh(){
        getBindBanks(2,false);
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
