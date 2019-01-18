package com.seition.cloud.pro.newcloud.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.utils.DefaultSpringUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;

/**
 * Created by xzw on 2018/4/27.
 */

public class FragmentLifecyleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {


    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);

    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        if (v.findViewById(R.id.springview) != null)
            if (v instanceof IFragment) {
                if (((IFragment) f).getLoadMoreFooterView() != null)
                    ((SpringView) v.findViewById(R.id.springview)).setFooter(((IFragment) f).getLoadMoreFooterView());
                if (((IFragment) f).getRefreshHeaderView() != null)
                    ((SpringView) v.findViewById(R.id.springview)).setHeader(((IFragment) f).getRefreshHeaderView());
            } else {
                ((SpringView) v.findViewById(R.id.springview)).setFooter(DefaultSpringUtils.getLoadMoreFooterView(f.getContext()));
                ((SpringView) v.findViewById(R.id.springview)).setHeader(DefaultSpringUtils.getRefreshHeaderView(f.getContext()));
            }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
    }
}
