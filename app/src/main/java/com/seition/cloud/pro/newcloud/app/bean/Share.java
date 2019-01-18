package com.seition.cloud.pro.newcloud.app.bean;


import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-07-03.
 */

public class Share extends DataBean<Share> {
    String share_url;

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
}
