package com.bokecc.dwlivedemo_new.fragment;

import android.app.Activity;
import android.content.Context;
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
import com.bokecc.dwlivedemo_new.activity.ReplayActivity;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.popup.TxtLoadingPopup;
import com.bokecc.dwlivedemo_new.view.LoginLineLayout;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.PublishInfo;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplay;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplayLoginListener;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bokecc.dwlivedemo_new.util.LoginUtil.isNewLoginButtonEnabled;
import static com.bokecc.dwlivedemo_new.util.LoginUtil.toast;

public class ReplayFragment extends BaseFragment {

    @BindView(R2.id.btn_login_replay)
    Button btnLoginReplay;
    @BindView(R2.id.lll_login_replay_uid)
    LoginLineLayout lllLoginReplayUid;
    @BindView(R2.id.lll_login_replay_roomid)
    LoginLineLayout lllLoginReplayRoomid;
    @BindView(R2.id.lll_login_replay_liveid)
    LoginLineLayout lllLoginReplayLiveid;
    @BindView(R2.id.lll_login_replay_recordid)
    LoginLineLayout lllLoginReplayRecordid;
    @BindView(R2.id.lll_login_replay_name)
    LoginLineLayout lllLoginReplayName;
    @BindView(R2.id.lll_login_replay_password)
    LoginLineLayout lllLoginReplayPassword;

    @BindView(R2.id.ll_replay_login_layout)
    LinearLayout mRoot;

    SharedPreferences preferences;
    private TxtLoadingPopup mLoadingPopup;
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
                    Intent intent = new Intent(mContext, ReplayActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_replay, container, false);
        ButterKnife.bind(this, view);

        lllLoginReplayUid.setHint(getResources().getString(R.string.login_uid_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginReplayRoomid.setHint(getResources().getString(R.string.login_roomid_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginReplayLiveid.setHint(getResources().getString(R.string.login_liveid_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginReplayRecordid.setHint(getResources().getString(R.string.login_recordid_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginReplayName.setHint(getResources().getString(R.string.login_name_hint)).addOnTextChangeListener(myTextWatcher);
        lllLoginReplayName.maxEditTextLength = nameMax;
        lllLoginReplayPassword.setHint(getResources()
                .getString(R.string.login_s_password_hint))
                .addOnTextChangeListener(myTextWatcher)
                .setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

        preferences = getActivity().getSharedPreferences("replay_login_info", Activity.MODE_PRIVATE);
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
            btnLoginReplay.setEnabled(isNewLoginButtonEnabled(lllLoginReplayUid, lllLoginReplayRoomid, lllLoginReplayLiveid, lllLoginReplayName));
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    boolean isSuccessed = false;
    PublishInfo info;
    @OnClick(R2.id.btn_login_replay)
    public void onClick() {
        mLoadingPopup.show(mRoot);
        isSuccessed = false;
        DWLiveReplay.getInstance().setLoginParams(new DWLiveReplayLoginListener() {
            @Override
            public void onException(final DWLiveException exception) {
                isSuccessed = false;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toast(mContext, exception.getLocalizedMessage());
                        mLoadingPopup.dismiss();
                    }
                });
            }

            @Override
            public void onLogin(TemplateInfo templateInfo) {
                isSuccessed = true;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingPopup.dismiss();
                    }
                });

            }
        },false , lllLoginReplayUid.getText(), lllLoginReplayRoomid.getText(), lllLoginReplayLiveid.getText(), lllLoginReplayRecordid.getText(), lllLoginReplayName.getText(), lllLoginReplayPassword.getText());

        DWLiveReplay.getInstance().startLogin();
    }

    Map<String, String> map;

    @Override
    public void setLoginInfo(Map<String, String> map) {
        this.map = map;
        if (lllLoginReplayUid != null) {
            initEditTextInfo();
        }
    }

    private void initEditTextInfo() {
        if (map.containsKey(roomIdStr)) {
            lllLoginReplayRoomid.setText(map.get(roomIdStr));
        }

        if (map.containsKey(userIdStr)) {
            lllLoginReplayUid.setText(map.get(userIdStr));
        }

        if (map.containsKey(liveIdStr)) {
            lllLoginReplayLiveid.setText(map.get(liveIdStr));
        }

        if (map.containsKey(recordIdStr)) {
            lllLoginReplayRecordid.setText(map.get(recordIdStr));
        }
    }

    private void writeSharePreference() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid", lllLoginReplayUid.getText());
        editor.putString("roomid", lllLoginReplayRoomid.getText());
        editor.putString("liveid", lllLoginReplayLiveid.getText());
        editor.putString("recordid", lllLoginReplayRecordid.getText());
        editor.putString("username", lllLoginReplayName.getText());
        editor.putString("password", lllLoginReplayPassword.getText());
        editor.commit();
    }

    private void getSharePrefernce() {
        lllLoginReplayUid.setText(preferences.getString("uid", ""));
        lllLoginReplayRoomid.setText(preferences.getString("roomid", ""));
        lllLoginReplayLiveid.setText(preferences.getString("liveid", ""));
        lllLoginReplayRecordid.setText(preferences.getString("recordid", ""));
        lllLoginReplayName.setText(preferences.getString("username", ""));
        lllLoginReplayPassword.setText(preferences.getString("password", ""));
    }
}
