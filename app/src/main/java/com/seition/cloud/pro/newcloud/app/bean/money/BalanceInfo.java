package com.seition.cloud.pro.newcloud.app.bean.money;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/5/30.
 */
public class BalanceInfo extends MBaseBean {
    int id;
    int uid;
    double score;
    double balance;
    double frozen;
    int vip_type;
    int vip_expire;
    int ctime;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFrozen() {
        return frozen;
    }

    public void setFrozen(double frozen) {
        this.frozen = frozen;
    }

    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }

    public int getVip_expire() {
        return vip_expire;
    }

    public void setVip_expire(int vip_expire) {
        this.vip_expire = vip_expire;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }
}
