package com.seition.cloud.pro.newcloud.app.bean.user;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018/5/10.
 */

public class RegistResp extends DataBean<RegistResp> {//ArrayList<RegistResp>

    private String status ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
