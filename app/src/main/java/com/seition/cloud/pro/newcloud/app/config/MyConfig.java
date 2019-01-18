package com.seition.cloud.pro.newcloud.app.config;


/**
 * 数据配置及常量设置
 *
 * @author Addis
 */
public class MyConfig {

    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxbbb961a0b0bf577a";
    public static int no_read_message;// 未读私信数量
    public static int no_read_comment;// 未读评论数量
    public static int no_read_notify;// 未读系统消息数量

    public static final String ALIPAY = "alipay";
    public static final String WXPAY = "wxpay";
    public static final String ICNPAY = "lcnpay";//余额
    public static final String CREDIT = "credit";//积分
    public static final String UNIONPAY = "unionpay";//银行卡
    public static final String SPIPAY = "spipay";//收入余额
    public static final String PREFERENCE_NAME = "cache_chuyouyun";
    public static final String CACHE_SP_NAME = "cache_chuyouyun";

    public static final String WXPAY_SUCCESS = "wxpay_success";

    /**
     * 展示营销数据开关，与课程学习人数相关
     */
    public static boolean isOpenMarketStatus = false;

    public static boolean isDefaultOpenHome = true;//是否打卡软件默认打开首页，false打开登录界面

    /*###############################  人脸登录配置开关  ##############################################*/
    private static boolean isFaceChecking = false;//是否已经验证过人脸
    public static boolean isOpenFaceMoudle = true;//是否开启人脸功能
    public static boolean isOpenFaceMoudleLoginCompulsiveFaceCheck = true;//是否开启登录后强制人脸验证
    public static boolean isOpenFaceMoudleLoginSingleCheck = true;//是否开启人脸登录方式一次验证不退出不验证
    public static boolean isOpenFaceMoudleVideoSingleCheck = true;//是否开启人脸验证播放视频功能一次验证后无需验证
    public static boolean isOpenFaceMoudleExamSingleCheck = false;//是否开启人脸验证考试功能一次验证后无需验证


    public static boolean isOpenAboutSchool = true;//是否开启机构相关功能，单机构版须设为false
    public static boolean isOpenRefund = false;//是否开启退款功能，默认关闭

    public static boolean isOpenCredPay = true;//是否开启积分支付功能
    public static String credRatio = "";

    /*###############################  RequestCode  ##############################################*/
    public static final int RequestCodeReceive = 100;
    public static final int RequestCodeQuestionaskClassify = 200;
    public static final int RequestCodeArea = 300;
    public static final int RequestCodeCouponSelect = 400;
    public static final int RequestCodeEntityCard = 501;
    public static final int RequestCodeBank = 601;
    public static final int RequestCodeFace = 701;

    public static final int RequestCodeImagePicker = 801;
    public static final int RequestCodeImagePicker_Logo = 802;
    public static final int RequestCodeImagePicker_Cover = 803;
    public static final int RequestCodeImagePicker_Muti_Organ = 804;
    public static final int RequestCodeImagePicker_Muti_Id = 805;


    public static final int RequestCodeOrderReimburseCamera = 901;
    public static final int RequestCodeOrderReimburseMuti = 902;
    public static final int RequestCodeOrderReimburseBack = 903;

    /*###############################   ResultCode  ##############################################*/
    public static final int ResultCodeFaceCheck = 702;
    public static final int ResultCodeFaceTest = 703;


    /*###############################   配置  ##############################################*/
    public static final String Config_McryptKey = "McryptKey";
    public static final String Config_MarketStatus = "MarketStatus";
}
