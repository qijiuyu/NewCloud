package com.seition.cloud.pro.newcloud.app;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-06-27.
 */

public class PayResponse extends DataBean<PayResponse> {
    private int is_free;
    private PayResponseAli alipay;
    private PayResponseWx wxpay;

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public PayResponseAli getAlipay() {
        return alipay;
    }

    public void setAlipay(PayResponseAli alipay) {
        this.alipay = alipay;
    }

    public PayResponseWx getWxpay() {
        return wxpay;
    }

    public void setWxpay(PayResponseWx wxpay) {
        this.wxpay = wxpay;
    }
}
