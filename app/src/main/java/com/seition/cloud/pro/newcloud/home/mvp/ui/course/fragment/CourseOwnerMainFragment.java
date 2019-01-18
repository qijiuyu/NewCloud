package com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.home.mvp.ui.live.fragment.LiveListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment.OfflineOwnerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by xzw on 2018/4/28.
 */

public class CourseOwnerMainFragment extends BaseBackFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    boolean isTeacherCourse;

    public static CourseOwnerMainFragment newInstance(boolean isTeacherCourse) {
        Bundle args = new Bundle();
        args.putBoolean("isTeacherCourse", isTeacherCourse);
        CourseOwnerMainFragment fragment = new CourseOwnerMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        LiveListFragment fragment;
        LiveListFragment fragment2;
        if (isTeacherCourse) {
            fragmenList.add(new FragmentBean("直播", fragment = LiveListFragment.newInstance(LiveListFragment.LIVE_OWNER_BY_TEACHER)));
            fragmenList.add(new FragmentBean("点播", fragment2 = LiveListFragment.newInstance(LiveListFragment.COURSE_OWNER_BY_TEACHER)));
            fragmenList.add(new FragmentBean("线下课", OfflineOwnerFragment.newInstance(OfflineOwnerFragment.Offline_OWNER_TEACHER)));
        }else{
            fragmenList.add(new FragmentBean("直播", fragment = LiveListFragment.newInstance(LiveListFragment.LIVE_OWNER)));
            fragmenList.add(new FragmentBean("点播", fragment2 = LiveListFragment.newInstance(LiveListFragment.COURSE_OWNER)));
            fragmenList.add(new FragmentBean("线下课", OfflineOwnerFragment.newInstance(OfflineOwnerFragment.Offline_OWNER)));
        }
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setupWithViewPager(viewPager);
        fragment.setIsShowBuy(false);
        fragment2.setIsShowBuy(false);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_study_main, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isTeacherCourse = getArguments().getBoolean("isTeacherCourse");
        if (isTeacherCourse)
            setTitle(getResources().getString(R.string.owner_course_by_teacher));
        else
            setTitle(getResources().getString(R.string.owner_course));
        setFragmenList();
    }

    @Override
    public void setData(Object data) {

    }
}
