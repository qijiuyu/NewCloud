package com.seition.cloud.pro.newcloud.app.bean.course;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/3/19.
 */

public class CourseSearch extends DataBean<ArrayList<CourseSearch>> {
    private int type;

    private ArrayList<CourseOnline> list;

//    private CourseOnlines list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<CourseOnline> getList() {
        return list;
    }

    public void setList(ArrayList<CourseOnline> list) {
        this.list = list;
    }

//    public CourseOnlines getList() {
//        return list;
//    }
//
//    public void setList(CourseOnlines list) {
//        this.list = list;
//    }
}
