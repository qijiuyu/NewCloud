package com.seition.cloud.pro.newcloud.app.bean.group;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/4/17.
 */

public class GroupData extends DataBean<ArrayList<GroupData>> {
    private String id;
    private String title;
    private ArrayList<Group> group_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Group> getGroup_list() {
        return group_list;
    }

    public void setGroup_list(ArrayList<Group> group_list) {
        this.group_list = group_list;
    }
}
