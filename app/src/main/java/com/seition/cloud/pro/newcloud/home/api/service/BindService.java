package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.bind.Banks;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindAliAccount;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindBank;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.user.User;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface BindService {
    //全局-通用分类
    String  BindBankList= "user.bankCardList";
    //获取支持绑定银行卡列表
    String  BankList= "user.getBankList";
    //解绑银行卡
    String  UnBindBank= "user.unbindBankCard";
    //添加绑定银行卡
    String  AddBindBank= "user.bindBankCard";

    @POST(BindBankList)
    Observable<BindBank> getBindBanks(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(BankList)
    Observable<Banks> getBanks(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(UnBindBank)
    Observable<DataBean> unbindBank(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(AddBindBank)
    Observable<DataBean> addBindBank(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //获取支付宝绑定信息
    String  BindAliPayInfo= "user.getAlipayInfo";
    //设置支付宝绑定账号
    String  SetAliPay= "user.setAlipay";
    //解绑支付宝
    String  unbindAliPayInfo= "user.unbindAlipay";
    @POST(BindAliPayInfo)
    Observable<BindAliAccount> getAlipayInfo(@Header("oauth-token") String oauthToken);
    @POST(SetAliPay)
    Observable<BindAliAccount> setAlipay(@Header("en-params") String en_params,@Header("oauth-token") String oauthToken);
    @POST(unbindAliPayInfo)
    Observable<DataBean> unbindAlipay(@Header("en-params") String en_params,@Header("oauth-token") String oauthToken);

    //人脸识别-检测储存状态
    String  FaceSaveStatus= "youtu.status";
    //人脸识别-检测人脸是否存在
    String  FaceIsExist= "youtu.isExist";
    //人脸识别-创建人脸(绑定)
    String  FaceCeate= "youtu.createPerson";
    //人脸识别-添加人脸(继续完成绑定)
    String  FaceAdd= "youtu.addFace";
    //人脸识别-人脸验证
    String  FaceVerify= "youtu.faceverify";
    //	人脸识别-人脸登陆
    String  FaceLogin= "youtu.faceLogin";
    //	第三方绑定状态
    String  BindData= "user.bindData";


    @POST(FaceSaveStatus)
    Observable<FaceStatus> getFaceSaveStatus( @Header("oauth-token") String oauthToken);

    @POST(FaceLogin)
    Observable<User> faceLogin(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(FaceVerify)
    Observable<DataBean> faceVerify(@Header("en-params") String en_params,@Header("oauth-token") String oauthToken);
    @POST(FaceCeate)
    Observable<DataBean> faceCreate(@Header("en-params") String en_params,@Header("oauth-token") String oauthToken);
    @POST(FaceAdd)
    Observable<DataBean> faceAdd(@Header("en-params") String en_params,@Header("oauth-token") String oauthToken);
    @POST(BindData)
    Observable<DataBean> getLoginBindStatus(@Header("oauth-token") String oauthToken);
}
