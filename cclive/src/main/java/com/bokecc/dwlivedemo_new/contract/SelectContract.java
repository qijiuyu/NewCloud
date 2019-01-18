package com.bokecc.dwlivedemo_new.contract;

import com.bokecc.dwlivedemo_new.base.contract.BaseContract;
import com.bokecc.sdk.mobile.push.entity.SpeedRtmpNode;

import java.util.ArrayList;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public interface SelectContract {
    interface View extends BaseContract.View {

        void showLoading();

        void dismissLoading();

        void updateServers(ArrayList<SpeedRtmpNode> rtmpNodes);
    }
    interface presenter {
        void testSpeed();
    }
}
