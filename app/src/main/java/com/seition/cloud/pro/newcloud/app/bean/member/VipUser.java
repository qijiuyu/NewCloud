package com.seition.cloud.pro.newcloud.app.bean.member;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class VipUser extends DataBean<ArrayList<VipUser>> {

    private String uid;
    private String uname;
    private String user_head_portrait;

    public String getUser_head_portrait() {
        return user_head_portrait;
    }

    public void setUser_head_portrait(String user_head_portrait) {
        this.user_head_portrait = user_head_portrait;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


}
