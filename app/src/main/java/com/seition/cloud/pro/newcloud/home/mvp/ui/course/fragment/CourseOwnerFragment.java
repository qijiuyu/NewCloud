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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCourseComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CourseListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CourseOwnerFragment extends BaseFragment<CourseListPresenter> implements CourseContract.CourseListView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @Inject
    CourseLiveRecyclerAdapter adapter;

    private int courseId;

    public static CourseOwnerFragment newInstance(int courseId) {
        CourseOwnerFragment fragment = new CourseOwnerFragment();
        fragment.setCourseId(courseId);
        return fragment;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initList();
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }

    private void initList() {
        adapter.setShowBuy(false);
        recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);

//        mPresenter.getCourseSeitionList(courseId + "");
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

//    @Override
//    public void showSeition(ArrayList<MultiItemEntity> seitions) {
//        adapter.setNewData(seitions);
//        adapter.expandAll();
//    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
