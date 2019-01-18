package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.live.LiveTeacher;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface LiveService {
    //首页直播列表
    String HomeLive = "home.live";

    //直播列表
    String LiveList = "live.getList";

    //我的直播列表
    String MyLiveList = "live.getMyList";
    //我教的直播列表
    String MyTeachLiveList = "live.getTeacherList";
    //我教的课程列表
    String MyTeachCourseList = "video.getTeacherList";

    //收藏课程或直播列表
    String CollectCoursesOnline = "video.getCollectList";
    //直播详情
    String LiveDetails = "live.getInfo";
    //直播-直播课时信息	live.getCatalog


    //直播-讲师
    String LiveTeachers = "live.getScreenTeacher";

    //收藏课程（点播，直播，先下课）
    String CollectCourse = "video.collect";


    @POST(CollectCoursesOnline)
    Observable<CourseOnlines> getCollectCourse(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);


    @POST(HomeLive)
    Observable<HomeLiveBean> getHomeLive(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(LiveList)
    Observable<CourseOnlines> getLives(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    //   CourseOnlines
    @POST(MyLiveList)
    Observable<CourseOnlines> getMyLives(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(MyTeachLiveList)
    Observable<CourseOnlines> getMyTeachLiveList(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(MyTeachCourseList)
    Observable<CourseOnlines> getMyTeachCourseList(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);


    @POST(LiveTeachers)
    Observable<LiveTeacher> getLiveScreenTeacher(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(CollectCourse)
    Observable<DataBean> collectCourse(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

}
