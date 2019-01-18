package com.bokecc.dwlivedemo_new.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.bokecc.dwlivedemo_new.base.presenter.BasePresenter;
import com.bokecc.dwlivedemo_new.contract.SelectContract;
import com.bokecc.sdk.mobile.push.entity.SpeedRtmpNode;
import com.bokecc.sdk.mobile.push.tools.DWRtmpNodeTool;

import java.util.ArrayList;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class SelectPresenter extends BasePresenter<SelectContract.View> implements SelectContract.presenter {

    private Handler mHandler;

    public SelectPresenter(Activity context, SelectContract.View view) {
        super(context, view);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void testSpeed() {
        mView.showLoading();
        DWRtmpNodeTool.testSpeedForRtmpNodes(new DWRtmpNodeTool.OnTestSpeedFinishListener() {
            @Override
            public void onFinish(final ArrayList<SpeedRtmpNode> rtmpNodes) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.dismissLoading();
                        mView.updateServers(rtmpNodes);
                    }
                });
            }

            @Override
            public void onError(String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.dismissLoading();
                    }
                });
                mView.toastOnUiThread(s);
            }
        });
    }
}
