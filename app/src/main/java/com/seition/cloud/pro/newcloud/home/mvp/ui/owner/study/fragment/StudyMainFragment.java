package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.study.fragment;

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
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by xzw on 2018/4/28.
 */

public class StudyMainFragment extends BaseBackFragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;


    public static StudyMainFragment newInstance() {

        Bundle args = new Bundle();

        StudyMainFragment fragment = new StudyMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_study_main, container, false);
//        initView(view);
//        return view;
//    }



    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("直播",   OwnerStudyFragment.newInstance()));
        fragmenList.add(new FragmentBean("点播", OwnerStudyFragment.newInstance()));
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);

        tabs.setupWithViewPager(viewPager);
    }

/*
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        setFragmenList();
    }
*/



    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_study_main, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("我的学习记录");
        setFragmenList();
    }

    @Override
    public void setData(Object data) {

    }
}
