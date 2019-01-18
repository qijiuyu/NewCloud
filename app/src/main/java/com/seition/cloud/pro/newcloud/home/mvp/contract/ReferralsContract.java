package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.bean.referrals.Arr_Referrals;
import com.seition.cloud.pro.newcloud.app.bean.referrals.OwnerQRCode;
import com.seition.cloud.pro.newcloud.app.bean.referrals.ReferralsBean;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by addis on 2018/11/23.
 */
public interface ReferralsContract {

    interface View extends MultiView {
        void showMyQRCode(OwnerQRCode qrCode);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Arr_Referrals> getReferralsList(int page, int count,String uid);

        Observable<OwnerQRCode> getOwnerQRCode();
    }
}
