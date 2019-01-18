package com.seition.cloud.pro.newcloud.app.bean.order;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by xzw on 2018-08-15.
 */

public class OrganizationOrder extends DataBean<OrganizationOrder> {
    private ArrayList<Order> list;

    public ArrayList<Order> getList() {
        return list;
    }

    public void setList(ArrayList<Order> list) {
        this.list = list;
    }
}
