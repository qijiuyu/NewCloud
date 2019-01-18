package com.seition.cloud.pro.newcloud.app.bean.money;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/5/30.
 */
public class BalanceRechangeBean extends MBaseBean {
    private int rechange;
    private int give;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getRechange() {
        return rechange;
    }

    public void setRechange(int rechange) {
        this.rechange = rechange;
    }

    public int getGive() {
        return give;
    }

    public void setGive(int give) {
        this.give = give;
    }
}
