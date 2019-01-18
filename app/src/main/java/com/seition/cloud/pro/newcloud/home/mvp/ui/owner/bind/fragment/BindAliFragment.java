package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindAliAccount;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerBindManageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.BindManageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.BindAlipayManagePresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class BindAliFragment extends BaseBackFragment<BindAlipayManagePresenter> implements BindManageContract.AliManageView, TextWatcher {
    @BindView(R.id.aliAcountInfo)
    LinearLayout aliAcountInfo;
    @BindView(R.id.aliAcount)
    TextView aliAcount;
    @BindView(R.id.aliAcount_chang)
    TextView aliAcount_chang;
    @BindView(R.id.aliAcount_delete)
    TextView aliAcount_delete;

    @BindView(R.id.aliAcountInfo_binder)
    LinearLayout aliAcountInfo_binder;
    @BindView(R.id.aliAcount_input)
    EditText aliAcount_input;
    @BindView(R.id.aliAuther_input)
    EditText aliAuther_input;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.aliAuther_bind_commmit)
    TextView aliAuther_bind_commmit;


    @OnClick({R.id.aliAcount_chang, R.id.aliAcount_delete, R.id.aliAuther_bind_commmit})
    void doSomething(View view) {
        switch (view.getId()) {
            case R.id.aliAcount_chang:
                showDialog(1);
                break;
            case R.id.aliAcount_delete:
                showDialog(2);
                break;
            case R.id.aliAuther_bind_commmit:
                String name = aliAuther_input.getText().toString().trim();
                String aliAcount = aliAcount_input.getText().toString().trim();
                if ("".equals(name)) {
                    showMessage("请填写支付宝账号");
                    return;
                }
                if ("".equals(aliAcount)) {
                    showMessage("请填写真实姓名");
                    return;
                }
                mPresenter.setAlipay(name, aliAcount);
                break;
        }
    }

    private void showDialog(int type) {
        new MaterialDialog.Builder(_mActivity)
                .content((type == 1) ? "确定更改支付宝账号吗?" : "确定解绑支付宝账号吗?")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        if (type == 1) {
                            aliAcountInfo.setVisibility(View.GONE);
                            aliAcountInfo_binder.setVisibility(View.VISIBLE);
                            toolbar_title.setText("支付宝绑定");
                        } else if (type == 2)
                            mPresenter.unbindAlipay(aliId);
//                            deledtBindAlipayAcount();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static BindAliFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        BindAliFragment fragment = new BindAliFragment();
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.activity_bind_ali, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.bind_ali);

        aliAcount_input.addTextChangedListener(this);
        aliAuther_input.addTextChangedListener(this);
        mPresenter.getAlipayInfo();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String accountStr = aliAcount_input.getText().toString().trim();
        String pwd = aliAuther_input.getText().toString().trim();
        if (!TextUtils.isEmpty(accountStr) && !TextUtils.isEmpty(pwd)) {
            aliAuther_bind_commmit.setBackgroundResource(R.drawable.shape_frame_theme);
            aliAuther_bind_commmit.setEnabled(true);
        } else {
            aliAuther_bind_commmit.setBackgroundResource(R.drawable.shape_frame_undo);
            aliAuther_bind_commmit.setEnabled(false);
        }
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

    private String aliId = "";

    @Override
    public void showAlipayInfo(BindAliAccount aliAccount) {
        if (aliAccount != null) {
            aliId = aliAccount.getId();
            aliAcount.setText(aliAccount.getAccount());
            aliAcountInfo.setVisibility(View.VISIBLE);
            aliAcountInfo_binder.setVisibility(View.GONE);
        } else {
            aliId = "";
            toolbar_title.setText("支付宝绑定");
            aliAcountInfo.setVisibility(View.GONE);
            aliAcountInfo_binder.setVisibility(View.VISIBLE);
        }

    }
}
