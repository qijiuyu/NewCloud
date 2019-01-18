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

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.UseGetPasswordPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GetPasswordEmailActivity extends BaseActivity<UseGetPasswordPresenter> implements UserContract.FindPasswordView {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.commit)
    Button commit;

    @OnClick({R.id.phone, R.id.commit})
    void moreLogin(View v) {
        switch (v.getId()){
            case R.id.phone:
                launchActivity(new Intent(GetPasswordEmailActivity.this,GetPasswordPhoneActivity.class));
                killMyself();
                break;
            case R.id.commit:
                String inputEmail = email.getText().toString().trim();
                if (TextUtils.isEmpty(inputEmail)&&!Utils.isEmail(inputEmail)) {
                    Utils.showToast(GetPasswordEmailActivity.this, "邮箱格式不正确");
                    return;
                }
                mPresenter.getPasswordByEmail(inputEmail);
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
        return R.layout.activity_getpassword_email; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.get_password);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = s.toString().trim();
                if(!TextUtils.isEmpty(pwd))
                {
                    commit.setBackgroundResource(R.drawable.shape_frame_theme);
                    commit.setClickable(true);
                }
                else {
                    commit.setBackgroundResource(R.drawable.shape_frame_undo);
                    commit.setClickable(false);
                }
            }
        });

//        if (MyConfig.isOpenFaceMoudle) {
//            mPresenter.getFaceSence(false,"login");
//        }
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
}
