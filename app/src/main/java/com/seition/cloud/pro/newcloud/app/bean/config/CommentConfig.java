package com.seition.cloud.pro.newcloud.app.bean.config;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by addis on 2018/11/22.
 */
public class CommentConfig extends DataBean<CommentConfig> {
    private int course_switch;//点播
    private int live_switch;//直播
    private int album_switch;//班级
    private int course_line_switch;//线下课
    private int news_switch;//资讯
    private int wenda_switch;//问答

    public int getCourse_switch() {
        return course_switch;
    }

    public void setCourse_switch(int course_switch) {
        this.course_switch = course_switch;
    }

    public int getLive_switch() {
        return live_switch;
    }

    public void setLive_switch(int live_switch) {
        this.live_switch = live_switch;
    }

    public int getAlbum_switch() {
        return album_switch;
    }

    public void setAlbum_switch(int album_switch) {
        this.album_switch = album_switch;
    }

    public int getCourse_line_switch() {
        return course_line_switch;
    }

    public void setCourse_line_switch(int course_line_switch) {
        this.course_line_switch = course_line_switch;
    }

    public int getNews_switch() {
        return news_switch;
    }

    public void setNews_switch(int news_switch) {
        this.news_switch = news_switch;
    }

    public int getWenda_switch() {
        return wenda_switch;
    }

    public void setWenda_switch(int wenda_switch) {
        this.wenda_switch = wenda_switch;
    }
}
