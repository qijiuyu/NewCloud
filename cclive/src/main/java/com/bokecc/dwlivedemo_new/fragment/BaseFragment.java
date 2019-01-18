package com.bokecc.dwlivedemo_new.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bokecc.dwlivedemo_new.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.bokecc.dwlivedemo_new.util.LoginUtil.isLoginButtonEnabled;
import static com.bokecc.dwlivedemo_new.util.LoginUtil.toast;

public abstract class BaseFragment extends Fragment {

    String userIdStr = "userid";  // 用户id
    String roomIdStr = "roomid";  // 房间id
    String liveIdStr = "liveid";  // 直播id
    String recordIdStr = "recordid";  // 回放id 手动录制参数 @since SDK 2.2.2 版本添加

    int nameMax = 20;


    public abstract void setLoginInfo(Map<String, String> map);

    Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
