package com.seition.cloud.pro.newcloud.home.mvp.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
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
import com.seition.cloud.pro.newcloud.home.di.component.DaggerHomeComponent;
import com.seition.cloud.pro.newcloud.home.di.module.HomeModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.HomeCourseFragmentPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.fragment.MainFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;
import com.yanzhenjie.sofia.StatusView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HomeCourseFragment extends BaseBackFragment<HomeCourseFragmentPresenter> implements HomeContract.CoursesFragmentView, BaseQuickAdapter.OnItemClickListener
        , CategoryPickPopupWindow.OnDialogItemClickListener, ListPopWindow.OnDialogItemClickListener, CourseTypeListPopWindow.OnDialogItemClickListener {

    @BindView(R.id.topBar)
    TopBar mTobBar;

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    CourseLiveRecyclerAdapter adapter;


//    private String keyword = "",  location  = "",  cate_id = "",  order = "default";

    public static HomeCourseFragment newInstance() {
        Bundle args = new Bundle();
        HomeCourseFragment fragment = new HomeCourseFragment();
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
        return inflater.inflate(R.layout.fragment_home_course, container, false);
    }

    @BindView(R.id.status_view)
    StatusView mStatusView;
    TopBarTab mTab;

    @Override
    public void initData(Bundle savedInstanceState) {
        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mTobBar.addItem(new TopBarTab(getContext(), R.drawable.ic_arrow_down_gray, "点播课程"))
                .addItem(new TopBarTab(getContext(), R.drawable.ic_arrow_down_gray, "分类"))
                .addItem(new TopBarTab(getContext(), R.drawable.ic_arrow_down_gray, "综合排序"));

        mTobBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition, TopBarTab tab) {
                mTab = tab;
                switch (position) {
                    case 0:
                        courseTypePopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                    case 1:
                        if (commonCategories.size() == 0)
                            try {
                                mPresenter.getCommonCategory(true);
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
        initListWindow();
        initView();
        loadData(true);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器

  /*      adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                ;
            }
        },recyclerView);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(new CustomLoadMoreView());*/
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

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

    private String cate_id = "", keyword = "", location = "", order = "default", vipid = "";
    private int type = 1;

    private void loadData(boolean pull) {
        try {
            mPresenter.searchCourses(keyword, location, type, cate_id, order, vipid, pull, false);
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
        CourseOnline course = ((CourseOnline) adapter.getItem(position));
        String live_id = course.getLive_id();
        String courseId = course.getId();
        boolean isLive = courseId == null ? true : false;
//        Intent intent = new Intent(_mActivity, CourseDetailsActivity.class);
////        Intent intent = new Intent(getActivity(), CourseDetailsFragment.class);
//        intent.putExtra("courseId", isLive ? live_id : courseId);
//        intent.putExtra("isLive", isLive);
//        launchActivity(intent);
        ((MainFragment) getParentFragment())
                .startBrotherFragment(CourseDetailsFragment.newInstance(isLive ? live_id : courseId, isLive, "", null));
    }

    private final static String TypeVideo = "点播课程";
    private final static String TypeLive = "直播课程";

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

    ListPopWindow listPopWindow;
    CourseTypeListPopWindow courseTypePopWindow;
    CategoryPickPopupWindow categoryPickPopupWindow;
    ArrayList<CommonCategory> commonCategories = new ArrayList<>();

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
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }

    @Override
    public void onDialogItemClick(BasePopWindow popWindow, String title, int position) {
       /* if(popWindow instanceof ListPopWindow){
            showMessage(""+title);;;;
//            /.
        }else if(popWindow instanceof  CourseTypeListPopWindow){
            showMessage(""+title);
        }*/

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
            mTab.setText(title);
        }
        loadData(true);

    }
}
