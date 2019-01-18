package com.seition.cloud.pro.newcloud.home.api.service;

import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchoolResponse;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface OfflineService {
    //线下课列表
    public final static String OfflineCoursesList = "lineVideo.getList";
    //我购买的线下课列表
    public final static String MyOfflineCoursesList = "lineVideo.getMyList";
    //我收藏的线下课列表
    public final static String MyCollectOfflineCoursesList = "lineVideo.getCollectList";
    //我教的线下课列表
    public final static String MyTeachOfflineCourses = "lineVideo.getTeacherList";
    //线下课详情
    public final static String OfflineCoursesDetails = "lineVideo.getInfo";
    //首页-线下课
    public final static String HomeOfflineCourses = "home.lineVideo";

    //线下课-获取筛选条件
    public final static String OfflineSchools = "lineVideo.screen";


    /* @POST( "&mod=Video&act=lineClassList")
     Observable<OfflineCoursesResponse> getOfflineCourses(@Query("page") int page, @Query("count") int count, @Query("cateId") String cateId, @Query("orderBy") String orderBy, @Query("school_id") String school_id, @Query("time") String time);

     @POST( "&mod=Video&act=lineClassList")
     Observable<OfflineCoursesResponse> getOfflineCourses(@Query("page") int page, @Query("count") int count, @Query("cateId") String cateId, @Query("orderBy") String orderBy, @Query("time") String time);
 */
//    @POST("&mod=School&act=getSchoolCategory")
//    Observable<DataBean<ArrayList<CommonCategory>>> getOfflineCategory();


    @POST(HomeOfflineCourses)
    Observable<CourseOfflines> getHomeOfflines(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(OfflineCoursesList)
    Observable<CourseOfflines> getOfflineList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(OfflineCoursesDetails)
    Observable<CourseOffline> getOfflineCourseDetails(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    @POST(MyCollectOfflineCoursesList)
    Observable<CourseOfflines> getMyCollectOfflineCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(MyTeachOfflineCourses)
    Observable<CourseOfflines> getMyTeachOfflineCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(MyOfflineCoursesList)
    Observable<CourseOfflines> getMyOfflineCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(OfflineSchools)
    Observable<OfflineSchoolResponse> getOfflineShools(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
}
