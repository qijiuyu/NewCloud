package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.bean.order.OrganizationOrder;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.bean.organization.OrganizationStatus;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organizations;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface OrganizationContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息

    interface ListView extends MultiView {
        void showCategoryWindows(ArrayList<CommonCategory> commonCategories);
    }

    interface DetailsView extends IView {
        void setFragmenList(Organization organization);

        void setAttention(int attentionData);

        void share(Share data);
    }

    interface HomeView extends IView {
        void refreshDetails(Organization organization);
    }

    interface FragmentView extends MultiView {
        void showTeachers(ArrayList<Teacher> teachers, boolean pull);

        void setDatas(ArrayList<Order> orders, boolean pull);

        void showTeacherHome(Teacher teacher);
    }

    interface OwnerView extends IView {
        void showOrganizationStatus(OrganizationStatus organizationStatus);

        void showUploadAttachId(String attach_id);

        void showCategoryWindows(ArrayList<CommonCategory> commonCategories);
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        //        Observable<DataBean<ArrayList<Organization>>> getOrganizationList(int page, int count, String keyword, String user_id, String cateId, String orderBy, boolean cache);
        Observable<Organizations> getOrganizationList(int page, int count, String keyword, String user_id, String cateId, String orderBy, boolean cache);


        Observable<CommonCategory> getOrganizationCategory(boolean iscache);

        Observable<Organization> getOrganizationDetails(String school_id, boolean iscache);

        Observable<FollowState> doOrganizationFollow(String user_id);

        Observable<FollowState> cancelOrganizationFollow(String user_id);

        Observable<CourseOnlines> getOrganizationCourses(int page, int count, int school_id, boolean cache);

        Observable<Lecturers> getOrganizationTeacher(int page, int count, int school_id, boolean cache);

        Observable<OrganizationStatus> getMyOrganizationStatus();


        Observable<DataBean> uploadFiles(List<MultipartBody.Part> files);

        Observable<UploadResponse> uploadFile(MultipartBody.Part file);

        Observable<Organization> applyOrganization(String title,/*String logo_id,String cover_id,*/String cate_id,/*String info,*/String idcard, String phone, String province, String city, String area, String attach_id, String reason, String location, String address, String identity_id);

        Observable<OrganizationOrder> getOrganizationOrderList(int page, int count, String school_id, String pay_status, boolean iscache);

        Observable<Lecturers> getOrganizationLectures(int page, int count, String school_id, boolean iscache);

        Observable<Share> getShare(String type, String vid, String mhm_id);
    }
}
