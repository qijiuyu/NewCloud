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
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bokecc.dwlivedemo_new.DWApplication;
import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.adapter.LivePublicChatAdapter;
import com.bokecc.dwlivedemo_new.adapter.LiveQaAdapter;
import com.bokecc.dwlivedemo_new.base.BaseActivity;
import com.bokecc.dwlivedemo_new.global.QaInfo;
import com.bokecc.dwlivedemo_new.manage.ReplayPlayerManager;
import com.bokecc.dwlivedemo_new.module.ChatEntity;
import com.bokecc.dwlivedemo_new.popup.CommonPopup;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.Answer;
import com.bokecc.sdk.mobile.live.pojo.Question;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplay;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplayListener;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayAnswerMsg;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayChatMsg;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayPageInfo;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayQAMsg;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayQuestionMsg;
import com.bokecc.sdk.mobile.live.widget.DocView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 直播在线回放页面
 * <p></p>
 * Created by liufh on 2016/12/8.
 */
public class ReplayActivity extends BaseActivity implements TextureView.SurfaceTextureListener,
        IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnVideoSizeChangedListener,
        IMediaPlayer.OnCompletionListener {

    @BindView(R2.id.rl_pc_live_top_layout)
    RelativeLayout rlLiveTopLayout;

    @BindView(R2.id.textureview_pc_live_play)
    TextureView mPlayerContainer;

    @BindView(R2.id.replay_player_control_layout)
    RelativeLayout playerControlLayout;

    @BindView(R2.id.pc_live_infos_layout)
    RelativeLayout rlLiveInfosLayout;

    @BindView(R2.id.pc_portrait_progressBar)
    ProgressBar pcPortraitProgressBar;

    ReplayPlayerManager replayPlayerManager;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        replayPlayerManager.setScreenVisible(true, true);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setPortraitLayoutVisibility(View.VISIBLE);
            rlLiveTopLayout.setVisibility(View.VISIBLE);
            tagRadioGroup.setVisibility(View.VISIBLE);
            playerControlLayout.setVisibility(View.VISIBLE);
            mPlayerContainer.setLayoutParams(getVideoSizeParams());
            replayPlayerManager.onConfiChanged(true);
            if (inDocFullMode) {
                dwLiveReplay.docApplyNewConfig(newConfig);
                inDocFullMode = false;
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (toDocFullMode) {
                rlLiveTopLayout.setVisibility(View.GONE);
                tagRadioGroup.setVisibility(View.GONE);
                dwLiveReplay.docApplyNewConfig(newConfig);
                toDocFullMode = false;
                inDocFullMode = true;
            } else {
                setPortraitLayoutVisibility(View.GONE);
                playerControlLayout.setVisibility(View.VISIBLE);
                mPlayerContainer.setLayoutParams(getVideoSizeParams());
                replayPlayerManager.onConfiChanged(false);
            }
        }
    }


    @OnClick(R2.id.rl_pc_live_top_layout)
    void onPlayOnClick(View v) {
        boolean isLayoutShown = replayPlayerManager.OnPlayClick();
    }

    // 退出界面弹出框
    private CommonPopup mExitPopup;

    private View mRoot;
    private IjkMediaPlayer player;
    private DWLiveReplay dwLiveReplay = DWLiveReplay.getInstance();

    private WindowManager wm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pc_replay;
    }


    @Override
    protected void onViewCreated() {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mRoot = getWindow().getDecorView().findViewById(android.R.id.content);
        // 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        replayPlayerManager = new ReplayPlayerManager(this, playerControlLayout, mRoot);
        replayPlayerManager.init();

        initClosePopup();

        initViewPager();

        initPlayer();

    }

    /** 初始化 关闭回放界面 弹出框 */
    private void initClosePopup() {
        mExitPopup = new CommonPopup(this);
        mExitPopup.setOutsideCancel(true);
        mExitPopup.setKeyBackCancel(true);
        mExitPopup.setTip("您确认结束观看吗?");
        mExitPopup.setOKClickListener(new CommonPopup.OnOKClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    /** 初始化播放器 */
    private void initPlayer() {
        mPlayerContainer.setSurfaceTextureListener(this);
        player = new IjkMediaPlayer();
        player.setOnPreparedListener(this);
        player.setOnInfoListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnCompletionListener(this);

        player.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                replayPlayerManager.setBufferPercent(percent);
            }
        });

        dwLiveReplay.setReplayParams(myDWLiveReplayListener, this, player, docView);
    }

    private ArrayList<ChatEntity> mChatEntities;
    private LinkedHashMap<String, QaInfo> mQaInfoMap;

    private ChatEntity getReplayChatEntity(ReplayChatMsg msg) {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setUserId(msg.getUserId());
        chatEntity.setUserName(msg.getUserName());
        chatEntity.setPrivate(false);
        chatEntity.setPublisher(true);
        chatEntity.setMsg(msg.getContent());
        chatEntity.setTime(String.valueOf(msg.getTime())); // TODO 看看到底是个啥
        chatEntity.setUserAvatar(msg.getAvatar());
        return chatEntity;
    }

    private DWLiveReplayListener myDWLiveReplayListener = new DWLiveReplayListener() {

        @Override
        public void onQuestionAnswer(TreeSet<ReplayQAMsg> qaMsgs) {

            LinkedHashMap<String, QaInfo> qaInfoMap = new LinkedHashMap<>();

            for (ReplayQAMsg qaMsg: qaMsgs) {

                ReplayQuestionMsg questionMsg = qaMsg.getReplayQuestionMsg();
                Question question = new Question();
                question.setContent(questionMsg.getContent())
                        .setId(questionMsg.getQuestionId())
                        .setQuestionUserId(questionMsg.getQuestionUserId())
                        .setQuestionUserName(questionMsg.getQuestionUserName())
                        .setTime(String.valueOf(questionMsg.getTime()))
                        .setUserAvatar(questionMsg.getQuestionUserAvatar());

                TreeSet<ReplayAnswerMsg> answerMsgs = qaMsg.getReplayAnswerMsgs();

                // 没有回答
                if (answerMsgs.size() < 1) {
                    if (questionMsg.getIsPublish() == 0) {
                        // 未发布的问题
                        continue;
                    } else if (questionMsg.getIsPublish() == 1) {
                        // 发布的问题
                        QaInfo qaInfo = new QaInfo(question);
                        qaInfoMap.put(question.getId(), qaInfo);
                        continue;
                    }
                }

                // 回答过
                QaInfo qaInfo = new QaInfo(question);
                for (ReplayAnswerMsg answerMsg:answerMsgs) {
                    Answer answer = new Answer();
                    answer.setUserAvatar(answerMsg.getUserAvatar())
                            .setContent(answerMsg.getContent())
                            .setAnswerUserId(answerMsg.getUserId())
                            .setAnswerUserName(answerMsg.getUserName())
                            .setReceiveTime(String.valueOf(answerMsg.getTime()))
                            .setUserRole(answerMsg.getUserRole());
                    qaInfo.addAnswer(answer);
                }

                qaInfoMap.put(question.getId(), qaInfo);
            }

            mQaInfoMap = qaInfoMap;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 如果不需要根据时间轴展示，就直接交给UI去做展示
                    if (!DWApplication.REPLAY_QA_FOLLOW_TIME) {
                        qaLayoutController.addReplayQAInfos(mQaInfoMap);
                    }
                }
            });
        }

        @Override
        public void onChatMessage(TreeSet<ReplayChatMsg> replayChatMsgs) {

            ArrayList<ChatEntity> chatEntities = new ArrayList<>();

            for (ReplayChatMsg msg: replayChatMsgs) {
                chatEntities.add(getReplayChatEntity(msg));
            }

            mChatEntities = chatEntities;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 如果不需要根据时间轴展示，就直接交给UI去做展示
                    if (!DWApplication.REPLAY_CHAT_FOLLOW_TIME) {
                        if (chatLayoutController != null) {
                            chatLayoutController.addChatEntities(mChatEntities);
                        }
                    }
                }
            });
        }

        @Override
        public void onPageInfoList(ArrayList<ReplayPageInfo> infoList) {
            // TODO 回放页面信息列表
        }

        @Override
        public void onException(DWLiveException exception) {

        }

        @Override
        public void onInitFinished() {

        }
    };

    @Override
    protected void onDestroy() {
        replayPlayerManager.onDestroy();

        if (timerTask != null) {
            timerTask.cancel();
        }

        if (player != null) {
            player.pause();
            player.stop();
            player.release();
        }

        dwLiveReplay.onDestroy();

        super.onDestroy();
    }


    boolean isOnPause = false;

    long currentPosition;

    @Override
    protected void onPause() {
        super.onPause();
        isPrepared = false;
        isOnPause = true;
        if (player != null) {
            player.pause();
            if (player.getCurrentPosition() != 0) {
                currentPosition = player.getCurrentPosition();
            }
        }

        if (qaLayoutController != null) {
            qaLayoutController.clearQaInfo();
        }

        dwLiveReplay.stop();
        stopTimerTask();
        stopNetworkTimer();
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
            dwLiveReplay.start(surface);
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
        dwLiveReplay.start(surface);
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

        if (currentPosition > 0) {
            player.seekTo(currentPosition);
        }

        pcPortraitProgressBar.setVisibility(View.GONE);
        playerControlLayout.setVisibility(View.VISIBLE);

        if (isPortrait()) {
            setPortraitLayoutVisibility(View.VISIBLE);
        } else {
            setPortraitLayoutVisibility(View.GONE);
        }

        if (replayPlayerManager != null) {
            replayPlayerManager.onPrepared();
            replayPlayerManager.setDurationTextView(player.getDuration());
        }

        // 更新一下当前播放的按钮的状态
        replayPlayerManager.changePlayIconStatus(player.isPlaying());

        startTimerTask();

        isNetworkConnected = true;
        startNetworkTimer();

    }

    Timer timer = new Timer();
    TimerTask timerTask;

    private void startTimerTask() {

        stopTimerTask();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!player.isPlaying() && (player.getDuration() - player.getCurrentPosition() < 500)) {
                    replayPlayerManager.setCurrentTime(player.getDuration());
                } else {
                    replayPlayerManager.setCurrentTime(player.getCurrentPosition());
                }

                // 回放的聊天内容随时间轴推进展示
                if (DWApplication.REPLAY_CHAT_FOLLOW_TIME) {
                    if (mChatEntities != null && mChatEntities.size() >= 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<ChatEntity> temp_chatEntities = new ArrayList<>();
                                int time = Math.round(player.getCurrentPosition() / 1000);
                                for (ChatEntity entity : mChatEntities) {
                                    if (!TextUtils.isEmpty(entity.getTime()) && time >= Integer.valueOf(entity.getTime())) {
                                        temp_chatEntities.add(entity);
                                    }
                                }
                                chatLayoutController.addChatEntities(temp_chatEntities);
                            }
                        });
                    }
                }

                // 回放的问答内容随时间轴推进展示
                if (DWApplication.REPLAY_QA_FOLLOW_TIME) {
                    if (mQaInfoMap != null && mQaInfoMap.size() >= 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinkedHashMap<String, QaInfo> temp_qaInfoMap = new LinkedHashMap<>();
                                int time = Math.round(player.getCurrentPosition() / 1000);
                                Iterator it = mQaInfoMap.entrySet().iterator();
                                while(it.hasNext()) {
                                    Map.Entry entity = (Map.Entry) it.next();
                                    QaInfo qaInfo = (QaInfo) entity.getValue();
                                    String key = entity.getKey().toString();
                                    if (!TextUtils.isEmpty(qaInfo.getQuestion().getTime()) && time >= Integer.valueOf(qaInfo.getQuestion().getTime())) {
                                        temp_qaInfoMap.put(key, qaInfo);
                                    }
                                }
                                qaLayoutController.addReplayQAInfos(temp_qaInfoMap);
                            }
                        });
                    }
                }
            }
        };

        timer.schedule(timerTask, 0, 1 * 1000);

    }

    private void stopTimerTask() {
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    boolean isNetworkConnected;
    private void startNetworkTimer() {

        if (timerTaskNetwork != null) {
            timerTaskNetwork.cancel();
        }

        timerTaskNetwork = new TimerTask() {

            @Override
            public void run() {

                if (isNetworkConnected()) {

                    if (isNetworkConnected) {
                        return;
                    } else {
                        currentPosition = player.getCurrentPosition();
                        if (qaLayoutController != null) {
                            qaLayoutController.clearQaInfo();
                        }
                        dwLiveReplay.stop();
                        dwLiveReplay.start(surface);

                    }
                    isNetworkConnected = true;

                } else {
                    if (isNetworkConnected) {
                    } else {
                        return;
                    }
                    isNetworkConnected = false;
                }

            }
        };

        timerNetwork.schedule(timerTaskNetwork, 0, 1 * 1000);
    }

    private void stopNetworkTimer() {
        if (timerTaskNetwork != null) {
            timerTaskNetwork.cancel();
        }
    }


    private Timer timerNetwork = new Timer();

    private TimerTask timerTaskNetwork;

    /**
     * 检测网络是否可用
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isAvailable();
    }

    private void setPortraitLayoutVisibility(int i) {
        rlLiveInfosLayout.setVisibility(i);
    }

    private void setLandScapeVisibility(int i) {
        playerControlLayout.setVisibility(i);
        rlLiveInfosLayout.setVisibility(i);
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
        int height = wm.getDefaultDisplay().getHeight();

        int vWidth = player.getVideoWidth();
        int vHeight = player.getVideoHeight();

        if(isPortrait()) {
            height = height / 3; //TODO 根据当前布局更改
        }

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

    public boolean isPortrait() {
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
            if (chatLayoutController != null && chatLayoutController.onBackPressed()) {
                return;
            }
        }

        mExitPopup.show(mRoot);
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

    @BindView(R2.id.live_portrait_container_viewpager)
    ViewPager infoLayoutContainer;

    List<View> infoList = new ArrayList<>();
    List<Integer> tagIdList = new ArrayList<>();
    List<RadioButton> tagRBList = new ArrayList<>();

    View docLayout;
    View chatLayout;
    View qaLayout;

    DocLayoutController docLayoutController;
    ChatLayoutController chatLayoutController;
    QaLayoutController qaLayoutController;

    private String viewVisibleTag = "1";

    private boolean toDocFullMode;  // 是否要进入文档全屏模式
    private boolean inDocFullMode;  // 当前是否在文档全屏模式
    private DocView docView;

    private void initViewPager() {

        LayoutInflater inflater = LayoutInflater.from(this);

        if (viewVisibleTag.equals(dwLiveReplay.getTemplateInfo().getPdfView())) {
            initDocLayout(inflater);
            docView = docLayoutController.getDocView();
            docView.setClickable(true); // 设置文档区域可点击

            // 定义手势监听 -- 双击切换当前屏幕方向
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

        if (viewVisibleTag.equals(dwLiveReplay.getTemplateInfo().getChatView())) {
            initChatLayout(inflater);
        }

        if (viewVisibleTag.equals(dwLiveReplay.getTemplateInfo().getQaView())) {
            initQaLayout(inflater);
        }

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


        if (tagRBList.contains(chatTag)) {
            chatTag.performClick();
        } else if (tagRBList.size() > 0) {
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


    boolean isComplete = false;
    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        isComplete = true;
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

    //----------------------聊天-----------------------------
    public class ChatLayoutController {

        //TODO 多个pager切换的隐藏操作需要实现

        @BindView(R2.id.chat_container)
        RecyclerView mChatList;

        @BindView(R2.id.iv_live_pc_private_chat)
        ImageView mPrivateChatIcon;

        @BindView(R2.id.id_private_chat_user_layout)
        LinearLayout mPrivateChatUserLayout;

        @BindView(R2.id.id_push_chat_layout)
        RelativeLayout mChatLayout;

        int mChatInfoLength;

        Context mContext;

        public ChatLayoutController(Context context, View view) {
            mContext = context;
            ButterKnife.bind(this, view);
            mChatInfoLength = 0;
            mChatLayout.setVisibility(View.GONE);
            mPrivateChatIcon.setVisibility(View.GONE);
        }

        LivePublicChatAdapter mChatAdapter;

        public void initChat() {
            mChatList.setLayoutManager(new LinearLayoutManager(mContext));
            mChatAdapter = new LivePublicChatAdapter(mContext);
            mChatList.setAdapter(mChatAdapter);
        }


        public boolean onBackPressed() {
            return false;
        }

        /**
         * 回放的聊天添加
         * @param chatEntities
         */
        public void addChatEntities(ArrayList<ChatEntity> chatEntities) {
            // 回放的聊天内容随时间轴推进展示
            if (DWApplication.REPLAY_CHAT_FOLLOW_TIME) {
                // 如果数据长度没发生变化就不刷新
                if (mChatInfoLength != chatEntities.size()) {
                    mChatAdapter.add(chatEntities);
                    mChatList.scrollToPosition(chatEntities.size() - 1);
                    mChatInfoLength = chatEntities.size();
                }
            } else {
                mChatAdapter.add(chatEntities);
            }
        }
    }

    //----------------------问答----------------------------
    public class QaLayoutController {

        @BindView(R2.id.rv_qa_container)
        RecyclerView mQaList;

        @BindView(R2.id.rl_qa_input_layout)
        RelativeLayout mInputLayout;

        LiveQaAdapter mQaAdapter;

        int mQaInfoLength;

        Context mContext;

        public QaLayoutController(Context context, View view) {
            mContext = context;
            ButterKnife.bind(this, view);
            mQaInfoLength = 0;
            mInputLayout.setVisibility(View.GONE);
        }

        public void initQaLayout() {
            mQaList.setLayoutManager(new LinearLayoutManager(mContext));
            mQaAdapter = new LiveQaAdapter(mContext);
            mQaList.setAdapter(mQaAdapter);
            //TODO 增加分割线
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ReplayActivity.this, DividerItemDecoration.VERTICAL);
        }

        public void clearQaInfo() {
            mQaAdapter.resetQaInfos();
        }

        public void addReplayQAInfos(LinkedHashMap<String, QaInfo> replayQaInfos) {
            // 回放的问答内容随时间轴推进展示
            if (DWApplication.REPLAY_QA_FOLLOW_TIME) {
                // 如果数据长度没发生变化就不刷新
                if (mQaInfoLength != replayQaInfos.size()) {
                    mQaAdapter.addReplayQuestoinAnswer(replayQaInfos);
                    mQaList.scrollToPosition(replayQaInfos.size() - 1);
                    mQaInfoLength = replayQaInfos.size();
                }
            } else {
                mQaAdapter.addReplayQuestoinAnswer(replayQaInfos);
            }
        }

        public void addQuestion(Question question) {
            mQaAdapter.addQuestion(question);
            //TODO 跳转到那个地方
        }

        public void addAnswer(Answer answer) {
            mQaAdapter.addAnswer(answer);
        }

    }

    public void setPlayerStatus(boolean isPlaying) {
        if (isPlaying) {
            player.start();
        } else {
            player.pause();
        }
    }

    public void setScreenStatus(boolean isFull) {
        if (isFull) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void setSeekPosition(int position) {
        player.seekTo(position);

        if (isComplete) {
            player.start();
            isComplete = false;
            replayPlayerManager.setPlayingStatusIcon();
        }
    }

    Runnable r;
    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int arg1, int i1) {

        if (arg1 == IMediaPlayer.MEDIA_INFO_BUFFERING_START) { // 开始缓冲
            r = new Runnable() {
                @Override
                public void run() {
                    if (qaLayoutController != null) {
                        qaLayoutController.clearQaInfo();
                    }
                    dwLiveReplay.stop();
                    dwLiveReplay.start(surface);
                }
            };

            handler.postDelayed(r, 10 * 1000); // 延时定时器，此处设置的是10s，可自行设置
        } else if(arg1 == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
            if (r != null) {
                handler.removeCallbacks(r); // 如果收到了缓冲结束，那么取消延时定时器
            }
        }

        return false;
    }
}