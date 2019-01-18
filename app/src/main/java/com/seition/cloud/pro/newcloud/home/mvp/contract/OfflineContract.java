package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchool;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchoolResponse;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface OfflineContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface OfflineListView extends MultiView {
        void setDialogData(ArrayList<OfflineSchool> datas, boolean pull);

        void showCategoryWindows(ArrayList<CommonCategory> commonCategories);
    }

    interface OfflineDetailsView extends IView {
        void showDetails(CourseOffline courseOffline);

        void setIscollect(boolean iscollect);

        void share(Share data);

        void send(boolean successful);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<CourseOfflines> getOfflineCourses(int page, int count, String cateId, String orderBy, String school_id, String time, boolean cache);

        Observable<CourseOffline> getOfflineCourseDetails(String id, boolean cache);

        Observable<CommonCategory> getOfflineCategory(boolean iscache);

        Observable<CourseOfflines> getMyOfflineCourses(int page, int count, boolean cache);

        Observable<CourseOfflines> getMyCollectOfflineCourses(int page, int count);

        Observable<CourseOfflines> getMyTeachOfflineCourses(int page, int count);

        Observable<OfflineSchoolResponse> getOfflineSchools(int page, int count, boolean cache);

        Observable<DataBean> collectOffine(boolean is_collect, String source_id);

        Observable<Share> getShare(String type, String vid, String mhm_id);

        Observable<DataBean> sendMessage(String content, int to);
    }
}
