package com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
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
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.app.popupwindow.ListPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerSearchComponent;
import com.seition.cloud.pro.newcloud.home.di.module.SearchModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.SearchContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.SearchCoursesPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SearchLiveCoursesFragment extends BaseFragment<SearchCoursesPresenter> implements SearchContract.View ,BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    CourseLiveRecyclerAdapter adapter;



    public static SearchLiveCoursesFragment newInstance(Lecturer lecturer) {
        SearchLiveCoursesFragment fragment = new SearchLiveCoursesFragment();
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
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_courses, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));// 布局管理器
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));// 布局管理器
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);

        //添加ItemDecoration，item之间的间隔
        int leftRight = Utils.dip2px(getActivity(),5);
        int topBottom = Utils.dip2px(getActivity(),5);

        recyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                loadData(true);
            }

            @Override
            public void onLoadmore() {

                loadData(false);
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));

        loadData(true);
    }
    private String  cate_id = "",  keyword= "",  location= "",  order= "";
    private int type =1 ;
    private void loadData(boolean pull){
        try {
//            mPresenter.searchCourses( keyword,  location,  type,   cate_id,  order, pull,  true);
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

    }

    @Override
    public void showCategoryWindows(ArrayList<CommonCategory> commonCategories) {

    }

    private void initListWindow(){

        listPopWindow = new ListPopWindow(_mActivity,null,0 );
        listPopWindow.addItemDatas("智能排序");
        listPopWindow.addItemDatas("评分递增");
        listPopWindow.addItemDatas("评分递减");
        listPopWindow.addItemDatas("价格递增");
        listPopWindow.addItemDatas("价格递减");
        listPopWindow.addItemDatas("订单递增");
        listPopWindow.addItemDatas("订单递减");
//        listPopWindow.setOnDialogItemClickListener(this);

    }

    ListPopWindow listPopWindow;

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }
}
