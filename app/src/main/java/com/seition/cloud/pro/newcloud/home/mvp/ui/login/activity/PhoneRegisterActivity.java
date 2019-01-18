package com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerRegisterComponent;
import com.seition.cloud.pro.newcloud.home.di.module.RegisterModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.RegisterContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.RegisterPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.view.ShowAgreement;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class PhoneRegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, TextWatcher {
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;

    @BindView(R.id.procotol)
    CheckBox procotol;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.phone_number)
    EditText phone_number;
    @BindView(R.id.send_code)
    TextView send_code;
    @BindView(R.id.commit)
    Button commit;


    @OnClick(R.id.toolbar_right_text)
    void cutEmailReigister() {
        launchActivity(new Intent(this, EmailRegisterActivity.class)
                .putExtra("logintype", logintype)
                .putExtra("token", token)
                .putExtra("types", types)
        );
        killMyself();
    }

    private String phoneNumber, verificationCoe, userName, passWord;

    private static int type;
    private int types;
    private String token, logintype;

    @OnClick(R.id.commit)
    void onReigister() {

        phoneNumber = phone_number.getText().toString().trim();
        userName = user_name.getText().toString().trim();
        passWord = password.getText().toString().trim();
        verificationCoe = code.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            Utils.showToast(PhoneRegisterActivity.this, "手机号码不能为空");
            return;
        }
        if (!Utils.isPhone(phoneNumber)) {
            Utils.showToast(PhoneRegisterActivity.this, "手机号码格式不正确");
        }
        if (TextUtils.isEmpty(verificationCoe)) {
            Utils.showToast(PhoneRegisterActivity.this, "验证码不能为空");
            return;
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
                en_params = M.getEncryptData(
                        MApplication.getCodedLock()
                        , M.getMapString(
                                "login", phoneNumber
                                , "uname", userName
                                , "password", passWord
                                , "type", 2
                                , "code", verificationCoe
                                , "type_oauth", logintype
                                , "type_uid", token));
            else
                en_params = M.getEncryptData(
                        MApplication.getCodedLock()
                        , M.getMapString(
                                "login", phoneNumber
                                , "uname", userName
                                , "password", passWord
                                , "type", 2
                                , "code", verificationCoe));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPresenter.phoneRegister(en_params);

//        mPresenter.phoneRegister(phoneNumber,userName,passWord,2,verificationCoe);
    }


    @OnClick(R.id.send_code)
    void getVerifyCode() {
        try {
            phoneNumber = phone_number.getText().toString().trim();
            if (TextUtils.isEmpty(phoneNumber)) {
                Utils.showToast(PhoneRegisterActivity.this, "手机号码不能为空");
                return;
            }
//            timer.start();
            String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("phone", phoneNumber));
            mPresenter.getVerifyCode(en_params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.agreement)
    void getAgreement() {
        ShowAgreement.showAgreement(this, getLayoutInflater(), procotol, "reg");
    }

    // 倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            send_code.setEnabled(false);
            send_code.setText(millisUntilFinished / 1000 + "s后重发");
        }

        @Override
        public void onFinish() {
            send_code.setText("重新发送");
            send_code.setEnabled(true);
        }
    };

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
        return R.layout.activity_phone_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.register_phone);
        toolbar_right_text.setText(R.string.register_email);
        types = getIntent().getIntExtra("types", 0);
        if (types == 1) toolbar_right_text.setVisibility(View.VISIBLE);
        else toolbar_right_text.setVisibility(View.GONE);
        type = getIntent().getIntExtra("type", 0); // 默认0为获取注册手机短信验证码
        if (type == 0) {
            logintype = getIntent().getStringExtra("logintype");
            token = getIntent().getStringExtra("token");
        }
        phone_number.addTextChangedListener(this);
        code.addTextChangedListener(this);
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
        if (time == 0) {
            send_code.setText(R.string.get_verify_code);
            send_code.setClickable(true);
        } else {
            send_code.setText(time + "");
            timer.start();
        }
    }

    @Override
    public void regeisterResult() {
        Intent intent = new Intent(PhoneRegisterActivity.this, LoginActivity.class);
        intent.putExtra("flag", 0);
        intent.putExtra("current", 4);
        intent.putExtra("name", phoneNumber);
        intent.putExtra("pwd", password.getText().toString());
        PhoneRegisterActivity.this.startActivity(intent);
        PhoneRegisterActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String phone = phone_number.getText().toString().trim();
        String codeStr = code.getText().toString().trim();
        String uname = user_name.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(codeStr) && !TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pwd)) {
            commit.setBackgroundResource(R.drawable.shape_frame_theme);
            commit.setClickable(true);
        } else {
            commit.setBackgroundResource(R.drawable.shape_frame_undo);
            commit.setClickable(false);
        }
    }
}
