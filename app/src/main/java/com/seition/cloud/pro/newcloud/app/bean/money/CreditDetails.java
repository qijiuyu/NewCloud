package com.seition.cloud.pro.newcloud.app.bean.money;

import com.jess.arms.base.bean.MBaseBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/5/30.
 */
public class CreditDetails extends MBaseBean {
    private BalanceInfo credit_info;
    private ArrayList<PayType> pay_type;
    private String pay_note;

    public BalanceInfo getCredit_info() {
        return credit_info;
    }

    public void setCredit_info(BalanceInfo credit_info) {
        this.credit_info = credit_info;
    }

    public ArrayList<PayType> getPay_type() {
        return pay_type;
    }

    public void setPay_type(ArrayList<PayType> pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_note() {
        return pay_note;
    }

    public void setPay_note(String pay_note) {
        this.pay_note = pay_note;
    }
}
