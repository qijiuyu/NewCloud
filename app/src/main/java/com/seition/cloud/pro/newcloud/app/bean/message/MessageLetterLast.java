package com.seition.cloud.pro.newcloud.app.bean.message;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.adapter.MessageChatAdapter;

public class MessageLetterLast extends MBaseBean implements MultiItemEntity {
    private int uid;
    private int from_uid;
    private String content;
    private String mtime;
    private MessageUserInfo user_from_info;
    private MessageUserInfo user_info;
    private int message_id;
    private int list_id;

    public MessageUserInfo getUser_info() {
        return user_info;
    }

    public void setUser_info(MessageUserInfo user_info) {
        this.user_info = user_info;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public int getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(int from_uid) {
        this.from_uid = from_uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageUserInfo getUser_from_info() {
        return user_from_info;
    }

    public void setUser_from_info(MessageUserInfo user_from_info) {
        this.user_from_info = user_from_info;
    }

    @Override
    public int getItemType() {
        if (user_info == null)
            return MessageChatAdapter.RIGHT_TYPE;
        if (from_uid == uid)
            return MessageChatAdapter.RIGHT_TYPE;
        else
            return MessageChatAdapter.LEFT_TYPE;
    }
}
