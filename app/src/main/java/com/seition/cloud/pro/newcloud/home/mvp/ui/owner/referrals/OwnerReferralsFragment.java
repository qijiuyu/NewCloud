package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.bean.referrals.OwnerQRCode;
import com.seition.cloud.pro.newcloud.app.bean.referrals.ReferralsBean;
import com.seition.cloud.pro.newcloud.app.utils.ZXingUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerReferralsComponent;
import com.seition.cloud.pro.newcloud.home.di.module.ReferralsModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ReferralsContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.ReferralsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals.adapter.ReferralsListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by addis on 2018/11/23.
 */
public class OwnerReferralsFragment extends BaseBackFragment<ReferralsPresenter> implements ReferralsContract.View, BaseQuickAdapter.OnItemClickListener {
    @Inject
    ReferralsListAdapter adapter;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    private BottomSheetDialog bottomSheetDialog;

    @OnClick(R.id.toolbar_right_text)
    public void getOwnerQRCode() {
        if (bottomSheetDialog == null) initPayDialog();
        bottomSheetDialog.show();
        mPresenter.getOwnerQRCode();
    }

    @Override
    public void showMyQRCode(OwnerQRCode qrCode) {
        if (qrCode != null) {
            ImageView imgView = bottomSheetDialog.findViewById(R.id.qr_code);
            imgView.setImageBitmap(ZXingUtils.createQRImage(qrCode.getInvite_url(), imgView.getWidth(), imgView.getHeight()));
        }
    }

    public static OwnerReferralsFragment newInstance(String uid) {
        Bundle args = new Bundle();
        args.putString("uid", uid);
        OwnerReferralsFragment fragment = new OwnerReferralsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void initPayDialog() {
        bottomSheetDialog = new BottomSheetDialog(_mActivity);
        bottomSheetDialog.setContentView(R.layout.dialog_owner_qr_code);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerReferralsComponent
                .builder()
                .appComponent(appComponent)
                .referralsModule(new ReferralsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_referrals, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.owner_referrials));
        Drawable drawable = getResources().getDrawable(R.drawable.ic_owner_qr);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbar_right_text.setCompoundDrawables(drawable, null, null, null);
        initList();
    }

    private void initList() {
        recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(this);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(mPresenter);
        mPresenter.getReferralsList(true, getArguments().getString("uid"));
    }

    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }

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
        pop();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (position == 0) return;
        ReferralsBean referralsBean = (ReferralsBean) adapter.getItem(position);
        if (referralsBean.getLevel() == 1)
            start(OwnerReferralsFragment.newInstance(referralsBean.getUid()));
    }
}