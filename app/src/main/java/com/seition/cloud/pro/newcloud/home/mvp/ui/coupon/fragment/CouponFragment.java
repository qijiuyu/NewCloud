package com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCouponComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CouponModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CouponContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CouponPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CouponRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment.SearchMainFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by addis on 2018/4/12.
 */
@SuppressLint("ValidFragment")
public class CouponFragment extends BaseFragment<CouponPresenter> implements CouponContract.View, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    CouponRecyclerAdapter adapter;

    private int mId;


    int type;
    boolean isOrganization;
    String organizationId;

    public static CouponFragment newInstance(int type, boolean isOrganization, String organizationId) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putBoolean("isOrganization", isOrganization);
        args.putString("organizationId", organizationId);
        CouponFragment fragment = new CouponFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showFragment(ArrayList<FragmentBean> fragmenList) {

    }

    @Override
    public void setData(ArrayList<CouponBean> list) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
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
        return inflater.inflate(R.layout.fragment_coupon_list, container, false);
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);
        springView.isEnableFooter();
        springView.setEnableFooter(false);
//        springView.setListener(mPresenter);
        springView.setEnableFooter(false);
        springView.setEnableHeader(false);
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        isOrganization = getArguments().getBoolean("isOrganization");
        organizationId = getArguments().getString("organizationId");
        initList();

        loadData();

    }


    private void loadData() {
        if (isOrganization) {//机构的优惠券
            mPresenter.getOrganizationCouponList(isOrganization, type, organizationId, true, true);
        } else {
            //我的卡券
            mPresenter.getMyCouponList(isOrganization, type, 0, true, true);
        }
    }

    @Override
    public void refresh() {
        loadData();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
            launchActivity(new Intent(_mActivity, LoginActivity.class));
        else {
            CouponBean bean = (CouponBean) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.use:
                    switch (bean.getType()) {
                        case 1:
                        case 2:
                            if (isOrganization)
                                mPresenter.grantCoupon(bean.getCoupon_code());
                            else if (MyConfig.isOpenAboutSchool) {
                                if (getParentFragment() instanceof OwnerCouponMainFragment)
                                    ((OwnerCouponMainFragment) getParentFragment())
                                            .startBrotherFragment(OrganizationDetailsFragment.newInstance(bean.getSid()));
                            } else
                                ((OwnerCouponMainFragment) getParentFragment())
                                        .startBrotherFragment(SearchMainFragment.newInstance(SearchMainFragment.SEARCH_COURSES, ""));
                            break;
                        case 3://vipCard
                            if (isOrganization)
                                mPresenter.grantCoupon(bean.getCoupon_code() + "");//领取优惠券
                            else
                                mPresenter.useVipCoupon(bean.getCoupon_id() + "");//使用会员卡
                            break;
                        case 4:
                        case 5://CourseCard
                            if (isOrganization)
                                mPresenter.grantCoupon(bean.getCoupon_code() + "");//领取优惠券
                            else
                                mPresenter.useCoupon(bean.getCoupon_id() + "");//使用课程卡
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public SpringView.DragHander getRefreshHeaderView() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        if (MyConfig.isOpenAboutSchool)
//            ;
//        else

//            start(SearchMainFragment.newInstance(SearchMainFragment.SEARCH_COURSES,""));
    }
}
