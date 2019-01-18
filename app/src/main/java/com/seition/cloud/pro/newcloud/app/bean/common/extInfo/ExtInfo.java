package com.seition.cloud.pro.newcloud.app.bean.common.extInfo;

import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.bean.common.CountInfo;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.common.Profile;

import java.util.ArrayList;


public class ExtInfo extends MBaseBean {

//    private String video_count;
    private String uname;
    private String space_url;
    private FollowState follow_state;
    private ArrayList<Profile> profile;
    private String avatar_big;
    private String avatar_middle;
    private String avatar_small;
    private String sex;
    private String intro;
    private String location;
    private CountInfo count_info;
//    private int follower_count;
//    private int following_count;

//    public int getFollower_count() {
//        return follower_count;
//    }
//
//    public void setFollower_count(int follower_count) {
//        this.follower_count = follower_count;
//    }
//
//    public int getFollowing_count() {
//        return following_count;
//    }
//
//    public void setFollowing_count(int following_count) {
//        this.following_count = following_count;
//    }

//    public String getVideo_count() {
//        return video_count;
//    }
//
//    public void setVideo_count(String video_count) {
//        this.video_count = video_count;
//    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getSpace_url() {
        return space_url;
    }

    public void setSpace_url(String space_url) {
        this.space_url = space_url;
    }

    public FollowState getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(FollowState follow_state) {
        this.follow_state = follow_state;
    }

    public ArrayList<Profile> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<Profile> profile) {
        this.profile = profile;
    }

    public String getAvatar_big() {
        return avatar_big;
    }

    public void setAvatar_big(String avatar_big) {
        this.avatar_big = avatar_big;
    }

    public String getAvatar_middle() {
        return avatar_middle;
    }

    public void setAvatar_middle(String avatar_middle) {
        this.avatar_middle = avatar_middle;
    }

    public String getAvatar_small() {
        return avatar_small;
    }

    public void setAvatar_small(String avatar_small) {
        this.avatar_small = avatar_small;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CountInfo getCount_info() {
        return count_info;
    }

    public void setCount_info(CountInfo count_info) {
        this.count_info = count_info;
    }
}
