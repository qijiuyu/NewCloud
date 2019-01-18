package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMessageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MessageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MessageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MessageFragementPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MessageSystemRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MessageSystemFragment extends BaseFragment<MessageFragementPresenter> implements MessageContract.FragmentView {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    MessageSystemRecyclerAdapter systemRecyclerAdapter;

    public static MessageSystemFragment newInstance() {
        MessageSystemFragment fragment = new MessageSystemFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageModule(new MessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        mPresenter.getMessageSystems();
    }

    private void initView() {
        mPresenter.initAdapterListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(systemRecyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(mPresenter);
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
}
