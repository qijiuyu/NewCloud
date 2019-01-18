package com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerQuestionaskComponent;
import com.seition.cloud.pro.newcloud.home.di.module.QuestionaskModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.QuestionaskPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QuestionAskRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class QuestionaskFragment extends BaseBackFragment<QuestionaskPresenter> implements QuestionaskContract.View, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    QuestionAskRecyclerAdapter adapter;

    public static QuestionaskFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);

        QuestionaskFragment fragment = new QuestionaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerQuestionaskComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .questionaskModule(new QuestionaskModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    private int type = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(getActivity(),5), Utils.dip2px(getActivity(),10)/*,R.color.color_e5*/));
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
        springView.setHeader(new DefaultHeader(getContext()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getContext()));
        springView.setEnableFooter(false);
        loadData(true);
    }


    public void reLoadData(int wdtype) {
        this.wdtype = wdtype;
        loadData(true);
    }

    int wdtype = 0;

    private void loadData(boolean pull) {
        switch (type) {
            case 1:
            case 2:
            case 3:
                mPresenter.getQAList(wdtype, type, pull, false);
                break;
            case 4:
                mPresenter.getMyQuestionList(pull);
                break;
            case 5:
                mPresenter.getMyAnswerList(pull);
                break;
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
        Questionask questionask = (Questionask) adapter.getItem(position);
        if (getParentFragment() instanceof QuestionaskOwnerFragment)
            ((QuestionaskOwnerFragment) getParentFragment()).startBrotherFragment(QuestionaskDetailsFragment.newInstance(questionask));
        else if (getParentFragment() instanceof QuestionaskMainFragment)
            ((QuestionaskMainFragment) getParentFragment()).startBrotherFragment(QuestionaskDetailsFragment.newInstance(questionask));

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
//        hideSoftInput();
    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }
}
