package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerBindManageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.BindBankListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.BindBankRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class BindBankListFragment extends BaseBackFragment<BindBankListPresenter> implements BindManageContract.BindBankListView {
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @Inject
    BindBankRecyclerAdapter adapter;


    public static BindBankListFragment newInstance() {
        Bundle args = new Bundle();
        BindBankListFragment fragment = new BindBankListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.toolbar_right_text)
    void toBankManage(View view) {
        start(BindBankManageFragment.newInstance());
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerBindManageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bindManageModule(new BindManageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bind_bank, container, false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        loadData();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar_right_text.setBackgroundResource(R.drawable.ic_set);
        setTitle(R.string.bind_bank);
        initView();
    }


    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewAdapter = new RecyclerViewAdapter(this, mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapter.setOnItemClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadmore() {

            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字

        View footerView = getFooterView(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(BankAddFragment.newInstance());
            }
        });
        adapter.addFooterView(footerView, 0);
    }

    private void loadData() {
        mPresenter.getBindBanks(1, true);
    }

    private View getFooterView(View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.footer_add_bank, (ViewGroup) recyclerView.getParent(), false);

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
        pop();
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
