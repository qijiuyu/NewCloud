package com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


public class GetPasswordPhoneActivity extends BaseActivity<UseGetPasswordPresenter> implements UserContract.FindPasswordView,TextWatcher {

    @BindView(R.id.get_area)
    TextView get_area;
    @BindView(R.id.send_code)
    TextView send_code;

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.mes_code)
    EditText mes_code;

    @BindView(R.id.commit)
    Button commit;

    @OnClick({R.id.get_area, R.id.send_code,R.id.commit,R.id.get_email})
    void moreLogin(View v) {
        switch (v.getId()){
            case R.id.get_area:
                break;
            case R.id.send_code:
                String phoneNumber = phone.getText().toString().trim();
                if (!Utils.isPhone(phoneNumber)) {
                    Utils.showToast(GetPasswordPhoneActivity.this, "请输入正确手机号码");
                    return;
                }
                timer.start();
                mPresenter.getPasswordBackVerifyCode(phoneNumber);
                break;
            case R.id.commit:
                String phones = phone.getText().toString().trim();
                String code = mes_code.getText().toString().trim();
                if (!Utils.isPhone(phones)) {
                    Utils.showToast(GetPasswordPhoneActivity.this, "请输入正确手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    Utils.showToast(GetPasswordPhoneActivity.this, "请输入验证码");
                    return;
                }
                mPresenter.checkVerifyCode(phones,code);
                break;
            case R.id.get_email:
                launchActivity(new Intent(GetPasswordPhoneActivity.this,GetPasswordEmailActivity.class));
                killMyself();
                break;
        }
    }

    // 倒计时
    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

            send_code.setText(millisUntilFinished / 1000 + "s后重发");
        }

        @Override
        public void onFinish() {
            send_code.setText("重新发送");
            send_code.setClickable(true);
        }
    };
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
        return R.layout.activity_getpassword_phone; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.get_password);


//        if (MyConfig.isOpenFaceMoudle) {
//            mPresenter.getFaceSence(false,"login");
//        }
        phone.addTextChangedListener(this);
        mes_code.addTextChangedListener(this);
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
    public void toChangePwd(String  phone,String code) {

        launchActivity(new Intent(GetPasswordPhoneActivity.this,SetPasswordActivity.class).putExtra("phone",phone).putExtra("code",code));
        killMyself();
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
        String phoneStr = phone.getText().toString().trim();
        String code = mes_code.getText().toString().trim();
        if(!TextUtils.isEmpty(phoneStr)&&!TextUtils.isEmpty(code))
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
