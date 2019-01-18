package com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.AreaInfo;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UserAreaListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.AreaRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class AreaFragment extends BaseBackFragment<UserAreaListPresenter> implements UserContract.View ,BaseQuickAdapter.OnItemClickListener{

    private static final String AREA_ID = "area_id";
    private static final String AREA_TYPE = "area_type";

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    AreaRecyclerAdapter adapter;

    private OnFragmentInteractionListener mListener;

    //用来和Activity交互的回调接口
    public interface OnFragmentInteractionListener {

        //        void onFragmentInteraction(int level);
        void onFragmentInteraction(AreaInfo areaInfo, int nextAreaType);
    }

    int area_id = 0;
    int area_type = 0;
    public static AreaFragment newInstance(int area_id,int area_type) {
        Bundle args = new Bundle();
        args.putInt(AREA_ID,area_id);
        args.putInt(AREA_TYPE,area_type);
        AreaFragment fragment = new AreaFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /*@OnClick({R.id.toolbar_right_text,R.id.receive_area_input})
    void addAddress(View view)
    {
        switch (view.getId()){
            case R.id.toolbar_right_text:


                break;
            case R.id.receive_area_input:


                break;
        }

    }*/




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
        view = inflater.inflate(R.layout.fragment_area, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        int id = bundle.getInt(AREA_ID);
        int type = bundle.getInt(AREA_TYPE);
        if (getArguments() != null) {
            //获取父地区的code，用来查询子地区
            area_id = getArguments().getInt(AREA_ID);
            area_type = getArguments().getInt(AREA_TYPE);
        }
        area_id = id ;
        area_type= type;

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            //获取父地区的code，用来查询子地区
            area_id = getArguments().getInt(AREA_ID);
            area_type = getArguments().getInt(AREA_TYPE);
        }

        initview();
        loadData();

    }

    private void initview(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (getParentFragment() instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) getParentFragment();
        } else {
            throw new RuntimeException(getParentFragment().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
//    String province;String city;String area;String location;String address;String name;String phone;String is_default;
    private void loadData(){
        mPresenter.getArea(area_id,true);
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
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AreaInfo areaInfo = (AreaInfo) adapter  .getItem(position);
        if (areaInfo==null) return;
        System.out.println("CurrentArea_type = "+area_type);
        int nextAreaType = area_type+1;

        if (mListener!=null){
            mListener.onFragmentInteraction(areaInfo,nextAreaType);
        }
    }
}
