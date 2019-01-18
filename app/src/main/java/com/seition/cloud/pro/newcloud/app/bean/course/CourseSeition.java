package com.seition.cloud.pro.newcloud.app.bean.course;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/4/9.
 */
public class CourseSeition extends DataBean<ArrayList<CourseSeition>> {
    private int id;
    private int vid;
    private String title;
    private ArrayList<CourseSeitionVideo> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<CourseSeitionVideo> getChild() {
        return child;
    }

    public void setChild(ArrayList<CourseSeitionVideo> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "CourseSeition{" +
                "id=" + id +
                ", vid=" + vid +
                ", title='" + title + '\'' +
                ", child=" + child +
                '}';
    }
}
