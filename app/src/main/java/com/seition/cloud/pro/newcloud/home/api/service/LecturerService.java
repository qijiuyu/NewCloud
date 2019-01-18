package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.comment.Comments;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface LecturerService {

    String LectureDetails = "teacher.getInfo";
    String LectureHome = "home.teacher";

    //讲师-获取讲师列表
    String LectureList = "teacher.getList";
    //	讲师-获取讲师分类
    String LectureCategory = "teacher.getCategory";

    //讲师-获取讲师课程列表
    String LecturCourses = "teacher.getVideoList";
    //点评讲师
    String LecturAddReview = "teacher.addReview";
    //讲师点评列表
    String LecturCommentList = "teacher.getCommentList";

    //点评列表
    @POST(LecturCommentList)
    Observable<Comments> getComment(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(LectureList)
    Observable<Lecturers> getLecturers(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

  /*  @POST( "&mod=Video&act=screen")
    Observable<DataBean<ArrayList<CourseSearch>>> searchCourses(@Query("keyword") String keyword, @Query("location") String location
            , @Query("page") int page, @Query("count") int count, @Query("cate_id") String cate_id, @Query("order") String order);*/

    @POST(LectureHome)
    Observable<Lecturers> getHomeLectures(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(LectureDetails)
    Observable<Lecturer> getLectureDetails(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(LectureCategory)
    Observable<CommonCategory> getLectureCategory(@Header("oauth-token") String oauthToken);

    //添加点评
    @POST(LecturAddReview)
    Observable<DataBean> commentTeacher(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(LecturCourses)
    Observable<CourseOnlines> getTeacherCourse(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


}