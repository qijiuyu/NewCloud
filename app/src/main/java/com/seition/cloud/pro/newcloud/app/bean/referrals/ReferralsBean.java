package com.seition.cloud.pro.newcloud.app.bean.referrals;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/11/26.
 */
public class ReferralsBean extends MBaseBean {
    private String uid;
    private String uname;
    private String userCount;
    private long ctime;
    private double num;
    private int level;

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

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public double getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
