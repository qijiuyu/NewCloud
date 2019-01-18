package com.seition.cloud.pro.newcloud.app.bean.group;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xzw on 2018/3/19.
 */

public class GroupDataOld implements Serializable {//MBaseBean  DataBean
    private String id;
    private String title;
    private ArrayList<Group> group_list;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Group> getGroup_list() {
        return group_list;
    }

    public void setGroup_list(ArrayList<Group> group_list) {
        this.group_list = group_list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
