package com.seition.cloud.pro.newcloud.app.bean.study;


import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/4/26.
 */

public class StudyRecordContent extends DataBean<ArrayList<StudyRecordContent>> {
    private int vid;
    private int sid;
    private int record_id;
    private String time;
    private String totaltime;
    private String ctime;
    private CourseOnline video_info;
    private CourseSeitionVideo video_section;
    private boolean isSelect;


    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public CourseOnline getVideo_info() {
        return video_info;
    }

    public void setVideo_info(CourseOnline video_info) {
        this.video_info = video_info;
    }

    public CourseSeitionVideo getVideo_section() {
        return video_section;
    }

    public void setVideo_section(CourseSeitionVideo video_section) {
        this.video_section = video_section;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
