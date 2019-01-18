package com.seition.cloud.pro.newcloud.home.api.service;

import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.bean.member.VipUser;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface MemberService {
    //获取所有会员等级列表
    String VipGrades = "user.getUserVipList";

    //获取最新会员
    String NewMembers = "user.getNewVipUser";

    //VIP课程
    String VipCourses = "user.vipCourse";

    @POST(VipGrades)
    Observable<Member> getVipGrades( @Header("oauth-token") String oauthToken);

    @POST(NewMembers)
    Observable<VipUser> getNewMembers(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(VipCourses)
    Observable<CourseOnlines> getVipCourses(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
}
