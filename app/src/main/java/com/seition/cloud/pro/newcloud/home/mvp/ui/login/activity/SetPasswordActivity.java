package com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UseGetPasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SetPasswordActivity extends BaseActivity<UseGetPasswordPresenter> implements UserContract.FindPasswordView ,TextWatcher{


    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;
    @BindView(R.id.commit)
    Button commit;

    private String phone, code;

    @OnClick({R.id.commit,})
    void moreLogin(View v) {
        switch (v.getId()){
            case R.id.commit:
                String pwd = et_pwd.getText().toString().trim();
                String repwd = et_confirm_pwd.getText().toString().trim();
                if (repwd.isEmpty()) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (repwd.length()<6) {
                    Toast.makeText(this, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!repwd.equals(pwd)) {
                    Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.resetPassword(phone,code,pwd,repwd);

                break;
        }

    }


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_set_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.get_password);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
        et_pwd.addTextChangedListener(this);
        et_confirm_pwd.addTextChangedListener(this);
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
        finish();
    }


    @Override
    public void toChangePwd(String phone, String code) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String pwdAgain = et_confirm_pwd.getText().toString().trim();
        String pwdFirst = et_pwd.getText().toString().trim();
        if (!TextUtils.isEmpty(pwdAgain)&&!TextUtils.isEmpty(pwdFirst))
        {
            commit.setBackgroundResource(R.drawable.shape_frame_theme);
            commit.setClickable(true);
        }
        else {
            commit.setBackgroundResource(R.drawable.shape_frame_undo);
            commit.setClickable(false);
        }
    }
}
