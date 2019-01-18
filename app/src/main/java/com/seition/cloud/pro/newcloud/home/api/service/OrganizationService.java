package com.seition.cloud.pro.newcloud.home.api.service;

import com.seition.cloud.pro.newcloud.app.bean.order.OrganizationOrder;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.bean.organization.OrganizationStatus;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organizations;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface OrganizationService {
    //	机构-获取机构详情
    String OrganizationDetails  = "school.getInfo";
    String OrganizationLectures = "";
    String HomeOrganization = "home.school";
    //获取机构列表
    String OrganizationList ="school.getList";
    //获取机构状态
    String OrganizationState ="school.getMySchoolStatus";
    //机构-申请成为机构
    String OrganizationApply ="school.apply";
    //机构-获取机构订单
    String OrganizationOrderList ="school.getOrderList";

    /*  @POST( "&mod=School&act=getSchoolList")
      Observable<DataBean<ArrayList<Organization>>> getOrganizationList(@Query("page") int page, @Query("count") int count, @Query("keyword") String keyword
              , @Query("user_id") String user_id, @Query("cateId") String cateId, @Query("orderBy") String orderBy);
    */

    @POST(OrganizationList)
    Observable<Organizations> getOrganizationList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
//    @Query("page") int page, @Query("count") int count, @Query("keyword") String keyword
//            , @Query("user_id") String user_id, @Query("cateId") String cateId, @Query("orderBy") String orderBy

//    @POST( "&mod=School&act=getSchoolCategory")
//    Observable<DataBean<ArrayList<CommonCategory>>> getOrganizationCategory( @Header("oauth-token") String oauthToken);

    @POST(OrganizationDetails)
    Observable<Organization> getOrganizationDetails(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


//    @POST( "&mod=Video&act=videoList")
//    Observable<CourseOnlines> getOrganizationCourses(@Query("page") int page, @Query("count") int count, @Query("school_id") int school_id);//

    @POST(HomeOrganization)
    Observable<Organizations> getHomeOrganization(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(OrganizationState)
    Observable<OrganizationStatus> getMyOrganizationStatue(@Header("oauth-token") String oauthToken);

    @POST(OrganizationApply)
    Observable<Organization> applyOrganization(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(OrganizationOrderList)
    Observable<OrganizationOrder> getOrganizationOrderList(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);
}