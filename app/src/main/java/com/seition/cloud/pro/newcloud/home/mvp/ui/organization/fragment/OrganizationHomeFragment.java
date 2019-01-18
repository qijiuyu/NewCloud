package com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment;

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
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrganizationComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrganizationModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrganizationHomePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrganizationHomeRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrganizationHomeFragment extends BaseFragment<OrganizationHomePresenter> implements OrganizationContract.HomeView /*,BaseQuickAdapter.OnItemChildClickListener*/{
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    OrganizationHomeRecyclerAdapter adapter;

    private String school_id = "";

    public static OrganizationHomeFragment newInstance(Organization organ) {
        Bundle args = new Bundle();
        args.putSerializable("organ",organ);
        OrganizationHomeFragment fragment = new OrganizationHomeFragment();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_organization_home, container, false);

    }


    private void initView(){


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        adapter.setNewData(datas);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.organization_discounts:
                        ((OrganizationDetailsFragment)getParentFragment()).startBrotherFragment(OrganizationCouponMainFragment.newInstance(organization));
                        break;
                }
            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());
   /*     recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Organization itemBean = (Organization) adapter.getItem(position);
                switch(view.getId()){
                    case R.id.organization_discounts:
                        System.out.print("121123123");
                        break;

                }
            }
        });*/


        springView.setType(SpringView.Type.FOLLOW);
        springView.setEnableFooter(true);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                loadData(page,true);
                mPresenter.getOrganizationDetails(school_id,false);
            }

            @Override
            public void onLoadmore() {

            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(false);
    }

    ArrayList<Organization> datas = new ArrayList<>();
    Organization organization;
    @Override
    public void initData(Bundle savedInstanceState) {
         organization = (Organization)getArguments().getSerializable("organ");
        datas.add(organization);
        school_id = organization.getSchool_id()+"";
        initView();
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

    }




    @Override
    public void refreshDetails(Organization organization) {

        ((OrganizationDetailsFragment)getParentFragment()).setOrganizationDetails(organization);
    }

}
