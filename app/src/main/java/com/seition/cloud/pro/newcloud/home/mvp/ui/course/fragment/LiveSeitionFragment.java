package com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCourseComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LiveSeitionPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity.BuyFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.LiveSeitionAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by addis on 2018/6/13.
 */
public class LiveSeitionFragment extends BaseFragment<LiveSeitionPresenter> implements CourseContract.LiveSeitionView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.view)
    View view;
    Unbinder unbinder;
    @Inject
    LiveSeitionAdapter adapter;
    CourseContract.View courseView;
    private boolean isBuy = false;
    private boolean isFree = false;

    public static LiveSeitionFragment newInstance(CourseContract.View course) {
        LiveSeitionFragment fragment = new LiveSeitionFragment();
        fragment.setCourseView(course);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCourseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))
                .build()
                .inject(this);
    }

    public void setCourseView(CourseContract.View courseView) {
        this.courseView = courseView;
    }

    public void setLiveSeition(ArrayList<Section> sections, boolean isBuy, boolean free) {
        this.isBuy = isBuy;
        isFree = free;
        if (adapter != null) adapter.setBuyOrFree(isBuy, isFree);
        mPresenter.setLiveSeition(sections, isBuy,free);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_seition, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initList();
    }

    private void initList() {
        mPresenter.setView(view);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        recycleView.setAdapter(adapter);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showSeition(ArrayList<Section> datas) {
        if (datas.size() > 0) {
            adapter.setNewData(datas);
//                if (adapter.getFooterViewsCount() == 0)
//                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(_mActivity));
        } else
            adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(_mActivity));//getEmptyViwe
    }

    @Override
    public void toBuySection(Section section) {
        if (courseView != null) courseView.start(section);
    }
}
