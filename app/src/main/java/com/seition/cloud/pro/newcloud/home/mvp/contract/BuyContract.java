package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;
import com.seition.cloud.pro.newcloud.app.bean.member.PaySwitch;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface BuyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showUserAccount(UserAccount account);

        void showPayView(ArrayList<String> datas);

        void showPayResult(PayResponse data);

        void showCoupon(ArrayList<CouponBean> couponBeans);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<PayResponse> buyCourseVideo(String vids, String pay_for, int coupon_id);

        Observable<PayResponse> buyCourseVideoItem(String vids, String sid, String pay_for,int vtype);

        Observable<DataBean> buyCourseVideoNoWxOrAli(String vids, String pay_for, int coupon_id);

        Observable<PayResponse> buyCourseLive(String live_id, String pay_for, int coupon_id);

        Observable<PayResponse> buyCourseOffline(String vids, String pay_for, int coupon_id);//微信支付和支付宝支付

        Observable<DataBean> buyCourseOffline1(String vids, String pay_for, int coupon_id);//其他支付

        Observable<DataBean> addFreeOrder(String vid, int vtype, String pay_for, int coupon_id);


        Observable<UserAccount> getUserAccount(boolean iscache);

        Observable<PaySwitch> getPaySwitch();

        Observable<CouponBeans> getVideoCoupon(String id);

        Observable<CouponBeans> getLiveCoupon(String live_id);

    }
}
