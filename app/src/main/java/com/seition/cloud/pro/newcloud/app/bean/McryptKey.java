package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-08-14.
 */

public class McryptKey extends DataBean<McryptKey> {
    private String  mcrypt_key;

    public String getMcrypt_key() {
        return mcrypt_key;
    }

    public void setMcrypt_key(String mcrypt_key) {
        this.mcrypt_key = mcrypt_key;
    }
}
