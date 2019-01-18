package com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategoryBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLibraryComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LibraryModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LibraryContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LibraryPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.library.adapter.LibraryListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ArticleLibraryOwnerFragment extends BaseBackFragment<LibraryPresenter> implements LibraryContract.View {
    @Inject
    LibraryListAdapter adapter;

    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;


    public static ArticleLibraryOwnerFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        ArticleLibraryOwnerFragment fragment = new ArticleLibraryOwnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLibraryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .libraryModule(new LibraryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_article_library_owner, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.owner_library);
        initList();
        adapter.setMe(true);
        mPresenter.setRecycle_view(recycle_view);
        mPresenter.getOwnerLibraryList(true);
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

    private void initList() {
        recycle_view.setLayoutManager(new LinearLayoutManager(_mActivity));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());

        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(mPresenter);
        springView.setHeader(new DefaultHeader(_mActivity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(_mActivity));
        springView.setEnableFooter(false);
        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);
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
    public void setData(ArrayList<LibraryItemBean> datas) {
        adapter.setNewData(datas);

        if (datas.size() > 0)
            adapter.setNewData(datas);
        else
            adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(_mActivity));

    }

    @Override
    public void addData(ArrayList<LibraryItemBean> datas) {
        adapter.addData(datas);
    }

    @Override
    public void showRightText(int position) {

    }

    @Override
    public void showCategoryWindows(ArrayList<LibraryCategoryBean> commonCategories) {

    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }
}
