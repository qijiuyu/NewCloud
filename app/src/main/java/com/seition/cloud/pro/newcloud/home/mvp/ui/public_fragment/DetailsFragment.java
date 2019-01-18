package com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerDetailsComponent;
import com.seition.cloud.pro.newcloud.home.di.module.DetailsModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.DetailsContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.DetailsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LecturerDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationDetailsFragment;
import com.seition.cloud.pro.newcloud.widget.ExpandableTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class DetailsFragment extends BaseFragment<DetailsPresenter> implements DetailsContract.View {
    @BindView(R.id.course_intro)
    ExpandableTextView course_intro;
    @BindView(R.id.teacher_name)
    TextView teacher_name;
    @BindView(R.id.teacher_course_count)
    TextView teacher_course_count;
    @BindView(R.id.teacher_fans_count)
    TextView teacher_fans_count;
    @BindView(R.id.teacher_leave_word)
    ExpandableTextView teacher_leave_word;
    @BindView(R.id.teacher_header_portrait)
    ImageView teacher_header_portrait;
    @BindView(R.id.school_name)
    TextView school_name;
    @BindView(R.id.school_course_count)
    TextView school_course_count;
    @BindView(R.id.school_teacher_count)
    TextView school_teacher_count;
    @BindView(R.id.school_header_portrait)
    ImageView school_header_portrait;
    @BindView(R.id.school)
    LinearLayout school;

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    public CourseOnline course;

    @OnClick({R.id.school, R.id.teacher})
    void onClick(View view) {
        if (course == null) return;
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.school:
                if (course.getSchool_info() != null)
                    ((CourseDetailsFragment) getParentFragment())
                            .startBrotherFragment(OrganizationDetailsFragment.newInstance(course.getSchool_info().getSchool_id() + ""));
//                intent.setClass(_mActivity, OrganizationDetailsActivity.class);
//                intent.putExtra("schoolId", course.getSchool_info().getSchool_id());
                break;
            case R.id.teacher:
                ((CourseDetailsFragment) getParentFragment())
                        .startBrotherFragment(LecturerDetailsFragment.newInstance(course.getTeacher_id() + ""));
                break;
        }
        if (intent != null)
            launchActivity(intent);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .detailsModule(new DetailsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

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
        course = (CourseOnline) data;
        showView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (teacher_leave_word != null) teacher_leave_word.onDestory();
        if (course_intro != null) teacher_leave_word.onDestory();
    }

    public void showView() {
//        course_intro.setText(Html.fromHtml(HtmlUtils.cleanP(course.getVideo_intro()), new URLImageParser(_mActivity, course_intro), null));

        course_intro.setText(_mActivity, course.getVideo_intro());
        mPresenter.showTeacher(course.getTeacher_id());
        if (MyConfig.isOpenAboutSchool) {
            school.setVisibility(View.VISIBLE);
            showSchool(course.getSchool_info());
        } else school.setVisibility(View.GONE);
    }

    public void showSchool(Organization school) {
        if (school == null) return;
        //需要单独调接口获取讲师信息数据
//        school_header_portrait.setText(course.getVideo_title());
        GlideLoaderUtil.LoadCircleImage(_mActivity, school.getCover(), school_header_portrait);
        school_name.setText(school.getTitle());
        school_course_count.setText(getResources().getString(R.string.course_number)
                + " " + school.getCount().getVideo_count());//课程数
        school_teacher_count.setText(getResources().getString(R.string.favorable_rate)
                + " " + school.getCount().getComment_rate());//粉丝数
        school.getCount().getComment_star();
    }

    @Override
    public void showTeacher(Teacher teacher) {
        //需要单独调接口获取讲师信息数据
        GlideLoaderUtil.LoadCircleImage(_mActivity, teacher.getHeadimg(), teacher_header_portrait);
        teacher_name.setText(teacher.getName());
        teacher_course_count.setText(getResources().getString(R.string.course_number)
                + " " + teacher.getVideo_count());//课程数
        teacher_fans_count.setText(getResources().getString(R.string.fans_number)
                + " " + teacher.getFollow_state().getFollower());//粉丝数

        teacher_leave_word.setText(_mActivity, teacher.getInfo());
//        teacher_leave_word.setText(teacher.getInfo());
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

}
