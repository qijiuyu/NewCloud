package com.seition.cloud.pro.newcloud.app.bean.common;

import com.jess.arms.base.bean.DataBean;


public class FollowState extends DataBean<FollowState> {

    private int following;//当前用户是否关注TA
    private int follower;//是否为当前用户的粉丝

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }
}
