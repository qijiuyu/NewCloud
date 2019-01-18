package com.bokecc.dwlivedemo_new.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 直播悬浮窗
 * 需要权限 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 *
 * @author CC视频
 */
public class LiveFloatingView {

    private static final int stateBarHeight = 50;
    private static final int liveWidth = 600;
    private static final int liveHeight = 400;

    private Context mContext;

    private WindowManager windowManager = null;
    private WindowManager.LayoutParams wmParams = null;
    private Display currentDisplay;


    private ViewGroup mLiveLayout;

    private LinearLayout mContainer;
    private LinearLayout mTempLayout;

    /**
     * 构造函数
     *
     * @param context    上下文
     * @param liveLayout 包含播放器的View
     */
    public LiveFloatingView(Context context, ViewGroup liveLayout) {
        mContext = context;
        mLiveLayout = liveLayout;
        initWindow();
    }

    private void initWindow() {
        windowManager = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        wmParams = new WindowManager.LayoutParams();
        // TYPE_TOAST 生命周期不兼容
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wmParams.format = PixelFormat.TRANSLUCENT;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.width = liveWidth;
        wmParams.height = liveHeight;
        wmParams.x = 0;
        wmParams.y = 0 - stateBarHeight;
        currentDisplay = windowManager.getDefaultDisplay();

        // 尝试添加，探测无权限崩溃
        mTempLayout = new LinearLayout(mContext);
        mTempLayout.setLayoutParams(new LinearLayout.LayoutParams(1,1));
        windowManager.addView(mTempLayout, wmParams);

        mLiveLayout.setLayoutParams(new ViewGroup.LayoutParams(liveWidth, liveHeight));
        mContainer = new LinearLayout(mContext);
        mContainer.setLayoutParams(new LinearLayout.LayoutParams(liveWidth, liveHeight));
        mContainer.addView(mLiveLayout);
        mLiveLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                onTouchEvent(paramMotionEvent);
                return true;
            }
        });

        windowManager.addView(mContainer, wmParams);
    }


    private boolean IsDouble = false;
    private float lastX;
    private float lastY;

    private boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 1) {
                    IsDouble = false;
                }
                if (IsDouble == false) {
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() > 1) {
                    IsDouble = true;
                }
                if (IsDouble == false) {
                    int deltaX = (int) (event.getRawX() - lastX);
                    lastX = event.getRawX();
                    int deltaY = (int) (event.getRawY() - lastY);
                    lastY = event.getRawY();
                    updateViewPosition(deltaX, deltaY);
                }
                break;

            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 更新悬浮窗的位置
     *
     * @param deltaX X方向移动距离
     * @param deltaY Y方向移动距离
     */
    private void updateViewPosition(int deltaX, int deltaY) {
        int newX = wmParams.x + deltaX;
        int newY = wmParams.y + deltaY;
        newX = newX < 0 ? 0 : newX;
        newY = (newY + stateBarHeight) < 0 ? (0 - stateBarHeight) : newY;
        newX = (newX + liveWidth) > currentDisplay.getWidth() ? (currentDisplay.getWidth() - liveWidth) : newX;
        newY = (newY + liveHeight) > (currentDisplay.getHeight() - stateBarHeight) ? (currentDisplay.getHeight() - stateBarHeight - liveHeight) : newY;
        wmParams.x = newX;
        wmParams.y = newY;
        windowManager.updateViewLayout(mContainer, wmParams);
    }

    public void removeView() {
        mLiveLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mContainer.removeView(mLiveLayout);
        windowManager.removeView(mContainer);
    }

    /**
     * 退出悬浮窗
     */
    public void quit() {
        windowManager.removeView(mTempLayout);
    }


}