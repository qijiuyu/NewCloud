package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageComment;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatter;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatterItem;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageSystem;

import io.reactivex.Observable;


public interface MessageContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
    }

    interface FragmentView extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Arr_MessageLatter> getMessagePrivateLetters();
        Observable<Arr_MessageComment> getMessageComment();
        Observable<Arr_MessageSystem> getMessageSystems();
        Observable<Arr_MessageLatterItem> getChatList(String id);
        Observable<DataBean> replyMsg(String content,String id, String uid);
    }
}
