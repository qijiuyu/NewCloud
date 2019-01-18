package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMoneyComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MoneyModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MoneyContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MoneyDetailsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MoneyDetailsListRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MoneyDetailsListFragment extends BaseBackFragment<MoneyDetailsPresenter> implements MoneyContract.ListView {

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    MoneyDetailsListRecyclerAdapter adapter;

    private int type;//1:积分 2：收入 3：余额

    public static MoneyDetailsListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        MoneyDetailsListFragment fragment = new MoneyDetailsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMoneyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .moneyModule(new MoneyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moneydetails_list ,container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        setTitle(type == 3 ? "余额明细" : type == 2 ? "收入明细" : "积分明细");
        initView();
        loadData(true);
    }

    @Override
    public void setData(Object data) {

    }

    private void initView(){
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
//        adapter.setOnItemClickListener(this);

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


    private void loadData(boolean pull){
        if (type == 1)
        mPresenter.getCreditList(pull);
        else if(type == 2)
            mPresenter.getSpiltList(pull);
        else if (type == 3)
            mPresenter.getBalanceList(pull);
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
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }


}
