package com.seition.cloud.pro.newcloud.app.bean.offline;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/6/11.
 */

public class OfflineSchoolResponse extends DataBean<OfflineSchoolResponse> {

    private ArrayList<OfflineSchool> school;

    public ArrayList<OfflineSchool> getSchool() {
        return school;
    }

    public void setSchool(ArrayList<OfflineSchool> school) {
        this.school = school;
    }
}
