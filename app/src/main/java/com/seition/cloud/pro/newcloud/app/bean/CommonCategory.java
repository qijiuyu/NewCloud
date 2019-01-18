package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class CommonCategory extends DataBean<ArrayList<CommonCategory>> /*extends BeanContent*/ {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String icon;

    private String pid;
    private String level;


    private ArrayList<CommonCategory> childs;



    private ArrayList<CommonCategory> child;


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }



    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    public ArrayList<CommonCategory> getChild() {
        return child;
    }

    public void setChild(ArrayList<CommonCategory> child) {
        this.child = child;
    }

    public ArrayList<CommonCategory> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<CommonCategory> childs) {
        this.childs = childs;
    }
}
