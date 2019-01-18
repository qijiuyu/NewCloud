package com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLecturerComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LecturerModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LecturerContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LecturerPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.CommentFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LecturerDetailsFragment extends BaseBackFragment<LecturerPresenter> implements LecturerContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.lecture_attention)
    LinearLayout lecture_attention;
    @BindView(R.id.lecture_attention_img)
    ImageView lecture_attention_img;
    @BindView(R.id.lecture_attention_txt)
    TextView lecture_attention_txt;

    @BindView(R.id.lecture_cover)
    ImageView lecture_cover;
    @BindView(R.id.lecture_name)
    TextView lecture_name;
    @BindView(R.id.lecture_course_count)
    TextView lecture_course_count;
    @BindView(R.id.lecture_fans_count)
    TextView lecture_fans_count;
    private int teacherId;
    LectureHomeFragment homeFragment;
    LectureCourseFragment courseFragment;
    CommentFragment commentFragment;

    public static LecturerDetailsFragment newInstance(String teacherId) {
        Bundle args = new Bundle();
        args.putString("teacherId", teacherId);
        LecturerDetailsFragment fragment = new LecturerDetailsFragment();
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
        return inflater.inflate(R.layout.activity_lecturer, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String teachid = getArguments().getString("teacherId");
        teacherId = Integer.parseInt(teachid);
        setTitle("讲师详情");
        setFragmenList();
        try {
            mPresenter.getLecturerDetails(teacherId,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(Object data) {

    }

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("简介", homeFragment = LectureHomeFragment.newInstance(teacherId)));
        fragmenList.add(new FragmentBean("课程", courseFragment = LectureCourseFragment.newInstance(teacherId)));
        fragmenList.add(new FragmentBean("点评", commentFragment = CommentFragment.newInstance(teacherId + "", CommentFragment.Comment.Teacher)));
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);

        tabs.setupWithViewPager(viewPager);
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
        pop();
    }

//    @OnClick(R.id.lecture_attention)
//    void lectureAttention() {
//        mPresenter.attentionTeacher();
//    }
    @OnClick({R.id.lecture_attention,R.id.toolbar_right_text})
    void organizationAttention(View view){

        switch (view.getId()) {
            case R.id.lecture_attention:

                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
                    launchActivity(new Intent(_mActivity, LoginActivity.class));
                } else {
                    mPresenter.attentionTeacher();
                }

                break;
            case R.id.toolbar_right_text:
//                if(organization == null){
//                    showMessage("正在获取机构详情");
//                    return;
//                }
//                UmengUtil.shareUrl(_mActivity, Share.getSchoolHome(organization.getSchool_id()+"",organization.getDoadmin()),
//                        organization.getTitle(),organization.getIdcard(),organization.getCover());
                break;
        }
    }

    @Override
    public void showTeacher(Teacher teacher) {
        GlideLoaderUtil.LoadRoundImage1(_mActivity, teacher.getHeadimg(), lecture_cover);
        lecture_name.setText(teacher.getName());
        lecture_course_count.setText(teacher.getVideo_count()+"");
        lecture_fans_count.setText(teacher.getFollow_state().getFollower() + "");
        lecture_attention_txt.setText(teacher.getFollow_state().getFollowing() == 0 ? "关注" : "取消关注");
        homeFragment.showTeacher(teacher);
        courseFragment.showTeacher(teacher);

    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }
}
