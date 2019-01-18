package com.bokecc.dwlivedemo_new;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.bokecc.dwlivedemo_new.activity.PilotActivity;
import com.bokecc.dwlivedemo_new.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DWApplication.mAppStatus = 0; // 当前状态正常
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreated() {
        //取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                go(PilotActivity.class);
                finish();
            }
        }, 3 * 1000L);
    }
}
