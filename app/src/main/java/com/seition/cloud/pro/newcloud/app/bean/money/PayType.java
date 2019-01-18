package com.seition.cloud.pro.newcloud.app.bean.money;

import com.seition.cloud.pro.newcloud.app.bean.bind.BankBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/5/30.
 */
public class PayType {

    String pay_num;
    String pay_type_note;
    double learn_balance;
    double balance;
    ArrayList<BankBean> card_list;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPay_num() {
        return pay_num;
    }

    public void setPay_num(String pay_num) {
        this.pay_num = pay_num;
    }

    public String getPay_type_note() {
        return pay_type_note;
    }

    public void setPay_type_note(String pay_type_note) {
        this.pay_type_note = pay_type_note;
    }

    public double getLearn_balance() {
        return learn_balance;
    }

    public void setLearn_balance(double learn_balance) {
        this.learn_balance = learn_balance;
    }

    public ArrayList<BankBean> getCard_list() {
        return card_list;
    }

    public void setCard_list(ArrayList<BankBean> card_list) {
        this.card_list = card_list;
    }
}
