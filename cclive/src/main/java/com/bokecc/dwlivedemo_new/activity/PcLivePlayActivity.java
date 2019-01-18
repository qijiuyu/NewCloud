package com.bokecc.dwlivedemo_new.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.dwlivedemo_new.DWApplication;
import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.adapter.EmojiAdapter;
import com.bokecc.dwlivedemo_new.adapter.LivePublicChatAdapter;
import com.bokecc.dwlivedemo_new.adapter.LiveQaAdapter;
import com.bokecc.dwlivedemo_new.adapter.PrivateChatAdapter;
import com.bokecc.dwlivedemo_new.adapter.PrivateUserAdapter;
import com.bokecc.dwlivedemo_new.base.BaseActivity;
import com.bokecc.dwlivedemo_new.manage.AppRTCAudioManager;
import com.bokecc.dwlivedemo_new.manage.PcLiveLandscapeViewManager;
import com.bokecc.dwlivedemo_new.manage.PcLivePortraitViewManager;
import com.bokecc.dwlivedemo_new.module.ChatEntity;
import com.bokecc.dwlivedemo_new.module.PrivateUser;
import com.bokecc.dwlivedemo_new.popup.CommonPopup;
import com.bokecc.dwlivedemo_new.popup.ExeternalQuestionnairePopup;
import com.bokecc.dwlivedemo_new.popup.LotteryPopup;
import com.bokecc.dwlivedemo_new.popup.LotteryStartPopup;
import com.bokecc.dwlivedemo_new.popup.QuestionnairePopup;
import com.bokecc.dwlivedemo_new.popup.QuestionnaireStopPopup;
import com.bokecc.dwlivedemo_new.popup.RollCallPopup;
import com.bokecc.dwlivedemo_new.popup.RtcPopup;
import com.bokecc.dwlivedemo_new.popup.VotePopup;
import com.bokecc.dwlivedemo_new.recycle.BaseOnItemTouch;
import com.bokecc.dwlivedemo_new.recycle.OnClickListener;
import com.bokecc.dwlivedemo_new.util.EmojiUtil;
import com.bokecc.dwlivedemo_new.util.SoftKeyBoardState;
import com.bokecc.dwlivedemo_new.view.BarrageLayout;
import com.bokecc.dwlivedemo_new.view.LiveFloatingView;
import com.bokecc.dwlivedemo_new.view.MixedTextView;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveListener;
import com.bokecc.sdk.mobile.live.DWLivePlayer;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.Answer;
import com.bokecc.sdk.mobile.live.pojo.ChatMessage;
import com.bokecc.sdk.mobile.live.pojo.PrivateChatInfo;
import com.bokecc.sdk.mobile.live.pojo.QualityInfo;
import com.bokecc.sdk.mobile.live.pojo.Question;
import com.bokecc.sdk.mobile.live.pojo.QuestionnaireInfo;
import com.bokecc.sdk.mobile.live.rtc.RtcClient;
import com.bokecc.sdk.mobile.live.widget.DocView;
import com.bokecc.sdk.mobile.push.chat.model.ChatUser;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.EglBase;
import org.webrtc.SurfaceViewRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 直播界面
 * Created by liufh on 2016/12/8.
 */
public class PcLivePlayActivity extends BaseActivity implements TextureView.SurfaceTextureListener,
        IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnVideoSizeChangedListener {

    @BindView(R2.id.pc_live_main)
    LinearLayout pc_live_main;

    @BindView(R2.id.textureview_pc_live_play)
    TextureView mPlayerContainer;

    @BindView(R2.id.bl_pc_barrage)
    BarrageLayout blPcBarrage;

    @BindView(R2.id.rl_pc_live_top_layout)
    RelativeLayout rlLiveTopLayout;

    @BindView(R2.id.rl_pc_landscape_layout)
    RelativeLayout rlLandscapeLayout;

    @BindView(R2.id.rl_pc_portrait_layout)
    RelativeLayout rlPortraitLayout;

    @BindView(R2.id.rl_sound_layout)
    RelativeLayout rlSoundLayout;

    @BindView(R2.id.pc_live_infos_layout)
    RelativeLayout rlLiveInfosLayout;

    @BindView(R2.id.tv_living)
    TextView livingSign;

    // 连麦使用的布局
    @BindView(R2.id.svr_local_render)
    SurfaceViewRenderer localRender;

    @BindView(R2.id.svr_remote_render)
    SurfaceViewRenderer remoteRender;

    @BindView(R2.id.tv_pc_portrait_prepare)
    TextView tvPcPortraitStatusTips;

    @BindView(R2.id.pc_portrait_progressBar)
    ProgressBar pcPortraitProgressBar;

    // 直播视频悬浮窗
    LiveFloatingView floatingView;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 进入竖屏视频模式
            rlLiveTopLayout.setVisibility(View.VISIBLE);
            tagRadioGroup.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setPortraitLayoutVisibility(View.VISIBLE);
            rlLandscapeLayout.setVisibility(View.GONE);
            pcLivePortraitViewManager.setScreenVisible(true, true);
            mPlayerContainer.setLayoutParams(getVideoSizeParams());
            remoteRender.setLayoutParams(getVideoSizeParams());

            if (inDocFullMode) {
                // 判断之前是否在文档全屏模式则退出，并恢复竖屏展示状态
                if (floatingView != null) {
                    floatingView.removeView();
                    pc_live_main.addView(rlLiveTopLayout, 0);
                    rlLiveTopLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
                    floatingView.quit();
                    floatingView = null;
                }

                dwLive.docApplyNewConfig(newConfig);
                inDocFullMode = false;
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (toDocFullMode) {
                // 采取尝试弹窗的方式 -- 因为：ContextCompat.checkSelfPermission 直接检测存在机型问题
                try {
                    pc_live_main.removeView(rlLiveTopLayout);
                    floatingView = new LiveFloatingView(PcLivePlayActivity.this, rlLiveTopLayout);
                } catch (Exception e) {
                    // 将悬浮窗的弹出时的崩溃捕获，并展示之前的文档全屏效果，不做视频的全屏展示
                    pc_live_main.addView(rlLiveTopLayout, 0);
                    rlLiveTopLayout.setVisibility(View.GONE);
                }
                rlLandscapeLayout.setVisibility(View.GONE);
                rlPortraitLayout.setVisibility(View.GONE);
                rlLiveInfosLayout.setVisibility(View.VISIBLE);
                tagRadioGroup.setVisibility(View.GONE);
                dwLive.docApplyNewConfig(newConfig);
                toDocFullMode = false;
                inDocFullMode = true;
            } else {
                setPortraitLayoutVisibility(View.GONE);
                rlLandscapeLayout.setVisibility(View.VISIBLE);
                pcLiveLandscapeViewManager.setScreenVisible(true, true);
                mPlayerContainer.setLayoutParams(getVideoSizeParams());
                remoteRender.setLayoutParams(getVideoSizeParams());
            }
        }

        blPcBarrage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        blPcBarrage.init();
    }


    public void setRlSoundLayout(int i) {
        rlSoundLayout.setVisibility(i);
    }

    @OnClick(R2.id.rl_pc_live_top_layout)
    void onPlayOnClick(View v) {
        if (isPortrait()) {
            pcLivePortraitViewManager.onPlayClick();
        } else {
            pcLiveLandscapeViewManager.OnPlayClick();
        }
    }

    private CommonPopup mExitPopup; // 退出界面弹出框
    private LotteryStartPopup mLotteryStartPopup; // 开始抽奖弹出框
    private LotteryPopup mLotteryPopup;  // 抽奖结果弹出框
    private RollCallPopup mRollcallPopup;
    private VotePopup mVotePopup;
    private QuestionnairePopup mQuestionnairePopup;  // 问卷弹出界面
    private QuestionnaireStopPopup mQuestionnaireStopPopup; // 问卷结束弹出界面
    private ExeternalQuestionnairePopup mExeternalQuestionnairePopup; // 第三方问卷弹出界面

    private View mRoot;
    private DWLivePlayer player;
    private DWLive dwLive = DWLive.getInstance();

    private WindowManager wm;

    private boolean hasLoadedHistoryChat; // 是否加载过了历史聊天

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pc_live;
    }

    PcLivePortraitViewManager pcLivePortraitViewManager;
    PcLiveLandscapeViewManager pcLiveLandscapeViewManager;
    RtcPopup rtcPopup;

    @Override
    protected void onViewCreated() {
        hasLoadedHistoryChat = false;
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mRoot = getWindow().getDecorView().findViewById(android.R.id.content);
        // 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        onSoftInputChange();

        rtcPopup = new RtcPopup(this);

        pcLiveLandscapeViewManager = new PcLiveLandscapeViewManager(this, rlLandscapeLayout, mRoot, livingSign, rtcPopup, mImm);
        pcLiveLandscapeViewManager.init();

        pcLivePortraitViewManager = new PcLivePortraitViewManager(this, rlLiveTopLayout, mRoot, livingSign, rtcPopup, mImm);
        pcLivePortraitViewManager.init();

        initClosePopup();

        initLotteryPopup();

        initRollcallPopup();

        initVotePopup();

        initQuestionnairePopup();

        initQuestionnaireStopPopup();

        initViewPager();

        initPlayer();

        blPcBarrage.start();
    }

    private InputMethodManager mImm;

    // 软键盘监听
    private SoftKeyBoardState mSoftKeyBoardState;

    private void onSoftInputChange() {
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mSoftKeyBoardState = new SoftKeyBoardState(mRoot, false);
        mSoftKeyBoardState.setOnSoftKeyBoardStateChangeListener(new SoftKeyBoardState.OnSoftKeyBoardStateChangeListener() {
            @Override
            public void onChange(boolean isShow) {
                pcLiveLandscapeViewManager.onSoftKeyChange(isShow);
            }
        });
    }

    private void initClosePopup() {
        mExitPopup = new CommonPopup(this);
        mExitPopup.setOutsideCancel(true);
        mExitPopup.setKeyBackCancel(true);
        mExitPopup.setTip("您确认结束观看吗?");
        mExitPopup.setOKClickListener(new CommonPopup.OnOKClickListener() {
            @Override
            public void onClick() {
                //TODO
                finish();
            }
        });

    }

    private void initLotteryPopup() {
        mLotteryStartPopup = new LotteryStartPopup(this);
        mLotteryStartPopup.setKeyBackCancel(true);
        mLotteryPopup = new LotteryPopup(this);
        mLotteryPopup.setKeyBackCancel(true);
    }

    private void initRollcallPopup() {
        mRollcallPopup = new RollCallPopup(this);
    }

    private void initVotePopup() {
        mVotePopup = new VotePopup(this);
    }

    private void initQuestionnairePopup() {
        mQuestionnairePopup = new QuestionnairePopup(this);
        mExeternalQuestionnairePopup = new ExeternalQuestionnairePopup(this);
    }

    private void initQuestionnaireStopPopup() {
        mQuestionnaireStopPopup = new QuestionnaireStopPopup(this);
        mQuestionnaireStopPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mQuestionnairePopup != null) {
                    mQuestionnairePopup.dismiss();
                }
            }
        });
    }

    private void initPlayer() {
        mPlayerContainer.setSurfaceTextureListener(this);
        player = new DWLivePlayer(this);
        player.setOnPreparedListener(this);
        player.setOnVideoSizeChangedListener(this);
        dwLive.setDWLivePlayParams(myDWLiveListener, this, docView, player);
        initRtc();
    }

    //-----------------------rtc连麦------------------------
    private void initRtc() {
        EglBase rootEglBase = EglBase.create();
        localRender.init(rootEglBase.getEglBaseContext(), null);
        remoteRender.init(rootEglBase.getEglBaseContext(), null);

        localRender.setMirror(true);
        localRender.setZOrderMediaOverlay(true); // 设置让本地摄像头置于最顶层

        dwLive.setRtcClientParameters(rtcClientListener, localRender, remoteRender);
    }

    public boolean isRtc = false; // TODO 回头看一下，是否需要删除掉

    public void onApplyRtc() {

        if (!isNetworkConnected()) {
            Toast.makeText(getApplicationContext(), "没有网络，请检查", Toast.LENGTH_SHORT).show();
            return;
        }

        isRtc = true;
        livingSign.setVisibility(View.INVISIBLE);
        // 可选择音频连麦or视频连麦
        if (DWApplication.RTC_AUDIO) {
            dwLive.startVoiceRTCConnect();
        } else {
            dwLive.startRtcConnect();
        }
    }

    public void onCancelRtc() {
        dwLive.disConnectApplySpeak();
        hideSpeak();
    }

    public void onHangupRtc() {
        dwLive.disConnectSpeak();
        hideSpeak();

    }


    // 远程视频的宽高
    private int[] mVideoSizes = new int[2];

    public boolean isSpeaking = false;

    private void hideVideoRenderAndTips() {
        if (localRender != null) {
            localRender.setVisibility(View.GONE);
        }

        if (remoteRender != null) {
            remoteRender.setVisibility(View.GONE);
        }
    }

    public boolean isAllowRtc = false;
    private RtcClient.RtcClientListener rtcClientListener = new RtcClient.RtcClientListener() {
        @Override
        public void onAllowSpeakStatus(final boolean isAllowSpeak) {

            isAllowRtc = isAllowSpeak;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isAllowSpeak && isSpeaking) {
                        return;
                    }

                    hideVideoRenderAndTips();
                    rtcPopup.resetView();
                    if (!isAllowSpeak) {
                        isSpeaking = false;
                        isRtc = false;
                        mPlayerContainer.setVisibility(View.VISIBLE);
                        reloadVideo();
                        rtcPopup.dismiss();

                        pcLivePortraitViewManager.showNormalRtcIcon();
                        pcLiveLandscapeViewManager.showNormalRtcIcon();

                    }
                }
            });
        }

        AppRTCAudioManager mAudioManager;

        private void processRemoteVideoSize(String videoSize) {
            String[] sizes = videoSize.split("x");
            int width = Integer.parseInt(sizes[0]);
            int height = Integer.parseInt(sizes[1]);
            double ratio = (double) width / (double) height;
            // 对于分辨率为16：9的，更改默认分辨率为16：10
            if (ratio > 1.76 && ratio < 1.79) {
                mVideoSizes[0] = 1600;
                mVideoSizes[1] = 1000;
            }
        }

        @Override
        public void onEnterSpeak(final String videoSize) {

            processRemoteVideoSize(videoSize);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isSpeaking) {
                        return;
                    }

                    // 根据连麦模式执行不同的UI处理
                    if (DWApplication.RTC_AUDIO) {
                        player.setVolume(0f, 0f);
                    } else {
                        player.pause();
                        player.stop();
                        mPlayerContainer.setVisibility(View.INVISIBLE);

                        localRender.setVisibility(View.INVISIBLE);
                        remoteRender.setVisibility(View.VISIBLE);
                        remoteRender.setLayoutParams(getRemoteRenderSizeParams());
                    }

                    // 由于rtc是走的通话音频，所以需要做处理
                    mAudioManager = AppRTCAudioManager.create(PcLivePlayActivity.this, null);
                    mAudioManager.init();
                    setPlayControllerVisible(false);
                    isSpeaking = true;

                    //设置为视频模式
                    if (!isVideo) {
                        isVideo = true;
                        dwLive.setDefaultPlayMode(DWLive.PlayMode.VIDEO);
                        setRlSoundLayout(View.INVISIBLE);
                        changeVideoAudioIcon();
                    }

                    dwLive.removeLocalRender();

                    rtcPopup.showConnectedView();

                    startCmTimer();
                }
            });

        }

        @Override
        public void onDisconnectSpeak() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mAudioManager != null) {
                        mAudioManager.close();
                    }

                    hideSpeak();
                }
            });
        }

        @Override
        public void onSpeakError(final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    if (mAudioManager != null) {
                        mAudioManager.close();
                    }
                    hideSpeak();
                }
            });
        }

        @Override
        public void onCameraOpen(final int width, final int height) {
        }
    };


    private void showNorRtcIcon() {
        pcLivePortraitViewManager.showNormalRtcIcon();
        pcLiveLandscapeViewManager.showNormalRtcIcon();
    }

    private Timer cmTimer;
    private TimerTask cmTimerTask;

    // 增加一个间隔为1s的定时器，如果断网，则增加一个10s的延时器，超过10s，重置dwlive
    private void startCmTimer() {
        cmCount = 0;

        if (cmTimer == null) {
            cmTimer = new Timer();
        }

        if (cmTimerTask != null) {
            cmTimerTask.cancel();
        }

        cmTimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rtcPopup.setCounterText(cmCount++);
                        if (!isNetworkConnected()) {
                            start10sTimerTask();
                        } else {
                            cancel10sTimerTask();
                        }
                    }
                });
            }
        };

        cmTimer.schedule(cmTimerTask, 0, 1000);
    }

    /**
     * 检测网络是否可用
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isAvailable();
    }

    private int cmCount;

    private void stopCmTimer() {

        if (cmTimerTask != null) {
            cmTimerTask.cancel();
        }
    }

    private TimerTask cm10sTimerTask;

    private void start10sTimerTask() {
        if (cm10sTimerTask != null) {
            return;
        }

        cm10sTimerTask = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dwLive.disConnectSpeak();
                        hideSpeak();
                        stopCmTimer();
                        cancel10sTimerTask();
                        rtcPopup.showNoNetworkView();
                    }
                });
            }
        };

        cmTimer.schedule(cm10sTimerTask, 10 * 1000);
    }

    private void cancel10sTimerTask() {
        if (cm10sTimerTask != null) {
            cm10sTimerTask.cancel();
            cm10sTimerTask = null;
        }

    }

    private void hideSpeak() {
        if (isRtc || isSpeaking) {
            dwLive.closeCamera();
            hideVideoRenderAndTips();
            mPlayerContainer.setVisibility(View.VISIBLE);
            isRtc = false;
            isSpeaking = false;
            stopCmTimer();

            rtcPopup.resetView();
            showNorRtcIcon();

            reloadVideo();
        }
    }

    private void reloadVideo() {
        if (player.isPlaying() || !isPrepared) {
            // 播放到下一个关键帧的时候，声音就会恢复
            if (DWApplication.RTC_AUDIO) {
                player.setVolume(1.0f, 1.0f);
            }
            return;
        }

        try {
            dwLive.restartVideo(surface);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DWLiveException e) {
            e.printStackTrace();
        }
    }


    void setPlayControllerVisible(boolean isVisible) {

    }

    // 连麦远端视频组件等比缩放
    private RelativeLayout.LayoutParams getRemoteRenderSizeParams() {
        int width = 600;
        int height = 400;

        if (isPortrait()) {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight() / 3; //TODO 根据当前布局更改
        } else {
            width = wm.getDefaultDisplay().getWidth();
            height = wm.getDefaultDisplay().getHeight();
        }

        int vWidth = mVideoSizes[0];
        int vHeight = mVideoSizes[1];

        if (vWidth == 0) {
            vWidth = 600;
        }
        if (vHeight == 0) {
            vHeight = 400;
        }

        if (vWidth > width || vHeight > height) {
            float wRatio = (float) vWidth / (float) width;
            float hRatio = (float) vHeight / (float) height;
            float ratio = Math.max(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth / ratio);
            height = (int) Math.ceil((float) vHeight / ratio);
        } else {
            float wRatio = (float) width / (float) vWidth;
            float hRatio = (float) height / (float) vHeight;
            float ratio = Math.min(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth * ratio);
            height = (int) Math.ceil((float) vHeight * ratio);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    public void showClosePopupWindow() {
        mExitPopup.show(mRoot);
    }


    private ChatEntity getChatEntity(ChatMessage msg) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setUserId(msg.getUserId());
        chatEntity.setUserName(msg.getUserName());
        chatEntity.setPrivate(!msg.isPublic());

        if (msg.getUserId().equals(dwLive.getViewer().getId())) {
            chatEntity.setPublisher(true);
        } else {
            chatEntity.setPublisher(false);
        }

        chatEntity.setMsg(msg.getMessage());
        chatEntity.setTime(msg.getTime());
        chatEntity.setUserAvatar(msg.getAvatar());
        return chatEntity;
    }

    private ChatEntity getChatEntity(PrivateChatInfo info, boolean isPublisher) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setUserId(info.getFromUserId());
        chatEntity.setUserName(info.getFromUserName());
        chatEntity.setPrivate(true);
        chatEntity.setReceiveUserId(info.getToUserId());

        if (info.getToUserName() == null && userInfoMap.containsKey(info.getToUserId())) {
            info.setToUserName(userInfoMap.get(info.getToUserId()));
        }
        chatEntity.setReceivedUserName(info.getToUserName());
        chatEntity.setReceiveUserAvatar(""); //TODO
        chatEntity.setPublisher(isPublisher);
        chatEntity.setMsg(info.getMsg());
        chatEntity.setTime(info.getTime());
        chatEntity.setUserAvatar("");
        return chatEntity;
    }

    private DWLiveListener myDWLiveListener = new DWLiveListener() {
        @Override
        public void onQuestion(final Question question) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (qaLayoutController != null) {
                        qaLayoutController.addQuestion(question);
                    }
                }
            });
        }

        @Override
        public void onPublishQuestion(final String questionId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (qaLayoutController != null) {
                        qaLayoutController.showQuestion(questionId);
                    }
                }
            });
        }

        @Override
        public void onAnswer(final Answer answer) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (qaLayoutController != null) {
                        qaLayoutController.addAnswer(answer);
                    }
                }
            });
        }

        @Override
        public void onLiveStatus(final DWLive.PlayStatus playStatus) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (playStatus) {
                        case PLAYING:
                            hideVideoRenderAndTips();
                            pcPortraitProgressBar.setVisibility(View.VISIBLE);
                            tvPcPortraitStatusTips.setVisibility(View.GONE);
                            if (docView != null) {
                                docView.setVisibility(View.VISIBLE);
                            }

                            break;
                        case PREPARING:
                            pcPortraitProgressBar.setVisibility(View.GONE);
                            tvPcPortraitStatusTips.setVisibility(View.VISIBLE);
                            tvPcPortraitStatusTips.setText("直播尚未开始！");
                            break;
                    }

                }
            });

        }

        @Override
        public void onStreamEnd(boolean b) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (docView != null) {
                        docView.clearDrawInfo();
                        docView.setVisibility(View.GONE);
                    }

                    isAllowRtc = false;

                    player.pause();
                    player.stop();
                    player.reset();

                    pcPortraitProgressBar.setVisibility(View.GONE);
                    tvPcPortraitStatusTips.setVisibility(View.VISIBLE);
                    tvPcPortraitStatusTips.setText("直播已结束！");
                }
            });

        }

        @Override
        public void onHistoryChatMessage(final ArrayList<ChatMessage> chatLogs) {

            // 如果之前已经加载过了历史聊天信息，就不再接收
            if (hasLoadedHistoryChat) {
                return;
            }

            // 历史聊天信息
            if (chatLogs == null || chatLogs.size() == 0) {
                Log.e("onHistoryChatMessage", "无历史聊天信息");
                return;
            }

            hasLoadedHistoryChat = true;
            // 注：历史聊天信息中 ChatMessage 的 currentTime = ""
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 将历史聊天信息添加到UI
                    for (int i = 0; i < chatLogs.size(); i++) {
                        blPcBarrage.addNewInfo(chatLogs.get(i).getMessage());
                        if (chatLayoutController != null) {
                            chatLayoutController.addChatEntity(getChatEntity(chatLogs.get(i)));
                        }
                        userInfoMap.put(chatLogs.get(i).getUserId(), chatLogs.get(i).getUserName());
                    }
                }
            });
        }

        @Override
        public void onPublicChatMessage(final ChatMessage chatMessage) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    blPcBarrage.addNewInfo(chatMessage.getMessage());

                    if (chatLayoutController != null) {
                        chatLayoutController.addChatEntity(getChatEntity(chatMessage));
                    }

                    userInfoMap.put(chatMessage.getUserId(), chatMessage.getUserName());

                }
            });
        }

        @Override
        public void onSilenceUserChatMessage(final ChatMessage chatMessage) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    blPcBarrage.addNewInfo(chatMessage.getMessage());

                    if (chatLayoutController != null) {
                        chatLayoutController.addChatEntity(getChatEntity(chatMessage));
                    }

                }
            });
        }

        @Override
        public void onPrivateQuestionChatMessage(ChatMessage chatMessage) {

        }

        @Override
        public void onPrivateAnswerChatMessage(ChatMessage chatMessage) {

        }

        @Override
        public void onPrivateChat(final PrivateChatInfo info) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatLayoutController.updatePrivateChat(getChatEntity(info, false));
                }
            });
        }

        @Override
        public void onPrivateChatSelf(final PrivateChatInfo info) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatLayoutController.updatePrivateChat(getChatEntity(info, true));
                }
            });
        }

        @Override
        public void onUserCountMessage(final int i) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pcLiveLandscapeViewManager.onUserCountMsg(i);
                    pcLivePortraitViewManager.onUserCountMsg(i);
                }
            });
        }

        @Override
        public void onNotification(String s) {
            Log.e("onNotification", s);
        }

        // 接收到广播信息
        @Override
        public void onBroadcastMsg(final String msg) {

            // 判断空
            if (msg == null || msg.isEmpty()) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (chatLayoutController != null) {
                        // 构建一个对象
                        ChatEntity chatEntity = new ChatEntity();
                        chatEntity.setUserId("");
                        chatEntity.setUserName("");
                        chatEntity.setPrivate(false);
                        chatEntity.setPublisher(true);
                        chatEntity.setMsg("系统消息: " + msg);
                        chatEntity.setTime("");
                        chatEntity.setUserAvatar("");
                        chatLayoutController.addChatEntity(chatEntity);
                    }
                }
            });
        }

        @Override
        public void onInformation(final String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(PcLivePlayActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onException(DWLiveException e) {

        }

        @Override
        public void onInitFinished(final int i, final List<QualityInfo> list) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pcLiveLandscapeViewManager.onInitFinish(i, list, surface);
                    pcLivePortraitViewManager.onInitFinish(i, list, surface);
                }
            });

        }

        @Override
        public void onKickOut() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "您已被踢出直播间", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

        @Override
        public void onLivePlayedTime(int i) {
        }

        @Override
        public void onLivePlayedTimeException(Exception e) {
        }

        @Override
        public void isPlayedBack(boolean b) {
        }

        @Override
        public void onStatisticsParams(Map<String, String> map) {
        }

        @Override
        public void onCustomMessage(String s) {

        }

        @Override
        public void onBanStream(String reason) {

        }

        @Override
        public void onUnbanStream() {

        }

        @Override
        public void onAnnouncement(final boolean isRemove, final String announcement) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (pcLivePortraitViewManager.isAnnouncementShown() || pcLiveLandscapeViewManager.isAnnouncementShown()) {
                        pcLiveLandscapeViewManager.onNewAnnounce(isRemove, announcement, true);
                        pcLivePortraitViewManager.onNewAnnounce(isRemove, announcement, true);
                    } else {
                        pcLiveLandscapeViewManager.onNewAnnounce(isRemove, announcement, false);
                        pcLivePortraitViewManager.onNewAnnounce(isRemove, announcement, false);
                    }

                }
            });
        }

        @Override
        public void onRollCall(final int duration) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mRollcallPopup != null) {
                        mRollcallPopup.show(mRoot);
                        mRollcallPopup.startRollcall(duration);
                    }
                }
            });
        }

        boolean isLotteryWin = false;
        @Override
        public void onStartLottery(String lotteryId) {
            isLotteryWin = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLotteryStartPopup.show(mRoot);
                    mLotteryStartPopup.startLottery();
                }
            });

        }

        @Override
        public void onLotteryResult(final boolean isWin, final String lotteryCode, final String lotteryId, final String winnerName) {

            // 如果已经中奖了，而且中奖界面没有关闭，则不做后续的界面处理
            if (isLotteryWin && mLotteryPopup.isShowing()) {
                return;
            }

            this.isLotteryWin = isWin;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLotteryPopup.show(mRoot);
                    mLotteryPopup.onLotteryResult(isWin, lotteryCode, winnerName);

                    if (!isLotteryWin) {
                        handler.postDelayed(dismissLottery, lotteryDelay);
                    }
                }
            });
        }

        @Override
        public void onStopLottery(String lotteryId) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLotteryStartPopup != null && mLotteryStartPopup.isShowing()) {
                        mLotteryStartPopup.dismiss();
                    }

                    if (!isLotteryWin) {
                        handler.postDelayed(dismissLottery, lotteryDelay);
                    }
                }
            });
        }

        boolean isVoteResultShow = false;
        @Override
        public void onVoteStart(final int voteCount, final int VoteType) {
            isVoteResultShow = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mVotePopup.startVote(voteCount, VoteType);
                    mVotePopup.show(mRoot);
                }
            });

        }

        @Override
        public void onVoteStop() {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isVoteResultShow) {
                        return;
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVotePopup.dismiss();
                            }
                        });
                    }
                }
            }, 1000);
        }

        @Override
        public void onVoteResult(final JSONObject jsonObject) {
            isVoteResultShow = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mVotePopup.onVoteResult(jsonObject);
                    mVotePopup.show(mRoot);
                }
            });
        }

        @Override
        public void onQuestionnairePublish(final QuestionnaireInfo info) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mQuestionnairePopup.setQuestionnaireInfo(info);
                    mQuestionnairePopup.show(mRoot);
                }
            });
        }

        public void onQuestionnaireStop(final String questionnaireId) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mQuestionnairePopup != null && mQuestionnairePopup.isShowing()) {
                        if (!mQuestionnairePopup.hasSubmitedQuestionnaire()) {
                            mQuestionnaireStopPopup.show(mRoot);
                        }
                    }
                }
            });
        }

        public void onExeternalQuestionnairePublish(final String title, final String externalUrl) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mExeternalQuestionnairePopup != null) {
                        mExeternalQuestionnairePopup.setQuestionnaireInfo(title, externalUrl);
                        mExeternalQuestionnairePopup.show(mRoot);
                    }
                }
            });
        }
    };

    int lotteryDelay = 3 * 1000;

    Runnable dismissLottery = new Runnable() {
        @Override
        public void run() {
            if (mLotteryPopup != null && mLotteryPopup.isShowing()) {
                mLotteryPopup.dismiss();
            }
        }
    };

    @Override
    protected void onDestroy() {
        pcLiveLandscapeViewManager.onDestroy();

        handler.removeCallbacks(dismissLottery);

        if (qaLayoutController != null) {
            qaLayoutController.removeTipsHideCallBack();
        }

        if (mRollcallPopup != null) {
            mRollcallPopup.onDestroy();
        }

        if (player != null) {
            player.pause();
            player.stop();
            player.release();
        }

        localRender.release();
        remoteRender.release();

        if (mSoftKeyBoardState != null) {
            mSoftKeyBoardState.release();
        }

        cancel10sTimerTask();
        stopCmTimer();

        dwLive.onDestroy();

        super.onDestroy();
    }


    boolean isOnPause = false;

    @Override
    protected void onPause() {

        isPrepared = false;
        isOnPause = true;

        // 如果当前存在悬浮窗，就退出悬浮窗
        if (floatingView != null) {
            floatingView.removeView();
            pc_live_main.addView(rlLiveTopLayout, 0);
            rlLiveTopLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            floatingView.quit();
            floatingView = null;
        }

        if (player != null && player.isPlaying()) {
            player.pause();
        }

        hideSpeak();

        if (qaLayoutController != null) {
            qaLayoutController.clearQaInfo();
        }


        pcLiveLandscapeViewManager.onPause();

        dwLive.stop();

        mRollcallPopup.dismissImmediate();

        super.onPause();
    }

    boolean isOnResumeStart = false;

    @Override
    protected void onResume() {
        super.onResume();

        // 判断是否在文档全屏模式下，如果在，就退出全屏模式，触发重新拉流的操作
        if (inDocFullMode) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        isOnResumeStart = false;
        if (surface != null) {
            dwLive.start(surface);
            isOnResumeStart = true;
        }
    }

    Surface surface;

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        surface = new Surface(surfaceTexture);
        if (isOnResumeStart) {
            return;
        }

        if (player.isPlaying()) {
            player.setSurface(surface);
        } else {
            dwLive.start(surface);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        surface = null;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    boolean isPrepared = false;

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        isPrepared = true;
        player.start();

        pcPortraitProgressBar.setVisibility(View.GONE);
        tvPcPortraitStatusTips.setVisibility(View.GONE);

        if (rtcPopup.isShow()) {
            return;
        }

        if (isPortrait()) {
            setPortraitLayoutVisibility(View.VISIBLE);
        } else {
            rlLandscapeLayout.setVisibility(View.VISIBLE);
            pcLiveLandscapeViewManager.setScreenVisible(true, false);
        }
    }

    private void setPortraitLayoutVisibility(int i) {
        rlPortraitLayout.setVisibility(i);
        rlLiveInfosLayout.setVisibility(i);
        pcLivePortraitViewManager.setScreenVisible(true, false);
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {

        if (width == 0 || height == 0) {
            return;
        }
        mPlayerContainer.setLayoutParams(getVideoSizeParams());
    }

    // 视频等比缩放
    private RelativeLayout.LayoutParams getVideoSizeParams() {

        int width = wm.getDefaultDisplay().getWidth();
        int height= 0;
        if(isPortrait()) {
            height = wm.getDefaultDisplay().getHeight() / 3;
        } else {
            height = wm.getDefaultDisplay().getHeight();
        }


        int vWidth = player.getVideoWidth();
        int vHeight = player.getVideoHeight();

        if (vWidth == 0) {
            vWidth = 600;
        }
        if (vHeight == 0) {
            vHeight = 400;
        }

        if (vWidth > width || vHeight > height) {
            float wRatio = (float) vWidth / (float) width;
            float hRatio = (float) vHeight / (float) height;
            float ratio = Math.max(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth / ratio);
            height = (int) Math.ceil((float) vHeight / ratio);
        } else {
            float wRatio = (float) width / (float) vWidth;
            float hRatio = (float) height / (float) vHeight;
            float ratio = Math.min(wRatio, hRatio);

            width = (int) Math.ceil((float) vWidth * ratio);
            height = (int) Math.ceil((float) vHeight * ratio);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    private boolean isPortrait() {
        int mOrientation = getApplicationContext().getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (!isPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        } else {
            if (pcLiveLandscapeViewManager.onBackPressed()) {
                return;
            }

            if (chatLayoutController != null && chatLayoutController.onBackPressed()) {
                return;
            }
        }

        mExitPopup.show(mRoot);
    }

    private boolean isVideo = true;

    //监听音视频变化
    public void changeVideoAudioStatus() {
        if (isVideo) {
            isVideo = false;
            dwLive.changePlayMode(DWLive.PlayMode.SOUND);
            setRlSoundLayout(View.VISIBLE);
        } else {
            isVideo = true;
            dwLive.changePlayMode(DWLive.PlayMode.VIDEO);
            setRlSoundLayout(View.INVISIBLE);
        }

        changeVideoAudioIcon();
    }

    private void changeVideoAudioIcon() {
        pcLivePortraitViewManager.onVideoAudioChanged(isVideo);
        pcLiveLandscapeViewManager.onVideoAudioChanged(isVideo);
    }

    private boolean isBarrageOn = true;

    public void changeBarrageStatus() {
        if (isBarrageOn) {
            blPcBarrage.setVisibility(View.GONE);
            blPcBarrage.stop();
            isBarrageOn = false;
        } else {
            blPcBarrage.setVisibility(View.VISIBLE);
            blPcBarrage.start();
            isBarrageOn = true;
        }

        changeBarrageIcon();
    }

    private void changeBarrageIcon() {
        pcLivePortraitViewManager.onBarrageChanged(isBarrageOn);
        pcLiveLandscapeViewManager.onBarrageChanged(isBarrageOn);
    }


    public void changeSource(boolean isPortartLayout, int selectItem) {

        if (isPortartLayout) {
            pcLiveLandscapeViewManager.updateSourceSelectItem(selectItem);
        } else {
            pcLivePortraitViewManager.updateSourceSelectItem(selectItem);
        }

        dwLive.changePlaySource(selectItem); //TODO 可能会慢
    }

    public void onShowAnnounce() {
        pcLiveLandscapeViewManager.onShowAnnouce();
        pcLivePortraitViewManager.onShowAnnounce();
    }

    //------------------------下方布局------------------------

    @BindView(R2.id.rg_infos_tag)
    RadioGroup tagRadioGroup;

    @BindView(R2.id.live_portrait_info_document)
    RadioButton docTag;

    @BindView(R2.id.live_portrait_info_chat)
    RadioButton chatTag;

    @BindView(R2.id.live_portrait_info_qa)
    RadioButton qaTag;

    @BindView(R2.id.live_portrait_info_intro)
    RadioButton introTag;

    @BindView(R2.id.live_portrait_container_viewpager)
    ViewPager infoLayoutContainer;

    List<View> infoList = new ArrayList<>();
    List<Integer> tagIdList = new ArrayList<>();
    List<RadioButton> tagRBList = new ArrayList<>();

    View docLayout;
    View chatLayout;
    View qaLayout;
    View introLayout;


    DocLayoutController docLayoutController;
    ChatLayoutController chatLayoutController;
    QaLayoutController qaLayoutController;
    IntroLayoutController introLayoutController;

    private String viewVisibleTag = "1";

    private DocView docView;

    private boolean toDocFullMode;  // 是否要进入文档全屏模式
    private boolean inDocFullMode;  // 当前是否在文档全屏模式

    private void initViewPager() {

        LayoutInflater inflater = LayoutInflater.from(this);

        if (viewVisibleTag.equals(dwLive.getTemplateInfo().getPdfView())) {
            initDocLayout(inflater);
            docView = docLayoutController.getDocView();
            docView.setClickable(true);
            final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    if (isPortrait()) {
                        toDocFullMode = true;
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                    return true;
                }
            });

            docView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
        }

        if (viewVisibleTag.equals(dwLive.getTemplateInfo().getChatView())) {
            initChatLayout(inflater);
        }

        if (viewVisibleTag.equals(dwLive.getTemplateInfo().getQaView())) {
            initQaLayout(inflater);
        }

        initIntroLayout(inflater);

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return infoList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                container.addView(infoList.get(position));
                return infoList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(infoList.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        infoLayoutContainer.setAdapter(adapter);


        infoLayoutContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tagRBList.get(position).setChecked(true);
                hideKeyboard();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tagRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                infoLayoutContainer.setCurrentItem(tagIdList.indexOf(i), true);
            }
        });


        if (tagRBList.contains(docTag)) {
            docTag.performClick();
        } else {
            tagRBList.get(0).performClick();
        }

    }

    private void initDocLayout(LayoutInflater inflater) {
        tagIdList.add(R.id.live_portrait_info_document);
        tagRBList.add(docTag);
        docTag.setVisibility(View.VISIBLE);
        docLayout = inflater.inflate(R.layout.live_portrait_doc_layout, null);
        infoList.add(docLayout);

        docLayoutController = new DocLayoutController(this, docLayout);
    }

    private void initChatLayout(LayoutInflater inflater) {
        tagIdList.add(R.id.live_portrait_info_chat);
        tagRBList.add(chatTag);
        chatTag.setVisibility(View.VISIBLE);
        chatLayout = inflater.inflate(R.layout.live_portrait_chat_layout, null);
        infoList.add(chatLayout);

        chatLayoutController = new ChatLayoutController(this, chatLayout);
        chatLayoutController.initChat();

    }

    private void initQaLayout(LayoutInflater inflater) {
        tagIdList.add(R.id.live_portrait_info_qa);
        tagRBList.add(qaTag);
        qaTag.setVisibility(View.VISIBLE);
        qaLayout = inflater.inflate(R.layout.live_portrait_qa_layout, null);
        infoList.add(qaLayout);

        qaLayoutController = new QaLayoutController(this, qaLayout);
        qaLayoutController.initQaLayout();
    }

    private void initIntroLayout(LayoutInflater inflater) {

        tagIdList.add(R.id.live_portrait_info_intro);
        tagRBList.add(introTag);
        introTag.setVisibility(View.VISIBLE);
        introLayout = inflater.inflate(R.layout.live_portrait_intro_layout, null);
        infoList.add(introLayout);

        introLayoutController = new IntroLayoutController(this, introLayout);
        introLayoutController.initIntro();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rlLiveTopLayout.getWindowToken(), 0);
    }

    //----------------------文档----------------------------
    public class DocLayoutController {

        @BindView(R2.id.live_doc)
        DocView mDocView;


        Context mContext;

        public DocLayoutController(Context context, View view) {
            mContext = context;
            ButterKnife.bind(this, view);
        }

        public DocView getDocView() {
            return mDocView;
        }


    }

    Handler handler = new Handler(Looper.getMainLooper());

    // 可能出现没有username的情况，故先存储下来
    private Map<String, String> userInfoMap = new HashMap<String, String>();

    //----------------------聊天-----------------------------
    public class ChatLayoutController {

        //TODO 多个pager切换的隐藏操作需要实现

        @BindView(R2.id.chat_container)
        RecyclerView mChatList;

        @BindView(R2.id.id_private_chat_user_layout)
        LinearLayout mPrivateChatUserLayout;

        @BindView(R2.id.id_push_chat_layout)
        RelativeLayout mChatLayout;

        @BindView(R2.id.id_push_chat_input)
        EditText mInput;

        @BindView(R2.id.id_push_chat_emoji)
        ImageView mEmoji;

        @BindView(R2.id.id_push_emoji_grid)
        GridView mEmojiGrid;

        @BindView(R2.id.iv_live_pc_private_chat)
        ImageView mPrivateIcon;

        @BindView(R2.id.id_private_chat_msg_layout)
        LinearLayout mPrivateChatMsgLayout;

        @BindView(R2.id.id_private_chat_user_list)
        RecyclerView mPrivateChatUserList;

        @BindView(R2.id.id_private_chat_msg_mask)
        FrameLayout mPrivateChatMsgMask;

        @BindView(R2.id.id_private_chat_title)
        TextView mPrivateChatUserName;

        @BindView(R2.id.id_private_chat_list)
        RecyclerView mPrivateChatMsgList;

        // 软键盘是否显示
        private boolean isSoftInput = false;
        // emoji是否需要显示 emoji是否显示
        private boolean isEmoji = false, isEmojiShow = false;
        // 聊天是否显示
        private boolean isChat = false;
        // 是否是私聊
        private boolean isPrivate = false;
        // 是否显示私聊用户列表
        private boolean isPrivateChatUser = false;
        // 是否显示私聊列表
        private boolean isPrivateChatMsg = false;
        private String mCurPrivateUserId = "";

        // 私聊用户列表适配器
        private PrivateUserAdapter mPrivateUserAdapter;
        // 私聊信息列表
        private PrivateChatAdapter mPrivateChatAdapter;
        private ChatUser mTo; // 私聊对象
        private ArrayList<ChatEntity> mPrivateChats; // 存放所有的私聊信息

        // 软键盘监听
        private SoftKeyBoardState mSoftKeyBoardState;

        Context mContext;

        public ChatLayoutController(Context context, View view) {
            mContext = context;
            ButterKnife.bind(this, view);
        }

        LivePublicChatAdapter mChatAdapter;

        public void initChat() {
            mChatList.setLayoutManager(new LinearLayoutManager(mContext));
            mChatAdapter = new LivePublicChatAdapter(mContext);
            mChatList.setAdapter(mChatAdapter);
            mChatList.addOnItemTouchListener(new BaseOnItemTouch(mChatList, new OnClickListener() {
                @Override
                public void onClick(RecyclerView.ViewHolder viewHolder) {
                    int position = mChatList.getChildAdapterPosition(viewHolder.itemView);
                    ChatEntity chatEntity = mChatAdapter.getChatEntities().get(position);
                    click2PrivateChat(chatEntity, false);
                }
            }));

            mChatList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideKeyboard();
                    return false;
                }
            });

            initChatView();
        }


        private short maxInput = 300;
        public void initChatView() {

            mInput.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideEmoji();
                    return false;
                }
            });

            mInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    String inputText = mInput.getText().toString();

                    if (inputText.length() > maxInput) {
                        Toast.makeText(getApplicationContext(), "字数超过300字", Toast.LENGTH_SHORT).show();
                        mInput.setText(inputText.substring(0, maxInput));
                        mInput.setSelection(maxInput);
                    }
                }
            });

            EmojiAdapter emojiAdapter = new EmojiAdapter(mContext);
            emojiAdapter.bindData(EmojiUtil.imgs);
            mEmojiGrid.setAdapter(emojiAdapter);
            mEmojiGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == EmojiUtil.imgs.length - 1) {
                        EmojiUtil.deleteInputOne(mInput);
                    } else {
                        EmojiUtil.addEmoji(mContext, mInput, position);
                    }
                }
            });

            mPrivateChats = new ArrayList<>(); // 初始化私聊数据集合

            mPrivateChatUserList.setLayoutManager(new LinearLayoutManager(mContext));
            mPrivateUserAdapter = new PrivateUserAdapter(mContext);
            mPrivateChatUserList.setAdapter(mPrivateUserAdapter);
            mPrivateChatUserList.addOnItemTouchListener(new BaseOnItemTouch(mPrivateChatUserList, new OnClickListener() {
                @Override
                public void onClick(RecyclerView.ViewHolder viewHolder) {
                    // TODO 隐藏用户列表
                    mPrivateChatUserLayout.setVisibility(View.GONE);
                    isPrivateChatUser = false;
                    int position = mPrivateChatUserList.getChildAdapterPosition(viewHolder.itemView);
                    PrivateUser privateUser = mPrivateUserAdapter.getPrivateUsers().get(position);
                    privateUser.setRead(true);
                    mPrivateUserAdapter.notifyDataSetChanged();
                    if (isAllPrivateChatRead()) {
                        mPrivateIcon.setImageResource(R.mipmap.video_ic_private_msg_nor);
                    }
                    ChatEntity chatEntity = new ChatEntity();
                    chatEntity.setUserId(privateUser.getId());
                    chatEntity.setUserName(privateUser.getName());
                    chatEntity.setUserAvatar(privateUser.getAvatar());
                    click2PrivateChat(chatEntity, true);
                }
            }));

            mPrivateChatMsgList.setLayoutManager(new LinearLayoutManager(mContext));
            mPrivateChatAdapter = new PrivateChatAdapter(mContext);
            mPrivateChatMsgList.setAdapter(mPrivateChatAdapter);

            onSoftInputChange();
        }

        /**
         * 点击发起私聊
         */
        private void click2PrivateChat(ChatEntity chatEntity, boolean flag) {
            if (flag) { // 私聊用户列表点击发起私聊
                goPrivateChat(chatEntity);
                mCurPrivateUserId = chatEntity.getUserId();
            } else {
                if (!chatEntity.isPublisher()) { // 如果当前被点击的用户不是主播，则进行私聊
                    hideKeyboard();
                    mPrivateChatUserLayout.setVisibility(View.GONE);
                    mPrivateIcon.setVisibility(View.GONE);
                    goPrivateChat(chatEntity);
                    mCurPrivateUserId = chatEntity.getUserId();
                }
            }
        }

        /**
         * 跳转私聊
         */
        private void goPrivateChat(ChatEntity chatEntity) {
            isPrivate = true;
            mTo = null;
            mTo = new ChatUser();
            mTo.setUserId(chatEntity.getUserId());
            mTo.setUserName(chatEntity.getUserName());
            ArrayList<ChatEntity> toChatEntitys = new ArrayList<>();
            for (ChatEntity entity : mPrivateChats) {
                // 从私聊列表里面读取到 当前发起私聊的俩个用户聊天列表
                if (entity.getUserId().equals(chatEntity.getUserId()) || entity.getReceiveUserId().equals(chatEntity.getUserId())) {
                    toChatEntitys.add(entity);
                }
            }
            mPrivateChatAdapter.setDatas(toChatEntitys);
            showPrivateChatMsgList(chatEntity.getUserName());
        }

        /**
         * 判断是否所有私聊信息全部读完
         */
        private boolean isAllPrivateChatRead() {
            int i = 0;
            for (; i < mPrivateUserAdapter.getPrivateUsers().size(); i++) {
                if (!mPrivateUserAdapter.getPrivateUsers().get(i).isRead()) {
                    break;
                }
            }
            return i >= mPrivateUserAdapter.getPrivateUsers().size();
        }

        private void onSoftInputChange() {
            mSoftKeyBoardState = new SoftKeyBoardState(mRoot, false);
            mSoftKeyBoardState.setOnSoftKeyBoardStateChangeListener(new SoftKeyBoardState.OnSoftKeyBoardStateChangeListener() {
                @Override
                public void onChange(boolean isShow) {
                    isSoftInput = isShow;
                    if (!isSoftInput) { // 软键盘隐藏
                        if (isEmoji) {
                            mEmojiGrid.setVisibility(View.VISIBLE);// 避免闪烁
                            isEmojiShow = true; // 修改emoji显示标记
                            isEmoji = false; // 重置
                        } else {
                            hideChatLayout(); // 隐藏聊天操作区域
                        }
                        if (isPrivateChatMsg && !isEmojiShow) { // 私聊软键盘隐藏时，显示公聊列表
                            mChatList.setVisibility(View.VISIBLE);
                        }
                    } else {
                        hideEmoji();
                        if (isPrivateChatMsg) { // 私聊进行消息输入的时候隐藏公聊列表
                            mChatList.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }

        @OnClick(R2.id.iv_live_pc_private_chat)
        void openPrivateChatUserList() { // 显示私聊用户列表
            hideEmoji();
            hideKeyboard();
            showPrivateChatUserList();
        }

        @OnClick(R2.id.id_private_chat_user_close)
        void closePrivateChatUserList() { // 关闭私聊用户列表
            hidePrivateChatUserList();
        }

        @OnClick(R2.id.id_private_chat_close)
        void closePrivate() { // 关闭私聊
            hidePrivateChatMsgList();
        }

        @OnClick(R2.id.id_push_chat_emoji)
        void emoji() {
            if (isEmojiShow) {
                hideEmoji();
                mInput.requestFocus();
                mInput.setSelection(mInput.getEditableText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                showEmoji();
            }
        }

        @OnClick(R2.id.id_push_chat_send)
        void sendMsg() { // 发送聊天
            String msg = mInput.getText().toString().trim();
            if (TextUtils.isEmpty(msg)) {
                toastOnUiThread("聊天内容不能为空");
                return;
            }
            if (isPrivate) {
                DWLive.getInstance().sendPrivateChatMsg(mTo.getUserId(), msg);
            } else {
                DWLive.getInstance().sendPublicChatMsg(msg);
            }

            clearChatInput();
        }

        @OnClick(R2.id.id_private_chat_back)
        void backChatUser() { // 返回私聊用户列表
            if (isSoftInput) {
                mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
            }
            hidePrivateChatMsgList();
            showPrivateChatUserList();
        }

        //TODO 看看是不是需要
        void dismissAll() {
            if (isSoftInput) {
                mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
            }
            hideChatLayout();
            hideEmoji();
            hidePrivateChatUserList();
            hidePrivateChatMsgList();
        }

        /**
         * 显示私聊用户列表
         */
        private void showPrivateChatUserList() {
            mChatLayout.setVisibility(View.GONE); // 隐藏聊天操作
            mPrivateIcon.setVisibility(View.GONE);
            mPrivateChatUserLayout.setVisibility(View.VISIBLE); // 显示用户列表
            isPrivateChatUser = true;
        }

        /**
         * 隐藏私聊用户
         */
        private void hidePrivateChatUserList() {
            if (isPrivateChatUser) {
                mChatLayout.setVisibility(View.VISIBLE);
                mPrivateIcon.setVisibility(View.VISIBLE);
                mPrivateChatUserLayout.setVisibility(View.GONE);
                isPrivateChatUser = false;
            }
        }

        public void hideChatLayout() {
            if (isChat) {
                AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                animation.setDuration(300L);
                mInput.setFocusableInTouchMode(false);
                mInput.clearFocus();
                mChatLayout.setVisibility(View.VISIBLE);
                isChat = false;
            }
        }

        /**
         * 显示emoji
         */
        public void showEmoji() {
            if (isSoftInput) {
                isEmoji = true; // 需要显示emoji
                mInput.clearFocus();
                mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
            } else {
                mEmojiGrid.setVisibility(View.VISIBLE);// 避免闪烁
                isEmojiShow = true; // 修改emoji显示标记
            }
            mEmoji.setImageResource(R.drawable.push_chat_emoji);
        }

        /**
         * 隐藏emoji
         */
        public void hideEmoji() {
            if (isEmojiShow) { // 如果emoji显示
                mEmojiGrid.setVisibility(View.GONE);
                isEmojiShow = false; // 修改emoji显示标记
                mEmoji.setImageResource(R.drawable.push_chat_emoji_normal);
                if (!isSoftInput) {
                    mChatList.setVisibility(View.VISIBLE);
                }
            }
        }

        /**
         * 显示私聊信息列表
         */
        public void showPrivateChatMsgList(final String username) {
            mChatLayout.setVisibility(View.VISIBLE);
            mInput.setFocusableInTouchMode(true);
            TranslateAnimation animation = new TranslateAnimation(1f, 1f, 0f, 1f);
            animation.setDuration(300L);
            mPrivateChatMsgLayout.startAnimation(animation);
            mPrivateChatMsgMask.setBackgroundColor(Color.parseColor("#FAFAFA"));
            mPrivateChatUserName.setText(username);
            mPrivateChatMsgLayout.setVisibility(View.VISIBLE);
            if (mPrivateChatAdapter.getItemCount() - 1 > 0) {
                mPrivateChatMsgList.smoothScrollToPosition(mPrivateChatAdapter.getItemCount() - 1);// 进行定位
            }
            isPrivateChatMsg = true;
        }

        /**
         * 隐藏私聊信息列表
         */
        public void hidePrivateChatMsgList() {
            if (isPrivateChatMsg) {
                hideEmoji();
                // 展示公聊区域和公聊内容
                mChatLayout.setVisibility(View.VISIBLE);
                mChatList.setVisibility(View.VISIBLE);
                mPrivateIcon.setVisibility(View.VISIBLE);
                mInput.setText("");
                mPrivateChatMsgMask.setBackgroundColor(Color.parseColor("#00000000"));
                mPrivateChatMsgLayout.setVisibility(View.GONE);
                isPrivateChatMsg = false;
                isPrivate = false;
            }
        }

        public void clearChatInput() {
            mInput.setText("");
            hideKeyboard();
        }

        public void hideKeyboard() {
            hideEmoji();
            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
        }

        public void updatePrivateChat(ChatEntity chatEntity) {
            if (isPrivateChatMsg && (chatEntity.isPublisher() ||
                    chatEntity.getUserId().equals(mCurPrivateUserId))) { // 如果当前界面是私聊信息界面直接在该界面进行数据更新
                mPrivateChatAdapter.add(chatEntity);
                mPrivateChatMsgList.smoothScrollToPosition(mPrivateChatAdapter.getItemCount() - 1);// 进行定位
            }
            PrivateUser privateUser = new PrivateUser();
            if (chatEntity.isPublisher()) {
                privateUser.setId(chatEntity.getReceiveUserId());
                privateUser.setName(chatEntity.getReceivedUserName());
                privateUser.setAvatar(chatEntity.getReceiveUserAvatar());
            } else {
                privateUser.setId(chatEntity.getUserId());
                privateUser.setName(chatEntity.getUserName());
                privateUser.setAvatar(chatEntity.getUserAvatar());
            }
            privateUser.setMsg(chatEntity.getMsg());
            privateUser.setTime(chatEntity.getTime());
            // TODO 判断
            privateUser.setRead(isPrivateChatMsg && (chatEntity.isPublisher() ||
                    chatEntity.getUserId().equals(mCurPrivateUserId)));
            mPrivateUserAdapter.add(privateUser);
            if (!isAllPrivateChatRead()) {
                mPrivateIcon.setImageResource(R.mipmap.video_ic_private_msg_new);
            }
            mPrivateChats.add(chatEntity);
        }

        public boolean onBackPressed() {
            if (isEmojiShow) {
                hideEmoji();
                hideChatLayout();
                return true;
            }
            if (isPrivateChatMsg) {
                hidePrivateChatMsgList();
                showPrivateChatUserList();
                return true;
            }
            if (isPrivateChatUser) {
                hidePrivateChatUserList();
                return true;
            }

            return false;
        }

        public void addChatEntity(ChatEntity chatEntity) {
            mChatAdapter.add(chatEntity);
            mChatList.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
        }
    }

    //----------------------问答----------------------------
    public class QaLayoutController {

        @BindView(R2.id.rv_qa_container)
        RecyclerView mQaList;
        @BindView(R2.id.id_qa_input)
        EditText qaInput;
        @BindView(R2.id.self_qa_invisible)
        ImageView qaVisibleStatus;
        @BindView(R2.id.qa_show_tips)
        TextView qaTips;

        LiveQaAdapter mQaAdapter;

        Context mContext;

        public QaLayoutController(Context context, View view) {
            mContext = context;
            ButterKnife.bind(this, view);
        }

        public void initQaLayout() {
            mQaList.setLayoutManager(new LinearLayoutManager(mContext));
            mQaAdapter = new LiveQaAdapter(mContext);
            mQaList.setAdapter(mQaAdapter);

            mQaList.addItemDecoration(new DividerItemDecoration(PcLivePlayActivity.this, DividerItemDecoration.VERTICAL));

            mQaList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    mImm.hideSoftInputFromWindow(qaInput.getWindowToken(), 0);
                    return false;
                }
            });
        }

        public void clearQaInfo() {
            mQaAdapter.resetQaInfos();
        }

        public void addQuestion(Question question) {
            mQaAdapter.addQuestion(question);
        }

        public void showQuestion(String questionId) {
            mQaAdapter.showQuestion(questionId);
        }

        public void addAnswer(Answer answer) {
            mQaAdapter.addAnswer(answer);
        }

        @OnClick(R2.id.id_qa_input)
        void inputQaMsg() {

        }

        @OnClick(R2.id.self_qa_invisible)
        void changeShowQaStatus() {
            if (qaVisibleStatus.isSelected()) {
                qaVisibleStatus.setSelected(false);
                qaTips.setText("显示所有回答");
                mQaAdapter.setOnlyShowSelf(false);


            } else {
                qaVisibleStatus.setSelected(true);
                qaTips.setText("只看我的回答");
                mQaAdapter.setOnlyShowSelf(true);
            }

            removeTipsHideCallBack();
            qaTips.setVisibility(View.VISIBLE);
            handler.postDelayed(tipsRunnable, 3 * 1000);
        }

        Runnable tipsRunnable = new Runnable() {
            @Override
            public void run() {
                qaTips.setVisibility(View.INVISIBLE);
            }
        };

        public void removeTipsHideCallBack() {
            handler.removeCallbacks(tipsRunnable);
        }

        @OnClick(R2.id.id_qa_send)
        void sendQaMsg() {

            // 判断如果直播未开始，则告诉用户，无法提问
            if (DWLive.getInstance().getPlayStatus() == DWLive.PlayStatus.PREPARING) {
                toastOnUiThread("直播未开始，无法提问");
                return;
            }

            // 直播中，提问判断内容是否符合要求，符合要求，进行提问
            String questionMsg = qaInput.getText().toString().trim();
            if (TextUtils.isEmpty(questionMsg)) {
                toastOnUiThread("输入信息不能为空");
            } else {
                try {
                    dwLive.sendQuestionMsg(questionMsg);
                    qaInput.setText("");
                    mImm.hideSoftInputFromWindow(qaInput.getWindowToken(), 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //-----------------------简介----------------------------
    public class IntroLayoutController {

        @BindView(R2.id.tv_intro_title)
        TextView title;

        @BindView(R2.id.content_layer)
        LinearLayout content_layer;

        Context mContext;

        public IntroLayoutController(Context context, View view) {
            mContext = context;
            ButterKnife.bind(this, view);
        }

        public void initIntro() {
            title.setText(DWLive.getInstance().getRoomInfo().getName());

            content_layer.removeAllViews();
            content_layer.addView(new MixedTextView(PcLivePlayActivity.this, DWLive.getInstance().getRoomInfo().getDesc()));

        }
    }
}