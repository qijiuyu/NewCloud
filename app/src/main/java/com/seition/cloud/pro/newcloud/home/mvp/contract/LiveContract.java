package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.live.LiveTeacher;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface LiveContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息

    interface MainView extends IView {
        void setDialogData(ArrayList<LiveTeacher> datas, boolean pull);

        void showCategory(ArrayList<CommonCategory> categories);
    }

    interface View extends MultiView {
//        void setNewDialogData(ArrayList<LiveTeacher> liveTeachers);
//        void addDialogData(ArrayList<LiveTeacher> liveTeachers);
//        void showCategory(ArrayList<CommonCategory> categories);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        //  CourseOnlines
        Observable<CourseOnlines> getMyLives(int page, int count);

        Observable<CourseOnlines> getMyTeachLiveList(int page, int count);

        Observable<CourseOnlines> getMyTeachCourseList(int page, int count);

        Observable<CourseOnlines> getMyCourses(int page, int count, boolean cache);

        // LiveListResponse  CourseOnlines
        Observable<CourseOnlines> getCollectCourse(int type, boolean cache);


        Observable<CourseOnlines> getLives(String cate_id, String keyword, String begin_time, String end_time, String order, String teacher_id, int page, int count, String status, boolean cache);

        Observable<CommonCategory> getCommonCategory(boolean iscache);

        Observable<LiveTeacher> getLiveScreenTeacher(int page, int count, boolean cache);
    }
}
