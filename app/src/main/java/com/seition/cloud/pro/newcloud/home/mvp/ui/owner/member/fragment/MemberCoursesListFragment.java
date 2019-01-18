package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.member.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMemberComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MemberModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MemberCoursePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LecturerDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment.SearchMainFragment;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by addis on 2018/3/26.
 */

public class MemberCoursesListFragment extends BaseFragment<MemberCoursePresenter> implements MemberContract.MemberCourseView, BaseQuickAdapter.OnItemClickListener {
    //    @BindView(R.id.springview)
//    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @Inject
    CourseGridRecyclerAdapter adapter;
    int vip_id;

    public static MemberCoursesListFragment newInstance(int vip_id) {
        Bundle args = new Bundle();
        args.putInt("vip_id", vip_id);
        MemberCoursesListFragment fragment = new MemberCoursesListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMemberComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .memberModule(new MemberModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        vip_id = getArguments().getInt("vip_id");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recycle_view.setLayoutManager(gridLayoutManager);// 布局管理器

        recycle_view.setAdapter(adapter);
        recycle_view.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(getActivity(), 10), Utils.dip2px(getActivity(), 6)));
        adapter.setOnItemClickListener(this);
        loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.footer_member_more, (ViewGroup) recycle_view.getParent(), false);

        view.setOnClickListener(listener);
        return view;
    }

    @Override
    public void setData(Object data) {

    }

    private void loadData() {
        mPresenter.getVipCourse(vip_id, true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CourseOnline course = (CourseOnline) adapter.getItem(position);
        if (course.getType().equals("1")) {
            ((MemberCenterFragment) getParentFragment())
                    .startBrotherFragment(CourseDetailsFragment.newInstance(course.getId(), false, "", null));
        } else {
            ((MemberCenterFragment) getParentFragment())
                    .startBrotherFragment(CourseDetailsFragment.newInstance(course.getLive_id() == null ? course.getId() : course.getLive_id(), true, "", null));
        }
    }

    @Override
    public void addFooter() {
        View headerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MemberCenterFragment) getParentFragment()).startBrotherFragment(SearchMainFragment.newInstance(SearchMainFragment.SEARCH_COURSES, vip_id + ""));
            }
        });
        adapter.addFooterView(headerView);
    }
}
