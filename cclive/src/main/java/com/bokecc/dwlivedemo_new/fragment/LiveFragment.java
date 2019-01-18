package com.bokecc.dwlivedemo_new.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.activity.PcLivePlayActivity;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.popup.TxtLoadingPopup;
import com.bokecc.dwlivedemo_new.view.LoginLineLayout;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveLoginListener;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.PublishInfo;
import com.bokecc.sdk.mobile.live.pojo.RoomInfo;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.pojo.Viewer;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bokecc.dwlivedemo_new.util.LoginUtil.isNewLoginButtonEnabled;
import static com.bokecc.dwlivedemo_new.util.LoginUtil.toast;

public class LiveFragment extends BaseFragment {

    @BindView(R2.id.btn_login_live)
    Button btnLoginLive;

    @BindView(R2.id.lll_login_live_uid)
    LoginLineLayout lllLoginLiveUid;
    @BindView(R2.id.lll_login_live_roomid)
    LoginLineLayout lllLoginLiveRoomid;
    @BindView(R2.id.lll_login_live_name)
    LoginLineLayout lllLoginLiveName;
    @BindView(R2.id.lll_login_live_password)
    LoginLineLayout lllLoginLivePassword;
    @BindView(R2.id.ll_login_live_root)
    LinearLayout mRoot;

    private TxtLoadingPopup mLoadingPopup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingPopup = new TxtLoadingPopup(mContext);
        mLoadingPopup.setKeyBackCancel(true);
        mLoadingPopup.setOutsideCancel(true);
        mLoadingPopup.setTipValue("正在登录...");
        mLoadingPopup.setOnPopupDismissListener(new BasePopupWindow.OnPopupDismissListener() {
            @Override
            public void onDismiss() {
                if (isSuccessed) {
                    writeSharePreference();
                    Intent intent = new Intent(mContext, PcLivePlayActivity.class);

                    startActivity(intent);
                }
            }
        });
    }

    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_live, container, false);
        ButterKnife.bind(this, view);

        lllLoginLiveUid.setHint(getResources().getString(R.string.login_uid_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginLiveRoomid.setHint(getResources().getString(R.string.login_roomid_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginLiveName.setHint(getResources().getString(R.string.login_name_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginLiveName.maxEditTextLength = nameMax;
        lllLoginLivePassword.setHint(getResources().getString(R.string.login_s_password_hint))
                .addOnTextChangeListener(myTextWatcher)
                .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        preferences = getActivity().getSharedPreferences("live_login_info", Activity.MODE_PRIVATE);
        getSharePrefernce();
        if (map != null) {
            initEditTextInfo();
        }

        return view;
    }

    boolean isSuccessed = false;
    PublishInfo info;
    @OnClick(R2.id.btn_login_live)
    public void onClick() {
        mLoadingPopup.show(mRoot);
        isSuccessed = false;
        DWLive.getInstance().setDWLiveLoginParams(new DWLiveLoginListener() {
            @Override
            public void onLogin(TemplateInfo templateInfo, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {
                isSuccessed = true;
                info = publishInfo;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingPopup.dismiss();
                    }
                });
            }

            @Override
            public void onException(final DWLiveException e) {
                isSuccessed = false;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toast(mContext, e.getLocalizedMessage());
                        mLoadingPopup.dismiss();
                    }
                });
            }
        }, lllLoginLiveUid.getText(), lllLoginLiveRoomid.getText(), lllLoginLiveName.getText(), lllLoginLivePassword.getText());

        DWLive.getInstance().startLogin();
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            btnLoginLive.setEnabled(isNewLoginButtonEnabled(lllLoginLiveName, lllLoginLiveRoomid, lllLoginLiveUid));
        }
    };

    Map<String, String> map;

    @Override
    public void setLoginInfo(Map<String, String> map) {
        this.map = map;
        if (lllLoginLiveUid != null) {
            initEditTextInfo();
        }
    }

    private void initEditTextInfo() {
        if (map.containsKey(roomIdStr)) {
            lllLoginLiveRoomid.setText(map.get(roomIdStr));
        }

        if (map.containsKey(userIdStr)) {
            lllLoginLiveUid.setText(map.get(userIdStr));
        }
    }

    private void writeSharePreference() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid", lllLoginLiveUid.getText());
        editor.putString("roomid", lllLoginLiveRoomid.getText());
        editor.putString("username", lllLoginLiveName.getText());
        editor.putString("password", lllLoginLivePassword.getText());
        editor.commit();
    }

    private void getSharePrefernce() {
        lllLoginLiveUid.setText(preferences.getString("uid", ""));
        lllLoginLiveRoomid.setText(preferences.getString("roomid", ""));
        lllLoginLiveName.setText(preferences.getString("username", ""));
        lllLoginLivePassword.setText(preferences.getString("password", ""));
    }

}
