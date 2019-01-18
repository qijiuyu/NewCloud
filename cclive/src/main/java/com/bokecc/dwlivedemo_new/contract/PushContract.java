package com.bokecc.dwlivedemo_new.contract;

import android.widget.EditText;

import com.bokecc.dwlivedemo_new.base.contract.BaseContract;
import com.bokecc.dwlivedemo_new.module.ChatEntity;
import com.bokecc.sdk.mobile.push.chat.model.ChatUser;
import com.bokecc.sdk.mobile.push.core.DWPushConfig;
import com.bokecc.sdk.mobile.push.view.DWTextureView;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public interface PushContract {

    interface View extends BaseContract.View {

        void showLoading();

        void dismissLoading();

        /**
         * 更新直播间人数
         */
        void updateRoomCount(int count);

        /**
         * 清空输入框
         */
        void clearChatInput();

        /**
         * 更新私聊图标
         */
        void updatePrivateChat(ChatEntity chatEntity);

        /**
         * 更新聊天界面
         */
        void updateChat(ChatEntity chatEntity);
    }

    interface Presenter {

        void onResume();

        void onPause();

        void onDestory();

        void setTextureView(DWTextureView textureView);

        /**
         * 开始推流
         */
        void start(DWPushConfig pushConfig);

        /**
         * 结束推流
         */
        void stop();

        /**
         * 切换摄像头
         */
        void swapCamera();

        /**
         * 开关声音
         */
        void toggleVolume(boolean isMute);

        /**
         * 开关美颜
         */
        void toggleBeauty(boolean isBeauty);

        /**
         * 发送聊天信息
         */
        void sendChatMsg(String msg, ChatUser to);

        /**
         * 循环获取聊天人数
         */
        void loopForChatCount();

        /**
         * 删除输入框的一个元素
         */
        void deleteInputOne(EditText mInput);

        /**
         * 添加emoji表情
         */
        void addEmoji(EditText mInput, int position);
    }

}
