package com.seition.cloud.pro.newcloud.app.bean.user;

import java.io.Serializable;

/**
 * Created by xzw on 2018/4/2.
 */

public class UserGroup implements Serializable {


    private String user_group_id;
    private String pid;
    private String user_group_name;
    private String ctime;
    private String user_group_icon;
    private String user_group_type;
    private String app_name;
    private String is_authenticate;
    private String rule_list;
    private String uid  ;
    private String user_group_icon_url;

    public String getUser_group_id() {
        return user_group_id;
    }

    public void setUser_group_id(String user_group_id) {
        this.user_group_id = user_group_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUser_group_name() {
        return user_group_name;
    }

    public void setUser_group_name(String user_group_name) {
        this.user_group_name = user_group_name;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUser_group_icon() {
        return user_group_icon;
    }

    public void setUser_group_icon(String user_group_icon) {
        this.user_group_icon = user_group_icon;
    }

    public String getUser_group_type() {
        return user_group_type;
    }

    public void setUser_group_type(String user_group_type) {
        this.user_group_type = user_group_type;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getIs_authenticate() {
        return is_authenticate;
    }

    public void setIs_authenticate(String is_authenticate) {
        this.is_authenticate = is_authenticate;
    }

    public String getRule_list() {
        return rule_list;
    }

    public void setRule_list(String rule_list) {
        this.rule_list = rule_list;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_group_icon_url() {
        return user_group_icon_url;
    }

    public void setUser_group_icon_url(String user_group_icon_url) {
        this.user_group_icon_url = user_group_icon_url;
    }
}
