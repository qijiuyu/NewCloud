package com.seition.cloud.pro.newcloud.app.bean.bind;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class BindBank extends DataBean<ArrayList<BindBank>> {

    private String id;
    private String accounttype;
    private String account;
    private String accountmaster;
    private String card_info_endnum;
    private String card_info_abb;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountmaster() {
        return accountmaster;
    }

    public void setAccountmaster(String accountmaster) {
        this.accountmaster = accountmaster;
    }

    public String getCard_info_endnum() {
        return card_info_endnum;
    }

    public void setCard_info_endnum(String card_info_endnum) {
        this.card_info_endnum = card_info_endnum;
    }

    public String getCard_info_abb() {
        return card_info_abb;
    }

    public void setCard_info_abb(String card_info_abb) {
        this.card_info_abb = card_info_abb;
    }
}
