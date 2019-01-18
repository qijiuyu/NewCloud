package com.seition.cloud.pro.newcloud.app.bean.course;

/**
 * Created by addis on 2018/8/31.
 */
public class HomeLiveCourse {
    private String id;
    private String video_title;
    private String imageurl;
    private double t_price;
    private String video_order_count;
    private String video_order_count_mark;
    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public double getT_price() {
        return t_price;
    }

    public void setT_price(double t_price) {
        this.t_price = t_price;
    }

    public String getVideo_order_count() {
        return video_order_count;
    }

    public void setVideo_order_count(String video_order_count) {
        this.video_order_count = video_order_count;
    }

    public String getVideo_order_count_mark() {
        return video_order_count_mark;
    }

    public void setVideo_order_count_mark(String video_order_count_mark) {
        this.video_order_count_mark = video_order_count_mark;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
