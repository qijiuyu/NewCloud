package com.seition.cloud.pro.newcloud.app.bean.note;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.user.UserInfo;

public class NoteComment extends DataBean<NoteComment> {
    private String ctime;
    private UserInfo userinfo;
    private String note_description;

    private String note_comment_count;
//    private String ;
//    private String ;


    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public String getNote_description() {
        return note_description;
    }

    public void setNote_description(String note_description) {
        this.note_description = note_description;
    }

    public String getNote_comment_count() {
        return note_comment_count;
    }

    public void setNote_comment_count(String note_comment_count) {
        this.note_comment_count = note_comment_count;
    }
}
