package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategorys;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface LibraryService {
    String DOC_LIST = "doc.getList";//文库列表
    String DOC_MY_LIST = "doc.getMyList";//我的文库
    String DOC_CATEGORY = "doc.getCategory";//文库分类
    String DOC_EXCHANGE = "doc.exchange";//兑换


    @POST(DOC_LIST)
    Observable<Arr_Library> getLibraryList(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(DOC_MY_LIST)
    Observable<Arr_Library> getOwnerLibraryList(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(DOC_EXCHANGE)
    Observable<DataBean> exchangeLibrary(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(DOC_CATEGORY)
    Observable<LibraryCategorys> getCommonCategory();
}