package com.seition.cloud.pro.newcloud.app.bean.order;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-07-24.
 */

public class OrderRefund extends DataBean<OrderRefund> {
    private String price;
    private String pay_type;
    private String type;
    private String refundConfig;
    private String refund_numsty;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefundConfig() {
        return refundConfig;
    }

    public void setRefundConfig(String refundConfig) {
        this.refundConfig = refundConfig;
    }

    public String getRefund_numsty() {
        return refund_numsty;
    }

    public void setRefund_numsty(String refund_numsty) {
        this.refund_numsty = refund_numsty;
    }
}
