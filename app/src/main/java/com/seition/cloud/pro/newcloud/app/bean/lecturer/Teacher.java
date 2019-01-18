package com.seition.cloud.pro.newcloud.app.bean.lecturer;

import com.jess.arms.base.bean.MBaseBean;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.common.extInfo.ExtInfo;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;

/**
 * Created by addis on 2018/5/17.
 */
public class Teacher extends MBaseBean{
    private static final long serialVersionUID = -3529493388288457864L;

    private String id; // 讲师id
    private String uid; // 讲师管理的用户ID
    private String name; // 讲师姓名
    private String teacher_category;
    private String fullcategorypath;
    private String mhm_id;
    private String info; // 讲师简介
    private String head_id; // 讲师头像id
    private String title_category;
    private String teacher_schedule;
    private String course_count;
    private String reservation_count;
    private String review_count;
    private String views;
    private String collect_num;
    private String is_best;
    private String best_sort;
    private String background_id;
    private String attach_id;
    private String reason;
    private String reason_no;
    private String verified_status;
    private String ctime; // 添加时间
    private String is_del;
    private int video_count; // 讲师相关课程数量
    private String headimg; // 讲师头像地址
    private FollowState follow_state;
    private String title; // 讲师职业

    private ExtInfo ext_info;
    private Organization school_info;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher_category() {
        return teacher_category;
    }

    public void setTeacher_category(String teacher_category) {
        this.teacher_category = teacher_category;
    }

    public String getFullcategorypath() {
        return fullcategorypath;
    }

    public void setFullcategorypath(String fullcategorypath) {
        this.fullcategorypath = fullcategorypath;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getHead_id() {
        return head_id;
    }

    public void setHead_id(String head_id) {
        this.head_id = head_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_category() {
        return title_category;
    }

    public void setTitle_category(String title_category) {
        this.title_category = title_category;
    }

    public String getTeacher_schedule() {
        return teacher_schedule;
    }

    public void setTeacher_schedule(String teacher_schedule) {
        this.teacher_schedule = teacher_schedule;
    }

    public String getCourse_count() {
        return course_count;
    }

    public void setCourse_count(String course_count) {
        this.course_count = course_count;
    }

    public String getReservation_count() {
        return reservation_count;
    }

    public void setReservation_count(String reservation_count) {
        this.reservation_count = reservation_count;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getMhm_id() {
        return mhm_id;
    }

    public void setMhm_id(String mhm_id) {
        this.mhm_id = mhm_id;
    }


    public String getIs_best() {
        return is_best;
    }

    public void setIs_best(String is_best) {
        this.is_best = is_best;
    }

    public String getBest_sort() {
        return best_sort;
    }

    public void setBest_sort(String best_sort) {
        this.best_sort = best_sort;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }


    public String getBackground_id() {
        return background_id;
    }

    public void setBackground_id(String background_id) {
        this.background_id = background_id;
    }

    public String getVerified_status() {
        return verified_status;
    }

    public void setVerified_status(String verified_status) {
        this.verified_status = verified_status;
    }

    public String getAttach_id() {
        return attach_id;
    }

    public void setAttach_id(String attach_id) {
        this.attach_id = attach_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public FollowState getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(FollowState follow_state) {
        this.follow_state = follow_state;
    }

    public ExtInfo getExt_info() {
        return ext_info;
    }

    public void setExt_info(ExtInfo ext_info) {
        this.ext_info = ext_info;
    }

    public Organization getSchool_info() {
        return school_info;
    }

    public void setSchool_info(Organization school_info) {
        this.school_info = school_info;
    }
}
