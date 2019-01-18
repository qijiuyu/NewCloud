package com.seition.cloud.pro.newcloud.app.bean.money;

import com.jess.arms.base.bean.MBaseBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/5/30.
 */
public class BalanceDetails extends MBaseBean {
    private String pay_note;
    private ArrayList<String> pay;
    private ArrayList<BalanceRechangeBean> rechange_default;
    private BalanceInfo learncoin_info;

    public String getPay_note() {
        return pay_note;
    }

    public void setPay_note(String pay_note) {
        this.pay_note = pay_note;
    }

    public ArrayList<String> getPay() {
        return pay;
    }

    public void setPay(ArrayList<String> pay) {
        this.pay = pay;
    }

    public ArrayList<BalanceRechangeBean> getRechange_default() {
        return rechange_default;
    }

    public void setRechange_default(ArrayList<BalanceRechangeBean> rechange_default) {
        this.rechange_default = rechange_default;
    }

    public BalanceInfo getLearncoin_info() {
        return learncoin_info;
    }

    public void setLearncoin_info(BalanceInfo learncoin_info) {
        this.learncoin_info = learncoin_info;
    }
}
