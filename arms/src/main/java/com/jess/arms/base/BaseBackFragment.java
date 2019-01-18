/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.R;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DefaultSpringUtils;
import com.jess.arms.widget.statue.MultiStateView;
import com.jess.arms.widget.statue.SimpleMultiStateView;
import com.liaoinstan.springview.widget.SpringView;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.yanzhenjie.sofia.StatusView;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * ================================================
 * 因为 Java 只能单继承,所以如果要用到需要继承特定 @{@link Fragment} 的三方库,那你就需要自己自定义 @{@link Fragment}
 * 继承于这个特定的 @{@link Fragment},然后再按照 {@link BaseBackFragment} 的格式,将代码复制过去,记住一定要实现{@link IFragment}
 *
 * Fragmentation 直接跳转 目标Fragment(Activity->Fragment,Fragment->Fragment) 的基类
 *
 * <p>
 * Created by JessYan on 22/03/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public abstract class BaseBackFragment<P extends IPresenter> extends SwipeBackFragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;

//    @Nullable
//    @BindView(R.id.multistateview)
    SimpleMultiStateView mSimpleMultiStateView;

    StatusView mStatusView;

//    @BindView(R.id.multiStateView)
//    com.kennyc.view.MultiStateView mMultiStateView;


    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache =  ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }


    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getParentFragment() instanceof  SwipeBackFragment)
            return initView(inflater, container, savedInstanceState);
        else
            return attachToSwipeBack(initView(inflater, container, savedInstanceState));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleStatusView(view);
        initMuliStateView(view );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }


    private void initTitleStatusView(View view){
        mStatusView = view.findViewById(R.id.status_view);
        if (mStatusView == null) return;
        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }
    private void initMuliStateView(View view ) {
//        mMultiStateView= view.findViewById(R.id.multiStateView);
//        mMultiStateView.setViewState(com.kennyc.view.MultiStateView.VIEW_STATE_LOADING);

        mSimpleMultiStateView = view.findViewById(R.id.multistateview);
        if (mSimpleMultiStateView == null) return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.view_retry)
                .setLoadingResource(R.layout.view_loading)
                .setNoNetResource(R.layout.view_nonet)
                .build()
                .setonReLoadlistener(new MultiStateView.onReLoadlistener() {
                    @Override
                    public void onReload() {
//                        onRetry();
                    }
                });
    }

//    public abstract void onReload();

    public void showMultiViewState(int state){
        if (mSimpleMultiStateView ==null) return;
        switch (state){
            case MultiStateView.STATE_EMPTY:
                mSimpleMultiStateView.showEmptyView();
                break;
            case MultiStateView.STATE_FAIL:
                mSimpleMultiStateView.showErrorView();
                break;
            case MultiStateView.STATE_CONTENT:
                mSimpleMultiStateView.showContent();
                break;
            case MultiStateView.STATE_NONET:
                mSimpleMultiStateView.showNoNetView();
                break;
        }
    }

 /*   public void showSuccess() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }*/

  /*  public void showFaild() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }*/
    private CharSequence mTitle;


    public   void setTitle(CharSequence title){
         mTitle = title;
    };

    public   void setTitle(int titleid){

        mTitle = getResources().getString(titleid);
    };

    public  CharSequence getTitle(){
        return  mTitle;
    };

    @Override
    public SpringView.DragHander getRefreshHeaderView() {
        return DefaultSpringUtils.getRefreshHeaderView(getContext());
    }

    @Override
    public SpringView.DragHander getLoadMoreFooterView() {
        return DefaultSpringUtils.getLoadMoreFooterView(getContext());
    }

    public void startBrotherFragment(BaseBackFragment targetFragment) {
        start(targetFragment);
    }



}
