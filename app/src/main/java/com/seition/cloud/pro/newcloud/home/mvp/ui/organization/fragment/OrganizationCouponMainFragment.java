package com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCouponComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CouponModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CouponContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CouponPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrganizationCouponMainFragment extends BaseBackFragment<CouponPresenter> implements CouponContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;


    public static OrganizationCouponMainFragment newInstance(Organization organ ) {
        Bundle args = new Bundle();
        args.putSerializable("organ",organ);
        OrganizationCouponMainFragment fragment = new OrganizationCouponMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCouponComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .couponModule(new CouponModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_coupon ,container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Organization organization = (Organization)getArguments().getSerializable("organ");
        setTitle(organization.getTitle()+"优惠券");
        mPresenter.showCouponFragment(true,organization.getSchool_id()+"");
    }

    @Override
    public void setData(Object data) {

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

    }


    @Override
    public void showFragment(ArrayList<FragmentBean> fragmenList) {
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

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
