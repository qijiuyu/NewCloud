package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by addis on 2018/4/12.
 */

public interface CouponContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    public interface View extends MultiView {
        void showFragment(ArrayList<FragmentBean> fragmenList);

        void setData(ArrayList<CouponBean> list);

        void refresh();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    public interface Model extends IModel {
        Observable<CouponBeans> getCourseCoupon(int type, String courseId);

        Observable<CouponBeans> getLiveCoupon(int type, String courseId);

        Observable<CouponBeans> getOrganizationCoupons(int type, String school_id, boolean cache);

        Observable<CouponBeans> getMyCoupons(int type, int status, boolean cache);

        Observable<DataBean> grantCoupon(String code);

        Observable<DataBean> useCoupon(String coupon_id);

        Observable<DataBean> useVipCoupon(String coupon_id);

        Observable<CouponBeans> getCanUseCourseCouponWithMe(String courseId);

        Observable<CouponBeans> getCanUseLiveCouponWithMe(String courseId);
    }
}
