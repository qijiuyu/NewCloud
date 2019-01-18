package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

/**
 * Created by xzw on 2018-08-16.
 */

public class EntityCard extends DataBean<EntityCard> {
    private String id;
//    private String code;
    private String sid;
    private String type;
    private String maxprice;
    private String price;
    private String discount;
    private String vip_grade;
    private String vip_date;
    private String recharge_price;
    private String exp_date;
    private String status;
    private String ctime;
    private String start_time;
    private String end_time;
    private String is_del;
    private String video_type;
    private String video_id;
    private String coupon_type;
    private String count;
    private String coupon_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    @Override
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getVip_grade() {
        return vip_grade;
    }

    public void setVip_grade(String vip_grade) {
        this.vip_grade = vip_grade;
    }

    public String getVip_date() {
        return vip_date;
    }

    public void setVip_date(String vip_date) {
        this.vip_date = vip_date;
    }

    public String getRecharge_price() {
        return recharge_price;
    }

    public void setRecharge_price(String recharge_price) {
        this.recharge_price = recharge_price;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }
}
