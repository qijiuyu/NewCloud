package com.seition.cloud.pro.newcloud.app.bean.user;

import com.jess.arms.base.bean.DataBean;

/**
 * 用户流水信息
 * Created by xzw on 2018/4/24.
 */

public class UserAccount extends DataBean<UserAccount> {
    private double  learn ;
    private double  split;
    private double  score;

    public double getLearn() {
        return learn;
    }

    public void setLearn(double learn) {
        this.learn = learn;
    }

    public double getSplit() {
        return split;
    }

    public void setSplit(double split) {
        this.split = split;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
