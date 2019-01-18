package com.seition.cloud.pro.newcloud.app.bean.login;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by addis on 2018/11/19.
 */
public class RegisterTypeInit extends DataBean<RegisterTypeInit> {
    String account_type;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }
}
