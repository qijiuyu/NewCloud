package com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMallComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MallModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MallPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MallRankHorRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MallFragment extends BaseBackFragment<MallPresenter> implements MallContract.View, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.mall_search_et)
    EditText mall_search_et;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;

    @BindView(R.id.mall_banner)
    Banner mBanner;

    @BindView(R.id.horizontal_recyler)
    RecyclerView recyclerView;

    @Inject
    MallRankHorRecyclerAdapter adapter;


    public static MallFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putSerializable("mallCategory",categories);
        MallFragment fragment = new MallFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mallModule(new MallModule(this))
                .build()
                .inject(this);
    }

    View view;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mall, container, false);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.mall));
        initView();
        initListener();
        mPresenter.getInitCredpayConfig();
        mPresenter.getMallBanner("app_goods_banner", true);
        mPresenter.getMallRankListData("rank", "", 1, 6, true);
        mPresenter.getMallCategory(0, true);
    }

    @Override
    public void setData(Object data) {

    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);


        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        //Glide 加载图片简单用法
                        GlideLoaderUtil.LoadImage(_mActivity, path, imageView);
                    }
                })
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                if (mBannerList.size() < 1) return;
//                bannerToRead(mBannerList.get(position));
            }
        });
    }

    private String keyword = "";

    private void initListener() {

        mall_search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    page = 1;
                    keyword = mall_search_et.getText().toString();
                    hideSoftInput();
//                    hide(mall_search_et);
                    ((MallListFragment) fragmenList.get(viewPager.getCurrentItem()).getFragment()).searchData(keyword);
                    return true;
                }

                return false;
            }
        });
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
//        finish();
        pop();
    }

//    @Override
//    public boolean isSupportSwipeBack() {
//        return false;
//    }

    @Override
    public void setBanner(ArrayList<AdvertBean> advertBeans) {
        List<String> mTitleList = new ArrayList<>();
        List<String> mUrlList = new ArrayList<>();
        if (advertBeans.size() > 0) {
            for (AdvertBean advertBean : advertBeans) {
                mUrlList.add(advertBean.getBanner());
                mTitleList.add(advertBean.getBanner_title());
            }
            mBanner.setImages(mUrlList);
            mBanner.setBannerTitles(mTitleList);
            mBanner.start();
//            if (detailAdapter.getHeaderLayoutCount()<1){
//                detailAdapter.addHeaderView(view_Focus);
//            }
        }
    }

    @Override
    public void setRankList(ArrayList<Mall> mallRanks) {

    }

    ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();

    @Override
    public void setFragmentList(ArrayList<MallCategory> commonCategories) {


//        for (MallCategory bean:commonCategories){
//            fragmenList.add(new FragmentBean(bean.getTitle(),   MallListFragment.newInstance(bean)));
//        }
        for (int i = 0; i < commonCategories.size(); i++) {
            fragmenList.add(new FragmentBean(commonCategories.get(i).getTitle(), MallListFragment.newInstance(commonCategories.get(i), i, commonCategories)));
        }

        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);

        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void showListData() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
        if (fragmenList != null)
            for (FragmentBean bean : fragmenList)
                ((MallListFragment) bean.getFragment()).notifyDataSetChanged();
    }

    public void setVPosition(int position) {
        viewPager.setCurrentItem(position);
    }

    public void startDetail(Mall mall) {
        /*   Intent intent = new Intent(_mActivity, MallDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mall", mall);
        intent.putExtras(bundle);
        launchActivity(intent);*/

        if (mall != null)
            start(MallDetailsFragment.newInstance(mall));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startDetail((Mall) adapter.getItem(position));
    }
}
