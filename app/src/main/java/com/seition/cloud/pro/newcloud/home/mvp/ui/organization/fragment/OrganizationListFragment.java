package com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment;

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
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.popupwindow.BasePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ListPopWindow;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrganizationComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrganizationModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrganizationListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrganizationRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrganizationListFragment extends BaseBackFragment<OrganizationListPresenter> implements OrganizationContract.ListView,
        BaseQuickAdapter.OnItemClickListener, CategoryPickPopupWindow.OnDialogItemClickListener, ListPopWindow.OnDialogItemClickListener {
    @BindView(R.id.topBar)
    TopBar mTobBar;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    OrganizationRecyclerAdapter adapter;

    CategoryPickPopupWindow categoryPickPopupWindow;
    ListPopWindow listPopWindow;


    private String keyword = "", user_id = "", cateId = "", orderBy = "";


    public static OrganizationListFragment newInstance() {
        OrganizationListFragment fragment = new OrganizationListFragment();
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
        return inflater.inflate(R.layout.activity_organization_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.organization));
        initView();
        loadData(cateId, true, false);
    }

    @Override
    public void setData(Object data) {

    }

    private void initView() {
        mTobBar.addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "分类"))
                .addItem(new TopBarTab(_mActivity, R.drawable.ic_arrow_down_gray, "综合排序"));

        mTobBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition, TopBarTab tab) {
                switch (position) {
                    case 0:
                        if (commonCategories.size() == 0)
                            mPresenter.getOrganizationCategory(true);
                        else
                            categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                    case 1:
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

        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));// 布局管理器
//        recyclerView.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(_mActivity,0), Utils.dip2px(_mActivity,0.3f),R.color.color_e5));

  /*      adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData(cateId,false,false);
            }
        },recyclerView);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(new CustomLoadMoreView());*/

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                loadData(cateId, true, false);
            }

            @Override
            public void onLoadmore() {
                loadData(cateId, false, false);
            }
        });
        springView.setHeader(new DefaultHeader(_mActivity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(_mActivity));
        springView.setEnableFooter(false);
        initListWindow();
    }


    private void loadData(String cateId, boolean pull, boolean cache) {
        mPresenter.getOrganizationList(keyword, user_id, cateId, orderBy, pull, false);
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
        Organization organiztionBean = (Organization) adapter.getItem(position);
        start(OrganizationDetailsFragment.newInstance(organiztionBean.getSchool_id() + ""));

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
//        categoryPickPopupWindow.showPopAtDown(ll_course_dialog);

//        loadData(page,cateId,false);
    }

    @Override
    public void onWindowItemClick(Object p) {
        CommonCategory category = (CommonCategory) p;
        cateId = category.getId();
        loadData(cateId, true, false);
        System.out.println("dadf");
    }

    private final static String OrderDefault = "智能排序";
    private final static String OrderNew = "最新";
    private final static String OrderHot = "热门机构";

    private void initListWindow() {

        listPopWindow = new ListPopWindow(_mActivity, null, 0);
        listPopWindow.addItemDatas(OrderDefault);
        listPopWindow.addItemDatas(OrderNew);
        listPopWindow.addItemDatas(OrderHot);
        listPopWindow.setOnDialogItemClickListener(this);

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
    public void onDialogItemClick(BasePopWindow popWindow, String type, int position) {
        switch (type) {
            case OrderDefault:
                orderBy = "default";
                break;
            case OrderNew:
                orderBy = "new";
                break;
            case OrderHot:
                orderBy = "hot";
                break;
        }
        loadData(cateId, true, false);
    }
}
