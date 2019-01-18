package com.seition.cloud.pro.newcloud.app.bean.user;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018/4/24.
 */

public class UserMember extends DataBean<UserMember> {
    private String id;
    private String uid;
    private String vip_type;
    private String vip_expire;
    private String ctime;
    private String vip_type_txt;
    private String cover;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVip_type() {
        return vip_type;
    }

    public void setVip_type(String vip_type) {
        this.vip_type = vip_type;
    }

    public String getVip_expire() {
        return vip_expire;
    }

    public void setVip_expire(String vip_expire) {
        this.vip_expire = vip_expire;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getVip_type_txt() {
        return vip_type_txt;
    }

    public void setVip_type_txt(String vip_type_txt) {
        this.vip_type_txt = vip_type_txt;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
