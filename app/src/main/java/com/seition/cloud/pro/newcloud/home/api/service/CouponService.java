package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface CouponService {
    //获取相应课程的可使用的优惠券
    String GetCourseCoupon = "video.getCanUseCouponList";
    //获取相应直播的可使用的优惠券
    String GetLiveCoupon = "live.getCanUseCouponList";
    //机构-获取可领取的优惠券列表
    String OrganizationCoupon = "school.getCouponList";
    //获取我的优惠券列表
    String MyCoupon = "coupon.getMyCouponList";
    //领取优惠券
    String GrantCoupon = "coupon.grant";
    //使用课程卡
    String UseCoupon = "coupon.use";
    //使用会员卡
    String UseVipCoupon = "coupon.useVipCard";

    String Coupon = "coupon_line_yh.getMyCouponList";
    //卡券-获取实体卡内卡券
    String GetExchangeCard = "coupon.getExchangeCard";
    //卡券-取消实体卡内卡券
    String CancelExchangeCard = "coupon.cancelExchangeCard";

    @POST(GetCourseCoupon)
    Observable<CouponBeans> getCourseCoupon(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(GetLiveCoupon)
    Observable<CouponBeans> getLiveCoupon(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(OrganizationCoupon)
    Observable<CouponBeans> getOrganizationCoupon(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(MyCoupon)
    Observable<CouponBeans> getMyCoupon(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(GrantCoupon)
    Observable<DataBean> grantCoupon(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(UseCoupon)
    Observable<DataBean> useCoupon(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(UseVipCoupon)
    Observable<DataBean> useVipCoupon(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(GetExchangeCard)
    Observable<CouponBean> getExchangeCard(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(CancelExchangeCard)
    Observable<DataBean> cancelExchangeCard(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
}