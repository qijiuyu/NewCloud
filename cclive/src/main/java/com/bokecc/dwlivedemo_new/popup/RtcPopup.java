package com.bokecc.dwlivedemo_new.popup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.activity.PcLivePlayActivity;
import com.bokecc.dwlivedemo_new.util.DensityUtil;
import com.bokecc.sdk.mobile.live.DWLive;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 连麦弹出界面
 * Created by liufh on 2016/12/30.
 */

public class RtcPopup {

    PopupWindow popupWindow;
    Context mContext;
    View parentView;


    public RtcPopup(Context mContext) {
        this.mContext = mContext;
        initRtcPopupWindow();
    }

    void initRtcPopupWindow() {

        popupWindow = new PopupWindow(mContext);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        parentView = inflater.inflate(R.layout.live_rtc_layout, null);
        initContentView();

        popupWindow.setContentView(parentView);
        popupWindow.setWidth(DensityUtil.dp2px(mContext, 158));
        popupWindow.setHeight(DensityUtil.dp2px(mContext, 101));

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                if (listenerList.size() > 0) {
                    for (OnDismissListener listener: listenerList) {
                        listener.onDismiss();
                    }
                }
            }
        });

    }


    LinearLayout noNetworkLayout;
    LinearLayout permissionLayout;
    LinearLayout applyLayout;
    LinearLayout applyedLayout;
    LinearLayout connnectedLayout;

    Button btnApply;
    Button btnCancel;
    Button btnHangup;

    TextView rtcCounter;

    TextView cameraPermission;
    TextView audioPermission;

    void initContentView() {
        noNetworkLayout = findViewById(parentView, R.id.ll_rtc_no_network);
        permissionLayout = findViewById(parentView, R.id.ll_rtc_permission);
        applyLayout = findViewById(parentView, R.id.ll_rtc_apply);
        applyedLayout = findViewById(parentView, R.id.ll_rtc_applyed);
        connnectedLayout = findViewById(parentView, R.id.ll_rtc_connected);

        btnApply = findViewById(parentView, R.id.btn_rtc_apply);
        btnApply.setOnClickListener(listener);
        btnCancel = findViewById(parentView, R.id.btn_rtc_cancel);
        btnCancel.setOnClickListener(listener);
        btnHangup = findViewById(parentView, R.id.btn_rtc_hangup);
        btnHangup.setOnClickListener(listener);

        rtcCounter = findViewById(parentView, R.id.tv_rtc_counter);

        cameraPermission = findViewById(parentView, R.id.rtc_camera_permission);
        audioPermission = findViewById(parentView, R.id.rtc_audio_permission);
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.btn_rtc_apply) {
                applyRtc();

            } else if (i == R.id.btn_rtc_cancel) {
                cancelRtc();

            } else if (i == R.id.btn_rtc_hangup) {
                hangupRtc();

            }
        }
    };

    private void applyRtc() {
        setViewGone();
        applyedLayout.setVisibility(View.VISIBLE);

        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity)mContext).onApplyRtc();
        }
    }

    private void cancelRtc() {
        resetView();
        dismiss();
        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity)mContext).onCancelRtc();
        }
    }

    private void hangupRtc() {
        resetView();
        dismiss();
        if (mContext instanceof PcLivePlayActivity) {
            ((PcLivePlayActivity)mContext).onHangupRtc();
        }
    }

    public void resetView() {
        ((PcLivePlayActivity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setViewGone();
                applyLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void showNoNetworkView() {

        DWLive.getInstance().closeCamera();

        ((PcLivePlayActivity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setViewGone();
                noNetworkLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void showConnectedView() {
        setViewGone();
        connnectedLayout.setVisibility(View.VISIBLE);
    }

    void setViewGone() {
        noNetworkLayout.setVisibility(View.GONE);
        permissionLayout.setVisibility(View.GONE);
        applyLayout.setVisibility(View.GONE);
        applyedLayout.setVisibility(View.GONE);
        connnectedLayout.setVisibility(View.GONE);
    }

    public void showRtcPopupWindow(View view, int xoff, int yoff) {
        checkPermissionAndNetwork();
        if (hasCameraPermission && hasAudioPermission) {
            popupWindow.setWidth(DensityUtil.dp2px(mContext, 158));
            popupWindow.setHeight(DensityUtil.dp2px(mContext, 101));
        } else {
            popupWindow.setWidth(DensityUtil.dp2px(mContext, 187));
            popupWindow.setHeight(DensityUtil.dp2px(mContext, 134));
        }

        popupWindow.showAsDropDown(view, xoff, yoff);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        startNetworkTimer();
    }


    boolean isNetworkConnected;
    private void startNetworkTimer() {

        if (timerTask != null) {
            timerTask.cancel();
        }

        if (((PcLivePlayActivity) mContext).isNetworkConnected()) {
            isNetworkConnected = true;
        } else {
            isNetworkConnected = false;
            showNoNetworkView();
        }

        timerTask = new TimerTask() {

            @Override
            public void run() {

                if (((PcLivePlayActivity) mContext).isNetworkConnected()) {

                    if (isNetworkConnected) {
                        return;
                    } else {
                        resetView();
                    }
                    isNetworkConnected = true;

                } else {

                    if (isNetworkConnected) {
                        showNoNetworkView();
                    } else {
                        return;
                    }
                    isNetworkConnected = false;
                }

            }
        };

        timer.schedule(timerTask, 0, 1 * 1000);
    }

    private void stopNetworkTimer() {
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private Timer timer = new Timer();

    private TimerTask timerTask;

    public <T extends View> T findViewById(View view, int resId) {
        return (T)view.findViewById(resId);
    }

    public void dismiss() {
        popupWindow.dismiss();

        stopNetworkTimer();
        if (!((PcLivePlayActivity) mContext).isNetworkConnected()) {
            resetView();
        }
    }

    public boolean isShow() {
        return popupWindow.isShowing();
    }

    public void setCounterText(int count) {
        rtcCounter.setText(formatTime(count));
    }

    private String formatTime(int t) {
        StringBuilder sb = new StringBuilder("视频连麦中: ");

        int minTime = t / 60;
        int secondTime = t % 60;

        sb.append(addZero(minTime));
        sb.append(":");
        sb.append(addZero(secondTime));
        return sb.toString();
    }

    private String addZero(int t){
        return t > 9?String.valueOf(t): String.valueOf("0" + t);
    }

    ArrayList<OnDismissListener> listenerList = new ArrayList<>();
    public void setOnDismissListener(OnDismissListener listener) {
        listenerList.add(listener);
    }


    public interface OnDismissListener {
        public void onDismiss();
    }

    boolean hasCameraPermission = true;
    boolean hasAudioPermission = true;
    private void checkPermissionAndNetwork() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            setNoPermissionTheme(cameraPermission);
            hasCameraPermission = false;
            cameraPermission.setOnClickListener(mPermissionOnclickListener);
        } else {
            setHasPermissionTheme(cameraPermission);
            cameraPermission.setOnClickListener(null);
            hasCameraPermission = true;
        }

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            setNoPermissionTheme(audioPermission);
            hasAudioPermission = false;
            audioPermission.setOnClickListener(mPermissionOnclickListener);
        } else {
            setHasPermissionTheme(audioPermission);
            audioPermission.setOnClickListener(null);
            hasAudioPermission = true;
        }

        //TODO 看看对勾的效果
        if (hasAudioPermission && hasCameraPermission) {
            if (mContext instanceof PcLivePlayActivity) {
                if (!(((PcLivePlayActivity) mContext).isNetworkConnected()) && !(((PcLivePlayActivity) mContext).isSpeaking)) {
                    showNoNetworkView();
                    return;
                }
            }

            setViewGone();
            if (((PcLivePlayActivity) mContext).isSpeaking) {
                connnectedLayout.setVisibility(View.VISIBLE);
            } else if (((PcLivePlayActivity) mContext).isRtc) {
                applyedLayout.setVisibility(View.VISIBLE);
            } else {
                applyLayout.setVisibility(View.VISIBLE);
            }

        } else {
            setViewGone();
            permissionLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setHasPermissionTheme(TextView tv) {
        tv.setTextColor(Color.argb(102, 255, 255, 255));
        Drawable rightDrawable =  mContext.getResources().getDrawable(R.mipmap.line_btn_select);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, rightDrawable, null);
        tv.setCompoundDrawablePadding(10);
    }

    private void setNoPermissionTheme(TextView tv) {
        tv.setTextColor(Color.argb(255, 255, 255, 255));
        tv.setCompoundDrawables(null, null, null, null);
    }

    private View.OnClickListener mPermissionOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getPermission();
        }
    };

    private void getPermission() {
        dismiss();
        mContext.startActivity(getAppDetailSettingIntent());
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        return localIntent;
    }
}
