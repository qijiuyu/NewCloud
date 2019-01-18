package com.bokecc.dwlivedemo_new.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.activity.SettingActivity;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.popup.TxtLoadingPopup;
import com.bokecc.dwlivedemo_new.view.LoginLineLayout;
import com.bokecc.sdk.mobile.push.core.DWPushSession;
import com.bokecc.sdk.mobile.push.core.listener.OnLoginStatusListener;
import com.bokecc.sdk.mobile.push.exception.DWPushException;

import java.net.URLEncoder;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bokecc.dwlivedemo_new.util.LoginUtil.isNewLoginButtonEnabled;
import static com.bokecc.dwlivedemo_new.util.LoginUtil.toast;

public class PushFragment extends BaseFragment {

    @BindView(R2.id.id_login_push_root)
    LinearLayout mRoot;

    @BindView(R2.id.btn_login_push)
    Button btnLoginPush;

    @BindString(R2.string.test_userid)
    String mUserId;
    @BindString(R2.string.test_username)
    String mUserName;
    @BindString(R2.string.test_roomid)
    String mRoomId;
    @BindString(R2.string.test_passwd)
    String mPasswd;

    @BindView(R2.id.lll_login_push_uid)
    LoginLineLayout pushUidLayout;
    @BindView(R2.id.lll_login_push_roomid)
    LoginLineLayout pushRoomidLayout;
    @BindView(R2.id.lll_login_push_name)
    LoginLineLayout pushNameLayout;
    @BindView(R2.id.lll_login_push_password)
    LoginLineLayout pushPasswordLayout;

    private TxtLoadingPopup mLoadingPopup;

    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingPopup = new TxtLoadingPopup(mContext);
        mLoadingPopup.setKeyBackCancel(false);
        mLoadingPopup.setOutsideCancel(false);
        mLoadingPopup.setTipValue("正在登录...");
        mLoadingPopup.setOnPopupDismissListener(new BasePopupWindow.OnPopupDismissListener() {
            @Override
            public void onDismiss() {
                if(isSuccessed) {
                    writeSharePreference();
                    Intent intent = new Intent(mContext, SettingActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_push, container, false);
        ButterKnife.bind(this, view);

        pushUidLayout.setHint(getResources().getString(R.string.login_uid_hint)).addOnTextChangeListener(myTextWatcher);
        pushRoomidLayout.setHint(getResources().getString(R.string.login_roomid_hint)).addOnTextChangeListener(myTextWatcher);
        pushNameLayout.setHint(getResources().getString(R.string.login_name_hint)).addOnTextChangeListener(myTextWatcher);
        pushNameLayout.maxEditTextLength = nameMax;
        pushPasswordLayout.setHint(getResources().getString(R.string.login_t_password_hint))
                .addOnTextChangeListener(myTextWatcher)
                .setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

        preferences = getActivity().getSharedPreferences("push_login_info", Activity.MODE_PRIVATE);
        getSharePrefernce();

        if (map != null) {
            initEditTextInfo();
        }

        return view;
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            btnLoginPush.setEnabled(isNewLoginButtonEnabled(pushUidLayout, pushRoomidLayout, pushNameLayout));
        }
    };



    boolean isSuccessed = false;
    @OnClick(R2.id.btn_login_push)
    public void onClick() {
        isSuccessed = false;
        if (TextUtils.isEmpty(pushPasswordLayout.getText().toString())) {
            toast(getContext(), "密码不能为空");
            return;
        }

        mLoadingPopup.show(mRoot);
        DWPushSession.getInstance().login(pushUidLayout.getText().toString(),
                pushRoomidLayout.getText().toString(),
                URLEncoder.encode(pushNameLayout.getText().toString()),
                pushPasswordLayout.getText().toString(),
                new OnLoginStatusListener() {
                    @Override
                    public void failed(DWPushException e) {
                        //TODO 为啥是null
//                        toast(getContext(), e.getLocalizedMessage() + "");
                        toast(getContext(), "登录失败，请确认登录信息是否正确");
                        mLoadingPopup.dismiss();
                        isSuccessed = false;
                    }

                    @Override
                    public void successed() {
                        mLoadingPopup.dismiss();
                        isSuccessed = true;
                    }
                });
    }

    Map<String, String> map;

    @Override
    public void setLoginInfo(Map<String, String> map) {
        this.map = map;
        if (pushUidLayout != null) {
            initEditTextInfo();
        }
    }

    private void initEditTextInfo() {
        if (map.containsKey(roomIdStr)) {
            pushRoomidLayout.setText(map.get(roomIdStr));
        }

        if (map.containsKey(userIdStr)) {
            pushUidLayout.setText(map.get(userIdStr));
        }
    }

    private void writeSharePreference() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid", pushUidLayout.getText());
        editor.putString("roomid", pushRoomidLayout.getText());

        editor.putString("username", pushNameLayout.getText());
        editor.putString("password", pushPasswordLayout.getText());
        editor.commit();
    }

    private void getSharePrefernce() {
        pushUidLayout.setText(preferences.getString("uid", ""));
        pushRoomidLayout.setText(preferences.getString("roomid", ""));
        pushNameLayout.setText(preferences.getString("username", ""));
        pushPasswordLayout.setText(preferences.getString("password", ""));
    }

}
