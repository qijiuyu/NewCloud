package com.seition.cloud.pro.newcloud.app.bean.organization;

import com.jess.arms.base.bean.MBaseBean;

import java.io.Serializable;

/**
 * Created by addis on 2016/11/8.
 */

public class OrganizationCount extends MBaseBean {

    private int follower_count;
    private int video_count;
    private int learn_count;
    private int comment_score;
    private int comment_star;
    private int view_count;
    private int teacher_count;
    private String comment_rate;

    public int getTeacher_count() {
        return teacher_count;
    }

    public void setTeacher_count(int teacher_count) {
        this.teacher_count = teacher_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getComment_rate() {
        return comment_rate;
    }

    public void setComment_rate(String comment_rate) {
        this.comment_rate = comment_rate;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public int getLearn_count() {
        return learn_count;
    }

    public void setLearn_count(int learn_count) {
        this.learn_count = learn_count;
    }

    public int getComment_score() {
        return comment_score;
    }

    public void setComment_score(int comment_score) {
        this.comment_score = comment_score;
    }

    public int getComment_star() {
        return comment_star;
    }

    public void setComment_star(int comment_star) {
        this.comment_star = comment_star;
    }
}
