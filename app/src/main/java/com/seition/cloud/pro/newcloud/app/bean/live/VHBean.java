package com.seition.cloud.pro.newcloud.app.bean.live;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/7/18.
 */
public class VHBean extends MBaseBean {
    String user_id;
    String name;
    String email;
    String phone;
    String pwd_str;

    public String getPwd_str() {
        return pwd_str;
    }

    public void setPwd_str(String pwd_str) {
        this.pwd_str = pwd_str;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
