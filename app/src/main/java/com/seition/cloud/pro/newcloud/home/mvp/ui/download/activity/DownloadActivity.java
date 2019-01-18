package com.seition.cloud.pro.newcloud.home.mvp.ui.download.activity;

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
import com.seition.cloud.pro.newcloud.home.di.component.DaggerDownloadComponent;
import com.seition.cloud.pro.newcloud.home.di.module.DownloadModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.DownloadContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.DownloadPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.fragment.DownloadCourseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.fragment.DownloadLibraryFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class DownloadActivity extends BaseActivity<DownloadPresenter> implements DownloadContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;

    private DownloadLibraryFragment libraryFragment;
    private DownloadCourseFragment courseFragment;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerDownloadComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .downloadModule(new DownloadModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_download; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.owner_download);
        setFragmenList();
    }

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
//        fragmenList.add(new FragmentBean("课程", courseFragment = new DownloadCourseFragment()));
        fragmenList.add(new FragmentBean("文库", libraryFragment = new DownloadLibraryFragment()));
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);

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


}
