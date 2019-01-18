package com.seition.cloud.pro.newcloud.app.bean.news;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2018/3/26.
 */

public class NewsItemBean extends MBaseBean {
    private String id;//资讯id
    private String desc;//摘要
    private String text;//内容
    private String title;//标题
    private String dateline;//发布时间
    private String read;//
    private String is_del;//是否删除【1是 0否】
    private String re;//
    private String recount;//推荐数量
    private String readcount;//阅读量
    private String cate;//分类
    private String image;//封面

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public String getRecount() {
        return recount;
    }

    public void setRecount(String recount) {
        this.recount = recount;
    }

    public String getReadcount() {
        return readcount;
    }

    public void setReadcount(String readcount) {
        this.readcount = readcount;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
