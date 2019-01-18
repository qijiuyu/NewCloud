package com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.popupwindow.BasePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CourseTypeListPopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ListPopWindow;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerSearchComponent;
import com.seition.cloud.pro.newcloud.home.di.module.SearchModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.SearchContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.SearchCoursesPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SearchVideoCoursesFragment extends BaseFragment<SearchCoursesPresenter> implements SearchContract.View, BaseQuickAdapter.OnItemClickListener,
        CategoryPickPopupWindow.OnDialogItemClickListener, ListPopWindow.OnDialogItemClickListener, CourseTypeListPopWindow.OnDialogItemClickListener {

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    CourseLiveRecyclerAdapter adapter;

    @BindView(R.id.topBar)
    TopBar mTobBar;
    /*复用presenter方法一 ，SearchComponent 做相应的改变*/
//    @Inject
//    HomeCourseFragmentPresenter homeCourseFragmentPresenter;

    /*复用presenter方法二*/
//    @Inject
//    HomeCourseFragmentPresenter homeCourseFragmentPresenter;
//    SearchComponent searchComponent;
    public static SearchVideoCoursesFragment newInstance(int type, String vipid) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("vipid", vipid);
        SearchVideoCoursesFragment fragment = new SearchVideoCoursesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);

       /* searchComponent =  DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build();

        searchComponent.plus(new HomeModule(this))
                .inject(this);*/
//        homeCourseFragmentPresenter.
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_courses, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        vipid = getArguments().getString("vipid");
        initListWindow();
        mTobBar.addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "课程类型"))
                .addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "分类"))
                .addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "综合排序"));

        mTobBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition, TopBarTab tab) {
                hideSoftInput();
                switch (position) {
                    case 0:
                        courseTypePopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                    case 1:
                        if (commonCategories.size() == 0)
                            try {
                                mPresenter.getCoursesCategory(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        else
                            categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                    case 2:
                        listPopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;

                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));// 布局管理器
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));// 布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

//        //添加ItemDecoration，item之间的间隔
//        int leftRight = Utils.dip2px(getActivity(), 5);
//        int topBottom = Utils.dip2px(getActivity(), 5);
//
//        recyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
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
        loadData(true);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    private String cate_id = "", keyword = "", location = "", order = "default", vipid = "";
    private int type = 1;


    public void loadData(boolean pull) {

        try {
            mPresenter.searchCourses(keyword, location, type, cate_id, order, vipid, pull, true);
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
        springView.onFinishFreshAndLoad();
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CourseOnline course = (CourseOnline) adapter.getItem(position);
        String live_id = course.getLive_id();
        String courseId = course.getId();
        boolean isLive = courseId == null ? true : false;

//        Intent intent  = new Intent (_mActivity, CourseDetailsActivity.class);
//        intent.putExtra("courseId", isLive?live_id:courseId);
//        intent.putExtra("isLive", isLive);
//        launchActivity(intent);

        ((SearchMainFragment) getParentFragment()).startBrotherFragment(CourseDetailsFragment.newInstance(isLive ? live_id : courseId, isLive, "", null));
    }

    private void initListWindow() {

        listPopWindow = new ListPopWindow(_mActivity, null, 0);
        listPopWindow.addItemDatas("智能排序");
        listPopWindow.addItemDatas("评分递增");
        listPopWindow.addItemDatas("评分递减");
        listPopWindow.addItemDatas("价格递增");
        listPopWindow.addItemDatas("价格递减");
        listPopWindow.addItemDatas("订单递增");
        listPopWindow.addItemDatas("订单递减");
        listPopWindow.setOnDialogItemClickListener(this);

        courseTypePopWindow = new CourseTypeListPopWindow(_mActivity, null, 0);
        courseTypePopWindow.addItemDatas(TypeVideo);
        courseTypePopWindow.addItemDatas(TypeLive);
        courseTypePopWindow.setOnDialogItemClickListener(this);

    }

    CourseTypeListPopWindow courseTypePopWindow;
    ListPopWindow listPopWindow;

    ArrayList<CommonCategory> commonCategories = new ArrayList<>();
    CategoryPickPopupWindow categoryPickPopupWindow;

    @Override
    public void showCategoryWindows(ArrayList<CommonCategory> commonCategories) {
        this.commonCategories = commonCategories;

        categoryPickPopupWindow = new CategoryPickPopupWindow(getActivity(), commonCategories);
        if (commonCategories.size() == 0)
            categoryPickPopupWindow.setDialogEmptyViewVisibility(true);
        else
            categoryPickPopupWindow.setDialogEmptyViewVisibility(false);
        categoryPickPopupWindow.setOnDialogItemClickListener(this);
        categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
    }

    @Override
    public void onWindowItemClick(Object p) {
        CommonCategory category = (CommonCategory) p;
        cate_id = category.getId();
        loadData(true);
    }

    @Override
    public void showStateViewState(int state) {

    }

    private final static String TypeVideo = "点播课程";
    private final static String TypeLive = "直播课程";

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }

    @Override
    public void onDialogItemClick(BasePopWindow popWindow, String title, int position) {
        if (popWindow instanceof ListPopWindow) {
            switch (position) {
                case 0:
                    order = "default";
                    break;
                case 1:
                    order = "scoreasc";
                    break;
                case 2:
                    order = "scoredesc";
                    break;
                case 3:
                    order = "t_price";
                    break;
                case 4:
                    order = "t_price_down";
                    break;
                case 5:
                    order = "saleasc";
                    break;
                case 6:
                    order = "saledesc";
                    break;
            }
        } else if (popWindow instanceof CourseTypeListPopWindow) {
            if (title.equals(TypeVideo))
                type = 1;
            else if (title.equals(TypeLive))
                type = 2;
        }
        loadData(true);
    }
}
