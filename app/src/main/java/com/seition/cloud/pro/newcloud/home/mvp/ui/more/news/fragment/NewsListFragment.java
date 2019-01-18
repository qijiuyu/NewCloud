package com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.news.NewsClassifyBean;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerNewsComponent;
import com.seition.cloud.pro.newcloud.home.di.module.NewsModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.NewsContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.NewsListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.news.adapter.NewsListAdapter;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by addis on 2018/3/26.
 */

public class NewsListFragment extends BaseFragment<NewsListPresenter> implements NewsContract.FragmentView {
    @Inject
    NewsListAdapter adapter;
    private NewsClassifyBean bean;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    public NewsListFragment() {
    }

    public static NewsListFragment getNewsListFragment(NewsClassifyBean bean) {
        NewsListFragment nlf = new NewsListFragment();
        nlf.setBean(bean);
        return nlf;
    }

    public void setBean(NewsClassifyBean bean) {
        this.bean = bean;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    public void showMessage(String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .newsModule(new NewsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_springview_recyclerview, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(mPresenter);
        recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);
        springView.setHeader(new DefaultHeader(_mActivity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(_mActivity));
        springView.setEnableFooter(false);
        mPresenter.getNewsList(bean.getZy_Topic_category_id(), true);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void setData(Object data) {
    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }
}
