package com.bokecc.ccsskt.example.interact;

import com.bokecc.ccsskt.example.CCApplication;
import com.bokecc.ccsskt.example.entity.ChatEntity;
import com.bokecc.ccsskt.example.entity.MyEBEvent;
import com.bokecc.ccsskt.example.global.Config;
import com.bokecc.sskt.CCInteractSession;
import com.bokecc.sskt.SubscribeRemoteStream;
import com.bokecc.sskt.bean.ChatMsg;
import com.bokecc.sskt.bean.User;
import com.bokecc.sskt.bean.Vote;
import com.bokecc.sskt.bean.VoteResult;
import com.bokecc.sskt.doc.DocInfo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class InteractSessionManager {

    private EventBus mEventBus;
    private CCInteractSession mInteractSession;
    private static InteractSessionManager instance;

    public static InteractSessionManager getInstance() {
        if (instance == null) {
            synchronized (InteractSessionManager.class) {
                if (instance == null) {
                    instance = new InteractSessionManager();
                }
            }
        }
        return instance;
    }

    private InteractSessionManager() {
        mInteractSession = CCInteractSession.getInstance();
        addInteractListeners();
    }

    public void setEventBus(EventBus eventBus) {
        mEventBus = eventBus;
    }

    private CCInteractSession.OnChatListener mChatListener = new CCInteractSession.OnChatListener() {
        @Override
        public void onReceived(User from, ChatMsg msg, boolean self) {
            final ChatEntity chatEntity = new ChatEntity();
            chatEntity.setType(msg.getType());
            chatEntity.setUserId(from.getUserId());
            chatEntity.setUserName(from.getUserName());
            chatEntity.setMsg(msg.getMsg());
            chatEntity.setTime(msg.getTime());
            chatEntity.setSelf(self);
            chatEntity.setUserRole(from.getUserRole());
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_CHAT, chatEntity, self));
        }

        @Override
        public void onError(String err) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ERROR, err));
        }
    };

    private CCInteractSession.OnUserCountUpdateListener mUserCountUpdateListener = new CCInteractSession.OnUserCountUpdateListener() {

        @Override
        public void onUpdate(int classCount, int audienceCount) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_USER_COUNT, classCount, audienceCount));
        }
    };
    private CCInteractSession.OnUserListUpdateListener mUserListUpdateListener = new CCInteractSession.OnUserListUpdateListener() {
        @Override
        public void onUpdate(final ArrayList<User> users) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_USER_LIST, users));
        }
    };
    private CCInteractSession.OnGagOneListener mGagOneUpdateListener = new CCInteractSession.OnGagOneListener() {
        @Override
        public void onGagOne(String userid, boolean isAllowChat) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_USER_GAG, userid, isAllowChat));
        }
    };
    private CCInteractSession.OnAuthDrawListener mAuthDrawListener = new CCInteractSession.OnAuthDrawListener() {
        @Override
        public void onAuth(String userid, boolean isAllowDraw) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_AUTH_DRAW, userid, isAllowDraw));
        }
    };
    private CCInteractSession.OnSetupTeacherListener mSetupTeacher = new CCInteractSession.OnSetupTeacherListener() {

        @Override
        public void onSetupTeacher(String userid, boolean isAllowDraw) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_SETUP_THEACHER, userid, isAllowDraw));
        }

        @Override
        public void onChangePage(DocInfo mDocInfo, int position) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_SETUP_THEACHER_PAGE, mDocInfo,position));
        }

        @Override
        public void onDocChange(DocInfo mDocInfo,int position) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_DOC_CHANGE, mDocInfo,position));
        }
    };
    private CCInteractSession.OnAudioListener mAudioListener = new CCInteractSession.OnAudioListener() {
        @Override
        public void onAudio(String userid, boolean isAllowAudio, boolean isSelf) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_USER_AUDIO, userid, isAllowAudio, isSelf));
        }
    };
    private CCInteractSession.OnVideoListener mVideoListener = new CCInteractSession.OnVideoListener() {
        @Override
        public void onVideo(String userid, boolean isAllowVideo, boolean isSelf) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_USER_VIDEO, userid, isAllowVideo, isSelf));
        }
    };
    private CCInteractSession.OnGagAllListener mGagAllListener = new CCInteractSession.OnGagAllListener() {
        @Override
        public void onGag(boolean isAllowChat) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ALL_GAG, isAllowChat));
        }
    };
    private CCInteractSession.OnKickOutListener mKickOutListener = new CCInteractSession.OnKickOutListener() {
        @Override
        public void onKickOut() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_KICK_OUT));
        }
    };
    private CCInteractSession.OnQueueMaiUpdateListener mQueueMaiUpdateListener = new CCInteractSession.OnQueueMaiUpdateListener() {
        @Override
        public void onUpdate(ArrayList<User> users) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_QUEUE_MAI, users));
        }
    };
    private CCInteractSession.OnNotifyMaiStatusLisnter mNotifyMaiStatusLisnter = new CCInteractSession.OnNotifyMaiStatusLisnter() {
        @Override
        public void onUpMai(int oldStatus) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_UP_MAI, oldStatus));
        }

        @Override
        public void onDownMai() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_DOWN_MAI));
        }
    };
    private CCInteractSession.OnMediaModeUpdateListener mMediaModeUpdateListener = new CCInteractSession.OnMediaModeUpdateListener() {
        @Override
        public void onUpdate(@CCInteractSession.MediaMode int mode) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_UPDATE_MEDIA_MODE, mode));
        }
    };
    private CCInteractSession.OnLianmaiModeUpdateListener mLianmaiModeUpdateListener = new CCInteractSession.OnLianmaiModeUpdateListener() {
        @Override
        public void onUpdate(@CCInteractSession.LianmaiMode int mode) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_UPDATE_LIANMAI_MODE, mode));
        }
    };
    private CCInteractSession.OnNotifyStreamListener mNotifyStreamListener = new CCInteractSession.OnNotifyStreamListener() {
        @Override
        public void onStreamAdded(SubscribeRemoteStream remoteStream) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_STREAM_ADDED, remoteStream));
        }

        @Override
        public void onStreamRemoved(SubscribeRemoteStream remoteStream) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_STREAM_REMOVED, remoteStream));
        }

        @Override
        public void onStreamError() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_STREAM_ERROR));
        }
    };
    private CCInteractSession.OnReceiveNamedListener mStartRollCallListener = new CCInteractSession.OnReceiveNamedListener() {
        @Override
        public void onReceived(int namedTime) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_START_NAMED, namedTime));
        }
    };
    private CCInteractSession.OnStartNamedListener mRollCallListListener = new CCInteractSession.OnStartNamedListener() {
        @Override
        public void onStartNamedResult(boolean isAllow, ArrayList<String> ids) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ROLL_CALL_LIST, isAllow, ids));
        }
    };
    private CCInteractSession.OnAnswerNamedListener mAnswerRollCallListener = new CCInteractSession.OnAnswerNamedListener() {
        @Override
        public void onAnswered(String answerUserId, ArrayList<String> answerIds) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ANSWER_NAMED, answerUserId, answerIds));
        }
    };
    private CCInteractSession.OnServerListener mServerDisconnectListener = new CCInteractSession.OnServerListener() {
        @Override
        public void onDisconnect(int platform) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_SERVER_DISCONNECT, platform));
        }

        @Override
        public void onConnect() {
            CCApplication.isConnect = true;
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_SERVER_CONNECT));
        }

        @Override
        public void onReconnect() {

        }

        @Override
        public void onReconnecting() {

        }
    };
    private CCInteractSession.OnNotifyInviteListener mNotifyInviteListener = new CCInteractSession.OnNotifyInviteListener() {
        @Override
        public void onInvite() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_INVITE));
        }

        @Override
        public void onCancel() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_INVITE_CANCEL));
        }
    };
    private CCInteractSession.OnClassStatusListener mClassStatusListener = new CCInteractSession.OnClassStatusListener() {
        @Override
        public void onStart() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_CLASS_STATUS_START));
        }

        @Override
        public void onStop() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_CLASS_STATUS_STOP));
        }
    };
    private CCInteractSession.OnFollowUpdateListener mFollowUpdateListener = new CCInteractSession.OnFollowUpdateListener() {
        @Override
        public void onFollow(String userid) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_MAIN_VIDEO_FOLLOW, userid));
        }
    };
    private CCInteractSession.OnTemplateTypeUpdateListener mTemplateTypeUpdateListener = new CCInteractSession.OnTemplateTypeUpdateListener() {
        @Override
        public void onTemplateUpdate(@CCInteractSession.Template int template) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_TEMPLATE, template));
        }
    };
    private CCInteractSession.OnTeacherDownListener mTeacherDownListener = new CCInteractSession.OnTeacherDownListener() {
        @Override
        public void onTeacherDown() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_TEACHER_DOWN));
        }
    };
    private CCInteractSession.OnRoomTimerListener mRoomTimerListener = new CCInteractSession.OnRoomTimerListener() {
        @Override
        public void onTimer(long startTime, long lastTime) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ROOM_TIMER_START, startTime, lastTime));
        }

        @Override
        public void onStop() {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ROOM_TIMER_STOP));
        }
    };
    private CCInteractSession.OnRollCallListener mRollCallListener = new CCInteractSession.OnRollCallListener() {
        @Override
        public void onStart(Vote vote) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ROOM_ROLL_CALL_START, vote));
        }

        @Override
        public void onStop(String voteId) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ROOM_ROLL_CALL_STOP, voteId));
        }

        @Override
        public void onResult(VoteResult voteResult) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_ROOM_ROLL_CALL_RESULT, voteResult));
        }
    };
    private CCInteractSession.OnHandupListener mHandupListener = new CCInteractSession.OnHandupListener() {
        @Override
        public void onHandup(String userid, boolean isHandup) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_HANDUP, userid, isHandup));
        }
    };
    private CCInteractSession.OnLockListener mLockListener = new CCInteractSession.OnLockListener() {
        @Override
        public void onLock(String userid, boolean isLock) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_LOCK, userid, isLock));
        }
    };
    private CCInteractSession.OnRecivePublishError mRecivePublishError = new CCInteractSession.OnRecivePublishError() {
        @Override
        public void onError(String userid, String username) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_DEVICE_FAIL, userid, username));
        }
    };
    private CCInteractSession.OnInterludeMediaListener mInterludeMediaListener = new CCInteractSession.OnInterludeMediaListener() {
        @Override
        public void onInterlude(JSONObject object) {
            try {
                String handler = object.getString("handler");
                mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_INTERLUDE_MEDIA, object));
            } catch (Exception ignored) {}
        }
    };
    private CCInteractSession.OnTeacherSetupTeacherListener mTeacherSetupPageListener = new CCInteractSession.OnTeacherSetupTeacherListener() {
        @Override
        public void onTeacherSetupTeacher(int position) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_TEACHER_SETUPTHEACHER_FLAG, position));
        }
    };
    private CCInteractSession.OnVideoControlListener mVideoControlListener = new CCInteractSession.OnVideoControlListener() {

        @Override
        public void OnVideoControl(String userid,String type) {
            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_VIDEO_CONTROL, userid,type));
        }
    };
//    /**
//     * 流中断监听事件
//     */
//    private CCInteractSession.OnPublishBeakOffListener mOnInterruptPublishListener = new CCInteractSession.OnPublishBeakOffListener() {
//
//        @Override
//        public void onPublishBeakOff(JSONObject object) {
//            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_INTERRUPT_PUBLISH, object));
//        }
//
//        @Override
//        public void onNotifyPublish(String streamid, String userid) {
//            mEventBus.post(new MyEBEvent(Config.INTERACT_EVENT_WHAT_NOTIFY_PUBLISH,streamid,userid));
//        }
//    };

    private void addInteractListeners() {
        mInteractSession.setOnChatListener(mChatListener);
        mInteractSession.setOnUserCountUpdateListener(mUserCountUpdateListener);
        mInteractSession.setOnUserListUpdateListener(mUserListUpdateListener);
        mInteractSession.setOnGagAllListener(mGagAllListener);
        mInteractSession.setOnGagOneListener(mGagOneUpdateListener);
        mInteractSession.setOnAuthDrawListener(mAuthDrawListener);
        mInteractSession.setOnSetupTeacher(mSetupTeacher);
        mInteractSession.setOnTeacherSetupPageListener(mTeacherSetupPageListener);
        mInteractSession.setOnAudioListener(mAudioListener);
        mInteractSession.setOnVideoListener(mVideoListener);
        mInteractSession.setOnQueueMaiUpdateListener(mQueueMaiUpdateListener);
        mInteractSession.setOnNotifyMaiStatusLisnter(mNotifyMaiStatusLisnter);
        mInteractSession.setOnKickOutListener(mKickOutListener);
        mInteractSession.setOnMediaModeUpdateListener(mMediaModeUpdateListener);
        mInteractSession.setOnLianmaiModeUpdateListener(mLianmaiModeUpdateListener);
        mInteractSession.setOnNotifyStreamListener(mNotifyStreamListener);
        mInteractSession.setOnReceiveNamedListener(mStartRollCallListener);
        mInteractSession.setOnStartNamedListener(mRollCallListListener);
        mInteractSession.setOnAnswerNamedListener(mAnswerRollCallListener);
        mInteractSession.setOnServerListener(mServerDisconnectListener);
        mInteractSession.setOnNotifyInviteListener(mNotifyInviteListener);
        mInteractSession.setOnClassStatusListener(mClassStatusListener);
        mInteractSession.setOnFollowUpdateListener(mFollowUpdateListener);
        mInteractSession.setOnTemplateTypeUpdateListener(mTemplateTypeUpdateListener);
        mInteractSession.setOnTeacherDownListener(mTeacherDownListener);
        mInteractSession.setOnRoomTimerListener(mRoomTimerListener);
        mInteractSession.setOnRollCallListener(mRollCallListener);
        mInteractSession.setOnHandupListener(mHandupListener);
        mInteractSession.setOnLockListener(mLockListener);
        mInteractSession.setOnRecivePublishError(mRecivePublishError);
        mInteractSession.setOnInterludeMediaListener(mInterludeMediaListener);
        mInteractSession.setOnVideoControlListener(mVideoControlListener);
//        mInteractSession.setOnInterruptPublishListener(mOnInterruptPublishListener);
    }

    public void reset() {
        instance = null;
    }

}
