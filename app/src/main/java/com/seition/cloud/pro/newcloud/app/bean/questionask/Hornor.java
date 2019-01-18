package com.seition.cloud.pro.newcloud.app.bean.questionask;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;

import java.util.ArrayList;

/**
 * Created by xzw on 2018-06-26.
 */

public class Hornor extends DataBean<ArrayList<Hornor>> {

    private String uid;
    private String count;
    private MessageUserInfo userinfo;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public MessageUserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(MessageUserInfo userinfo) {
        this.userinfo = userinfo;
    }
}
