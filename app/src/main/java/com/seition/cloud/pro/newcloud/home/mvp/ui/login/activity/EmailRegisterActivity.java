package com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerRegisterComponent;
import com.seition.cloud.pro.newcloud.home.di.module.RegisterModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.RegisterContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.RegisterPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.view.ShowAgreement;
import com.seition.cloud.pro.newcloud.app.utils.YesDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class EmailRegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, TextWatcher {
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;

    @BindView(R.id.procotol)
    CheckBox procotol;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.commit)
    Button commit;

    @OnClick(R.id.toolbar_right_text)
    void cutPhoneReigister() {
        launchActivity(new Intent(this, PhoneRegisterActivity.class)
                .putExtra("logintype", logintype)
                .putExtra("token", token)
                .putExtra("types", types)
        );
        killMyself();
    }

    private int types;
    private String token, logintype;
    private String emailAddress, userName, passWord;

    @OnClick(R.id.commit)
    void onReigister() {
        emailAddress = email.getText().toString().trim();
        userName = user_name.getText().toString().trim();
        passWord = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailAddress) && !Utils.isEmail(emailAddress)) {
            Utils.showToast(EmailRegisterActivity.this, "邮箱格式不正确");
        }

        if ("".equals(userName)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(passWord)) {
            Toast.makeText(this, "请设置正确的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passWord.length() < 6) {
            Toast.makeText(this, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!procotol.isChecked()) {
            Toast.makeText(this, R.string.procotol4, Toast.LENGTH_SHORT).show();
            return;
        }
        String en_params = "";
        try {
            if (logintype != null && token != null)
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                        "login", emailAddress
                        , "uname", userName
                        , "password", passWord
                        , "type_oauth", logintype
                        , "type_uid", token
                        , "type", 1));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                        "login", emailAddress
                        , "uname", userName
                        , "password", passWord
                        , "type", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPresenter.phoneRegister(en_params);
    }

    @OnClick(R.id.agreement)
    void getAgreement() {
        ShowAgreement.showAgreement(this, getLayoutInflater(), procotol, "reg");
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_email_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar_right_text.setText(R.string.register_phone);
        setTitle(R.string.register_email);
        logintype = getIntent().getStringExtra("logintype");
        token = getIntent().getStringExtra("token");
        types = getIntent().getIntExtra("types", 0);
        if (types == 1) toolbar_right_text.setVisibility(View.VISIBLE);
        else toolbar_right_text.setVisibility(View.GONE);
        email.addTextChangedListener(this);
        user_name.addTextChangedListener(this);
        password.addTextChangedListener(this);
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
    public void showVerifyTime(int time) {

    }

    @Override
    public void regeisterResult() {
        YesDialog.Builder builder = new YesDialog.Builder(this);
        builder.setMessage("恭喜您，注册成功！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                Intent intent = new Intent(EmailRegisterActivity.this, LoginActivity.class);
                intent.putExtra("flag", 0);
                intent.putExtra("current", 4);
                intent.putExtra("name", "");
                intent.putExtra("pwd", password.getText().toString());
                EmailRegisterActivity.this.startActivity(intent);
                EmailRegisterActivity.this.finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String phone = email.getText().toString().trim();
        String uname = user_name.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pwd)) {
            commit.setBackgroundResource(R.drawable.shape_frame_theme);
            commit.setClickable(true);
        } else {
            commit.setBackgroundResource(R.drawable.shape_frame_undo);
            commit.setClickable(false);
        }
    }
}
