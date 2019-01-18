package com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment;

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


public class QuestionaskOwnerFragment extends BaseBackFragment/*<QuestionaskPresenter> implements QuestionaskContract.View*/ {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;


    public static QuestionaskOwnerFragment newInstance( ) {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        QuestionaskOwnerFragment fragment = new QuestionaskOwnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_questionask_owner,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.owner_qa);
        setFragmenList();

    }

    @Override
    public void setData(Object data) {

    }

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("我的问题",   QuestionaskFragment.newInstance(4)));
        fragmenList.add(new FragmentBean("我的回答", QuestionaskFragment.newInstance(5)));
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setupWithViewPager(viewPager);
    }

}
