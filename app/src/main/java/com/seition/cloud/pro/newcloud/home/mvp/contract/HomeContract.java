package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.ChangeFaceResponse;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.InitApp;
import com.seition.cloud.pro.newcloud.app.bean.MarketStatus;
import com.seition.cloud.pro.newcloud.app.bean.McryptKey;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSearch;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveBean;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organizations;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserCount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserMember;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
    }

    interface HomeView extends IView {
        void setBanners(ArrayList<AdvertBean> advertBeans);

        void setCategory(ArrayList<CommonCategory> category);

        void setNewCourse(ArrayList<CourseOnline> courseOnlines);

        void setHotCourse(ArrayList<CourseOnline> courseOnlines);
    }

    interface CoursesFragmentView extends MultiView {
        void showCategoryWindows(ArrayList<CommonCategory> commonCategories);
    }

    interface OwnerFragmentView extends IView {
        void showUserInfo(MessageUserInfo user);

        void showUserAccount(UserAccount userAccount);

        void showUserCount(UserCount userCount);

        void showUserMember(UserMember userMember);
    }

    interface SetInfFragmentView extends IView {
        void showUserInfo(MessageUserInfo user);

        void showSetUserFace(ChangeFaceResponse response);
    }

    interface ModifierPwdView extends IView {
        void modifierPasswordSuccessful(boolean success);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface HomeModel extends IModel {
        Observable<AdvertBean> getHomeBanner(String place, boolean iscache);
//        Observable<Banner> getMallBanner(String place);

        Observable<CommonCategory> getCommonCategory(boolean iscache);

        Observable<CommonCategory> getHomeCategory(int count, boolean iscache);


//        Observable<DataBean<ArrayList<CommonCategory>>> getHomeCoursessCategory( int count,boolean iscache);

        Observable<CourseOfflines> getHomeOffline(int page, int count, String cateId
                , String orderBy, String school_id, String time, boolean iscache);

        Observable<Lecturers> getHomeLectures(int count, boolean iscache);

        Observable<Organizations> getHomeOrganization(int count, boolean iscache);

        Observable<MessageUserInfo> getUserInfo(String user_id, boolean iscache);

        Observable<UserAccount> getUserAccount(boolean iscache);

        Observable<UserCount> getUserCount(boolean iscache);

        Observable<UserMember> getUserVip(String time, boolean iscache);

        Observable<ChangeFaceResponse> setUserFace(MultipartBody.Part file);

        Observable<DataBean> setUserInfo(String uname, int sex, String intro);

        /*New Or Changed Interface*/

        Observable<CourseSearch> searchCourses(
                String keyword, String location, int type, int page, int count
                , String cate_id, String order, String vip_id, boolean cache);

        Observable<CourseOnlines> getHomePerfectCourses(int count, boolean iscache);

        Observable<CourseOnlines> getHomeNewCourses(int count, boolean iscache);

        Observable<HomeLiveBean> getHomeLive(int count, boolean iscache);//


        Observable<DataBean> modifierPassword(String oldpassword, String password, String repassword);
//        Observable<CourseSearch> searchCourses(String keyword, String location , int page, int count, String cate_id, String order, boolean  cache) throws Exception;


        Observable<InitApp> getMcryptKey();

        Observable<MarketStatus> getMarketStatus();
    }


}
