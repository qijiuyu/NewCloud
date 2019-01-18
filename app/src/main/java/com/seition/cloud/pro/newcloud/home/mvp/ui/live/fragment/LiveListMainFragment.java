package com.seition.cloud.pro.newcloud.home.mvp.ui.live.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.live.LiveTeacher;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.LiveFiltratePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.LoadMoreListPopWindow;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLiveComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LiveModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LiveContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LiveMainPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.DialogLoadMoreTeacherListRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment.SearchMainFragment;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LiveListMainFragment extends BaseBackFragment<LiveMainPresenter> implements LiveContract.MainView, CategoryPickPopupWindow.OnDialogItemClickListener, LoadMoreListPopWindow.OnDialogLoadMoreListener, LiveFiltratePopWindow.OnDialogChildeViewClickListener {
    @BindView(R.id.topBar)
    TopBar mTobBar;

    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;

    @OnClick(R.id.toolbar_right_text)
    void toSearch() {
        start(SearchMainFragment.newInstance(SearchMainFragment.SEARCH_LIVE, ""));
    }

    public static LiveListMainFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        LiveListMainFragment fragment = new LiveListMainFragment();
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.activity_live_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("直播大厅");
        toolbar_right_text.setBackgroundResource(R.mipmap.icon_search);//R.drawable.ic_search_white
        initView();
        initListWindow();
        initFiltrateWindow();
        setDefaultFragment();
    }

    @Override
    public void setData(Object data) {

    }

    protected LiveListFragment liveListFragment;

    private void setDefaultFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        liveListFragment = LiveListFragment.newInstance(LiveListFragment.LIVE_LIST);
        transaction.replace(R.id.id_content, liveListFragment);
        transaction.commit();
    }

    private void initView() {
        mTobBar.addItem(new TopBarTab(getContext(), R.drawable.ic_arrow_down_gray, "分类"))
                .addItem(new TopBarTab(getContext(), R.drawable.ic_arrow_down_gray, "讲师"))
                .addItem(new TopBarTab(getContext(), R.drawable.ic_arrow_down_gray, "筛选条件"));

        mTobBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition, TopBarTab tab) {
                switch (position) {
                    case 0:
                        if (commonCategories.size() == 0)
                            try {
                                mPresenter.getCommonCategory(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        else
                            categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;

                    case 1:

                        try {
                            if (liveTeachers.size() == 0)
                                mPresenter.getLiveScreenTeacher(true);
                            else
                                listPopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                        listPopWindow.showPopAsDropDown(mTobBar,0 ,0, Gravity.BOTTOM);
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

    ArrayList<CommonCategory> commonCategories = new ArrayList<>();
    CategoryPickPopupWindow categoryPickPopupWindow;

    @Override
    public void onWindowItemClick(Object p) {
        CommonCategory category = (CommonCategory) p;
        liveListFragment.resetCategory(category.getId());
    }

    DialogLoadMoreTeacherListRecyclerAdapter dialogLoadMoreTeacherListRecyclerAdapter;

    private void initListWindow() {
        dialogLoadMoreTeacherListRecyclerAdapter = new DialogLoadMoreTeacherListRecyclerAdapter();
        listPopWindow = new LoadMoreListPopWindow(_mActivity, 0, dialogLoadMoreTeacherListRecyclerAdapter);
        listPopWindow.setOnDialogLoadMoreListenerListener(this);
    }

    LoadMoreListPopWindow listPopWindow;

    @Override
    public void onDialogRefresh() {
        try {
            mPresenter.getLiveScreenTeacher(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogLoadMore() {
        try {
            mPresenter.getLiveScreenTeacher(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogItemClick(Object p) {
        LiveTeacher liveTeacher = (LiveTeacher) p;
        liveListFragment.resetTeacher(liveTeacher.getId());
        System.out.println();
    }

    ArrayList<LiveTeacher> liveTeachers = new ArrayList<>();
    ;

    @Override
    public void setDialogData(ArrayList<LiveTeacher> datas, boolean pull) {
        if (pull)
            this.liveTeachers = datas;
        listPopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
        listPopWindow.setDialogData(datas, pull);
    }

    @Override
    public void showCategory(ArrayList<CommonCategory> categories) {
        this.commonCategories = categories;

        categoryPickPopupWindow = new CategoryPickPopupWindow(_mActivity, commonCategories);
        categoryPickPopupWindow.setOnDialogItemClickListener(this);
        categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
    }


    LiveFiltratePopWindow filtratePopWindow;

    private void initFiltrateWindow() {
        filtratePopWindow = new LiveFiltratePopWindow(_mActivity, R.layout.dialog_live_filtrate, 0);
        filtratePopWindow.setOnDialogChildeViewClickListener(this);
    }

    @Override
    public void onChildeStatusViewClick(String status) {
        liveListFragment.resetStatus(status);
    }

    @Override
    public void onChildeOrderViewClick(String order) {
        liveListFragment.resetOrder(order);
    }

    @Override
    public void onChildeSureViewClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                liveListFragment.resetPargram("", "", "default", "", "0", "0", "0");
                break;
            case R.id.sure:
                liveListFragment.reloadLivelist();
                break;
        }
    }


}
