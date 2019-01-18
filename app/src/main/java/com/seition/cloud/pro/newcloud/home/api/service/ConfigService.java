package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.AreaInfo;
import com.seition.cloud.pro.newcloud.app.bean.InitApp;
import com.seition.cloud.pro.newcloud.app.bean.MarketStatus;
import com.seition.cloud.pro.newcloud.app.bean.McryptKey;
import com.seition.cloud.pro.newcloud.app.bean.VersionInfo;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;
import com.seition.cloud.pro.newcloud.app.bean.config.CredPayConfig;
import com.seition.cloud.pro.newcloud.app.bean.config.FreeCourseNotLoginWatchVideo;
import com.seition.cloud.pro.newcloud.app.bean.config.RefundConfig;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.member.PaySwitch;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xzw on 2018/6/15.
 */

public interface ConfigService {
    //	配置-获取app版本
    String AppVerSions = "config.getAppVersion";

    //	配置-获取支付开关
    String GetPaySwitch = "config.paySwitch";

    /**
     * 根据后台返回数据 只需用条用 GetFaceScene 这个接口
     */
    //配置-获取人脸配置开启场景
    String GetFaceScene = "config.getFaceScene";//{"data":{"is_open":0,"open_scene":[]},"code":1,"msg":"获取成功"}
    //配置-获取指定场景人脸开启状态
    String GetFaceStatus = "config.getFaceStatus";//{"data":{"is_open":0},"code":1,"msg":"获取成功"}


    //	配置-获取视频加密key
    String GetVideoKey = "config.getVideoKey";
    //	配置-获取系统数据加密KEY
    String GetMcryptKey = "config.getMcryptKey";
    //	配置-初始化APP
    String InitAppUrl = "config.initApp";
    //	配置- 是否开启注册功能
    String InitRegisterType = "passport.registerConf";
    //	配置-是否可以申请退款
    String InitRefundConfig = "order.refundConf";
    //	配置- 商城积分支付是否开启
    String InitCredpayConfig = "goods.credpayConf";
    //	配置-获取营销数据开关
    String GetMarketStatus = "config.getMarketStatus";
    //配置-获取省市区列表
    String GetArea = "config.getArea";
    //配置-获取省市区列表
    String InitReviewConfig = "course.reviewConf";

    String FreeCourseLoginSwitch = "config.freeCourseLoginSwitch";

    //直播详情
    @POST(FreeCourseLoginSwitch)
    Observable<FreeCourseNotLoginWatchVideo> getNotLoginWatchFreeVideo(@Header("en-params") String en_params);

    @POST(GetPaySwitch)
    Observable<PaySwitch> getPaySwitch(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(GetArea)
    Observable<AreaInfo> getArea(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(AppVerSions)
    Observable<VersionInfo> getVersionInfo(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(GetFaceScene)
    Observable<FaceSence> getFaceSence(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(GetFaceStatus)
    Observable<DataBean> getFaceStatus(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(InitAppUrl)
    Observable<InitApp> getMcryptKey(@Query("hextime") String hextime, @Query("token") String token);

    @POST(InitRegisterType)
    Observable<RegisterTypeInit> getInitRegisterType(@Query("hextime") String hextime, @Query("token") String token);

    @POST(InitRefundConfig)
    Observable<RefundConfig> getInitRefundConfig(@Query("hextime") String hextime, @Query("token") String token);

    @POST(InitCredpayConfig)
    Observable<CredPayConfig> getInitCredpayConfig(@Query("hextime") String hextime, @Query("token") String token);

    @POST(GetMarketStatus)
    Observable<MarketStatus> getMarketStatus(@Header("en-params") String en_params);

    @POST(InitReviewConfig)
    Observable<CommentConfig> getInitReviewConfig(@Query("hextime") String hextime, @Query("token") String token);
}
