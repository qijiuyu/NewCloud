package com.seition.cloud.pro.newcloud.home.mvp.ui.live.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
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
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLiveComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LiveModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LiveContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LivePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.fragment.CourseOwnerMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.collect.CollectMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LiveGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LiveListFragment extends BaseFragment<LivePresenter> implements LiveContract.View
        , BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    LiveGridRecyclerAdapter adapter;

    @Inject
    CourseLiveRecyclerAdapter courseLiveRecyclerAdapter;

    public final static int LIVE_LIST = 1;//直播列表
    public final static int LIVE_COLLECT = 2;//收藏的直播列表
    public final static int LIVE_OWNER = 3;//购买的直播列表
    public final static int COURSE_COLLECT = 4;//收藏的课程列表
    public final static int COURSE_OWNER = 5;//购买的课程列表
    public final static int COURSE_OWNER_BY_TEACHER = 9;//发布的课程
    public final static int LIVE_OWNER_BY_TEACHER = 10;//发布的直播
    private int liveType;

    private int page = 1, count = 10;

    public static LiveListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        LiveListFragment fragment = new LiveListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setIsShowBuy(boolean isShowBuy) {
        if (courseLiveRecyclerAdapter != null) courseLiveRecyclerAdapter.setShowBuy(isShowBuy);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLiveComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .liveModule(new LiveModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        liveType = getArguments().getInt("type");
        initView();
    }

    private void initView() {
        if (liveType == LIVE_LIST) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));// 布局管理器
            recyclerView.setAdapter(adapter);
            //添加ItemDecoration，item之间的间隔
            int leftRight = Utils.dip2px(getActivity(), 5);
            int topBottom = Utils.dip2px(getActivity(), 5);

            recyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(courseLiveRecyclerAdapter);
        }


        adapter.setOnItemClickListener(this);
        courseLiveRecyclerAdapter.setOnItemClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                springView.setEnableFooter(false);
                loadData(page, true);
            }

            @Override
            public void onLoadmore() {
                page++;
                loadData(page, false);
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(false);
        loadData(page, true);
    }

    private String cate_id = "", keyword = "", begin_time = "0", end_time = "0", order = "default", teacher_id = "0", status = "";


    private void loadData(int page, boolean pull) {
//        System.out.println("LiveListFragment liveType = " + liveType);
        try {
            if (liveType == LIVE_LIST)
                mPresenter.getLiveList(cate_id, keyword, begin_time, end_time, order, teacher_id, page, count, status, true, pull);
            else if (liveType == LIVE_OWNER)
                mPresenter.getMyLiveList(page, count, false, pull);
            else if (liveType == COURSE_OWNER)
                mPresenter.getMyCourses(page, count, false, pull);
            else if (liveType == LIVE_COLLECT)
                mPresenter.getCollectCourse(2, false, pull);
            else if (liveType == COURSE_COLLECT)
                mPresenter.getCollectCourse(1, false, pull);
            else if (liveType == LIVE_OWNER_BY_TEACHER)
                mPresenter.getMyTeachLiveList(page, count, pull);
            else if (liveType == COURSE_OWNER_BY_TEACHER)
                mPresenter.getMyTeachCourseList(page, count, pull);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetTeacher(String teacher_id) {
        this.teacher_id = teacher_id;
        loadData(1, true);
    }

    public void resetStatus(String status) {
        this.status = status;
    }

    public void resetCategory(String cate_id) {
        this.cate_id = cate_id;
        loadData(1, true);
    }

    public void resetOrder(String order) {
        this.order = order;
    }

    public void resetPargram(String cate_id, String keyword, String order, String status, String teacher_id, String begin_time, String end_time) {
        page = 1;
        this.cate_id = cate_id;
        this.keyword = keyword;
        this.order = order;
        this.status = status;
        this.teacher_id = teacher_id;
        this.begin_time = begin_time;
        this.end_time = end_time;
    }

    public void reloadLivelist() {

        try {
            mPresenter.getLiveList(cate_id, keyword, begin_time, end_time, order, teacher_id, 1, count, status, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        Intent intent = new Intent(getContext(), CourseDetailsActivity.class);
        String courseId = "";
        boolean isLive = false;
        switch (liveType) {
            case LIVE_LIST:
            case LIVE_OWNER:
            case LIVE_COLLECT:
            case LIVE_OWNER_BY_TEACHER:
//                intent.putExtra("courseId", ((CourseOnline) adapter.getItem(position)).getLive_id());
//                intent.putExtra("isLive", true);
                courseId = ((CourseOnline) adapter.getItem(position)).getLive_id();
                isLive = true;
                break;
            case COURSE_COLLECT:
            case COURSE_OWNER:
            case COURSE_OWNER_BY_TEACHER:
//                intent.putExtra("courseId", ((CourseOnline) adapter.getItem(position)).getId());
//                intent.putExtra("isLive", false);

                courseId = ((CourseOnline) adapter.getItem(position)).getId();
                isLive = false;
                break;
        }

        if (getParentFragment() instanceof CollectMainFragment)
            ((CollectMainFragment) getParentFragment()).startBrotherFragment(CourseDetailsFragment.newInstance(courseId, isLive, "", null));
        else if (getParentFragment() instanceof CourseOwnerMainFragment)
            ((CourseOwnerMainFragment) getParentFragment()).startBrotherFragment(CourseDetailsFragment.newInstance(courseId, isLive, "", null));
        else if (getParentFragment() instanceof LiveListMainFragment)
            ((LiveListMainFragment) getParentFragment()).startBrotherFragment(CourseDetailsFragment.newInstance(courseId, isLive, "", null));

//            launchActivity(intent);
    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }
}
