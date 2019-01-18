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
package com.seition.cloud.pro.newcloud.app;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.utils.DefaultSpringUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;

import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    private int mActivityCount = 0, mCount;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.w(activity + " - onActivityCreated");
        mActivityCount++;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.w(activity + " - onActivityStarted");
        if (!activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
            //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
            //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
            activity.getIntent().putExtra("isInitToolbar", true);
            //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
            if (activity.findViewById(R.id.toolbar) != null) {
                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                    ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                        activity.getActionBar().setDisplayShowTitleEnabled(false);
                    }
                }
            }
            if (activity.findViewById(R.id.toolbar_title) != null) {
                if (activity instanceof IActivity)
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                else
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
            }
            if (activity.findViewById(R.id.toolbar_back) != null) {
                activity.findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                    if (activity instanceof SupportActivity)
                        ((SupportActivity) activity).onBackPressedSupport();
                    else
                        activity.onBackPressed();
                });
            }
        }
        if (activity.findViewById(R.id.springview) != null)
            if (activity instanceof IActivity) {
                if (((IActivity) activity).getLoadMoreFooterView() != null)
                    ((SpringView) activity.findViewById(R.id.springview)).setFooter(((IActivity) activity).getLoadMoreFooterView());
                if (((IActivity) activity).getRefreshHeaderView() != null)
                    ((SpringView) activity.findViewById(R.id.springview)).setHeader(((IActivity) activity).getRefreshHeaderView());
            } else {
                ((SpringView) activity.findViewById(R.id.springview)).setFooter(DefaultSpringUtils.getLoadMoreFooterView(activity));
                ((SpringView) activity.findViewById(R.id.springview)).setHeader(DefaultSpringUtils.getRefreshHeaderView(activity));
            }

        if (mCount == 0) {
//            Log.i("vergo", "**********切到前台**********");
//            CCInteractSession.getInstance().switchCamera(null, new CCInteractSession.AtlasCallBack<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    CCInteractSession.getInstance().enableVideo(false);
//                }
//
//                @Override
//                public void onFailure(String err) {
//
//                }
//            });
        }
        mCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.w(activity + " - onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.w(activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.w(activity + " - onActivityStopped");

        mCount--;
        if (mCount == 0) {
            Log.i("vergo", "**********切到后台**********");
//            CCInteractSession.getInstance().disableVideo(false);
//            CCInteractSession.getInstance().switchCamera(null, null);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.w(activity + " - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.w(activity + " - onActivityDestroyed");
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity.getIntent().removeExtra("isInitToolbar");

        mActivityCount--;
        if (mActivityCount <= 0) {
//            InteractSessionManager.getInstance().reset();
//            CCInteractSession.getInstance().releaseAll();
        }
    }
}
