package com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerGroupComponent;
import com.seition.cloud.pro.newcloud.home.di.module.GroupModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.GroupFragmentPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupTopicListActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupRecyclerAdapter;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public  class GroupFragment extends BaseFragment<GroupFragmentPresenter> implements GroupContract.fView ,BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.springview)
     SpringView springView;
    @BindView(R.id.recycle_view)
     RecyclerView recyclerView;
    private String cateid;

    @Inject
    GroupRecyclerAdapter adapter;
    private int vPage = 0;
//    private int page = 1;
//    private int count = 10;
    private String group_category ="";
    public static GroupFragment newInstance(int vPage,String cateid) {
        Bundle args = new Bundle();
        args.putString("cateid", cateid);
        args.putInt("vpage", vPage);
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerGroupComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .groupModule(new GroupModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        cateid = getArguments().getString("cateid");
        vPage  = getArguments().getInt("vpage");
        loadData(true);
        initView();
    }


    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewAdapter = new RecyclerViewAdapter(this, mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.getGroupList(1,10,cateid,true,true);
                loadData(true);
            }

            @Override
            public void onLoadmore() {
//                mPresenter.getGroupList(+1,10,cateid,false,true);

                loadData(false);
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
    }

    private void loadData(boolean pullToRefresh){
        mPresenter.getGroupList(vPage,cateid,pullToRefresh,false);
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
     * @param data*/



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
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        Intent intent = new Intent(view.getContext(), GroupTopicListActivity.class);
        Group group = (Group) adapter.getItem(position);
//        EventBus.getDefault().postSticky(new GroupEvent(coGroup),"Group ");
        Bundle bundle = new Bundle();
        bundle.putSerializable("group",group);
        bundle.putString("group_category", "" + group_category);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    @Override
    public void setGroupCategory(String groupCategory) {
        group_category = groupCategory;
    }

    @Override
    public void setSpringViewLoader(boolean hasdata) {

        if (hasdata){

            springView.setEnableFooter(true);
        }
        else {

            springView.setEnableFooter(false);
            if (adapter.getFooterViewsCount()==0)
            adapter.addFooterView(getFooterView(1, getRemoveFooterListener()), 0);
        }



    }



    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.footer_with_no_data, (ViewGroup) recyclerView.getParent(), false);
//        if (type == 1) {
//            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
//            imageView.setImageResource(R.mipmap.rm_icon);
//        }
        view.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.removeFooterView(v);
            }
        };
    }
}
