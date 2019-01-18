package com.seition.cloud.pro.newcloud.app.bean.offline;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;

public class CourseOffline extends DataBean<CourseOffline> {


    /**
     * 线下课详情和列表共有
     */
    private String course_id;
    private String course_uid;
    private String course_name;
    private String course_category;
    private String fullcategorypath;
    private String teacher_id;
    private String cover;
    private String course_price;
    private String course_binfo;

    private String course_intro;
    private String view_nums;
    private String course_order_count;
    private String course_order_count_mark;
    private String listingtime;
    private String uctime;
    private String is_activity;
    private String is_charge;
    private String is_del;
    private String mhm_id;
    private String ctime;
    private String t_price; //原价
    private String price;  //当前用户的价格
    private String imageurl;
    private String teacher_name;
    private String teacher_uid;
    private String is_buy;


    /**
     * 线下课详情
     */
    private String uid;
    private String is_collect;
//    private Lecturers teacher_info;
    private Organization school_info;


    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_uid() {
        return course_uid;
    }

    public void setCourse_uid(String course_uid) {
        this.course_uid = course_uid;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_category() {
        return course_category;
    }

    public void setCourse_category(String course_category) {
        this.course_category = course_category;
    }

    public String getFullcategorypath() {
        return fullcategorypath;
    }

    public void setFullcategorypath(String fullcategorypath) {
        this.fullcategorypath = fullcategorypath;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCourse_price() {
        return course_price;
    }

    public void setCourse_price(String course_price) {
        this.course_price = course_price;
    }

    public String getCourse_binfo() {
        return course_binfo;
    }

    public void setCourse_binfo(String course_binfo) {
        this.course_binfo = course_binfo;
    }

    public String getCourse_intro() {
        return course_intro;
    }

    public void setCourse_intro(String course_intro) {
        this.course_intro = course_intro;
    }

    public String getView_nums() {
        return view_nums;
    }

    public void setView_nums(String view_nums) {
        this.view_nums = view_nums;
    }

    public String getCourse_order_count() {
        return course_order_count;
    }

    public void setCourse_order_count(String course_order_count) {
        this.course_order_count = course_order_count;
    }

    public String getCourse_order_count_mark() {
        return course_order_count_mark;
    }

    public void setCourse_order_count_mark(String course_order_count_mark) {
        this.course_order_count_mark = course_order_count_mark;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getListingtime() {
        return listingtime;
    }

    public void setListingtime(String listingtime) {
        this.listingtime = listingtime;
    }

    public String getUctime() {
        return uctime;
    }

    public void setUctime(String uctime) {
        this.uctime = uctime;
    }

    public String getIs_activity() {
        return is_activity;
    }

    public void setIs_activity(String is_activity) {
        this.is_activity = is_activity;
    }

    public String getIs_charge() {
        return is_charge;
    }

    public void setIs_charge(String is_charge) {
        this.is_charge = is_charge;
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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getT_price() {
        return t_price;
    }

    public void setT_price(String t_price) {
        this.t_price = t_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getTeacher_uid() {
        return teacher_uid;
    }

    public void setTeacher_uid(String teacher_uid) {
        this.teacher_uid = teacher_uid;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }


    public Organization getSchool_info() {
        return school_info;
    }

    public void setSchool_info(Organization school_info) {
        this.school_info = school_info;
    }




}
