package com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCouponComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CouponModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CouponContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CouponPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment.SelectCouponFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SelectCouponMainActivity extends BaseActivity<CouponPresenter> implements CouponContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    private int courseType;
    private String courseId;

    @OnClick(R.id.toolbar_right_text)
    public void notUseConpon() {
        setResult(103);
        killMyself();
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCouponComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .couponModule(new CouponModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_owner_coupon;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.owner_coupon));
        toolbar_right_text.setText("不使用优惠券");
        setCourseId(getIntent().getStringExtra("id"));
        setCourseType(getIntent().getIntExtra("type", 0));
        ArrayList<FragmentBean> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentBean("可使用", SelectCouponFragment.newInstance(0, courseType, courseId)));
        fragmentList.add(new FragmentBean("不可使用", SelectCouponFragment.newInstance(1, courseType, courseId)));
        showFragment(fragmentList);
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
    public void showFragment(ArrayList<FragmentBean> fragmenList) {
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
//        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void setData(ArrayList<CouponBean> list) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }
}
