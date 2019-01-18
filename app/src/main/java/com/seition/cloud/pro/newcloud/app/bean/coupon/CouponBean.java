package com.seition.cloud.pro.newcloud.app.bean.coupon;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.bean.DataBean;

public class CouponBean extends DataBean<CouponBean>  implements MultiItemEntity {

    public static final int COUPON_YH = 1;
    public static final int COUPON_DZ = 2;
    public static final int COUPON_HY = 3;
    public static final int COUPON_CZ = 4;
    public static final int COUPON_KC = 5;

    private String sid;//机构id
    private int type;//类型：1.优惠券2.打折券3.会员卡4.充值卡5课程卡

    private String coupon_code;//优惠券编码
//    private long  code;//优惠券编码
    private double discount;//会员折扣
    private String video_id;//课程id
    private double price;//优惠价格
    private int exp_date;//有效期天
    private int status;//我的 ：状态【0未使用，1已使用,2已被使用到订单】 领取：状态【0已过期，1未领取，2已领取，3已作废】
    private int ctime;//发放时间
    private int end_time;
    private int coupon_id;//优惠券id
    private String vip_date;
    private String vip_grade;
    private String video_title;

    private String vip_id;
    private String vip_title;
    private String vip_month;
    private String vip_year;
    private String vip_sort;
    private String vip_cover;

    private String vip_ctime;
    private String vip_is_del;
    private String vip_cover_url;


    private int stime;//领取时间
    private int etime;//过期时间
    private String recharge_price;//充值面额
    private String maxprice;//使用门槛
    private String school_title;//所属机构

    private GradeList  vip_grade_list;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getExp_date() {
        return exp_date;
    }

    public void setExp_date(int exp_date) {
        this.exp_date = exp_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getVip_date() {
        return vip_date;
    }

    public void setVip_date(String vip_date) {
        this.vip_date = vip_date;
    }

    public String getVip_grade() {
        return vip_grade;
    }

    public void setVip_grade(String vip_grade) {
        this.vip_grade = vip_grade;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVip_id() {
        return vip_id;
    }

    public void setVip_id(String vip_id) {
        this.vip_id = vip_id;
    }

    public String getVip_title() {
        return vip_title;
    }

    public void setVip_title(String vip_title) {
        this.vip_title = vip_title;
    }

    public String getVip_month() {
        return vip_month;
    }

    public void setVip_month(String vip_month) {
        this.vip_month = vip_month;
    }

    public String getVip_year() {
        return vip_year;
    }

    public void setVip_year(String vip_year) {
        this.vip_year = vip_year;
    }

    public String getVip_sort() {
        return vip_sort;
    }

    public void setVip_sort(String vip_sort) {
        this.vip_sort = vip_sort;
    }

    public String getVip_cover() {
        return vip_cover;
    }

    public void setVip_cover(String vip_cover) {
        this.vip_cover = vip_cover;
    }

    public String getVip_ctime() {
        return vip_ctime;
    }

    public void setVip_ctime(String vip_ctime) {
        this.vip_ctime = vip_ctime;
    }

    public String getVip_is_del() {
        return vip_is_del;
    }

    public void setVip_is_del(String vip_is_del) {
        this.vip_is_del = vip_is_del;
    }

    public String getVip_cover_url() {
        return vip_cover_url;
    }

    public void setVip_cover_url(String vip_cover_url) {
        this.vip_cover_url = vip_cover_url;
    }

    public int getStime() {
        return stime;
    }

    public void setStime(int stime) {
        this.stime = stime;
    }

    public int getEtime() {
        return etime;
    }

    public void setEtime(int etime) {
        this.etime = etime;
    }

    public String getRecharge_price() {
        return recharge_price;
    }

    public void setRecharge_price(String recharge_price) {
        this.recharge_price = recharge_price;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getSchool_title() {
        return school_title;
    }

    public void setSchool_title(String school_title) {
        this.school_title = school_title;
    }

    public GradeList getVip_grade_list() {
        return vip_grade_list;
    }

    public void setVip_grade_list(GradeList vip_grade_list) {
        this.vip_grade_list = vip_grade_list;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
