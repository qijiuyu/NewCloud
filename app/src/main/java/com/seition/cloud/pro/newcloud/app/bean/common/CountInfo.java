package com.seition.cloud.pro.newcloud.app.bean.common;

import com.jess.arms.base.bean.MBaseBean;

import java.io.Serializable;

public class CountInfo extends MBaseBean {

    private int check_connum;
    private int check_totalnum;

    /*
       讲师详情下的
     */
    private int follower_count;
    private int following_count;
    private int new_folower_count;
    private int feed_count;
    private int favorite_count;
    private int weibo_count;

    public int getCheck_connum() {
        return check_connum;
    }

    public void setCheck_connum(int check_connum) {
        this.check_connum = check_connum;
    }

    public int getCheck_totalnum() {
        return check_totalnum;
    }

    public void setCheck_totalnum(int check_totalnum) {
        this.check_totalnum = check_totalnum;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getNew_folower_count() {
        return new_folower_count;
    }

    public void setNew_folower_count(int new_folower_count) {
        this.new_folower_count = new_folower_count;
    }

    public int getFeed_count() {
        return feed_count;
    }

    public void setFeed_count(int feed_count) {
        this.feed_count = feed_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getWeibo_count() {
        return weibo_count;
    }

    public void setWeibo_count(int weibo_count) {
        this.weibo_count = weibo_count;
    }
}
