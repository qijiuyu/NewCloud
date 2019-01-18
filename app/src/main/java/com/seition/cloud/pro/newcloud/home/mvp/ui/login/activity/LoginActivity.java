package com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.addis.umeng.UmengUtil;
import com.seition.addis.umeng.login.AuthCallback;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLoginComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LoginModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LoginContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LoginPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.activity.MainActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindFaceChedkActivity;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeFace;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, TextWatcher {
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.face_login)
    TextView face_login;
    @BindView(R.id.face_view)
    View face_view;
    @BindView(R.id.commit)
    Button commit;
    String type;
    int registerType = 0;//1:所有都可以 2 只能手机注册 3：只能邮箱注册

    @OnClick({R.id.sina_weibo_login, R.id.qq_login, R.id.wx_login})
    void moreLogin(View v) {
        SHARE_MEDIA platform = null;
        switch (v.getId()) {
            case R.id.sina_weibo_login:
                platform = SHARE_MEDIA.SINA;
                break;
            case R.id.qq_login:
                platform = SHARE_MEDIA.QQ;
                break;
            case R.id.wx_login:
                platform = SHARE_MEDIA.WEIXIN;
                break;
        }

        UmengUtil.login(this, platform, new AuthCallback() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, String info, boolean hasUnionid) {
                if (hasUnionid) {
                    loginSync(share_media, info);
                } else {
                    getQQUnionid(share_media, info);
//                    mPresenter.getUnionId(info);
                }
            }

            @Override
            public void onError(int var2, Throwable var3) {
                showMessage(var3.toString());
            }

            @Override
            public void onCancel(int var2) {
                showMessage("取消第三方授权！");
            }
        });

        mPresenter.moreLogin(v.getId());

    }

    private void loginSync(SHARE_MEDIA share_media, String info) {
        if (share_media == SHARE_MEDIA.SINA) {
            mPresenter.loginSync(info, "sina");
        } else if (share_media == SHARE_MEDIA.QQ) {
            mPresenter.loginSync(info, "qzone");
        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            mPresenter.loginSync(info, "weixin");
        }
    }

    String url = "https://graph.qq.com/oauth2.0/me?unionid=1";
    OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 获取QQ的unionid
     *
     * @param share_media
     * @param access_token
     */
    private void getQQUnionid(SHARE_MEDIA share_media, String access_token) {
        url += "&access_token=" + access_token;
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.d(TAG, "onResponse: ");
                ResponseBody body = response.body();
                String bodyStr = "";
                try {
                    bodyStr = body.string();
                    JSONObject obj = new JSONObject(bodyStr.substring(bodyStr.indexOf("{"), bodyStr.lastIndexOf("}") + 1));
                    loginSync(share_media, obj.getString("unionid"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }


    @OnClick({R.id.commit, R.id.guest_login, R.id.forget_pwd, R.id.face_login})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                View view = this.getWindow().getDecorView();
                Utils.hideSoftInput(view);
                onLogin();
                break;
            case R.id.guest_login:
                toHome();
                break;
            case R.id.forget_pwd:
                launchActivity(new Intent(LoginActivity.this, GetPasswordPhoneActivity.class));
                break;
            case R.id.face_login:
                startActivityForResult(new Intent(LoginActivity.this, BindFaceChedkActivity.class).putExtra("OperationType", 1), RequestCodeFace);
                break;
        }
    }

    void onLogin() {
        String uname = account.getText().toString().trim();
        String upwd = password.getText().toString().trim();
        if (uname.trim().isEmpty()) {
            showMessage("请输入用户名！");
        } else if (upwd.trim().isEmpty()) {
            showMessage("请输入密码！");
        } else {
            try {
                mPresenter.login(uname, upwd);
            } catch (Exception e) {
                e.printStackTrace();
                showMessage("登录失败");
            }
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (type != null && type.equals(MessageConfig.BACK_IS_EXIT)) toHome();
        else
            super.onBackPressedSupport();
    }

    @Override
    public void toHome() {
//        if (type == MessageConfig.BACK_IS_EXIT) ;//强制下线的不操作
//        else
        launchActivity(new Intent(this, MainActivity.class));
        killMyself();
    }

    @Override
    public void toRegister(String app_token, String app_login_type) {
        launchActivity(new Intent(this, registerType == 3 ? EmailRegisterActivity.class : PhoneRegisterActivity.class)
                .putExtra("logintype", app_login_type)
                .putExtra("token", app_token)
                .putExtra("types", registerType)
        );
    }

    @Override
    public void showRegisterType(String type) {
        switch (type) {
            case "all":
                registerType = 1;
                toolbar_right_text.setVisibility(View.VISIBLE);
                break;
            case "phone":
                registerType = 2;
                toolbar_right_text.setVisibility(View.VISIBLE);
                break;
            case "email":
                registerType = 3;
                toolbar_right_text.setVisibility(View.VISIBLE);
                break;
        }
    }

    int operationType = -1;

    @Override
    public void showFaceSaveStatus(FaceStatus status) {
        switch (status.getStatus()) {
            case 0:
                operationType = 3;
                break;
            case 1:
                operationType = 2;
                break;
            case 2:
                operationType = 4;
                break;

        }
        showFaceDialog(operationType);

    }

    private void showFaceDialog(int operationType) {
        new MaterialDialog.Builder(LoginActivity.this)
                .content(operationType == 2 ? "登录需要扫脸验证，是否开始扫脸验证？" : "登录需要完善个人人脸信息，是否立即去完善？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        startActivityForResult(new Intent(LoginActivity.this, BindFaceChedkActivity.class).putExtra("OperationType", operationType), RequestCodeFace);
//                        startForResult(BindFaceChedkActivity.newInstance(operationType),RequestCodeFace);
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


    @Override
    public void setFaceLoginTextVisibiliity(boolean isvisibility) {
        face_login.setVisibility(isvisibility ? View.VISIBLE : View.GONE);
        face_view.setVisibility(isvisibility ? View.VISIBLE : View.GONE);
    }


    @OnClick(R.id.toolbar_right_text)
    void onReigister() {
        toRegister("", "");
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbar_right_text.setVisibility(View.GONE);//默认隐藏
        setTitle(R.string.login);
        toolbar_right_text.setText(R.string.register);

        type = getIntent().getType();
        account.addTextChangedListener(this);
        password.addTextChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getInitRegisterType();
        mPresenter.getMcryptKey();
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
        if (type == MessageConfig.BACK_IS_EXIT) {
            System.exit(0);
        } else
            finish();
    }

    public void ss() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.CALL_PHONE
                    , Manifest.permission.READ_LOGS
                    , Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.SET_DEBUG_APP
                    , Manifest.permission.SYSTEM_ALERT_WINDOW
                    , Manifest.permission.GET_ACCOUNTS
                    , Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MyConfig.ResultCodeFaceCheck)
            toHome();
        else
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String accountStr = account.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if (!TextUtils.isEmpty(accountStr) && !TextUtils.isEmpty(pwd)) {
            commit.setBackgroundResource(R.drawable.shape_frame_theme);
            commit.setClickable(true);
        } else {
            commit.setBackgroundResource(R.drawable.shape_frame_undo);
            commit.setClickable(false);
        }
    }
}
