package com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment;

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
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrganizationComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrganizationModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrganizationFragmentPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LecturerDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LectureListRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrganizationCommentFragment extends BaseFragment<OrganizationFragmentPresenter> implements OrganizationContract.FragmentView {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    LectureListRecyclerAdapter lectureListRecyclerAdapter;

    Organization organization;

    public static OrganizationCommentFragment newInstance(Organization organ) {


        Bundle args = new Bundle();
        args.putSerializable("organ", organ);
        OrganizationCommentFragment fragment = new OrganizationCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrganizationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .organizationModule(new OrganizationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_springview_recyclerview, container, false);//fragment_organization_comment
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        organization = (Organization) getArguments().getSerializable("organ");
        initView();
        loadData(true);
    }

    private void initView() {
        lectureListRecyclerAdapter.setIsShowOrganization(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(lectureListRecyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        lectureListRecyclerAdapter.setOnItemClickListener(mPresenter);
        springView.setType(SpringView.Type.FOLLOW);
        springView.isEnableFooter();
        springView.setEnableFooter(false);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                loadData(true);
//                mPresenter.getOrganizationDetails(school_id);
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
        mPresenter.getOrganizationTeachers(organization.getSchool_id(), pull, false);
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

    }


    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }


    @Override
    public void showTeachers(ArrayList<Teacher> datas, boolean pull) {
        if (pull) {
            if (datas.size() > 0) {
                lectureListRecyclerAdapter.setNewData(datas);
                if (datas.size() < 10) {
                    if (lectureListRecyclerAdapter.getFooterViewsCount() == 0)
                        lectureListRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(_mActivity));
                    springView.setEnableFooter(false);//springView不可上拉
                } else {
                    lectureListRecyclerAdapter.removeAllFooterView();
                    springView.setEnableFooter(true);
                }
            } else
                lectureListRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(_mActivity));
        } else {
            lectureListRecyclerAdapter.addData(datas);
            if (datas.size() < 10) {
                if (lectureListRecyclerAdapter.getFooterViewsCount() == 0)
                    lectureListRecyclerAdapter.addFooterView(AdapterViewUtils.getNoDataViwe(_mActivity));
                springView.setEnableFooter(false);
            } else {
                springView.setEnableFooter(true);
            }
        }
    }

    @Override
    public void setDatas(ArrayList<Order> orders, boolean pull) {

    }

    @Override
    public void showTeacherHome(Teacher teacher) {
        ((OrganizationDetailsFragment) getParentFragment())
                .startBrotherFragment(LecturerDetailsFragment.newInstance(teacher.getId()));
    }
}
