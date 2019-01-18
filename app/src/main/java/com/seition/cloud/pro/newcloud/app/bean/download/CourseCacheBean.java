package com.seition.cloud.pro.newcloud.app.bean.download;

import com.jess.arms.base.bean.MBaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by addis on 2018/4/10.
 */
@Entity
public class CourseCacheBean extends MBaseBean {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long key;
    private int section_count;//课程总数
    private int video_order_count;//报名人数
    @Property(nameInDb = "vid")
    private String courseId;
    private String title;
    private String cover;

    public CourseCacheBean(int section_count, int video_order_count,
                           String courseId, String title, String cover) {
        this.section_count = section_count;
        this.video_order_count = video_order_count;
        this.courseId = courseId;
        this.title = title;
        this.cover = cover;
    }


    @Generated(hash = 684365595)
    public CourseCacheBean() {
    }


    @Generated(hash = 219134900)
    public CourseCacheBean(Long key, int section_count, int video_order_count,
            String courseId, String title, String cover) {
        this.key = key;
        this.section_count = section_count;
        this.video_order_count = video_order_count;
        this.courseId = courseId;
        this.title = title;
        this.cover = cover;
    }

    public CourseCacheBean updata(CourseCacheBean newCourse) {
        setSection_count(newCourse.getSection_count());
        setTitle(newCourse.getTitle());
        setVideo_order_count(newCourse.getVideo_order_count());
        setCover(newCourse.getCover());
        return this;
    }

    @Override
    public String toString() {
        return "CourseCacheBean{" +
                "key=" + key +
                ", section_count=" + section_count +
                ", video_order_count=" + video_order_count +
                ", courseId='" + courseId + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public int getSection_count() {
        return section_count;
    }

    public void setSection_count(int section_count) {
        this.section_count = section_count;
    }

    public int getVideo_order_count() {
        return video_order_count;
    }

    public void setVideo_order_count(int video_order_count) {
        this.video_order_count = video_order_count;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
