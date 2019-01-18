package com.seition.cloud.pro.newcloud.app.bean.course;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/6/15.
 */
public class SeitionBody extends MBaseBean {
    private int uid;
    private String domain;
    private String account;
    private String pwd;
    private String join_pwd;
    private String number;
    private String livePlayback;
    private String userid;
    private String roomid;
    private String teacher_join_pwd;
    private String teacher_join_url;
    private String student_join_pwd;
    private String student_join_url;
    private String k;
    private int is_live;
    private int is_teacher;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        k = k;
    }

    public String getTeacher_join_pwd() {
        return teacher_join_pwd;
    }

    public void setTeacher_join_pwd(String teacher_join_pwd) {
        this.teacher_join_pwd = teacher_join_pwd;
    }

    public String getTeacher_join_url() {
        return teacher_join_url;
    }

    public void setTeacher_join_url(String teacher_join_url) {
        this.teacher_join_url = teacher_join_url;
    }

    public String getStudent_join_pwd() {
        return student_join_pwd;
    }

    public void setStudent_join_pwd(String student_join_pwd) {
        this.student_join_pwd = student_join_pwd;
    }

    public String getStudent_join_url() {
        return student_join_url;
    }

    public void setStudent_join_url(String student_join_url) {
        this.student_join_url = student_join_url;
    }

    public int getIs_teacher() {
        return is_teacher;
    }

    public void setIs_teacher(int is_teacher) {
        this.is_teacher = is_teacher;
    }

    public String getLivePlayback() {
        return livePlayback;
    }

    public void setLivePlayback(String livePlayback) {
        this.livePlayback = livePlayback;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public int getIs_live() {
        return is_live;
    }

    public void setIs_live(int is_live) {
        this.is_live = is_live;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getJoin_pwd() {
        return join_pwd;
    }

    public void setJoin_pwd(String join_pwd) {
        this.join_pwd = join_pwd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
