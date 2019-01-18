package com.seition.cloud.pro.newcloud.app.bean.coupon;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-08-23.
 */

public class GradeList extends DataBean<GradeList> {

    private String  id;
    private String  title;
    private String  vip_month;
    private String  vip_year;
    private String  sort;
    private String  cover;
    private String  ctime;
    private String  is_del;

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

    public String getVip_month() {
        return vip_month;
    }

    public void setVip_month(String vip_month) {
        this.vip_month = vip_month;
    }

    public String getVip_year() {
        return vip_year;
    }

    public void setVip_year(String vip_year) {
        this.vip_year = vip_year;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }
}
