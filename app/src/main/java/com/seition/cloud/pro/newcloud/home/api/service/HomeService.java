package com.seition.cloud.pro.newcloud.home.api.service;

import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface HomeService {
    String HomeBanner ="home.advert";
    @POST(HomeBanner)
    Observable<AdvertBean> getBanners(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);
}
