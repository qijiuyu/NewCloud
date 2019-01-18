package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.popupwindow.BasePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CourseTypeListPopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.OrderTypeListPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.fragment.OrderFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class OwnerOrderFragment extends BaseBackFragment/*<OrderPresenter> implements
        OrderContract.View */ implements OrderTypeListPopWindow.OnDialogItemClickListener {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    ArrayList<FragmentBean> fragmentList = new ArrayList<>();
    String orderType = "4";//5线下课   4点播   3直播
    String schoolId = "";//个人订单，机构id为空
    OrderTypeListPopWindow orderTypeListPopWindow;

    @OnClick(R.id.toolbar_title)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title:
                orderTypeListPopWindow.showPopAsDropDown(toolbar_title, 0, 0, Gravity.BOTTOM);
                break;
        }
    }

    public static OwnerOrderFragment newInstance(int id, String schoolId) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("schoolId", schoolId);
        OwnerOrderFragment fragment = new OwnerOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
    /*    DaggerOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);*/
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_order, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        PreferenceUtil.getInstance(_mActivity).saveBoolean("OdersNeedReload", false);
//        setTitle(R.string.owner_order);
        setTitle("点播订单");
        schoolId = getArguments().getString("schoolId");
        showOrderFragment();
        selectShowOrderFragment(getArguments().getInt("id", R.id.all_orders));
        orderTypeListPopWindow = new OrderTypeListPopWindow(_mActivity, null, 0);
        orderTypeListPopWindow.setOnDialogItemClickListener(this);
        Drawable drawable = _mActivity.getResources().getDrawable(R.drawable.ic_arrow_down);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbar_title.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void setData(Object data) {

    }

    public void rechangeOrderType(String type) {
        for (FragmentBean fragmentBean : fragmentList) {
            if (fragmentBean.getFragment() instanceof OrderFragment)
                ((OrderFragment) fragmentBean.getFragment()).setOrder_type(type);
        }
    }

    public void showOrderFragment() {
        if (fragmentList == null || fragmentList.size() == 0) {
            fragmentList = new ArrayList<>();
            if (schoolId == null || schoolId.isEmpty()) {
                //        fragmentList.add(new FragmentBean("全部", OrderFragment.newInstance(0)));
                fragmentList.add(new FragmentBean("待支付", OrderFragment.newInstance(1, orderType, schoolId)));
                fragmentList.add(new FragmentBean("已取消", OrderFragment.newInstance(2, orderType, schoolId)));
                fragmentList.add(new FragmentBean("已完成", OrderFragment.newInstance(3, orderType, schoolId)));
                fragmentList.add(new FragmentBean("申请退款", OrderFragment.newInstance(4, orderType, schoolId)));
                fragmentList.add(new FragmentBean("已退款", OrderFragment.newInstance(5, orderType, schoolId)));
                fragmentList.add(new FragmentBean("退款被驳回", OrderFragment.newInstance(6, orderType, schoolId)));
//                fragmentList.add(new FragmentBean("已失效", OrderFragment.newInstance(7, orderType, schoolId)));
            } else {
                fragmentList.add(new FragmentBean("已购买", OrderFragment.newInstance(3, orderType, schoolId)));
                fragmentList.add(new FragmentBean("申请退款", OrderFragment.newInstance(4, orderType, schoolId)));
                fragmentList.add(new FragmentBean("已退款", OrderFragment.newInstance(5, orderType, schoolId)));
            }
            showFragment(fragmentList);
        }
    }


    public void selectShowOrderFragment(int id) {
        switch (id) {
            case R.id.all_orders://全部
                viewPager.setCurrentItem(0);//取消全部
                break;
            case R.id.orders_no_payment://未支付
                viewPager.setCurrentItem(0);
                break;
            case R.id.orders_exit://已取消
                viewPager.setCurrentItem(1);
                break;
            case R.id.orders_done://已完成
                viewPager.setCurrentItem(2);
                break;
            case R.id.orders_pending://申请退款
                viewPager.setCurrentItem(3);
                break;
            case R.id.orders_refunded://已退款
                viewPager.setCurrentItem(4);
                break;
        }
    }

    public void showFragment(ArrayList<FragmentBean> fragmenList) {
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceUtil.getInstance(_mActivity).saveBoolean("OdersNeedReload", false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDialogItemClick(BasePopWindow popWindow, String type, String typeTitle) {
        rechangeOrderType(type);
        toolbar_title.setText(typeTitle);
//        setTitle(typeTitle);
    }
}
