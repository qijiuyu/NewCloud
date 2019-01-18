package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.group.Category;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupData;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupMember;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupTheme;
import com.seition.cloud.pro.newcloud.app.config.Service;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by addis on 2018/5/8.
 */
public interface GroupService {
    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=getGroupCate")
    Observable<Category> getGroupCategory();


    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=getList")
    Observable<GroupData> getGroupList(@Query("page") int page, @Query("count") int count, @Query("cate_id") String cate_id);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=editGroup")
    Observable<DataBean> editGroup(@Query("group_id") int group_id, @Query("name") String name, @Query("group_logo") int group_logo
            , @Query("cate_id") String cate_id, @Query("intro") String intro, @Query("announce") String announce);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=createGroup")
    Observable<DataBean> createGroup(@Query("name") String name, @Query("group_logo") int group_logo
            , @Query("cate_id") String cate_id, @Query("type") String type, @Query("intro") String intro, @Query("announce") String announce);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=getGroupInfo")
    Observable<DataBean<Group>> getGroupDetails(@Query("group_id") String group_id);


    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=getGroupoTpList")
    Observable<DataBean<ArrayList<GroupTheme>>> getTopicList(@Query("group_id") String group_id, @Query("page") int page, @Query("count") int count, @Query("dist") String dist, @Query("keysord") String keysord);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=deleteGroup")
    Observable<DataBean> deleteGroup(@Query("group_id") String group_id);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=quitGroup")
    Observable<DataBean> quitGroup(@Query("group_id") String group_id);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=joinGroup")
    Observable<DataBean> joinGroup(@Query("group_id") String group_id);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=operatTopic")
    Observable<DataBean> themeOperate(@Query("tid") String tid, @Query("action") int action, @Query("type") String type);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=deleteTopic")
    Observable<DataBean> themeDelete(@Query("tid") String tid, @Query("group_id") String group_id);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=commentTopic")
    Observable<DataBean> themeReply(@Query("tid") String tid, @Query("group_id") String group_id);

    @GET(Service.DOMAIN_NAME_DAFENGCHE+"&mod=Group&act=getGroupMember")
    Observable<DataBean<ArrayList<GroupMember>>> getGroupMemberList(@Query("group_id") String group_id, @Query("page") int page, @Query("count") int count, @Query("type") String type);


}