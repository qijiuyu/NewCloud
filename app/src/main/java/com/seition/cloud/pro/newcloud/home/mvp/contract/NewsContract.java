package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsClassify;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsItem;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface NewsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showFragment(ArrayList<FragmentBean> fragmenList);
    }

    interface DetailsView extends IView {
    }

    interface FragmentView extends MultiView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<ARRNewsClassify> getNewsClassifyList(String keysord, boolean cache);//获取资讯分类列表

        Observable<ARRNewsItem> getNewsList(int page, int count, String cid, String keysord, boolean cache);//资讯列表
    }
}
