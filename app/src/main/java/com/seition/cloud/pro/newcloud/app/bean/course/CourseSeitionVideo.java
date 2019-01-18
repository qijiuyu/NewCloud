package com.seition.cloud.pro.newcloud.app.bean.course;

import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.config.Service;

import io.vov.vitamio.DBVideoBean;

/**
 * Created by addis on 2018/4/9.
 */
public class CourseSeitionVideo extends MBaseBean {
    private int id;//视频id
    private int pid;//父级id
    private int vid;//课程id
    private int cid;//??id
    private int eid;//考试ID
    private int video_type;//
    private int level;//
    private int is_buy;//是否已购买
    private int is_free;//是否免费 1为免费，0为默认
    private int is_activity;//
    private int type;//3.文本2音频1视频
    private int is_shiting;//是否试听视频 1为试听视频，0为默认视频
    private double course_hour_price;//课时价格
    private String title;//标题
    private String v_title;
    private String duration;//视频时长
    private String video_address;//视频地址

    public DBVideoBean getDBVideoBean() {
        return new DBVideoBean(title, id + "", initUri(video_address), "本地地址", "后缀名", type, "封面地址", is_free == 1, is_buy == 1);
    }

    public String initUri(String uri) {
        if (!uri.contains("http"))
            uri = Service.DOMAIN + uri;
        return uri;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public double getCourse_hour_price() {
        return course_hour_price;
    }

    public void setCourse_hour_price(double course_hour_price) {
        this.course_hour_price = course_hour_price;
    }

    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public int getIs_activity() {
        return is_activity;
    }

    public void setIs_activity(int is_activity) {
        this.is_activity = is_activity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_shiting() {
        return is_shiting;
    }

    public void setIs_shiting(int is_shiting) {
        this.is_shiting = is_shiting;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getV_title() {
        return v_title;
    }

    public void setV_title(String v_title) {
        this.v_title = v_title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVideo_address() {
        return video_address;
    }

    public void setVideo_address(String video_address) {
        this.video_address = video_address;
    }

    @Override
    public String toString() {
        return "CourseSeitionVideo{" +
                "id=" + id +
                ", pid=" + pid +
                ", vid=" + vid +
                ", cid=" + cid +
                ", video_type=" + video_type +
                ", level=" + level +
                ", is_free=" + is_free +
                ", is_activity=" + is_activity +
                ", type=" + type +
                ", is_shiting=" + is_shiting +
                ", title='" + title + '\'' +
                ", v_title='" + v_title + '\'' +
                ", duration='" + duration + '\'' +
                ", video_address='" + video_address + '\'' +
                '}';
    }
}
