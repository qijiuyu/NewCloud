package com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchool;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.LoadMoreListPopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.OfflineFiltratePopWindow;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOfflineComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OfflineModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OfflineContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OfflineListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.DialogLoadMoreSchoolListRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OfflineListRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OfflineListFragment extends BaseBackFragment<OfflineListPresenter> implements OfflineContract.OfflineListView, BaseQuickAdapter.OnItemClickListener, CategoryPickPopupWindow.OnDialogItemClickListener, OfflineFiltratePopWindow.OnDialogChildeViewClickListener, LoadMoreListPopWindow.OnDialogLoadMoreListener {

    @BindView(R.id.topBar)
    TopBar mTobBar;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @Inject
    OfflineListRecyclerAdapter adapter;

    CategoryPickPopupWindow categoryPickPopupWindow;
    OfflineFiltratePopWindow filtratePopWindow;

    private String cateId = "", orderBy = "default", school_id = "", time = "";

    public static OfflineListFragment newInstance() {
        Bundle args = new Bundle();
        OfflineListFragment fragment = new OfflineListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOfflineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .offlineModule(new OfflineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_offline_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("线下课列表");
        initFiltrateWindow();
        mTobBar.addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "分类"));
        if (MyConfig.isOpenAboutSchool)
            mTobBar.addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "校区"));
        mTobBar.addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "筛选条件"));

        mTobBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition, TopBarTab tab) {
                switch (position) {
                    case 0:
                        if (commonCategories.size() == 0)
                            mPresenter.getOfflineCategory(true);
                        else
                            categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                    case 1:
                        if (MyConfig.isOpenAboutSchool)
                            try {
                                if (offlineSchools.size() == 0)
                                    mPresenter.getOfflineSchools(true, false);
                                else
                                    listPopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        else filtratePopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                    case 2:
                        filtratePopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
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

        initView();
        initListWindow();
        loadData(true);
        springView.setEnableFooter(false);
    }

    @Override
    public void setData(Object data) {

    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));// 布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(_mActivity, 5), Utils.dip2px(_mActivity, 10)/*,R.color.color_e5*/));
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
        springView.setHeader(new DefaultHeader(_mActivity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(_mActivity));
    }

    private void loadData(boolean pull) {
        try {
            mPresenter.getOfflineCourses(cateId, orderBy, school_id, time, pull, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CourseOffline courses = (CourseOffline) adapter.getItem(position);
        start(OfflineDetailsFragment.newInstance(courses.getCourse_id() + ""));
    }

    ArrayList<CommonCategory> commonCategories = new ArrayList<>();

    @Override
    public void showCategoryWindows(ArrayList<CommonCategory> commonCategories) {
        this.commonCategories = commonCategories;
        categoryPickPopupWindow = new CategoryPickPopupWindow(_mActivity, commonCategories);
        if (commonCategories.size() == 0)
            categoryPickPopupWindow.setDialogEmptyViewVisibility(true);
        else
            categoryPickPopupWindow.setDialogEmptyViewVisibility(false);
        categoryPickPopupWindow.setOnDialogItemClickListener(this);
        categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
    }

    @Override
    public void onWindowItemClick(Object p) {
        CommonCategory commonCategory = (CommonCategory) p;
        cateId = commonCategory.getId();
        System.out.println("cate_id =" + commonCategory.getId());

        loadData(true);
    }

    private void initFiltrateWindow() {
        filtratePopWindow = new OfflineFiltratePopWindow(_mActivity, 0);
        filtratePopWindow.setOnDialogChildeViewClickListener(this);
    }

    @Override
    public void onChildeStatusViewClick(String time) {
        this.time = time;
    }

    @Override
    public void onChildeOrderViewClick(String order) {
        orderBy = order;
    }

    @Override
    public void onChildeSureViewClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                cateId = "";
                orderBy = "default";
                school_id = "";
                time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME1)), TimeUtils.Format_TIME1) + "," + "";
//                live_title_per.setText("课程专业");
//                live_title_teacher.setText("讲师");
                break;
            case R.id.sure:
                loadData(true);
                break;
        }
    }

    DialogLoadMoreSchoolListRecyclerAdapter dialogLoadMoreSchoolListRecyclerAdapter;

    private void initListWindow() {
        dialogLoadMoreSchoolListRecyclerAdapter = new DialogLoadMoreSchoolListRecyclerAdapter();
        listPopWindow = new LoadMoreListPopWindow(_mActivity, 0, dialogLoadMoreSchoolListRecyclerAdapter);
        listPopWindow.setOnDialogLoadMoreListenerListener(this);
    }

    LoadMoreListPopWindow listPopWindow;
    ArrayList<OfflineSchool> offlineSchools = new ArrayList<>();

    @Override
    public void setDialogData(ArrayList<OfflineSchool> datas, boolean pull) {
        if (pull)
            this.offlineSchools = datas;
        listPopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
        listPopWindow.setDialogData(datas, pull);
    }

    @Override
    public void onDialogRefresh() {
        try {
            mPresenter.getOfflineSchools(true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogLoadMore() {

        try {
            mPresenter.getOfflineSchools(false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogItemClick(Object p) {
        OfflineSchool data = (OfflineSchool) p;
        school_id = data.getId();
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
}
