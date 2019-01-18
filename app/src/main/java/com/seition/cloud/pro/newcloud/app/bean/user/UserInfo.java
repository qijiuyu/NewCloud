package com.seition.cloud.pro.newcloud.app.bean.user;

import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.common.Profile;
import com.seition.cloud.pro.newcloud.app.bean.common.CountInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by addis on 2016/11/8.
 */

public class UserInfo implements Serializable {

    private String uname;
    private String space_url;
    private FollowState followState;
    private ArrayList<Profile> profile;
    private String avatar_big;
    private String avatar_middle;
    private String avatar_small;
    private String sex;
    private String intro;
    private String location;
    private CountInfo countInfo;

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

    public FollowState getFollowState() {
        return followState;
    }

    public void setFollowState(FollowState followState) {
        this.followState = followState;
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

    public CountInfo getCountInfo() {
        return countInfo;
    }

    public void setCountInfo(CountInfo countInfo) {
        this.countInfo = countInfo;
    }
}
