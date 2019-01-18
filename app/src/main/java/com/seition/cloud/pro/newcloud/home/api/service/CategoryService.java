package com.seition.cloud.pro.newcloud.home.api.service;

import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategorys;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface CategoryService {
    //全局-通用分类
    String  CommonCategory= "home.getCateList";
    //首页-分类
    String HomeCategory = "home.cate";

    //问答-获取分类
    String WenDaCategory = "wenda.getCategory";
    // wenda.getCategory

    //  机构-获取机构分类
    String OrganizationCategory ="school.getCategory";

    @POST(CommonCategory)
    Observable<CommonCategory> getCommonCategory( @Header("oauth-token") String oauthToken);

    @POST(HomeCategory)
    Observable<CommonCategory> getHomeCategory(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(WenDaCategory)
    Observable<QaCategorys> getQuestionCategory(@Header("oauth-token") String oauthToken);

    @POST( OrganizationCategory)
    Observable<CommonCategory> getOrganizationCategory(@Header("oauth-token") String oauthToken);
}
