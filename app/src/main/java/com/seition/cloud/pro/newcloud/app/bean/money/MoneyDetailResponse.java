package com.seition.cloud.pro.newcloud.app.bean.money;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class MoneyDetailResponse extends DataBean<MoneyDetailResponse> {

    private String total;
    private ArrayList<MoneyDetail> list;
    private String balance;

    private String score;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<MoneyDetail> getList() {
        return list;
    }

    public void setList(ArrayList<MoneyDetail> list) {
        this.list = list;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
