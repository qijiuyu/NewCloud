package com.bokecc.dwlivedemo_new.manage;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.activity.PcLivePlayActivity;
import com.bokecc.dwlivedemo_new.adapter.EmojiAdapter;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.popup.AnnounceLandscapePopup;
import com.bokecc.dwlivedemo_new.popup.DefinitionPopup;
import com.bokecc.dwlivedemo_new.popup.LiveSourcePcLandscapePopup;
import com.bokecc.dwlivedemo_new.popup.RtcPopup;
import com.bokecc.dwlivedemo_new.util.EmojiUtil;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.QualityInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liufh on 2016/12/27.
 */

public class PcLiveLandscapeViewManager {

    @BindView(R2.id.tv_phone_live_user_count)
    TextView mUserCount;

    @BindView(R2.id.ll_land_live_left_layout)
    LinearLayout mLeftLayout;

    @BindView(R2.id.tv_phone_live_announce)
    TextView mLiveAnnounce;

    @BindView(R2.id.iv_phone_live_barrage)
    ImageView mBarrageControl;

    @BindView(R2.id.iv_phone_live_close)
    ImageView mLiveClose;

    @BindView(R2.id.id_push_chat_emoji)
    ImageView mEmoji;

    @BindView(R2.id.id_push_chat_input)
    EditText mInput;

    @BindView(R2.id.id_push_chat_layout)
    RelativeLayout mChatLayout;

    @BindView(R2.id.id_push_emoji_grid)
    GridView mEmojiGrid;

    // TODO
    @BindView(R2.id.id_land_live_push_mask_layer)
    FrameLayout mMaskLayer;

    @BindView(R2.id.rl_top_layout)
    RelativeLayout mTopLayout;


    @BindView(R2.id.iv_land_live_change_source)
    ImageView mChangeSource;

    @BindView(R2.id.iv_land_live_change_audio_video)
    ImageView ivChangeAudioVideo;


    @BindView(R2.id.id_push_chat_send)
    Button idPushChatSend;

    @BindView(R2.id.id_push_bottom)
    LinearLayout idPushBottom;

    @BindView(R2.id.tv_phone_live_title)
    TextView mTitle;


    @BindView(R2.id.ll_pc_land_right_layout)
    LinearLayout mRightLayout;

    @BindView(R2.id.iv_announce_new)
    ImageView ivAnnounceNew;

    @BindView(R2.id.iv_land_live_video_rtc)
    ImageView rtcView;

    Context mContext;

    DWLive dwLive = DWLive.getInstance();

    View mRoot;

    TextView livingSign;

    InputMethodManager mImm;

    public PcLiveLandscapeViewManager(Context context, View parentView, View rootView, TextView livingSign, RtcPopup rtcPopup, InputMethodManager mImm) {
        this.mContext = context;
        ButterKnife.bind(this, parentView);
        mRoot = rootView;
        this.livingSign = livingSign;
        this.mImm = mImm;
        this.rtcPopup = rtcPopup;

        rtcPopup.setOnDismissListener(listener);
    }

    public RtcPopup.OnDismissListener listener = new RtcPopup.OnDismissListener() {
        @Override
        public void onDismiss() {
            if (mContext instanceof PcLivePlayActivity) {
                if (((PcLivePlayActivity) mContext).isSpeaking) {
                    rtcView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.video_ic_lianmai_hov));
                }
            }
            postDelayScreenDisappear(); // 重置消失时间
        }
    };

    public void init() {
        initEmoji();
        initChatLayout();
        initAnnouncePopup();
        initSourceChangePopup();
        initDefinitionPopup();
        mTitle.setText(DWLive.getInstance().getRoomInfo().getName());
    }

    private void initEmoji() {
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
    }

    private void initChatLayout() {
        mInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showChatLayout();
                hideEmoji();
                handler.removeCallbacks(runnable);
                return false;
            }
        });

    }

    // 公告弹出框
    private AnnounceLandscapePopup mAnnouncePopup;

    // 直播源弹出框
    private LiveSourcePcLandscapePopup mLiveSourcePopup;

    private void initAnnouncePopup() {
        mAnnouncePopup = new AnnounceLandscapePopup(mContext);
        mAnnouncePopup.setOnPopupDismissListener(new BasePopupWindow.OnPopupDismissListener() {
            @Override
            public void onDismiss() {
                hideAnnounce();
            }
        });

        mAnnouncePopup.setOutsideCancel(false);
        mAnnouncePopup.setKeyBackCancel(true);

        if (dwLive.getAnnouncement() != null) {
            mAnnouncePopup.setAnnounceContent(dwLive.getAnnouncement());
            ivAnnounceNew.setVisibility(View.VISIBLE);
        }
    }

    private void initSourceChangePopup() {
        mLiveSourcePopup = new LiveSourcePcLandscapePopup(mContext);
        mLiveSourcePopup.setOutsideCancel(true);
        mLiveSourcePopup.setKeyBackCancel(true);
        mLiveSourcePopup.setOnPopupDismissListener(mOnPopupDismissListener);
    }

    BasePopupWindow.OnPopupDismissListener mOnPopupDismissListener = new BasePopupWindow.OnPopupDismissListener() {
        @Override
        public void onDismiss() {
            postDelayScreenDisappear(); // 重置消失时间
        }
    };


    private DefinitionPopup definitionPopup;

    private void initDefinitionPopup() {
        definitionPopup = new DefinitionPopup(mContext);
        definitionPopup.setOutsideCancel(true);
        definitionPopup.setKeyBackCancel(true);
        definitionPopup.setOnPopupDismissListener(mOnPopupDismissListener);
    }

    void showSourcePopupWindow() {
        handler.removeCallbacks(runnable);
        mLiveSourcePopup.show(mRoot);
    }

    void showDefinitionWindow() {
        handler.removeCallbacks(runnable);
        definitionPopup.show(mRoot);
    }

    @OnClick({R2.id.tv_phone_live_announce,
            R2.id.iv_land_live_change_source,
            R2.id.iv_phone_live_barrage,
            R2.id.iv_phone_live_close,
            R2.id.tv_phone_definition,
            R2.id.id_land_live_push_mask_layer,
            R2.id.id_push_chat_emoji,
            R2.id.iv_land_live_change_audio_video,
            R2.id.id_push_chat_send,
            R2.id.iv_land_live_video_rtc})
    public void onClick(View view) {
        postDelayScreenDisappear(); // 重置消失时间


        // TODO 待测试
        if (mContext instanceof PcLivePlayActivity) {
            if (((PcLivePlayActivity) mContext).isSpeaking && view.getId() != R.id.iv_land_live_video_rtc && view.getId() != R.id.iv_phone_live_close) {
                toastOnUiThread("连麦中，暂不可用");
                return;
            }
        }

        int i = view.getId();
        if (i == R.id.tv_phone_live_announce) {
            showAnnounce();

        } else if (i == R.id.iv_land_live_change_source) {
            showSourcePopupWindow();

        } else if (i == R.id.iv_phone_live_barrage) {
            changeBarrageStatus();

        } else if (i == R.id.iv_phone_live_close) {
            changeToPortraitLayout();

        } else if (i == R.id.tv_phone_definition) {
            showDefinitionWindow();

        } else if (i == R.id.id_land_live_push_mask_layer) {
            dismissAll();

        } else if (i == R.id.id_push_chat_emoji) {
            emoji();

        } else if (i == R.id.iv_land_live_change_audio_video) {
            changeVideoAudioStatus();

        } else if (i == R.id.id_push_chat_send) {
            sendMsg();

        } else if (i == R.id.iv_land_live_video_rtc) {
            showRtcPopup();

        }
    }

    void changeToPortraitLayout() {
        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    void showAnnounce() {
        mAnnouncePopup.show(mRoot);
        setScreenVisible(false, false);

        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity) mContext).onShowAnnounce();
        }
    }

    public void onShowAnnouce() {
        ivAnnounceNew.setVisibility(View.INVISIBLE);
    }

    void hideAnnounce() {
        setScreenVisible(true, true);
    }

    public void onNewAnnounce(boolean isRemove, String announcement, boolean isShown) {
        if (isRemove) {
            mAnnouncePopup.setAnnounceContent("暂无公告");
            ivAnnounceNew.setVisibility(View.INVISIBLE);

        } else {
            mAnnouncePopup.setAnnounceContent(announcement);
            if (isShown) {
                ivAnnounceNew.setVisibility(View.INVISIBLE);
            } else {
                ivAnnounceNew.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean isAnnouncementShown() {
        return mAnnouncePopup.isShown();
    }

    public void onInitFinish(int i, List<QualityInfo> list, final Surface surface) {
        List<String> sourceList = new ArrayList<String>();
        for (int j = 0; j < i; j++) {
            sourceList.add("线路" + (j + 1));
        }

        mLiveSourcePopup.setSelectedItem(0)
                .setAdapterList(sourceList)
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mLiveSourcePopup.setSelectedItem(i);
                        mLiveSourcePopup.dismiss();
                        if (mContext instanceof PcLivePlayActivity) {
                            ((PcLivePlayActivity) mContext).changeSource(false, i);
                        }
                    }
                });

        definitionPopup.setSelectedItem(0)
                .setAdapterList(list)
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        definitionPopup.setSelectedItem(i);
                        definitionPopup.dismiss();

                        dwLive.setQuality(i);
                        try {
                            dwLive.restartVideo(surface);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DWLiveException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void updateSourceSelectItem(int i) {
        mLiveSourcePopup.setSelectedItem(i);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setScreenVisible(false, false);
        }
    };

    // 返回值表示显示或者隐藏，true表示显示
    public boolean OnPlayClick() {
        if (mEmojiGrid.isShown()) {
            hideEmoji();
            return true;
        }

        if (mTopLayout.isShown()) {
            setScreenVisible(false, true);
            return false;
        } else {
            setScreenVisible(true, true);
            return true;
        }
    }

    private void postDelayScreenDisappear() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 5 * 1000);
    }

    public void clearChatInput() {
        mInput.setText("");
    }

    Handler handler = new Handler();

    public void setScreenVisible(boolean isDisplay, boolean isAnimation) {
        handler.removeCallbacks(runnable);

        if (isDisplay) {
            // 延迟5s消失
            postDelayScreenDisappear();

            mRightLayout.setVisibility(View.VISIBLE);
            mTopLayout.setVisibility(View.VISIBLE);
            idPushBottom.setVisibility(View.VISIBLE);
            mLeftLayout.setVisibility(View.VISIBLE);
            livingSign.setVisibility(View.GONE);

            if (isAnimation) {
                mTopLayout.startAnimation(getTranslateAnimation(0.0f, 0.0f, -1 * mTopLayout.getHeight(), 0.0f, true));
                idPushBottom.startAnimation(getTranslateAnimation(0.0f, 0.0f, idPushBottom.getHeight(), 0.0f, true));
                mRightLayout.startAnimation(getTranslateAnimation(mRightLayout.getWidth() * 1.5f, 0.0f, 0.0f, 0.0f, true));

                mLeftLayout.startAnimation(getTranslateAnimation(mLeftLayout.getWidth() * -1.5f, 0.0f, 0.0f,0.0f, true));

            }
        } else {
            livingSign.setVisibility(View.VISIBLE);
            idPushBottom.startAnimation(getTranslateAnimation(0.0f, 0.0f, 0.0f, idPushBottom.getHeight(), false));
            idPushBottom.setVisibility(View.GONE);

            mRightLayout.startAnimation(getTranslateAnimation(0.0f, mRightLayout.getWidth() * 1.5f, 0.0f, 0.0f, false));
            mRightLayout.setVisibility(View.GONE);

            mTopLayout.startAnimation(getTranslateAnimation(0.0f, 0.0f, 0.0f, -1 * mTopLayout.getHeight(), false));
            mTopLayout.setVisibility(View.GONE);

            mLeftLayout.startAnimation(getTranslateAnimation(0.0f, mLeftLayout.getWidth() * -1.5f, 0.0f, 0.0f, false));
            mLeftLayout.setVisibility(View.GONE);

        }
    }

    void sendMsg() { // 发送聊天
        sendChatMsg(mInput.getText().toString().trim());
    }

    public void sendChatMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            toastOnUiThread("发送信息不能为空");
        } else {
            dwLive.sendPublicChatMsg(msg);
        }

        clearChatInput();
    }

    void toastOnUiThread(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    private TranslateAnimation getTranslateAnimation(float fromX, float toX, float fromY, float toY, boolean isFillAfter) {
        TranslateAnimation animation = new TranslateAnimation(fromX, toX, fromY, toY);
        animation.setFillAfter(isFillAfter);
        animation.setDuration(300);
        return animation;
    }

    void changeBarrageStatus() {
        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity) mContext).changeBarrageStatus();
        }
    }

    public void onBarrageChanged(boolean isBarrageOn) {
        if (isBarrageOn) {
            mBarrageControl.setImageResource(R.mipmap.video_btn_word_on);
        } else {
            mBarrageControl.setImageResource(R.mipmap.video_btn_word_off);
        }
    }

    private boolean isVideo = true;

    void changeVideoAudioStatus() {
        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity) mContext).changeVideoAudioStatus();
        }
    }

    public void onVideoAudioChanged(boolean isVideo) {
        if (isVideo) {
            ivChangeAudioVideo.setImageResource(R.mipmap.video_ic_live_nor);

        } else {
            ivChangeAudioVideo.setImageResource(R.mipmap.video_ic_live_hov);
        }
    }

    void dismissAll() {
        mMaskLayer.setVisibility(View.GONE);
        if (isSoftInput) {
            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
        }
        hideChatLayot();
        hideEmoji();

        postDelayScreenDisappear();
    }

    void emoji() {
        showEmoji();
        handler.removeCallbacks(runnable);
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
        }
        postDelayScreenDisappear();
    }

    public void showChatLayout() {
        isChat = true;
        mChatLayout.setVisibility(View.VISIBLE);
        mMaskLayer.setVisibility(View.VISIBLE);
        mInput.setFocusableInTouchMode(true);
        mInput.requestFocus();
        mImm.showSoftInput(mInput, 0);
    }

    private boolean isPrivate;
    private boolean isChat;

    public void hideChatLayot() {
        if (isChat) {
            AlphaAnimation animation = new AlphaAnimation(0f, 1f);
            animation.setDuration(300L);
            mInput.setFocusableInTouchMode(false);
            mInput.clearFocus();
            mMaskLayer.setVisibility(View.GONE);
//            mChatLayout.setVisibility(View.INVISIBLE);
            isChat = false;
        }
    }

    // emoji是否需要显示 emoji是否显示
    private boolean isEmoji = false, isEmojiShow = false;
    // 软键盘是否显示
    private boolean isSoftInput = false;

    public void onSoftKeyChange(boolean isShow) {
        isSoftInput = isShow;
        if (!isSoftInput) { // 软键盘隐藏
            if (isEmoji) {
                mEmojiGrid.setVisibility(View.VISIBLE);// 避免闪烁
                isEmojiShow = true; // 修改emoji显示标记
                isEmoji = false; // 重置
            } else {
                hideChatLayot(); // 隐藏聊天操作区域
                postDelayScreenDisappear();
            }
        } else {
            hideEmoji();
        }
    }

    public boolean onBackPressed() {
        if (isEmojiShow) {
            hideEmoji();
            hideChatLayot();
            return true;
        } else {
            return false;
        }
    }

    public void onUserCountMsg(int i) {
        mUserCount.setText(String.valueOf(i));
    }

    public void onPause() {
        mMaskLayer.setVisibility(View.GONE);
        if (isSoftInput) {
            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
        }
        hideChatLayot();
        hideEmoji();
    }

    public void onDestroy() {
        handler.removeCallbacks(runnable);
    }

    RtcPopup rtcPopup;

    public void showRtcPopup() {
        if (rtcPopup.isShow()) {
            rtcPopup.dismiss();
        } else {
            if (mContext instanceof PcLivePlayActivity) {
                if (((PcLivePlayActivity) mContext).isAllowRtc) {
                    handler.removeCallbacks(runnable);
                    rtcPopup.showRtcPopupWindow(rtcView, rtcView.getWidth() + 21, -1 * rtcView.getHeight());
                    showNormalRtcIcon();
                } else {
                    Toast.makeText(mContext, "主播未开通连麦", Toast.LENGTH_SHORT).show(); //TODO 未开通连麦
                }
            }
        }
    }

    public void showNormalRtcIcon() {
        rtcView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.video_ic_lianmai_nor));
    }
}
