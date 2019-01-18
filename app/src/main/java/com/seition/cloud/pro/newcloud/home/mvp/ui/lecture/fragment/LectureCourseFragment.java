package com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment;

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
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLecturerComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LecturerModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LecturerContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LecturerPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LectureCourseFragment extends BaseFragment<LecturerPresenter> implements LecturerContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    private int teacher_id;
    @Inject
    CourseLiveRecyclerAdapter courseAdapter;

    public static LectureCourseFragment newInstance(int teacher_id) {
        Bundle args = new Bundle();
        args.putInt("teacher_id", teacher_id);
        LectureCourseFragment fragment = new LectureCourseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLecturerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .lecturerModule(new LecturerModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lecturer_tab, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        teacher_id = getArguments().getInt("teacher_id");
        initView();
        loadData(true);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                loadData(true);
            }

            @Override
            public void onLoadmore() {
                loadData(false);
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(false);
    }


    private void loadData(boolean pull) {
        mPresenter.getTeacherCourse(teacher_id, pull, false);
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
        pop();
    }

    @Override
    public void showTeacher(Teacher teacher) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CourseOnline course = (CourseOnline) adapter.getItem(position);
//            Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
        if (course.getType().equals("1")) {
//                intent.putExtra("courseId", course.getId());
//                intent.putExtra("isLive", false);
            ((LecturerDetailsFragment) getParentFragment())
                    .startBrotherFragment(CourseDetailsFragment.newInstance(course.getId(), false, "", null));
        } else {
//                intent.putExtra("courseId", course.getLive_id() == null ? course.getId() : course.getLive_id());
//                intent.putExtra("isLive", true);
            ((LecturerDetailsFragment) getParentFragment())
                    .startBrotherFragment(CourseDetailsFragment.newInstance(course.getLive_id() == null ? course.getId() : course.getLive_id(), true, "", null));
        }
    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }
}
