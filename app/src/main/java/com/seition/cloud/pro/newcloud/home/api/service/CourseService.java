package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.comment.Comments;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSearch;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.course.SeitionDetailsBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.live.VH;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface CourseService {

    //点播课程列表
    String CourseList = "video.getList";
    //首页最新课程
    String HomeNewCourses = "home.newVideo";
    //首页推荐课程
    String HomeHotCourses = "home.hotVideo";
    //我的（购买的）课程列表
    String MyCoursesList = "video.getMyList";
    //搜索
    String Search = "home.search";

    //直播章节详细信息
    String SeitionDetails = "live.getCatalog";
    //获取微吼ID
    String getVHId = "live.getWhUserId";
    //课程详情
    String CourseDetails = "video.getInfo";
    //直播详情
    String LiveDetails = "live.getInfo";
    //课程目录
    String CourseSeitionList = "video.getCatalog";

    //点评列表
    String CommentList = "video.render";

    //添加点评
    String Comment = "video.addReview";

    //获取课程分享地址
    String SHAREURL = "video.getShareUrl";


    //点评列表
    @POST(CommentList)
    Observable<Comments> getComment(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //讲师点评列表
    @POST(CommentList)
    Observable<Comments> getTeacherComment(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //添加点评
    @POST(Comment)
    Observable<DataBean> commentCourse(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //课程详情
    @POST(CourseDetails)
    Observable<CourseOnline> getCourseDetails(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //直播章节详细信息
    @POST(SeitionDetails)
    Observable<SeitionDetailsBean> getLiveSeitionDetails(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //获取微吼直播ID
    @POST(getVHId)
    Observable<VH> getVHId(@Header("oauth-token") String oauthToken);

    //直播详情
    @POST(LiveDetails)
    Observable<CourseOnline> getLiveDetails(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //课程章节列表
    @POST(CourseSeitionList)
    Observable<CourseSeition> getCourseSeitionList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    /* New  Or Changer Interface*/
    @POST(CourseList)
    Observable<CourseOnlines> getCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    @POST(HomeHotCourses)
    Observable<CourseOnlines> getHomePerfectCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(HomeNewCourses)
    Observable<CourseOnlines> getHomeNewCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(MyCoursesList)
    Observable<CourseOnlines> getMyCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(Search)
    Observable<CourseSearch> searchCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    @POST(SHAREURL)
    Observable<Share> getShareUrl(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


}
