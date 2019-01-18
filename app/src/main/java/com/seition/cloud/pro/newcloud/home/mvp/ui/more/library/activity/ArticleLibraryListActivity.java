package com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategoryBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.popupwindow.ArticleLibraryCategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CourseTypeListPopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ListPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.download.DownloadChangeViewInterface;
import com.seition.cloud.pro.newcloud.app.utils.download.TasksManager;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLibraryComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LibraryModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LibraryContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LibraryPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.adapter.LibraryListAdapter;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ArticleLibraryListActivity extends BaseActivity<LibraryPresenter> implements LibraryContract.View, DownloadChangeViewInterface {
    @Inject
    LibraryListAdapter adapter;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.topBar)
    TopBar mTobBar;
    ArrayList<LibraryCategoryBean> commonCategories;
    ArticleLibraryCategoryPickPopupWindow categoryPickPopupWindow;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLibraryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .libraryModule(new LibraryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_library; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.library);
        initListWindow();
        initTopSelect();
        initList();
        mPresenter.setRecycle_view(recycle_view);
    }

    private void initList() {
        recycle_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(mPresenter);
        TasksManager.getImpl(getApplicationContext()).onCreate(new WeakReference<>(this));
        mPresenter.getLibraryList(true);
    }

    ListPopWindow listPopWindow;

    private void initListWindow() {
        listPopWindow = new ListPopWindow(this, null, 0);
        listPopWindow.addItemDatas("综合排序");
        listPopWindow.addItemDatas("热门");
        listPopWindow.addItemDatas("精华");
        listPopWindow.setOnDialogItemClickListener(mPresenter);
    }

    @Override
    public void showCategoryWindows(ArrayList<LibraryCategoryBean> commonCategories) {
        this.commonCategories = commonCategories;

        categoryPickPopupWindow = new ArticleLibraryCategoryPickPopupWindow(this, commonCategories);
        if (commonCategories.size() == 0)
            categoryPickPopupWindow.setDialogEmptyViewVisibility(true);
        else
            categoryPickPopupWindow.setDialogEmptyViewVisibility(false);
        categoryPickPopupWindow.setOnDialogItemClickListener(mPresenter);
        categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
    }

    public void initTopSelect() {
        mTobBar.addItem(new TopBarTab(this, R.drawable.ic_arrow_down_gray, getString(R.string.course_skill)))
                .addItem(new TopBarTab(this, R.drawable.ic_arrow_down_gray, getString(R.string.sort)));
        mTobBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition, TopBarTab tab) {
                if (commonCategories == null) commonCategories = new ArrayList<>();
                switch (position) {
                    case 0:
                        if (commonCategories == null || commonCategories.size() == 0)
                            try {
                                mPresenter.getCommonCategory();
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
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    protected void onDestroy() {
        adapter = null;
        super.onDestroy();
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
        finish();
    }


    @Override
    public void setData(ArrayList<LibraryItemBean> datas) {
        adapter.setNewData(datas);
    }

    @Override
    public void addData(ArrayList<LibraryItemBean> datas) {
        adapter.addData(datas);
    }

    @Override
    public void showRightText(int position) {
        LibraryItemBean item = adapter.getItem(position);
        item.setIs_buy(1);
//        adapter.getHelper().showBuy(item);
        adapter.notifyItemChanged(position);

    }

    @Override
    public void postNotifyDataChanged() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }
}
