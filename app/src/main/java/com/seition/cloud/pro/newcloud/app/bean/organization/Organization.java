package com.seition.cloud.pro.newcloud.app.bean.organization;


import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.user.UserInfo;

import java.util.ArrayList;

/**
 * Created by addis on 2016/11/8.
 */

public class Organization extends DataBean<Organization> {


    private String title;
    private String school_category;
    private String fullcategorypath;
    private String logo;
    private int uid;//
//    private Integer sat_school;
//    private Integer sat_teacher;
    private String doadmin;
    private String videoSpace;
    private String cover;
    private String info;
    private String school_and_teacher;
    private String school_and_oschool;
    private String school_and_oschool_action;
    private String school_pinclass;
    private String school_pinclass_action;
    private int status;
    private String ctime;
    private String collect_num;
    private String idcard;
    private String phone;
    private String address;
    private String identity_id;
    private String attach_id;
    private String reason;
    private String is_del;
    private String is_re;
    private String location;
    private String province;
    private String city;
    private String area;
    private String is_best;
    private String best_sort;
    private String is_cete_floor;
    private String cete_floor_sort;
    private String school_vip;
    private String template;
    private String banner;
    private String about_us;
    private String school_qq;
    private String visit_num;
    private String afnowhours;
    private String str_tag;
    private String max_price_sys;
    private String rejectInfo;
    private String review_count;
    private String school_and_buyschool;
    private String school_and_buyschool_action;
    private String is_default;
    private int school_id;
    private int logo_id;
    private int cover_id;
    private String pc_cover;
    private FollowState follow_state;//
    private OrganizationCount count;
    private String school_vip_name;


    /*
     * 机构详情,讲师列表 接口才会返回下列数据
     * */

    /*
     * 机构详情 接口才会返回下列数据（Teacher 和CourseOnline 中的机构信息 也有）
     * */
    private UserInfo user_info;
    private ArrayList<CourseOnline> recommend_list;//OrganiztionBeanRecommendList


    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getIs_re() {
        return is_re;
    }

    public void setIs_re(String is_re) {
        this.is_re = is_re;
    }



    public String getPc_cover() {
        return pc_cover;
    }

    public void setPc_cover(String pc_cover) {
        this.pc_cover = pc_cover;
    }


    public String getVideoSpace() {
        return videoSpace;
    }

    public void setVideoSpace(String videoSpace) {
        this.videoSpace = videoSpace;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void setSchool_and_teacher(String school_and_teacher) {
        this.school_and_teacher = school_and_teacher;
    }

    public String getSchool_and_teacher() {
        return school_and_teacher;
    }

    public String getFullcategorypath() {
        return fullcategorypath;
    }

    public void setFullcategorypath(String fullcategorypath) {
        this.fullcategorypath = fullcategorypath;
    }

    public String getSchool_category() {
        return school_category;
    }

    public void setSchool_category(String school_category) {
        this.school_category = school_category;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDoadmin(String doadmin) {
        this.doadmin = doadmin;
    }

    public String getDoadmin() {
        return doadmin;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCtime() {
        return ctime;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public OrganizationCount getCount() {
        return count;
    }

    public void setCount(OrganizationCount count) {
        this.count = count;
    }

    public ArrayList<CourseOnline> getRecommend_list() {
        return recommend_list;
    }

    public void setRecommend_list(ArrayList<CourseOnline> recommend_list) {
        this.recommend_list = recommend_list;
    }

    public String getSchool_vip_name() {
        return school_vip_name;
    }

    public void setSchool_vip_name(String school_vip_name) {
        this.school_vip_name = school_vip_name;
    }

    public String getSchool_and_oschool() {
        return school_and_oschool;
    }

    public void setSchool_and_oschool(String school_and_oschool) {
        this.school_and_oschool = school_and_oschool;
    }

    public String getSchool_and_oschool_action() {
        return school_and_oschool_action;
    }

    public void setSchool_and_oschool_action(String school_and_oschool_action) {
        this.school_and_oschool_action = school_and_oschool_action;
    }

    public String getSchool_pinclass() {
        return school_pinclass;
    }

    public void setSchool_pinclass(String school_pinclass) {
        this.school_pinclass = school_pinclass;
    }

    public String getSchool_pinclass_action() {
        return school_pinclass_action;
    }

    public void setSchool_pinclass_action(String school_pinclass_action) {
        this.school_pinclass_action = school_pinclass_action;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getIs_cete_floor() {
        return is_cete_floor;
    }

    public void setIs_cete_floor(String is_cete_floor) {
        this.is_cete_floor = is_cete_floor;
    }

    public String getCete_floor_sort() {
        return cete_floor_sort;
    }

    public void setCete_floor_sort(String cete_floor_sort) {
        this.cete_floor_sort = cete_floor_sort;
    }

    public String getSchool_vip() {
        return school_vip;
    }

    public void setSchool_vip(String school_vip) {
        this.school_vip = school_vip;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getSchool_qq() {
        return school_qq;
    }

    public void setSchool_qq(String school_qq) {
        this.school_qq = school_qq;
    }

    public String getVisit_num() {
        return visit_num;
    }

    public void setVisit_num(String visit_num) {
        this.visit_num = visit_num;
    }

    public String getAfnowhours() {
        return afnowhours;
    }

    public void setAfnowhours(String afnowhours) {
        this.afnowhours = afnowhours;
    }

    public String getStr_tag() {
        return str_tag;
    }

    public void setStr_tag(String str_tag) {
        this.str_tag = str_tag;
    }

    public String getMax_price_sys() {
        return max_price_sys;
    }

    public void setMax_price_sys(String max_price_sys) {
        this.max_price_sys = max_price_sys;
    }

    public String getRejectInfo() {
        return rejectInfo;
    }

    public void setRejectInfo(String rejectInfo) {
        this.rejectInfo = rejectInfo;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public String getSchool_and_buyschool() {
        return school_and_buyschool;
    }

    public void setSchool_and_buyschool(String school_and_buyschool) {
        this.school_and_buyschool = school_and_buyschool;
    }

    public String getSchool_and_buyschool_action() {
        return school_and_buyschool_action;
    }

    public void setSchool_and_buyschool_action(String school_and_buyschool_action) {
        this.school_and_buyschool_action = school_and_buyschool_action;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public int getLogo_id() {
        return logo_id;
    }

    public void setLogo_id(int logo_id) {
        this.logo_id = logo_id;
    }

    public int getCover_id() {
        return cover_id;
    }

    public void setCover_id(int cover_id) {
        this.cover_id = cover_id;
    }

    public FollowState getFollow_state() {
        return follow_state;
    }

    public void setFollow_state(FollowState follow_state) {
        this.follow_state = follow_state;
    }

    public UserInfo getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfo user_info) {
        this.user_info = user_info;
    }
}
