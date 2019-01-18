package com.seition.cloud.pro.newcloud.app.bean.order;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;

/**
 * Created by addis on 2016/12/3.
 */

public class Order extends DataBean<Order> {
    private int id;//订单id
    private int muid;
    private int video_id;//课程id
    private int live_id;//直播id
    private int course_hour_id;//课时id
    private int order_type;//分类1文库2商城3直播4课程5线下课
    private int coupon_id;//优惠券id
    private int order_album_id;//套餐id
    private int mhm_id;//机构id
    private String video_name;//课程名
    private String cover;//封面
    private String video_binfo;
    private String strtime;
    private String ctime;//购买时间
    private String ptime;//订单最后操作时间
    private String refund_reason;//申请原因
    private String reject_info;//被驳回原因
    private String course_hour_title;//课时名字
    private double price;//消耗积分数量
    private double pay_price;//用通过第三方买的
    private String old_price;//原价
    private String discount;//折扣
    private int source_id;//资源id
    private String discount_type;//折扣类型0无折扣1打折卡2优惠券
    private int pay_status;//订单状态1未支付,2已取消,3已支付;4申请退款;5退款成功
    private int learn_status;//学习状态	0:未开始,1:学习中,2:已完成
    // source_info
    private LibraryItemBean doc_info;//文库详情
    private Mall goods_info;//商品详情
//    private CourseOnline source_info;//课程详情
//    private CourseOffline line_class;//线下课程详情


    public double getPay_price() {
        return pay_price;
    }

    public void setPay_price(double pay_price) {
        this.pay_price = pay_price;
    }

    public String getCourse_hour_title() {
        return course_hour_title;
    }

    public void setCourse_hour_title(String course_hour_title) {
        this.course_hour_title = course_hour_title;
    }

    public int getLive_id() {
        return live_id;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public String getReject_info() {
        return reject_info;
    }

    public void setReject_info(String reject_info) {
        this.reject_info = reject_info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getLearn_status() {
        return learn_status;
    }

    public void setLearn_status(int learn_status) {
        this.learn_status = learn_status;
    }

    public LibraryItemBean getDoc_info() {
        return doc_info;
    }

    public void setDoc_info(LibraryItemBean doc_info) {
        this.doc_info = doc_info;
    }

    public Mall getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(Mall goods_info) {
        this.goods_info = goods_info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMuid() {
        return muid;
    }

    public void setMuid(int muid) {
        this.muid = muid;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getCourse_hour_id() {
        return course_hour_id;
    }

    public void setCourse_hour_id(int course_hour_id) {
        this.course_hour_id = course_hour_id;
    }

    public int getOrder_album_id() {
        return order_album_id;
    }

    public void setOrder_album_id(int order_album_id) {
        this.order_album_id = order_album_id;
    }

    public int getMhm_id() {
        return mhm_id;
    }

    public void setMhm_id(int mhm_id) {
        this.mhm_id = mhm_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideo_binfo() {
        return video_binfo;
    }

    public void setVideo_binfo(String video_binfo) {
        this.video_binfo = video_binfo;
    }

    public String getStrtime() {
        return strtime;
    }

    public void setStrtime(String strtime) {
        this.strtime = strtime;
    }
}
