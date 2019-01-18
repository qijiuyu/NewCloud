package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.fragment;

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


public class ExamOwnerMainFragment extends BaseBackFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;

    public static ExamOwnerMainFragment newInstance( ) {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        ExamOwnerMainFragment fragment = new ExamOwnerMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_exam_owner,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.owner_exam));
        setFragmenList();
    }

    @Override
    public void setData(Object data) {

    }

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("练习记录", ExamOwnerFragment.newInstance(1)));
        fragmenList.add(new FragmentBean("考试记录", ExamOwnerFragment.newInstance(2)));
        fragmenList.add(new FragmentBean("错题记录", ExamOwnerFragment.newInstance(3)));
        fragmenList.add(new FragmentBean("题目收藏", ExamOwnerFragment.newInstance(4)));
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);

//        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(viewPager);
    }


}
