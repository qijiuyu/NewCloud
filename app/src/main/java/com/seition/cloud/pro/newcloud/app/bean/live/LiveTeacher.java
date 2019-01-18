package com.seition.cloud.pro.newcloud.app.bean.live;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/6/1.
 */

public class LiveTeacher extends DataBean<ArrayList<LiveTeacher>> {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
