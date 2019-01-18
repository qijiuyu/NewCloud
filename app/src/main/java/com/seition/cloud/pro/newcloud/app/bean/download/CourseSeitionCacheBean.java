package com.seition.cloud.pro.newcloud.app.bean.download;

import com.jess.arms.base.bean.MBaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by addis on 2018/4/10.
 */
@Entity
public class CourseSeitionCacheBean extends MBaseBean {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long key;
    private int id;
    @Property(nameInDb = "vid")
    private String vid;
    private String title;

    public CourseSeitionCacheBean(int id, String vid, String title) {
        this.id = id;
        this.vid = vid;
        this.title = title;
    }

    @Generated(hash = 2127295606)
    public CourseSeitionCacheBean(Long key, int id, String vid, String title) {
        this.key = key;
        this.id = id;
        this.vid = vid;
        this.title = title;
    }

    @Generated(hash = 1662405490)
    public CourseSeitionCacheBean() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
