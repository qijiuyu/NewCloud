package com.bokecc.dwlivedemo_new.presenter;

import android.app.Activity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bokecc.dwlivedemo_new.base.presenter.BasePresenter;
import com.bokecc.dwlivedemo_new.contract.PushContract;
import com.bokecc.dwlivedemo_new.module.ChatEntity;
import com.bokecc.dwlivedemo_new.util.EmojiUtil;
import com.bokecc.sdk.mobile.push.chat.exception.ChatMsgIllegalException;
import com.bokecc.sdk.mobile.push.chat.listener.OnChatMsgListener;
import com.bokecc.sdk.mobile.push.chat.listener.OnChatRoomListener;
import com.bokecc.sdk.mobile.push.chat.model.ChatMsg;
import com.bokecc.sdk.mobile.push.chat.model.ChatUser;
import com.bokecc.sdk.mobile.push.core.DWPushConfig;
import com.bokecc.sdk.mobile.push.core.DWPushSession;
import com.bokecc.sdk.mobile.push.core.listener.DWOnPushStatusListener;
import com.bokecc.sdk.mobile.push.view.DWTextureView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class PushPresenter extends BasePresenter<PushContract.View> implements PushContract.Presenter {

    private DWPushSession mPushSession;

    private Timer mRoomTimer;
    // 当前直播间人数
    private int mCurCount = 0;

    public PushPresenter(final Activity context, PushContract.View view) {
        super(context, view);
        mPushSession = DWPushSession.getInstance();
        mPushSession.setBeautifulLevel(2, 8, 3);
        mPushSession.setOnChatRoomListener(new OnChatRoomListener() {
            @Override
            public void onRoomUserCountUpdate(int count) {
                if (mRoomTimer == null) {
                    return;
                }
                if (count != mCurCount) {
                    mView.updateRoomCount(count);
                    mCurCount = count;
                }
            }
        });
        mPushSession.setOnChatMsgListener(new OnChatMsgListener() {
            @Override
            public void onReceivedPublic(ChatUser from, ChatMsg msg, boolean isPublisher) {
                mView.updateChat(getChatEntity(from, null, msg, isPublisher));
            }

            @Override
            public void onReceivedPrivate(ChatUser from, ChatUser to, ChatMsg msg, boolean isPublisher) {
                mView.updatePrivateChat(getChatEntity(from, to, msg, isPublisher));
            }

            @Override
            public void onError(String errorMsg) {
                mView.toastOnUiThread(errorMsg);
            }
        });
    }

    @Override
    public void onResume() {
        mPushSession.onResume();
    }

    @Override
    public void onPause() {
        mPushSession.onPause();
    }

    @Override
    public void onDestory() {
        mPushSession.onDestory();
        if (mRoomTimer != null) {
            mRoomTimer.cancel();
            mRoomTimer = null;
        }
    }

    @Override
    public void setTextureView(DWTextureView textureView) {
        mPushSession.setTextureView(textureView);
    }

    @Override
    public void start(DWPushConfig pushConfig) {
        mView.showLoading();
        mPushSession.start(pushConfig, new DWOnPushStatusListener() {
            @Override
            public void onConfigMessage(String s) {

            }

            @Override
            public void onSuccessed() {
                mView.dismissLoading();
            }

            @Override
            public void onFailed(String s) {
                mView.dismissLoading();
                mView.toastOnUiThread(s);
                mView.finishSelf();
            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onReconnect() {
                mView.dismissLoading();
            }

            @Override
            public void onClosed(int action) {
                if (action == DWPushSession.RTMP_CLOSE_NO_HEART_BEAT_ACTION) {
                    mView.toastOnUiThread("心跳服务停止,连接关闭");
                    mView.finishSelf();
                }
            }
        });
    }

    @Override
    public void stop() {
        mPushSession.stop();
    }

    @Override
    public void swapCamera() {
        mPushSession.switchCamera();
    }

    @Override
    public void toggleVolume(boolean isMute) {
        if (isMute) {
            mPushSession.updateVolume(0);
        } else {
            mPushSession.updateVolume(1);
        }
    }

    @Override
    public void toggleBeauty(boolean isBeauty) {
        if (isBeauty) {
            mPushSession.openBeauty();
        } else {
            mPushSession.closeBeauty();
        }
    }

    private ChatEntity getChatEntity(ChatUser from, ChatUser to,
                                     ChatMsg msg, boolean isPublisher) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setUserId(from.getUserId());
        chatEntity.setUserName(from.getUserName());
        chatEntity.setPrivate((to != null));
        chatEntity.setReceiveUserId(to != null ? to.getUserId() : "");
        chatEntity.setReceivedUserName((to != null ? to.getUserName() : ""));
        chatEntity.setReceiveUserAvatar((to != null ? to.getUserAvatar() : ""));
        chatEntity.setPublisher(isPublisher);
        chatEntity.setMsg(msg.getMsg());
        chatEntity.setTime(msg.getTime());
        chatEntity.setUserAvatar(from.getUserAvatar());
        return chatEntity;
    }

    @Override
    public void sendChatMsg(String msg, ChatUser to) {
        if (TextUtils.isEmpty(msg.trim())) {
            mView.toastOnUiThread("禁止发送空消息!!!");
            return;
        }
        if (to == null) {
            sendChatMsgToAll(msg);
        } else {
            sendChatMsgToOne(to.getUserId(),
                    to.getUserName(), msg);
        }
        mView.clearChatInput();
    }

    @Override
    public void loopForChatCount() {
        if (mRoomTimer != null) {
            mRoomTimer.cancel();
            mRoomTimer = null;
        }
        mRoomTimer = new Timer();
        mRoomTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mPushSession != null) {
                    mPushSession.getRoomUserCount();
                }
            }
        }, 0, 5 * 1000);
    }

    private void sendChatMsgToAll(String msg) {
        try {
            mPushSession.sendChatMsgToAll(msg);
        } catch (ChatMsgIllegalException e) {
            Toast.makeText(mContext, e.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendChatMsgToOne(String userId, String userName, String msg) {
        try {
            mPushSession.sendMsgToOne(userId, userName, msg);
        } catch (ChatMsgIllegalException e) {
            Toast.makeText(mContext, e.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteInputOne(EditText mInput) {
        Editable editable = mInput.getText();
        int length = editable.length();
        if (length <= 0) {
            return;
        }
        int arrowPosition = mInput.getSelectionStart();
        if (arrowPosition == 0) {
            return;
        }
        String subString = editable.toString().substring(0, arrowPosition);
        if (subString.length() >= 8) {
            int imgIndex = subString.lastIndexOf("[em2_");

            if ((imgIndex + 8) == arrowPosition ) {
                if (EmojiUtil.pattern.matcher(editable.toString().substring(imgIndex, imgIndex+8)).find()) {
                    editable.delete(arrowPosition - 8, arrowPosition);
                } else {
                    editable.delete(arrowPosition - 1, arrowPosition);
                }
            } else {
                editable.delete(arrowPosition - 1, arrowPosition);
            }
        } else {
            editable.delete(arrowPosition - 1, arrowPosition);
        }
    }

    @Override
    public void addEmoji(EditText mInput, int position) {
        String emojiStr = EmojiUtil.imgNames[position];
        int index = mInput.getSelectionStart();
        String content = mInput.getText().toString();
        String pre = content.substring(0, index);
        String next = content.substring(index, content.length());
        SpannableString ss = new SpannableString(pre + emojiStr + next);
        mInput.setText(EmojiUtil.parseFaceMsg(mContext, ss));
        mInput.setSelection(index + emojiStr.length());
    }
}
