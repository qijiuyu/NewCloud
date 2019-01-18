package com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

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
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.fragment.GroupFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GroupActivity extends BaseActivity<GroupPresenter> implements GroupContract.View {

    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();

    @OnClick(R.id.toolbar_right_text)
    void createGroup() {
        launchActivity(new Intent(this, GroupCreateActivity.class));
    }

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
        return R.layout.activity_group; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar_right_text.setBackgroundResource(R.mipmap.makeup);
        setTitle("小组");
        mPresenter.getGroupCategory("GC", true);
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
    public void setTabsData(ArrayList<Category> groupCategorys) {
        for (int i = 0; i < groupCategorys.size(); i++) {
            fragmenList.add(new FragmentBean(groupCategorys.get(i).getTitle(), GroupFragment.newInstance(i, groupCategorys.get(i).getId())));
        }
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }
}
