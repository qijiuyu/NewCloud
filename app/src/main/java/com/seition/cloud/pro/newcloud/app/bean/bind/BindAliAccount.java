package com.seition.cloud.pro.newcloud.app.bean.bind;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018/6/5.
 */

public class BindAliAccount extends DataBean<BindAliAccount> {
    private String id;
    private String account;
    private String accountmaster;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
