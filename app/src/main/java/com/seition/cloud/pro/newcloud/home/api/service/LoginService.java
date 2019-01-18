package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.user.RegistResp;
import com.seition.cloud.pro.newcloud.app.bean.user.User;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface LoginService {
    //登录
    String login = "passport.login";
    //第三方登录
    String LoginSync = "passport.loginSync";
    //注册
    String regist = "passport.regist";
    //验证手机验证码
    String checkPhoneCode = "passport.clickPhoneCode";
    //获取验证码
    String getRegPhoneCode = "passport.getRegphoneCode";
    //找回密码
    String getPasswordByPhone = "passport.phoneGetPwd";
    //验证验证码是否正确
    String checkRegWdCode = "passport.clickRepwdCode";
    //修改
    String ModifyPassword = " passport.doModifyPassword";

    //重置密码
    String ResetPassword = "passport.savePwd";
    //通过Email找回密码
    String getPasswordByEmail = "passport.doFindPasswordByEmail";

    @POST(login)
    Observable<User> login(@Header("en-params") String enParams);

    @POST(LoginSync)
    Observable<User> loginSync(@Header("en-params") String en_params);

    @POST(getRegPhoneCode)
    Observable<RegistResp> getRegVerifyCode(@Header("en-params") String enParams);

    @POST(getPasswordByPhone)
    Observable<DataBean> getPasswordBackVerifyCode(@Header("en-params") String enParams);

    @POST(checkRegWdCode)
    Observable<DataBean> checkPhoneCode(@Header("en-params") String enParams);

    @POST(regist)
    Observable<User> register(@Header("en-params") String enParams);

    @POST(ModifyPassword)
    Observable<DataBean> modifierPassword(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(ResetPassword)
    Observable<DataBean> resetPssword(@Header("en-params") String enParams);

    @POST(getPasswordByEmail)
    Observable<DataBean> getPasswordByEmail(@Header("en-params") String enParams);
//    @GET(regist)
//    Observable<User> register(@Query("login") String phone
//            , @Query("uname") String uname, @Query("password") String password, @Query("type") int type, @Query("code") String code);
 /*   Observable<DataBean<User>> register(@Query("login") String phone
            , @Query("uname") String uname, @Query("password") String password
            , @Query("type") int type, @Query("code") String code);*/
}