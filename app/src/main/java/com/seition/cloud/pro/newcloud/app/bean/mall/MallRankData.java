package com.seition.cloud.pro.newcloud.app.bean.mall;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/3/30.
 */

public class MallRankData extends DataBean<MallRankData> {
    ArrayList<Mall> rank;

    public ArrayList<Mall> getRank() {
        return rank;
    }

    public void setRank(ArrayList<Mall> rank) {
        this.rank = rank;
    }
}
