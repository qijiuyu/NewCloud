package com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UserReceiveAddressListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ReceiveAddressRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ReceiveGoodsListFragment extends BaseBackFragment<UserReceiveAddressListPresenter> implements UserContract.View ,BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener{
//    @BindView(R.id.status_view)
//    StatusView mStatusView;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.springview)
    SpringView springView;
    @Inject
    ReceiveAddressRecyclerAdapter adapter;

    public static ReceiveGoodsListFragment newInstance() {
        ReceiveGoodsListFragment fragment = new ReceiveGoodsListFragment();
        return fragment;
    }

    @OnClick(R.id.toolbar_right_text)
    void addAddress(View view)
    {
        if(adapter.getItemCount()<11)
            start(ReceiveGoodsAddFragment.newInstance(0,null));
        else
            showMessage("收货地址最多只能添加10条");
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

        DaggerUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    View view;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receive_address, container, false);
        return view;
    }



    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("我的收获地址");
        toolbar_right_text.setBackgroundResource(R.drawable.ic_add_white);

//        mStatusView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewAdapter = new RecyclerViewAdapter(this, mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.getGroupList(1,10,cateid,true,true);
                loadData();
            }

            @Override
            public void onLoadmore() {

            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
//        springView.callFresh();
//        springView.setFooter(new DefaultFooter(getActivity()));

        loadData();

    }

    private void loadData(){
        mPresenter.getReceiveAddresses(true);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ReceiveGoodsAddress receiveGoodsAddress = (ReceiveGoodsAddress)adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ReceiveAddressResult",receiveGoodsAddress);
//        bundle.putString(DetailFragment.KEY_RESULT_TITLE, mEtModiyTitle.getText().toString());
        setFragmentResult(RESULT_OK, bundle);
        pop();
        System.out.println("onItemClick");
    }

    @Override
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        System.out.println("onItemChildClick");
        ReceiveGoodsAddress address = (ReceiveGoodsAddress)adapter.getItem(position);
        switch (view.getId()){
            case R.id.goods_address_list_do_defult_ll:
                if(address.getIs_default().equals("0"))
                mPresenter.setDefaultReceiveAddress(address.getAddress_id());
                break;
            case R.id.receive_address_modifycation:
                start(ReceiveGoodsAddFragment.newInstance(1,address));
                break;
            case R.id.receive_address_delete:
                mPresenter.deleteReceiveAddress(address.getAddress_id());
                break;

        }
    }
}
