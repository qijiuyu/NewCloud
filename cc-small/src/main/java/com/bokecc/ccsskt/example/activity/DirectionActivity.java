package com.bokecc.ccsskt.example.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.bokecc.ccsskt.example.CCApplication;
import com.bokecc.ccsskt.example.R;
import com.bokecc.ccsskt.example.R2;
import com.bokecc.ccsskt.example.base.TitleActivity;
import com.bokecc.ccsskt.example.base.TitleOptions;
import com.bokecc.ccsskt.example.entity.RoomDes;
import com.bokecc.ccsskt.example.popup.TxtLoadingPopup;
import com.bokecc.sskt.CCInteractSession;
import com.bokecc.sskt.bean.CCCityListSet;

import butterknife.OnClick;

import static com.bokecc.ccsskt.example.CCApplication.mAreaCode;
import static com.bokecc.ccsskt.example.global.Config.mNickName;
import static com.bokecc.ccsskt.example.global.Config.mPwd;
import static com.bokecc.sskt.CCInteractSession.TALKER;

public class DirectionActivity extends TitleActivity<DirectionActivity.DirectionViewHolder> {

    private static final String KEY_ROOM_ROLE = "direction_key_room_role";
    private static final String KEY_ROOM_USERID = "direction_key_room_userid";
    private static final String KEY_ROOM_ROOMID = "direction_key_room_roomid";
    private static final String KEY_ROOM_DESC = "direction_key_room_des";

    private static Intent newIntent(Context context, int role, String userid, String roomid, RoomDes roomDes) {
        Intent intent = new Intent(context, DirectionActivity.class);
        intent.putExtra(KEY_ROOM_ROLE, role);
        intent.putExtra(KEY_ROOM_USERID, userid);
        intent.putExtra(KEY_ROOM_ROOMID, roomid);
        intent.putExtra(KEY_ROOM_DESC, roomDes);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static void startSelf(Context context, int role, String userid, String roomid, RoomDes roomDes) {
        context.startActivity(newIntent(context, role, userid, roomid, roomDes));
    }

    private int mRole;
    private String mUserId, mRoomId;
    private RoomDes mRoomDes;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_direction;
    }

    @Override
    protected DirectionViewHolder getViewHolder(View contentView) {
        return new DirectionViewHolder(contentView);
    }

    @Override
    protected void onBindViewHolder(DirectionViewHolder holder) {
        initLoadingPopup();
        TitleOptions.Builder builder = new TitleOptions.Builder();
        TitleOptions options = builder.leftStatus(TitleOptions.VISIBLE).
                leftResId(R.drawable.title_back).
                rightStatus(TitleOptions.GONE).
                titleStatus(TitleOptions.VISIBLE).title("课堂模式").
                onTitleClickListener(new TitleOptions.OnLeftClickListener() {
                    @Override
                    public void onLeft() {
                        finish();
                    }
                }).
                build();
        setTitleOptions(options);

        mRole = getIntent().getExtras().getInt(KEY_ROOM_ROLE);
        mUserId = getIntent().getExtras().getString(KEY_ROOM_USERID);
        mRoomId = getIntent().getExtras().getString(KEY_ROOM_ROOMID);
        mRoomDes = (RoomDes) getIntent().getExtras().getSerializable(KEY_ROOM_DESC);
        initDispatch();
    }

    /**
     * 初始化城市列表
     */
    private void initDispatch() {
        mInteractSession.dispatch(mRoomId, mUserId, new CCInteractSession
                .AtlasCallBack<CCCityListSet>() {
            @Override
            public void onSuccess(final CCCityListSet ccCityInteractBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CCApplication.mFisrtCityName = ccCityInteractBean.getloc();
                        CCApplication.mAreaCode = ccCityInteractBean.getareacode();
                    }
                });
            }

            @Override
            public void onFailure(String err) {

            }
        });
    }

    final class DirectionViewHolder extends TitleActivity.ViewHolder {

        DirectionViewHolder(View view) {
            super(view);
        }

        @OnClick(R2.id.id_direction_class_h)
        void classHorizontal() {
            CCApplication.sClassDirection = 1;
            goValidateActivity();
        }

        @OnClick(R2.id.id_direction_class_v)
        void classVertical() {
            CCApplication.sClassDirection = 0;
            goValidateActivity();
        }

        private void goValidateActivity() {
            isNoPwd = mRole != CCInteractSession.PRESENTER && mRoomDes.getAuthtype() == 2;
            if (mNickName.isEmpty() || mPwd.isEmpty())
                ValidateActivity.startSelf(DirectionActivity.this, mRoomDes.getName(), mRoomDes.getDesc(), mRoomId, mUserId, mRole, isNoPwd);
            else goRoom();
        }

    }

    private boolean isNoPwd;
    private TxtLoadingPopup mLoading;
    public volatile boolean isJoin = false, isJump = false;

    private void initLoadingPopup() {
        mLoading = new TxtLoadingPopup(this);
        mLoading.setTipValue("正在登录");
        mLoading.setOutsideCancel(false);
        mLoading.setKeyBackCancel(false);
    }

    private void dismissLoadingOnUiThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoading.dismiss();
            }
        });
    }

    private void join() {
        mInteractSession.joinRoom(new CCInteractSession.AtlasCallBack<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                synchronized (DirectionActivity.this) {
                    isJoin = true;
                    if (CCApplication.isConnect && !isJump) {
                        isJump = true;
                        dismissLoadingOnUiThread();
                        if (mRole == CCInteractSession.PRESENTER) {
                            go(TeacherActivity.class);
                        } else {
                            go(StudentActivity.class);
                        }
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(String err) {
                dismissLoadingOnUiThread();
                toastOnUiThread(err);
            }
        });
    }

    private void saveUserMsg() {
        mSPUtil.put(mRoomId + "&" + mUserId + "&" + mRole + "-name", mNickName);
        if (!isNoPwd)
            mSPUtil.put(mRoomId + "&" + mUserId + "&" + mRole + "-password", mPwd);
    }

    void goRoom() {
        mLoading.show(mRoot);
        isJoin = false;
        isJump = false;
        mInteractSession.login(mRoomId,
                mUserId, mRole, mNickName, mPwd, mAreaCode, new CCInteractSession
                        .OnLoginStatusListener
                        () {
                    @Override
                    public void onSuccess() {
                        saveUserMsg();
                        join();
                    }

                    @Override
                    public void onFailed(String err) {
                        dismissLoadingOnUiThread();
                        toastOnUiThread(err);
                    }
                });
    }


}
