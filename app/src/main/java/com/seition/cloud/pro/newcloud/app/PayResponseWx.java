package com.seition.cloud.pro.newcloud.app;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-07-20.
 */

public class PayResponseWx extends DataBean {
    private Pay ios;
    private Pay basic;

    public Pay getIos() {
        return ios;
    }

    public void setIos(Pay ios) {
        this.ios = ios;
    }

    public Pay getBasic() {
        return basic;
    }

    public void setBasic(Pay basic) {
        this.basic = basic;
    }
}
