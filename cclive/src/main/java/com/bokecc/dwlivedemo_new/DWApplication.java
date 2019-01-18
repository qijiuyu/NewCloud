package com.bokecc.dwlivedemo_new;

import android.app.Application;
import android.arch.lifecycle.BuildConfig;
import android.content.Context;

import com.bokecc.sdk.mobile.live.logging.LogHelper;
import com.bokecc.sdk.mobile.push.DWPushEngine;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author CC
 */
public class DWApplication extends Application {

    public static int mAppStatus = -1; // 表示 force_kill

    public static final boolean RTC_AUDIO = false; // 是否使用语音连麦 （true-使用 ／ false-不使用，采用视频连麦）

    public static final boolean REPLAY_CHAT_FOLLOW_TIME = true; // 是否让回放的聊天内容随时间轴推进展示

    public static final boolean REPLAY_QA_FOLLOW_TIME = true; // 是否让回放的问答内容随时间轴推进展示

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        if (context == null) {
            context = this;
        }
        // 异常统计
        CrashReport.initCrashReport(getApplicationContext(), "32421e5c07", true);
        // CC PUSH SDK
//        DWPushEngine.init(this, BuildConfig.LOG_FLAG);
        DWPushEngine.init(this, BuildConfig.DEBUG);
        // 初始化日志记录模块
        LogHelper.getInstance().init(context, true, null);
    }

    public static Context getContext() {
        return context;
    }

}
