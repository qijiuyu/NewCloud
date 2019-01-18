package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface LecturerContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息

    interface View extends MultiView {
        void showTeacher(Teacher teacher);
    }

    interface ListContainerView extends MultiView {
        void showLecturerCategory(ArrayList<CommonCategory> categories);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Lecturers> getLecturers(int page, int count, String cateId, String orderBy, boolean cache);

        Observable<Lecturer> getLectureDetails(int teacher_id, boolean isCache);

        Observable<FollowState> doTeacherFollow(String user_id);

        Observable<FollowState> cancelTeacherFollow(String user_id);

        Observable<CommonCategory> getLectureCategory(boolean isCache);

        Observable<CourseOnlines> getTeacherCourse(int page ,int count ,int teacher_id,boolean isCache);
    }

}
