package com.seition.cloud.pro.newcloud.app.bean.news;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/3/26.
 */

public class NewsClassifyBean extends MBaseBean {

    private String zy_topic_category_id;
    private String title;
    private String pid;
    private String sort;

    public String getZy_Topic_category_id() {
        return zy_topic_category_id;
    }

    public void setZy_Topic_category_id(String zy_topic_category_id) {
        this.zy_topic_category_id = zy_topic_category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
