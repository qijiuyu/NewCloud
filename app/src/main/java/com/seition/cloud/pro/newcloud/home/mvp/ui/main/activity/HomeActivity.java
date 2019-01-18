package com.seition.cloud.pro.newcloud.home.mvp.ui.main.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.StatusBarUtil;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeCourseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeMoreFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment.HomeOwnerFragment;
import com.seition.cloud.pro.newcloud.widget.BottomBar;
import com.seition.cloud.pro.newcloud.widget.BottomBarTab;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class HomeActivity extends BaseActivity {

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(HomeActivity.this, 0, null);
        showBottomPager(savedInstanceState);
        HomeActivityPermissionsDispatcher.setupDatabaseWithPermissionCheck(HomeActivity.this);
    }

    /**
     * 配置数据库
     */
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void setupDatabase() {
        ((MApplication) getApplication()).setupDatabase();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    public void showBottomPager(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = HomeCourseFragment.newInstance();
            mFragments[2] = HomeMoreFragment.newInstance();
            mFragments[3] = HomeOwnerFragment.newInstance();
            getSupportDelegate().loadMultipleRootFragment(R.id.content, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3]);
        } else {
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(HomeCourseFragment.class);
            mFragments[2] = findFragment(HomeMoreFragment.class);
            mFragments[3] = findFragment(HomeOwnerFragment.class);
        }
        mBottomBar.addItem(new BottomBarTab(this, R.drawable.selector_home_bottom_drawable_home, getResources().getString(R.string.home)))
                .addItem(new BottomBarTab(this, R.drawable.selector_home_bottom_drawable_course, getResources().getString(R.string.course)))
                .addItem(new BottomBarTab(this, R.drawable.selector_home_bottom_drawable_more, getResources().getString(R.string.find)))
                .addItem(new BottomBarTab(this, R.drawable.selector_home_bottom_drawable_owner, getResources().getString(R.string.owner)));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                getSupportDelegate().showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

}
