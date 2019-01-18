package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.bind.Banks;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindAliAccount;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindBank;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface BindManageContract {

    interface BindBankListView extends MultiView {

    }

    interface ManageBankListView extends MultiView {
        void deleteBank(int position);
    }
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    interface AliManageView extends IView {
            void showAlipayInfo(BindAliAccount aliAccount);
    }

    interface FaceDetailsView extends IView {
        void showFaceSaveStatus(FaceStatus status);
    }

    interface FaceCheckView extends IView {
        void showUploadAttachId(UploadResponse response);
        void faceLogin(boolean success);
        void faceVerify(boolean success);
        void faceCreated(boolean success);
        void faceAdd(boolean success);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BindBank> getBindBanks(int limit ,boolean isCache);

        Observable<Banks> getBanks(int area_id , boolean isCache);
        Observable<DataBean> unbindBank(String id );
        Observable<DataBean> addBindBank(String account,String accountmaster,String accounttype,String bankofdeposit,String location,String province,String city,String area,String tel_num);

        Observable<BindAliAccount> getAlipayInfo(boolean isCache );
        Observable<BindAliAccount> setAlipay(String real_name,String alipay_account);
        Observable<DataBean> unbindAlipay(String id);
        Observable<FaceStatus> getFaceSaveStatus();

        Observable<UploadResponse> uploadFile(MultipartBody.Part file);

        Observable<User> faceLogin(String attach_id);
        Observable<DataBean> faceVerify(String attach_id);
        Observable<DataBean> faceCreate(String attach_id);
        Observable<DataBean> faceAdd(String attach_ids);
        Observable<DataBean> getLoginBindStatus();
    }
}
