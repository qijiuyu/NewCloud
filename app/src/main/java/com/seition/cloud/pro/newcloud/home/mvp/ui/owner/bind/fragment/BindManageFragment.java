package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerBindManageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.BindManagePresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class BindManageFragment extends BaseBackFragment<BindManagePresenter> implements BindManageContract.View {

    @BindView(R.id.bind_face_txt)
    TextView bind_face_txt;

    public static BindManageFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        BindManageFragment fragment = new BindManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.bind_bank_txt, R.id.bind_ali_txt, R.id.bind_face_txt})
    void toBindBlock(View view) {
        switch (view.getId()) {
            case R.id.bind_bank_txt:
                start(BindBankListFragment.newInstance());
                break;
            case R.id.bind_ali_txt:
                start(BindAliFragment.newInstance());
                break;
            case R.id.bind_face_txt:
                start(BindFaceFragment.newInstance());
        }
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
        return inflater.inflate(R.layout.activity_bind_manage, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.bind_manage));
        bind_face_txt.setVisibility(PreferenceUtil.getInstance(_mActivity).getInt("SenceOpen", 0) == 1 ? View.VISIBLE : View.GONE);
//        mPresenter.loginBind();
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


}
