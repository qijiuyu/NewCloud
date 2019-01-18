package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallListData;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MallGridRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class  MallFragmentPresenter extends BasePresenter<MallContract.Model, MallContract.FragmentView> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MallGridRecyclerAdapter adapter;

    @Inject
    public MallFragmentPresenter(MallContract.Model model, MallContract.FragmentView rootView) {
        super(model, rootView);
    }

    private int page =1 ,count =10;
    public void getMallListData(String type,String goods_category, String keyword,boolean pull,boolean iscache){
        if (pull)
            page = 1;
        else
            page++;
        mModel.getMallListDatas(type,goods_category,keyword,page,count, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if(mRootView!=null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MallListData>(mErrorHandler) {
                    @Override
                    public void onNext(MallListData data) {
                        ArrayList<Mall> mallLists = data.getData();
                        if(pull)
                            adapter.setNewData(mallLists);
                        else {
                            adapter.addData(mallLists);
                            if(mallLists.size() == 0)
                                page--;
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
