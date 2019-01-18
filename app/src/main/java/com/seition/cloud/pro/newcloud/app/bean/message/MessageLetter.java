package com.seition.cloud.pro.newcloud.app.bean.message;

import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageLetter extends MBaseBean {
    private String list_id;//会话的id
    private String member_uid;
    private String newd;
    private String message_num;
    private String ctime;//发送时间
    private String list_ctime;
    private String is_del;
    private String from_uid;
    private String type;
    private String title;
    private String member_num;
    private String min_max;
    private String mtime;
    private MessageLetterLast last_message;
    private MessageUserInfo to_user_info;
    private MessageUserInfo and_user_info;

    public MessageUserInfo getAnd_user_info() {
        return and_user_info;
    }

    public void setAnd_user_info(MessageUserInfo and_user_info) {
        this.and_user_info = and_user_info;
    }

    public MessageUserInfo getTo_user_info() {
        return to_user_info;
    }

    public void setTo_user_info(MessageUserInfo to_user_info) {
        this.to_user_info = to_user_info;
    }

    public MessageLetterLast getLast_message() {
        return last_message;
    }

    public void setLast_message(MessageLetterLast last_message) {
        this.last_message = last_message;
    }


    public String getMember_uid() {
        return member_uid;
    }

    public void setMember_uid(String member_uid) {
        this.member_uid = member_uid;
    }

    public String getNewd() {
        return newd;
    }

    public void setNewd(String newd) {
        this.newd = newd;
    }

    public String getMessage_num() {
        return message_num;
    }

    public void setMessage_num(String message_num) {
        this.message_num = message_num;
    }

    public String getList_ctime() {
        return list_ctime;
    }

    public void setList_ctime(String list_ctime) {

        long time = Long.parseLong(list_ctime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.list_ctime = formatter.format(new Date(time * 1000));
//		this.list_ctime = list_ctime;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(String from_uid) {
        this.from_uid = from_uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMember_num() {
        return member_num;
    }

    public void setMember_num(String member_num) {
        this.member_num = member_num;
    }

    public String getMin_max() {
        return min_max;
    }

    public void setMin_max(String min_max) {
        this.min_max = min_max;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getList_id() {
        return list_id;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        long time = Long.parseLong(ctime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.ctime = formatter.format(new Date(time * 1000));
    }


}
