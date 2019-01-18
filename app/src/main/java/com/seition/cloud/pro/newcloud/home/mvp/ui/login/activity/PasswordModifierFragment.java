package com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerHomeComponent;
import com.seition.cloud.pro.newcloud.home.di.module.HomeModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.HomeUserModifierPasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class PasswordModifierFragment extends BaseBackFragment<HomeUserModifierPasswordPresenter> implements HomeContract.ModifierPwdView {

/*  @BindView(R.id.radio_female)
    RadioButton radio_female;*/

    @BindView(R.id.et_old_pwd)
    EditText et_old_pwd;
    @BindView(R.id.et_new_pwd)
    EditText et_new_pwd;
    @BindView(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;

    @BindView(R.id.confirm_btn)
    Button confirm_btn;

    @OnClick({R.id.confirm_btn})
    void toSearch(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn:
                String oldPwd = et_old_pwd.getText().toString().trim();
                String newPwd = et_new_pwd.getText().toString().trim();
                String confirmPwd = et_confirm_pwd.getText().toString().trim();

                if (TextUtils.isEmpty(oldPwd)) {
                    Toast.makeText(_mActivity, "当前密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(newPwd)) {
                    Toast.makeText(_mActivity, "新密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(confirmPwd)) {
                    Toast.makeText(_mActivity, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newPwd.equals(confirmPwd)) {
                    mPresenter.modifierPassword(oldPwd, newPwd, confirmPwd);
                } else {
                    Toast.makeText(_mActivity, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;

        }

    }

    public static PasswordModifierFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        PasswordModifierFragment fragment = new PasswordModifierFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modifier_password, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("修改密码");

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
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        pop();
    }


    @Override
    public void modifierPasswordSuccessful(boolean success) {
        showMessage(success ? "密码修改成功" : "密码修改失败");
    }
}
