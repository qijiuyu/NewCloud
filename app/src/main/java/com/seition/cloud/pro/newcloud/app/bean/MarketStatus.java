package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-08-14.
 * 营销数据开关
 */

public class MarketStatus extends DataBean<MarketStatus> {
    private int order_switch ;

    public int getOrder_switch() {
        return order_switch;
    }

    public void setOrder_switch(int order_switch) {
        this.order_switch = order_switch;
    }
}
