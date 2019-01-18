package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSearch;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface SearchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends MultiView {
        void showCategoryWindows(ArrayList<CommonCategory> commonCategories);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<CourseSearch> searchCourses(String keyword, String location, int type, int page, int count, String cate_id, String order, String vip_id, boolean cache) throws Exception;

        Observable<CommonCategory> getCommonCategory(boolean iscache) throws Exception;
    }
}
