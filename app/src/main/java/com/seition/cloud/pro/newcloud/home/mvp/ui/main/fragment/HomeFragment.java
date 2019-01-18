package com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveCourse;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.face.utils.LogUtil;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerHomeComponent;
import com.seition.cloud.pro.newcloud.home.di.module.HomeModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.HomeFragmentPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LecturerDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment.LecturerListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.live.fragment.LiveListMainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.fragment.MainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment.OfflineDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment.OfflineListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationListFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.WebActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CategoryGridAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.HotCourseGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LecturerGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LiveHorRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OfflineGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrganizationGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment.SearchMainFragment;
import com.seition.cloud.pro.newcloud.widget.GridViewNoScroll;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;
import com.yanzhenjie.sofia.StatusView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HomeFragment extends BaseFragment<HomeFragmentPresenter> implements HomeContract.HomeView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.school)
    LinearLayout school;

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.cate_list)
    GridViewNoScroll cateList;

    @BindView(R.id.live_recyler)
    RecyclerView liveRecyler;

    @BindView(R.id.hot_courses_recyler)
    RecyclerView hotCoursesRecyle;
    @BindView(R.id.new_courses_recyler)
    RecyclerView newCoursesRecyle;

    @BindView(R.id.offline_recyler)
    RecyclerView offlineRecyler;

    @BindView(R.id.lecture_recyler)
    RecyclerView lectureRecyler;

    @BindView(R.id.organization_recyler)
    RecyclerView organizationRecyler;


    @BindView(R.id.live_more)
    TextView live_more;
    @BindView(R.id.recommend_course_more)
    TextView recommend_course_more;
    @BindView(R.id.newest_course_more)
    TextView newest_course_more;
    @BindView(R.id.offline_course_more)
    TextView offline_course_more;
    @BindView(R.id.lecture_more)
    TextView lecture_more;
    @BindView(R.id.organization_more)
    TextView organization_more;

    @Inject
    CategoryGridAdapter categoryGridAdapter;

    @Inject
    CourseGridRecyclerAdapter newCourseAdapter;

    HotCourseGridRecyclerAdapter hotCourseAdapter;

    @Inject
    LiveHorRecyclerAdapter liveHorRecyclerAdapter;

    @Inject
    OfflineGridRecyclerAdapter offlineGridRecyclerAdapter;
    @Inject
    LecturerGridRecyclerAdapter lecturerGridRecyclerAdapter;
    @Inject
    OrganizationGridRecyclerAdapter organizationGridRecyclerAdapter;

    @OnClick({R.id.tv_special, R.id.live_more, R.id.recommend_course_more, R.id.newest_course_more, R.id.offline_course_more, R.id.lecture_more, R.id.organization_more})
    void toLiveList(View v) {
        switch (v.getId()) {
            case R.id.live_more:
                ((MainFragment) getParentFragment()).startBrotherFragment(LiveListMainFragment.newInstance());
                break;
            case R.id.tv_special:
            case R.id.recommend_course_more:
            case R.id.newest_course_more:
                ((MainFragment) getParentFragment()).startBrotherFragment(SearchMainFragment.newInstance(SearchMainFragment.SEARCH_COURSES, ""));
                break;
            case R.id.offline_course_more:
                ((MainFragment) getParentFragment()).startBrotherFragment(OfflineListFragment.newInstance());
                break;
            case R.id.lecture_more:
                ((MainFragment) getParentFragment()).startBrotherFragment(LecturerListFragment.newInstance());
                break;
            case R.id.organization_more:
                ((MainFragment) getParentFragment()).startBrotherFragment(OrganizationListFragment.newInstance());

//                launchActivity(new Intent(getActivity(), OrganizationListActivity.class));
                break;
        }
    }


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @BindView(R.id.status_view)
    StatusView mStatusView;

    @Override
    public SpringView.DragHander getLoadMoreFooterView() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        initView();

        loadData();

    }


    private void initView() {
        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadmore() {
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        //Glide 加载图片简单用法
                        LogUtil.e("tag",path.toString()+"_____________");
                        GlideLoaderUtil.LoadImage(getContext(), path, imageView);
                    }
                })
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                launchActivity(new Intent(_mActivity, WebActivity.class).putExtra("url", advertBeans.get(position).getBannerurl())
                        .putExtra("title", advertBeans.get(position).getBanner_title()));
            }
        });

        cateList.setAdapter(categoryGridAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        liveRecyler.setLayoutManager(linearLayoutManager);// 布局管理器
        liveRecyler.setAdapter(liveHorRecyclerAdapter);
        liveHorRecyclerAdapter.setOnItemClickListener(this);
        liveRecyler.setItemAnimator(new DefaultItemAnimator());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        hotCoursesRecyle.setLayoutManager(gridLayoutManager);// 布局管理器
        hotCourseAdapter = new HotCourseGridRecyclerAdapter();
        hotCoursesRecyle.setAdapter(hotCourseAdapter);
        hotCourseAdapter.setOnItemClickListener(this);
        hotCoursesRecyle.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(getActivity(), 10), Utils.dip2px(getActivity(), 6)));


        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        newCoursesRecyle.setLayoutManager(gridLayoutManager2);// 布局管理器
//        newCourseAdapter = new CourseGridRecyclerAdapter();
        newCoursesRecyle.setAdapter(newCourseAdapter);//newcourseGridRecyclerAdapter
        newCourseAdapter.setOnItemClickListener(this);//newcourseGridRecyclerAdapter
        //添加ItemDecoration，item之间的间隔
        newCoursesRecyle.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(getActivity(), 10), Utils.dip2px(getActivity(), 6)));


        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        offlineRecyler.setLayoutManager(gridLayoutManager3);// 布局管理器
        offlineRecyler.setAdapter(offlineGridRecyclerAdapter);
        offlineGridRecyclerAdapter.setOnItemClickListener(this);
        offlineRecyler.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(getActivity(), 10), Utils.dip2px(getActivity(), 6)));

        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lectureRecyler.setLayoutManager(gridLayoutManager4);// 布局管理器
        lectureRecyler.setAdapter(lecturerGridRecyclerAdapter);
        lecturerGridRecyclerAdapter.setOnItemClickListener(this);
        lectureRecyler.setItemAnimator(new DefaultItemAnimator());

        GridLayoutManager gridLayoutManager5 = new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        organizationRecyler.setLayoutManager(gridLayoutManager5);// 布局管理器
        organizationRecyler.setAdapter(organizationGridRecyclerAdapter);
        organizationGridRecyclerAdapter.setOnItemClickListener(this);
        organizationRecyler.setItemAnimator(new DefaultItemAnimator());


    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getMarketStatus();
    }

    private void loadData() {
        try {
            springView.callFreshDelay();
            mPresenter.getMcryptKey();
            if (MyConfig.isOpenAboutSchool) {
                school.setVisibility(View.VISIBLE);
                mPresenter.getHomeOrganization(true);
            } else school.setVisibility(View.GONE);
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

    ArrayList<AdvertBean> advertBeans;

    @Override
    public void setBanners(ArrayList<AdvertBean> advertBeans) {
        this.advertBeans = advertBeans;
        List<String> mTitleList = new ArrayList<>();
        List<String> mUrlList = new ArrayList<>();
        if (advertBeans.size() > 0) {
            for (AdvertBean advertBean : advertBeans) {
                LogUtil.e("tag",advertBean.getBannerurl()+"++++++++++"+advertBean.getBanner_title());
                mUrlList.add(advertBean.getBanner());
                mTitleList.add(advertBean.getBanner_title());
            }
            mBanner.setImages(mUrlList);
            mBanner.setBannerTitles(mTitleList);
            mBanner.start();
//            if (detailAdapter.getHeaderLayoutCount()<1){
//                detailAdapter.addHeaderView(view_Focus);
//            }
        }
    }

    @Override
    public void setCategory(ArrayList<CommonCategory> category) {

    }

    @Override
    public void setNewCourse(ArrayList<CourseOnline> courseOnlines) {
        newCourseAdapter.setNewData(courseOnlines);
    }

    @Override
    public void setHotCourse(ArrayList<CourseOnline> courseOnlines) {
        hotCourseAdapter.setNewData(courseOnlines);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof CourseGridRecyclerAdapter || adapter instanceof HotCourseGridRecyclerAdapter) {
            CourseOnline course = (CourseOnline) adapter.getItem(position);
//            Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
            if (course.getType().equals("1")) {
//                intent.putExtra("courseId", course.getId());
//                intent.putExtra("isLive", false);
                ((MainFragment) getParentFragment())
                        .startBrotherFragment(CourseDetailsFragment.newInstance(course.getId(), false, "", null));
            } else {
//                intent.putExtra("courseId", course.getLive_id() == null ? course.getId() : course.getLive_id());
//                intent.putExtra("isLive", true);
                ((MainFragment) getParentFragment())
                        .startBrotherFragment(CourseDetailsFragment.newInstance(course.getLive_id() == null ? course.getId() : course.getLive_id(), true, "", null));
            }
//            launchActivity(intent);
        }

        if (adapter instanceof LiveHorRecyclerAdapter) {
            HomeLiveCourse course = (HomeLiveCourse) adapter.getItem(position);
            ((MainFragment) getParentFragment())
                    .startBrotherFragment(CourseDetailsFragment.newInstance(course.getId(), true, "", null));
        }

        if (adapter instanceof LecturerGridRecyclerAdapter) {
            Teacher teacher = (Teacher) adapter.getItem(position);
            ((MainFragment) getParentFragment())
                    .startBrotherFragment(LecturerDetailsFragment.newInstance(teacher.getId()));
        }

        if (adapter instanceof OfflineGridRecyclerAdapter) {
            CourseOffline courseOffline = (CourseOffline) adapter.getItem(position);
            ((MainFragment) getParentFragment())
                    .startBrotherFragment(OfflineDetailsFragment.newInstance(courseOffline.getCourse_id()));
        }

        if (adapter instanceof OrganizationGridRecyclerAdapter) {
            Organization organization = (Organization) adapter.getItem(position);
            ((MainFragment) getParentFragment())
                    .startBrotherFragment(OrganizationDetailsFragment.newInstance(organization.getSchool_id() + ""));
        }
    }
}
