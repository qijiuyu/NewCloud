package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.InitApp;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.user.User;

import io.reactivex.Observable;


public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void toHome();

        void toRegister(String app_token, String app_login_type);

        void showRegisterType(String type);

        void showFaceSaveStatus(FaceStatus status);

        void setFaceLoginTextVisibiliity(boolean isvisibility);
//        void showFaceSense(FaceSence faceSence,boolean isOpen);
    }

//    interface FaceDetailsView extends IView {
//        void showFaceSaveStatus(FaceStatus status);
//    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<User> login(String uname, String password) throws Exception;

        Observable<InitApp> getMcryptKey();

        Observable<RegisterTypeInit> getInitRegisterType();

        Observable<DataBean> feedBack(String content, String way);

        Observable<DataBean> getUnionId(String access_token);

        Observable<User> loginSync(String app_token, String app_login_type);

        Observable<FaceStatus> getFaceSaveStatus();

        Observable<FaceSence> getFaceSence();

    }
}
