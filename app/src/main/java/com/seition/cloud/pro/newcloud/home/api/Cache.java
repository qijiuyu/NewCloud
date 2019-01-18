/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seition.cloud.pro.newcloud.home.api;

import android.os.Environment;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.AreaInfo;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.bind.Banks;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindAliAccount;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindBank;
import com.seition.cloud.pro.newcloud.app.bean.comment.Comments;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSearch;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveBean;
import com.seition.cloud.pro.newcloud.app.bean.course.SeitionDetailsBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRMoudles;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARR_TestClassify;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamRankUser;
import com.seition.cloud.pro.newcloud.app.bean.group.Category;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupData;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupMember;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupTheme;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.live.LiveTeacher;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallListData;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallRankData;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.bean.member.VipUser;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageComment;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatter;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatterItem;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageSystem;
import com.seition.cloud.pro.newcloud.app.bean.money.MoneyDetailResponse;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsClassify;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsItem;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComments;
import com.seition.cloud.pro.newcloud.app.bean.note.Notes;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchoolResponse;
import com.seition.cloud.pro.newcloud.app.bean.order.Orders;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organizations;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategorys;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionasks;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecord;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserCount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserMember;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.internal.RxCache;
import okhttp3.ResponseBody;
import retrofit2.http.Query;

/**
 * ================================================
 * 展示 {@link RxCache#using(Class)} 中需要传入的 Providers 的使用方式
 * <p>
 * Created by JessYan on 08/30/2016 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class Cache {

    public static String CACHE_SD_CARD = Environment.getExternalStorageDirectory().toString();
    public static String Cache_Local = CACHE_SD_CARD + "/CloudCourses";
    public static String Cache_Video = Cache_Local + "/video/";
    public static String Cache_File = Cache_Local + "/file/";
    public static String Cache_DB_NAME = "Cloud.db";
    public static String DB_NAME = "cloud-db";

    public interface LoginCache {
        /**
         * 登录与注册
         *
         * @param users
         * @return
         */
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<User> login(Observable<User> users, DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }

    public interface LibraryCache {
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Arr_Library> getLibraryList(Observable<Arr_Library> moudles
                , DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }

    public interface ExamCache {

        /**
         * 考试
         *
         * @param moudles
         * @return
         */
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<ARRMoudles> examMoudleList(Observable<ARRMoudles> moudles
                , DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<ARRExamBean> examList(Observable<ARRExamBean> moudles
                , DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<ARR_TestClassify> examClassifyList(Observable<ARR_TestClassify> moudles
                , DynamicKey idLastUserQueried, EvictProvider evictProvider);
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Exam> getExamOwner(Observable<Exam> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CollectExam> getCollectExam(Observable<CollectExam> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<ExamRankUser> getExamRankUser(Observable<ExamRankUser> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }

    public interface GroupCache {
        /**
         * 小组
         *
         * @param cates
         * @param idLastUserQueried
         * @param evictProvider
         * @return
         */
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Category> getGroupCategory(Observable<Category> cates, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<GroupData> getGroupList(Observable<GroupData> cates
                , int page, int count, String id, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> createGroup(Observable<DataBean> data, String name, int group_logo, String cate_id, String type, String intro, String announce);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> editGroup(Observable<DataBean> data, int group_id, String name, int group_logo, String cate_id, String intro, String announce);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean<Group>> getGroupDetails(Observable<DataBean<Group>> topics, String group_id, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean<ArrayList<GroupTheme>>> getTopicList(Observable<DataBean<ArrayList<GroupTheme>>> topics
                , String group_id, int page, int count, String dist, String keysord, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> deleteGroup(String group_id);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> quitGroup(String group_id);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> joinGroup(String group_id);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> themeOperate(String tid, int action, String type);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> themeDelete(String tid, String group_id);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean> themeReply(String tid, String group_id);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean<ArrayList<GroupMember>>> getGroupMemberList(Observable<DataBean<ArrayList<GroupMember>>> topics, String group_id, int page, int count,
                                                                        String type/*, DynamicKey idLastUserQueried, EvictProvider evictProvider*/);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean<ArrayList<GroupMember>>> getApplyGroupMemberList(Observable<DataBean<ArrayList<GroupMember>>> topics, String group_id, int page, int count,
                                                                             String type/*, DynamicKey idLastUserQueried, EvictProvider evictProvider*/);
    }

    public interface NewsCache {
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<ARRNewsClassify> cacheNewsClassifyList(Observable<ARRNewsClassify> data
                , DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<ARRNewsItem> cacheNewsList(Observable<ARRNewsItem> data
                , int page, int count, String cid, DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }

    public interface OrganizationCache {

        @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
        Observable<Organizations> getOrganizationList(Observable<Organizations> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CommonCategory> getOrganizationCategory(Observable<CommonCategory> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Organization> getOrganizationDetails(Observable<Organization> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);


        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getOrganizationCourses(Observable<CourseOnlines> datas,  DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Lecturers> getOrganizationTeachers(Observable<Lecturers> datas,  DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
        Observable<Organizations> getHomeOrganization(Observable<Organizations> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Lecturers> getOrganizationLecturers(Observable<Lecturers> datas,  DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Orders> getOrganizationOrderList(Observable<Orders> datas,  DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }

    public interface MallCache {

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<MallCategory> getMallCategory(Observable<MallCategory> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);


        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<MallRankData> getMallRankDatas(Observable<MallRankData> data, String type, String keyword, int page, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<MallListData> getMallListDatas(Observable<MallListData> data, String goods_category, String type, String keyword, int page, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

//        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
//        Observable<ReceiveGoodsAddress> getReceiveAddress(Observable<ReceiveGoodsAddress> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }

    public interface MessageCache {

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Arr_MessageLatter> getMessagePrivateLetters(Observable<Arr_MessageLatter> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Arr_MessageComment> getMessageComment(Observable<Arr_MessageComment> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Arr_MessageSystem> getMessageSystems(Observable<Arr_MessageSystem> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Arr_MessageLatterItem> getChatList(Observable<Arr_MessageLatterItem> data, String id, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }

    public interface CourseCache {

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseSearch> searchCourses(Observable<CourseSearch> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnline> getCourseNow(Observable<CourseOnline> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<SeitionDetailsBean> getLiveSeitionDetails(Observable<SeitionDetailsBean> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseSeition> getCourseSeitionList(Observable<CourseSeition> data, String CourseId, DynamicKey idLastUserQueried, EvictProvider evictProvider);


   /*     @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<String> searchCourses(Observable<String> data, String keyword, String location,  int type, int page, int count, String cate_id, String order, DynamicKey idLastUserQueried, EvictProvider evictProvider);
*/

        @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
        Observable<CourseOnlines> getMyCourses(Observable<CourseOnlines> data, int page, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        /*New */
        @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getCourses(Observable<CourseOnlines> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getHomePerfectCourses(Observable<CourseOnlines> data, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getHomeNewCourses(Observable<CourseOnlines> data, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);


        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Comments> getComment(Observable<Comments> data,  DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Comments> getTeacherComment(Observable<Comments> data,  DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }

    public interface LiveCache {


        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getMyLives(Observable<CourseOnlines> data, int page, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        // LiveListResponse  CourseOnlines
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getCollectCourse(Observable<CourseOnlines> data, int type, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<HomeLiveBean> getHomeLive(Observable<HomeLiveBean> data, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getLives(Observable<CourseOnlines> data, String cate_id, String keyword, String begin_time, String end_time, String order, String teacher_id, int page, int count, String status, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<LiveTeacher> getLiveScreenTeacher(Observable<LiveTeacher> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }

    public interface LecturerCache {
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Lecturers> getLecturers(Observable<Lecturers> data, int page, int count, String cateId, String orderBy, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<DataBean<ArrayList<Lecturers>>> getHomeLectures(Observable<DataBean<ArrayList<Lecturers>>> data, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);


        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<Lecturer> getLectureDetails(Observable<Lecturer> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getTeacherCourse(Observable<CourseOnlines> datas,  DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }

    public interface BannerCache {
        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
        Observable<AdvertBean> getBanners(Observable<AdvertBean> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);
//    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
//    Observable<Banner> getMallBanneer(Observable<Banner> data /*, DynamicKey idLastUserQueried, EvictProvider evictProvider*/);


    }

    public interface CouponCache {
        @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
        Observable<CouponBeans> getOrganizationCoupons(Observable<CouponBeans> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
        Observable<CouponBeans> getCoupons(Observable<CouponBeans> data, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }


    public interface CategoryCache {
        @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
        Observable<CommonCategory> getCommonCategory(Observable<CommonCategory> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
        Observable<CommonCategory> getHomeCategory(Observable<CommonCategory> data, int count, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }

    public interface OfflineCache {
        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOfflines> getOfflineCourses(Observable<CourseOfflines> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOffline> getOfflineCourseDetails(Observable<CourseOffline> data, String Id, DynamicKey idLastUserQueried, EvictProvider evictProvider);


//        @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
//        Observable<DataBean<ArrayList<CommonCategory>>> getOfflineCategory(Observable<DataBean<ArrayList<CommonCategory>>> data ,DynamicKey idLastUserQueried,EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOfflines> getMyOfflineCourses(Observable<CourseOfflines> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOfflines> getMyCollectOfflineCourses(Observable<CourseOfflines> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<OfflineSchoolResponse> getOfflineSchools(Observable<OfflineSchoolResponse> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }


    public interface QuestionaskCache {
        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Questionasks> getQuestionAsks(Observable<Questionasks> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Questionasks> getMyQuestion(Observable<Questionasks> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Questionasks> getMyAnswer(Observable<Questionasks> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Questionask> getQuestionDetails(Observable<Questionask> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Questionasks> getQuestionAnswerList(Observable<Questionasks> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<QaCategorys> getQuestionCategoryList(Observable<QaCategorys> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }

    public interface UserCache {
        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<MessageUserInfo> getUserInfo(Observable<MessageUserInfo> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<UserAccount> getUserAccount(Observable<UserAccount> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<UserCount> getUserCount(Observable<UserCount> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<UserMember> getUserVip(Observable<UserMember> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<StudyRecord> getStudyRecord(Observable<StudyRecord> data , DynamicKey idLastUserQueried, EvictProvider evictProvider);
        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<Orders> getExchangeRecord(Observable<Orders> data , DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<ReceiveGoodsAddresss> getReceiveAddress(Observable<ReceiveGoodsAddresss> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<AreaInfo> getArea(Observable<AreaInfo> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
        Observable<Notes> getMynotes(Observable<Notes> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<NoteComments> getMynoteComments(Observable<NoteComments> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<Orders> getOrders(Observable<Orders> data , DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<MoneyDetailResponse> getBalanceList(Observable<MoneyDetailResponse> data , DynamicKey idLastUserQueried, EvictProvider evictProvider);
        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<MoneyDetailResponse> getSpiltList(Observable<MoneyDetailResponse> data , DynamicKey idLastUserQueried, EvictProvider evictProvider);
        @LifeCache(duration = 4, timeUnit = TimeUnit.MINUTES)
        Observable<MoneyDetailResponse> getCreditList(Observable<MoneyDetailResponse> data , DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }

    public interface MemberCache {

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Member> getMembers(Observable<Member> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<VipUser> getNewMembers(Observable<VipUser> data, @Query("limit") int limit, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<CourseOnlines> getVipCourses(Observable<CourseOnlines> data, int vip_id, DynamicKey idLastUserQueried, EvictProvider evictProvider);
    }


    public interface BindCache {
        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<BindBank> getBindBanks(Observable<BindBank> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<Banks> getBanks(Observable<Banks> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);


        @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
        Observable<BindAliAccount> getAlipayInfo(Observable<BindAliAccount> data, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    }

}
