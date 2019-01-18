package com.seition.cloud.pro.newcloud.app;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-07-20.
 */

public class PayResponseAli extends DataBean {
    private String ios;
    private String basic;

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }
}
