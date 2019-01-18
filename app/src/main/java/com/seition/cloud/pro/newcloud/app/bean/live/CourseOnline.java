package com.seition.cloud.pro.newcloud.app.bean.live;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.bean.user.UserInfo;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/4/19.
 */

public class CourseOnline extends DataBean<CourseOnline> {

    /*
     * 点播课 和直播课最基本的数据
     * */

    private String video_title;        //课程标题
    private int video_category;        //视频分类id
    private String fullcategorypath;
    private String video_binfo;
    private String video_intro;
    private String video_address;    //视频地址
    private String cover;
    private int teacher_id;                //讲师id;
    private String live_course_id;
    private String videofile_ids;
    private String str_tag;
    private String tag_id;
    private String is_part_album;
    private double v_price;             //市场价
    private double t_price;                // 原价
    private String discount;
    private String vip_level;
    private String vip_pattern;
    private int is_tlimit;            //是否参加限时活动
    private String starttime;        //限时活动开始时间
    private String endtime;            //限时活动结束时间
    private String limit_discount;
    private int video_collect_count;    //课程收藏个数
    private int video_comment_count;        //评论总数
    private int video_question_count;    //提问总数
    private int video_note_count;        //笔记总数
    private float video_score;
    private int video_order_count;        //订单数量
    private int video_order_count_mark;
    private int is_activity;        //是否审核
    private String is_charge;
    private String listingtime;        //上架时间
    private String uctime;            //下架时间
    private String ctime;            //创建时间
    private String utime;            //最后修改时间
    private String mhm_id;
    private String live_type;
    private String type;//1课程2直播（废弃,保留字段 ，不用忽略）
    private String maxmannums;
    private String view_nums;
    private String view_nums_mark;
    private String start;
    private String is_best;
    private String best_sort;
    private String school_switch;
    private String is_school;
    private int is_del;
    private double price;           //当前用户的价格
    private String imageurl;
    private String teacher_name;        //讲师名字
    private String time_limit;        //到期时间


    /*
     * 点播课 ： 基础数据 加如下数据
     * */
    private String id;
    private String uid;
    private String term;
    private String buy_count;
    private int section_count;//课程总数
    private int video_section_count;//课程总数

    /*
     * 首页最新和精品课程 ： 基础数据 加如下数据
     * */


    /*
     * 首页直播 ： 基础数据 加如下数据
     * */
    private ArrayList<Section> sections;


    /*
     * 直播课数据： 基础数据 加如下数据
     * */
    private String live_id;
    private int score;
    private String live_category;
    private String beginTime;
    private String endTime;
    private int is_buy;
    //    private int isBuy;
    private String iscollect;
    private String is_collect;
    private int section_num;

    /*
     * 直播课： 基础数据 +直播课数据+如下数据
     * */
    private UserInfo user;
    private Organization school_info;

    public Organization getSchool_info() {
        return school_info;
    }

    public void setSchool_info(Organization school_info) {
        this.school_info = school_info;
    }

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

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public int getVideo_category() {
        return video_category;
    }

    public void setVideo_category(int video_category) {
        this.video_category = video_category;
    }

    public String getFullcategorypath() {
        return fullcategorypath;
    }

    public void setFullcategorypath(String fullcategorypath) {
        this.fullcategorypath = fullcategorypath;
    }

    public String getVideo_binfo() {
        return video_binfo;
    }

    public void setVideo_binfo(String video_binfo) {
        this.video_binfo = video_binfo;
    }

    public String getVideo_intro() {
        return video_intro;
    }

    public void setVideo_intro(String video_intro) {
        this.video_intro = video_intro;
    }

    public String getVideo_address() {
        return video_address;
    }

    public void setVideo_address(String video_address) {
        this.video_address = video_address;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(String time_limit) {
        this.time_limit = time_limit;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getLive_course_id() {
        return live_course_id;
    }

    public void setLive_course_id(String live_course_id) {
        this.live_course_id = live_course_id;
    }

    public String getVideofile_ids() {
        return videofile_ids;
    }

    public void setVideofile_ids(String videofile_ids) {
        this.videofile_ids = videofile_ids;
    }

    public String getStr_tag() {
        return str_tag;
    }

    public void setStr_tag(String str_tag) {
        this.str_tag = str_tag;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getIs_part_album() {
        return is_part_album;
    }

    public void setIs_part_album(String is_part_album) {
        this.is_part_album = is_part_album;
    }

    public double getV_price() {
        return v_price;
    }

    public void setV_price(double v_price) {
        this.v_price = v_price;
    }

    public double getT_price() {
        return t_price;
    }

    public void setT_price(double t_price) {
        this.t_price = t_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getVip_level() {
        return vip_level;
    }

    public void setVip_level(String vip_level) {
        this.vip_level = vip_level;
    }

    public String getVip_pattern() {
        return vip_pattern;
    }

    public void setVip_pattern(String vip_pattern) {
        this.vip_pattern = vip_pattern;
    }

    public int getIs_tlimit() {
        return is_tlimit;
    }

    public void setIs_tlimit(int is_tlimit) {
        this.is_tlimit = is_tlimit;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getLimit_discount() {
        return limit_discount;
    }

    public void setLimit_discount(String limit_discount) {
        this.limit_discount = limit_discount;
    }

    public int getVideo_collect_count() {
        return video_collect_count;
    }

    public void setVideo_collect_count(int video_collect_count) {
        this.video_collect_count = video_collect_count;
    }

    public int getVideo_comment_count() {
        return video_comment_count;
    }

    public void setVideo_comment_count(int video_comment_count) {
        this.video_comment_count = video_comment_count;
    }

    public int getVideo_question_count() {
        return video_question_count;
    }

    public void setVideo_question_count(int video_question_count) {
        this.video_question_count = video_question_count;
    }

    public int getVideo_note_count() {
        return video_note_count;
    }

    public void setVideo_note_count(int video_note_count) {
        this.video_note_count = video_note_count;
    }

    public float getVideo_score() {
        return video_score;
    }

    public void setVideo_score(float video_score) {
        this.video_score = video_score;
    }

    public int getVideo_order_count() {
        return video_order_count;
    }

    public void setVideo_order_count(int video_order_count) {
        this.video_order_count = video_order_count;
    }

    public int getVideo_order_count_mark() {
        return video_order_count_mark;
    }

    public void setVideo_order_count_mark(int video_order_count_mark) {
        this.video_order_count_mark = video_order_count_mark;
    }

    public int getIs_activity() {
        return is_activity;
    }

    public void setIs_activity(int is_activity) {
        this.is_activity = is_activity;
    }

    public String getIs_charge() {
        return is_charge;
    }

    public void setIs_charge(String is_charge) {
        this.is_charge = is_charge;
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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getMhm_id() {
        return mhm_id;
    }

    public void setMhm_id(String mhm_id) {
        this.mhm_id = mhm_id;
    }

    public String getLive_type() {
        return live_type;
    }

    public void setLive_type(String live_type) {
        this.live_type = live_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxmannums() {
        return maxmannums;
    }

    public void setMaxmannums(String maxmannums) {
        this.maxmannums = maxmannums;
    }

    public String getView_nums() {
        return view_nums;
    }

    public void setView_nums(String view_nums) {
        this.view_nums = view_nums;
    }

    public String getView_nums_mark() {
        return view_nums_mark;
    }

    public void setView_nums_mark(String view_nums_mark) {
        this.view_nums_mark = view_nums_mark;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
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

    public String getSchool_switch() {
        return school_switch;
    }

    public void setSchool_switch(String school_switch) {
        this.school_switch = school_switch;
    }

    public String getIs_school() {
        return is_school;
    }

    public void setIs_school(String is_school) {
        this.is_school = is_school;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public String getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(String buy_count) {
        this.buy_count = buy_count;
    }

    public int getSection_count() {
        return section_count;
    }

    public void setSection_count(int section_count) {
        this.section_count = section_count;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }

    public String getLive_id() {
        return live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLive_category() {
        return live_category;
    }

    public void setLive_category(String live_category) {
        this.live_category = live_category;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

//    public int getIsBuy() {
//        return isBuy;
//    }

//    public void setIsBuy(int isBuy) {
//        this.isBuy = isBuy;
//    }

    public String getIscollect() {
        return iscollect;
    }

    public void setIscollect(String iscollect) {
        this.iscollect = iscollect;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public int getSection_num() {
        return section_num;
    }

    public void setSection_num(int section_num) {
        this.section_num = section_num;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getVideo_section_count() {
        return video_section_count;
    }

    public void setVideo_section_count(int video_section_count) {
        this.video_section_count = video_section_count;
    }
}
