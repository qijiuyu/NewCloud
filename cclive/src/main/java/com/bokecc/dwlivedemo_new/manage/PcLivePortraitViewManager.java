package com.bokecc.dwlivedemo_new.manage;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.view.Surface;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.activity.PcLivePlayActivity;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.popup.AnnouncePcPortaritPopup;
import com.bokecc.dwlivedemo_new.popup.LiveSourcePcPortraitPopup;
import com.bokecc.dwlivedemo_new.popup.RtcPopup;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.pojo.QualityInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liufh on 2016/12/27.
 */

public class PcLivePortraitViewManager {

    //------------------顶部布局---------------------------
    @BindView(R2.id.rl_portrait_live_top_layout)
    RelativeLayout mTopLayout;
    @BindView(R2.id.iv_portrait_live_close)
    ImageView ivPortraitLiveClose;
    @BindView(R2.id.tv_portrait_live_title)
    TextView mTitle;
    @BindView(R2.id.tv_portrait_live_announce) //TODO 看看顶部导航栏是不是可以共用的
            TextView tvPortraitLiveAnnounce;
    @BindView(R2.id.iv_portrait_announce_new)
    ImageView ivAnnounceNew;

    //------------------右侧布局---------------------------
    @BindView(R2.id.ll_portrait_live_right_layout)
    LinearLayout mRightLayout;
    @BindView(R2.id.iv_portrait_live_change_audio_video)
    ImageView ivChangeAudioVideo;
    @BindView(R2.id.iv_portrait_live_barrage)
    ImageView ivPortraitLiveBarrage;
    @BindView(R2.id.iv_portrait_live_full)
    ImageView ivFullScreen;

    //------------------左侧布局---------------------------
    @BindView(R2.id.ll_portrait_live_left_layout)
    LinearLayout mLeftLayout;
    @BindView(R2.id.iv_portrait_live_rtc)
    ImageView rtcView;
    @BindView(R2.id.iv_portrait_live_change_line)
    ImageView ivPortraitLiveChangeLine;

    @BindView(R2.id.tv_portrait_live_user_count)
    TextView tvPortraitLiveUserCount;
    @BindView(R2.id.rl_pc_portrait_layout)
    RelativeLayout rlPcPortraitLayout;

    Context mContext;
    DWLive dwLive = DWLive.getInstance();

    View mRoot;

    View mParentView;

    InputMethodManager mImm;

    RtcPopup rtcPopup;

    TextView livingSign;

    public PcLivePortraitViewManager(Context context, View parentView, View rootView, TextView livingSign, RtcPopup rtcPopup, InputMethodManager mImm) {
        this.mContext = context;
        ButterKnife.bind(this, parentView);
        mRoot = rootView;
        this.livingSign = livingSign;
        mParentView = parentView;
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
        mTitle.setText(DWLive.getInstance().getRoomInfo().getName());
        initAnnouncePopup();
        initSourceChangePopup();
    }

    LiveSourcePcPortraitPopup mLiveSourcePopup;
    private void initSourceChangePopup() {
        mLiveSourcePopup = new LiveSourcePcPortraitPopup(mContext);
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

    @OnClick(R2.id.iv_portrait_live_full)
    void showLandscapeLayout(View v) {
        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public boolean onPlayClick() {
        if (mTopLayout.isShown()) {
            setScreenVisible(false, true);
            return false;
        } else {
            setScreenVisible(true, true);
            return true;
        }
    }

    Handler handler = new Handler();

    public void setScreenVisible(boolean isDisplay, boolean isAnimation) {
        handler.removeCallbacks(runnable);

        if (isDisplay) {
            // 延迟5s消失
            postDelayScreenDisappear();

            mRightLayout.setVisibility(View.VISIBLE);
            mTopLayout.setVisibility(View.VISIBLE);
            mLeftLayout.setVisibility(View.VISIBLE);
            tvPortraitLiveUserCount.setVisibility(View.VISIBLE);
            livingSign.setVisibility(View.GONE);

            if (isAnimation) {
                mLeftLayout.startAnimation(getTranslateAnimation(mLeftLayout.getWidth() * -1.5f, 0.0f, 0.0f,0.0f, true));
                mRightLayout.startAnimation(getTranslateAnimation(mRightLayout.getWidth() * 1.5f, 0.0f, 0.0f, 0.0f, true));
                mTopLayout.startAnimation(getTranslateAnimation(0.0f, 0.0f, -1 * mTopLayout.getHeight(), 0.0f, true));


            }
        } else {
            livingSign.setVisibility(View.VISIBLE);
            mLeftLayout.startAnimation(getTranslateAnimation(0.0f, mLeftLayout.getWidth() * -1.5f, 0.0f, 0.0f, false));
            mLeftLayout.setVisibility(View.GONE);

            mRightLayout.startAnimation(getTranslateAnimation(0.0f, mRightLayout.getWidth() * 1.5f, 0.0f, 0.0f, false));
            mRightLayout.setVisibility(View.GONE);

            mTopLayout.startAnimation(getTranslateAnimation(0.0f, 0.0f, 0.0f, -1 * mTopLayout.getHeight(), false));
            mTopLayout.setVisibility(View.GONE);

            tvPortraitLiveUserCount.setVisibility(View.GONE);

        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setScreenVisible(false, false);
        }
    };

    private void postDelayScreenDisappear() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 5 * 1000);
    }

    private TranslateAnimation getTranslateAnimation(float fromX, float toX, float fromY, float toY, boolean isFillAfter) {
        TranslateAnimation animation = new TranslateAnimation(fromX, toX, fromY, toY);
        animation.setFillAfter(isFillAfter);
        animation.setDuration(300);
        return animation;
    }

    void toastOnUiThread(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({
            R2.id.tv_portrait_live_announce,
            R2.id.iv_portrait_live_change_line,
            R2.id.iv_portrait_live_barrage,
            R2.id.iv_portrait_live_close,
            R2.id.iv_portrait_live_change_audio_video,
            R2.id.iv_portrait_live_rtc,
    })
    public void onClick(View view) {

        postDelayScreenDisappear(); // 重置消失时间

        if (mContext instanceof PcLivePlayActivity) {
            if (((PcLivePlayActivity) mContext).isSpeaking && view.getId() != R.id.iv_portrait_live_rtc && view.getId() != R.id.iv_portrait_live_close) {
                toastOnUiThread("连麦中，暂不可用");
                return;
            }
        }

        int i = view.getId();
        if (i == R.id.tv_portrait_live_announce) {
            showAnnounce();

        } else if (i == R.id.iv_portrait_live_change_line) {
            showSourcePopupWindow();

        } else if (i == R.id.iv_portrait_live_barrage) {
            changeBarrageStatus();

        } else if (i == R.id.iv_portrait_live_close) {
            if (mContext instanceof PcLivePlayActivity) {
                ((PcLivePlayActivity) mContext).showClosePopupWindow();
            }

        } else if (i == R.id.iv_portrait_live_change_audio_video) {
            changeVideoAudioStatus();

        } else if (i == R.id.iv_portrait_live_rtc) {
            showRtcPopup();

        }
    }

    void showSourcePopupWindow() {
//        mLiveSourcePopup.show(mParentView);
        mLiveSourcePopup.show(mParentView);
        handler.removeCallbacks(runnable);
    }

    void changeBarrageStatus() {
        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity) mContext).changeBarrageStatus();
        }
    }

    public void onBarrageChanged(boolean isBarrageOn) {
        if (isBarrageOn) {
            ivPortraitLiveBarrage.setImageResource(R.mipmap.video_ic_barrage_nor);
        } else {
            ivPortraitLiveBarrage.setImageResource(R.mipmap.video_ic_barrage_ban);
        }
    }

    public void changeVideoAudioStatus() {
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

    public void updateSourceSelectItem(int i) {
        mLiveSourcePopup.setSelectedItem(i);
    }

    public void onUserCountMsg(int i) {
        tvPortraitLiveUserCount.setText(String.valueOf(i));
    }


    // 公告弹出框
    private AnnouncePcPortaritPopup mAnnouncePopup;
    private void initAnnouncePopup() {
        mAnnouncePopup = new AnnouncePcPortaritPopup(mContext);
        mAnnouncePopup.setOnPopupDismissListener(new BasePopupWindow.OnPopupDismissListener() {
            @Override
            public void onDismiss() {
                hideAnnounce();
            }
        });

        mAnnouncePopup.setOutsideCancel(true);
        mAnnouncePopup.setKeyBackCancel(true);

        if (dwLive.getAnnouncement() != null) {
            mAnnouncePopup.setAnnounceContent(dwLive.getAnnouncement());
            ivAnnounceNew.setVisibility(View.VISIBLE);
        }
    }

    void showAnnounce() {
        mAnnouncePopup.show(mParentView);
        setScreenVisible(false, false);

        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity) mContext).onShowAnnounce();
        }
    }

    public void onShowAnnounce() {
        ivAnnounceNew.setVisibility(View.INVISIBLE);
    }

    void hideAnnounce() {
        setScreenVisible(true, true);
    }

    /**
     * 收到新的公告
     * @param isRemove
     * @param announcement
     * @param isShown 公告界面是否正在展示
     */
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
                            ((PcLivePlayActivity) mContext).changeSource(true, i);
                        }
                    }
                });

    }

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
