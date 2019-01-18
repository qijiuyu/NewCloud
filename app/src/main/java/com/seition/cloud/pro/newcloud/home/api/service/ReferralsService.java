package com.seition.cloud.pro.newcloud.home.api.service;

import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.referrals.Arr_Referrals;
import com.seition.cloud.pro.newcloud.app.bean.referrals.OwnerQRCode;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/11/26.
 */
public interface ReferralsService {

    String REFERRALS_LIST = "user.member";
    String QR_CODE = "user.qrCode";

    @POST(REFERRALS_LIST)
    Observable<Arr_Referrals> getReferralsList(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(QR_CODE)
    Observable<OwnerQRCode> getQRCode(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);
}
