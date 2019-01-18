package com.seition.cloud.pro.newcloud.app.bean.bind;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by addis on 2017/11/10.
 */

public class BankBean extends DataBean {

    private int id;
    private String accounttype;
    private String account;
    private String accountmaster;
    private String card_info;


    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccountmaster(String accountmaster) {
        this.accountmaster = accountmaster;
    }

    public String getAccountmaster() {
        return accountmaster;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setCard_info(String card_info) {
        this.card_info = card_info;
    }

    public String getCard_info() {
        return card_info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
