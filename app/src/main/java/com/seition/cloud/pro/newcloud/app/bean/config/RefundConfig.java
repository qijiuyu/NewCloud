package com.seition.cloud.pro.newcloud.app.bean.config;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by addis on 2018/11/23.
 */
public class RefundConfig extends DataBean<RefundConfig> {
    private int refund_switch;
    private int refund_numday;

    public int getRefund_switch() {
        return refund_switch;
    }

    public void setRefund_switch(int refund_switch) {
        this.refund_switch = refund_switch;
    }

    public int getRefund_numday() {
        return refund_numday;
    }

    public void setRefund_numday(int refund_numday) {
        this.refund_numday = refund_numday;
    }
}
