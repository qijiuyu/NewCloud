package com.seition.cloud.pro.newcloud.app.bean.referrals;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by addis on 2018/11/28.
 */
public class OwnerQRCode extends DataBean<OwnerQRCode> {
    private String invite_url;

    public String getInvite_url() {
        return invite_url;
    }

    public void setInvite_url(String invite_url) {
        this.invite_url = invite_url;
    }
}
