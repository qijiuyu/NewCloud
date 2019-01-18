package com.seition.cloud.pro.newcloud.app.bean.config;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/11/23.
 */
public class CredPayConfig extends DataBean<CredPayConfig> {
    private int status;
    private String split_score;
    private ArrayList<String> pay_type;

    public ArrayList<String> getPay_type() {
        return pay_type;
    }

    public void setPay_type(ArrayList<String> pay_type) {
        this.pay_type = pay_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSplit_score() {
        return split_score;
    }

    public void setSplit_score(String split_score) {
        this.split_score = split_score;
    }
}
