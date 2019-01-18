package com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.group.Category;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerGroupComponent;
import com.seition.cloud.pro.newcloud.home.di.module.GroupModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.GroupPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.fragment.GroupOwnerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GroupOwnerActivity extends BaseActivity<GroupPresenter> implements GroupContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerGroupComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .groupModule(new GroupModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_group_owner; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.owner_group);
        setFragmenList();
    }

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("已加入",   GroupOwnerFragment.newInstance()));
        fragmenList.add(new FragmentBean("已创建", GroupOwnerFragment.newInstance()));
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 测试只有ViewPager在第0页时才开启滑动返回
                mSwipeBackHelper.setSwipeBackEnable(position == 0);
            }
        });
//        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void setTabsData(ArrayList<Category> categories) {

    }
}
