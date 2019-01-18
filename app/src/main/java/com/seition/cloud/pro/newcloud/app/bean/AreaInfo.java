package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class AreaInfo extends DataBean<ArrayList<AreaInfo>> {

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    private String area_id;
    private String title;
    private String pid;
    private String sort;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }






    /**
     * 直播结束
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AreaInfo other = (AreaInfo) obj;

        return true;
    }


}
