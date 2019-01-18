package com.seition.cloud.pro.newcloud.app.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by addis on 2017/11/22.
 */

public class TimerUtils {

    private static final int PAUSE = 0x11;
    private static final int START = 0x12;
    private static final int STOP = 0x13;
    private static final int COERCIVERESS_STOP = 0x14;

    protected TimerListener timerListener;
    private long timeCount;//时间总长度
    private long stepTimeCount;//步进时长
    private long nowTimeCount;//现在时间长度
    private int state = STOP;//计时器状态

    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (state) {
                case START:
                    nowTimeCount += stepTimeCount;
                    if (timerListener != null)
                        timerListener.TimerStep(getNowTime(), getRemainingTime());
                    if (timeCount != 0 && (nowTimeCount > timeCount || nowTimeCount == timeCount))
                        coercivenessStop();
                    timeHandler.sendEmptyMessageDelayed(0, stepTimeCount);
                    break;
                case PAUSE:
                    timeHandler.sendEmptyMessageDelayed(0, stepTimeCount);
                    pause();
                    break;
                case STOP:
//                    stop();
                    break;
                case COERCIVERESS_STOP:
                    break;
            }
        }
    };

    public TimerUtils(long time, long stepTime, long nowTime, TimerListener listener) {
        this.nowTimeCount = nowTime;
        this.stepTimeCount = stepTime;
        this.timeCount = time;
        this.timerListener = listener;
    }

    private long getRemainingTime() {
        return timeCount == 0 ? 0 : timeCount - nowTimeCount;
    }

    private long getNowTime() {
        return nowTimeCount;
    }

    public void start() {
        if (timerListener != null)
            timerListener.TimerLastStart(getNowTime(), getRemainingTime());
        if (state == STOP) {
            state = START;
            timeHandler.sendEmptyMessageDelayed(0, stepTimeCount);
        } else
            state = START;
    }

    public void stop() {//直接退出的操作
        if (timerListener != null)
            timerListener.TimerStop(getNowTime(), getRemainingTime(), false);
        state = STOP;
    }

    public void coercivenessStop() {//需要交卷的操作
        state = COERCIVERESS_STOP;
        if (timerListener != null)
            timerListener.TimerStop(getNowTime(), getRemainingTime(), true);
    }

    public void pause() {
        if (timerListener != null)
            timerListener.TimerPause(getNowTime(), getRemainingTime());
        state = PAUSE;
    }


    public interface TimerListener {
        void TimerLastStart(long nowTimeCount, long remainingTime);

        void TimerStep(long nowTimeCount, long remainingTime);

        void TimerStop(long nowTimeCount, long remainingTime, boolean isCoerciveress);

        void TimerPause(long nowTimeCount, long remainingTime);
    }

}