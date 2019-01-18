package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.user.RegistResp;
import com.seition.cloud.pro.newcloud.app.bean.user.User;

import io.reactivex.Observable;


public interface RegisterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showVerifyTime(int time);

        void regeisterResult();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<RegistResp> getVerifyCode(String en_params);

        Observable<User> register(String en_params);

//        Observable<User> register( String phone, String uname, String password, int type,String code);

    }
}
