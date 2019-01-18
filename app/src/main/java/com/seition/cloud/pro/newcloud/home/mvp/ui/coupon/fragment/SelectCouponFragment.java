package com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCouponComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CouponModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CouponContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CouponPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CouponRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by addis on 2018/4/12.
 */
@SuppressLint("ValidFragment")
public class SelectCouponFragment extends BaseFragment<CouponPresenter> implements CouponContract.View, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    CouponRecyclerAdapter adapter;

    private int mId;

    int type;
    int courseType;
    String courseId;

    public static SelectCouponFragment newInstance(int type, int courseType, String courseId) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putInt("courseType", courseType);
        args.putString("courseId", courseId);
        SelectCouponFragment fragment = new SelectCouponFragment();
        fragment.setArguments(args);
        return fragment;
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
        _mActivity.finish();
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
        if (type == 0)
            adapter.setOnItemChildClickListener(this);
        else
            adapter.setIsNotUse();
        springView.setType(SpringView.Type.FOLLOW);
        springView.isEnableFooter();
        springView.setEnableFooter(false);
        springView.setListener(mPresenter);
        springView.setEnableFooter(false);
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
//        springView.setFooter(new DefaultFooter(getActivity()));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        courseType = getArguments().getInt("courseType");
        courseId = getArguments().getString("courseId");
        initList();

        loadData();
    }


    private void loadData() {
        mPresenter.getCoupon(courseType, type, courseId);
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
    public void showFragment(ArrayList<FragmentBean> fragmenList) {

    }

    public void setData(ArrayList<CouponBean> list) {
//        adapter.setEmptyView(R.layout.adapter_empty_view);
        adapter.setNewData(list);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        CouponBean bean = (CouponBean) adapter.getItem(position);
        _mActivity.setResult(Activity.RESULT_OK, new Intent().putExtra("couponBean", bean));
        killMyself();
    }
}
