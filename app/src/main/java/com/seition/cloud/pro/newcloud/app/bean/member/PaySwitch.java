package com.seition.cloud.pro.newcloud.app.bean.member;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/6/5.
 */

public class PaySwitch extends DataBean<PaySwitch> {

    ArrayList<String> pay;

    public ArrayList<String> getPay() {
        return pay;
    }

    public void setPay(ArrayList<String> pay) {
        this.pay = pay;
    }
}
