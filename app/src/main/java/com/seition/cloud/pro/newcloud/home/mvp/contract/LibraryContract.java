package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategoryBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategorys;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface LibraryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends MultiView {
        void setData(ArrayList<LibraryItemBean> datas);

        void addData(ArrayList<LibraryItemBean> datas);

        void showRightText(int position);

        void showCategoryWindows(ArrayList<LibraryCategoryBean> commonCategories);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Arr_Library> getLibraryList(int page, int count, String doc_category_id, String order, String cacheName, boolean updates);//获取试卷列表

        Observable<Arr_Library> getOwnerLibraryList(int page, int count, String cacheName, boolean updates);//获取试卷列表

        Observable<DataBean> exchangeLibrary(int doc_id);

        Observable<LibraryCategorys> getCommonCategory();
    }
}
