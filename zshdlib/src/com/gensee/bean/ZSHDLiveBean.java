package com.gensee.bean;

import java.io.Serializable;

/**
 * Created by addis on 2018/7/17.
 */
public class ZSHDLiveBean implements Serializable {
    String number;
    String joinPwd;
    String domain;
    String account;
    String pwd;
    String nickName;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getJoinPwd() {
        return joinPwd;
    }

    public void setJoinPwd(String joinPwd) {
        this.joinPwd = joinPwd;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
