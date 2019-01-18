package com.seition.cloud.pro.newcloud.home.api.service;

import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsClassify;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsItem;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface NewsService {
    final String classify = "news.getCategory";
    final String list = "news.getList";

    @POST(classify)
    Observable<ARRNewsClassify> getNewsClassifyList(@Header("oauth-token") String oauthToken);//资讯分类列表

    @POST(list)
    Observable<ARRNewsItem> getNewsList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
}
