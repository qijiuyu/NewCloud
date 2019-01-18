package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.jess.arms.widget.statue.MultiStateView;
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
public class BankManageListPresenter extends BasePresenter<BindManageContract.Model, BindManageContract.ManageBankListView> {
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
    public BankManageListPresenter(BindManageContract.Model model, BindManageContract.ManageBankListView rootView) {
        super(model, rootView);
    }

    public void getBindBanks(int type, boolean iscache){
        mModel.getBindBanks(10, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BindBank>(mErrorHandler) {
                    @Override
                    public void onNext(BindBank data) {
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



    public void unbindBank( String  id,int position ){
        mModel.unbindBank(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        System.out.println("unbindBank ");
                        if(data.getCode() == 1){
                            mRootView.showMessage("银行卡解绑成功");
                            mRootView.deleteBank(position);
                        }else
                            mRootView.showMessage("银行卡解绑失败");
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage("银行卡解绑失败");
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
