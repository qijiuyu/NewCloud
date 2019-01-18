package com.seition.cloud.pro.newcloud.app.bean.money;

import com.jess.arms.base.bean.DataBean;

public class MoneyDetail extends DataBean<MoneyDetail> {

    private String id;
    private String uid;
    private String type;
    private String num;
    private String balance;
    private String rel_id;
    private String rel_type;
    private String note;
    private String ctime;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRel_id() {
        return rel_id;
    }

    public void setRel_id(String rel_id) {
        this.rel_id = rel_id;
    }

    public String getRel_type() {
        return rel_type;
    }

    public void setRel_type(String rel_type) {
        this.rel_type = rel_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }




}
