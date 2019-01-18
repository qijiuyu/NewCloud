package com.seition.cloud.pro.newcloud.app.bean.money;

import com.jess.arms.base.bean.MBaseBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/5/30.
 */
public class SpiltDetails extends MBaseBean {
    private BalanceInfo spilt_info;
    private ArrayList<PayType> pay_type;
    private String pay_note;
    private int withdraw_basenum;

    public BalanceInfo getSpilt_info() {
        return spilt_info;
    }

    public void setSpilt_info(BalanceInfo spilt_info) {
        this.spilt_info = spilt_info;
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

    public int getWithdraw_basenum() {
        return withdraw_basenum;
    }

    public void setWithdraw_basenum(int withdraw_basenum) {
        this.withdraw_basenum = withdraw_basenum;
    }
}
