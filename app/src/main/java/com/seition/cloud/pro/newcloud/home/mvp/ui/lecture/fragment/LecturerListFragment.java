package com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.popupwindow.BasePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ListPopWindow;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLecturerComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LecturerModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LecturerContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LecturerContainerPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.LectureListRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.view.CustomLoadMoreView;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LecturerListFragment extends BaseBackFragment<LecturerContainerPresenter> implements LecturerContract.ListContainerView, BaseQuickAdapter.OnItemClickListener,
        CategoryPickPopupWindow.OnDialogItemClickListener, ListPopWindow.OnDialogItemClickListener {
    @BindView(R.id.topBar)
    TopBar mTobBar;

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @Inject
    LectureListRecyclerAdapter adapter;


    private String cateId = "", orderBy = "default";


    public static LecturerListFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        LecturerListFragment fragment = new LecturerListFragment();
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
        return inflater.inflate(R.layout.activity_lecturer_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("讲师列表");
        initListWindow();
        initView();
        loadData(true);
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
                            try {
                                mPresenter.getLectureCategory(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
//        recyclerView.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(this,0), Utils.dip2px(this,10f),R.color.default_background_color));

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                System.out.println("");
            }
        }, recyclerView);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(new CustomLoadMoreView());
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
        springView.setHeader(new DefaultHeader(_mActivity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(_mActivity));
        springView.setEnableFooter(false);
    }

    private void loadData(boolean pull) {
        mPresenter.getLecturers(cateId, orderBy, pull, false);
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
        Teacher teacher = (Teacher) adapter.getItem(position);
        start(LecturerDetailsFragment.newInstance(teacher.getId()));
    }

    private final static String OrderDefault = "智能排序";
    private final static String OrderNew = "最新";
    private final static String OrderHot = "热门老师";

    private void initListWindow() {

        listPopWindow = new ListPopWindow(_mActivity, null, 0);
        listPopWindow.addItemDatas(OrderDefault);
        listPopWindow.addItemDatas(OrderNew);
        listPopWindow.addItemDatas(OrderHot);
        listPopWindow.setOnDialogItemClickListener(this);

    }

    ListPopWindow listPopWindow;
    CategoryPickPopupWindow categoryPickPopupWindow;
    ArrayList<CommonCategory> commonCategories = new ArrayList<>();

    @Override
    public void onWindowItemClick(Object p) {
        CommonCategory category = (CommonCategory) p;
        cateId = category.getId();
        loadData(true);
    }


    @Override
    public void showLecturerCategory(ArrayList<CommonCategory> categories) {
        this.commonCategories = categories;
        categoryPickPopupWindow = new CategoryPickPopupWindow(_mActivity, commonCategories);
        categoryPickPopupWindow.setOnDialogItemClickListener(this);
        categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
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

        loadData(true);
    }
}
