package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;
import com.seition.cloud.pro.newcloud.app.bean.member.PaySwitch;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.CouponService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BuyContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class BuyModel extends BaseModel implements BuyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BuyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<PayResponse> buyCourseVideo(String vids, String pay_for, int coupon_id) {
        String en_params = "";
        try {
            if (coupon_id != -1)
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for, "coupon_id", coupon_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buCourseVideo(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<PayResponse> buyCourseVideoItem(String vids, String sid, String pay_for, int vtype) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "vid", vids
                    , "pay_for", pay_for
                    , "sid", sid
                    , "vtype", vtype
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buyCourseVideoItem(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> buyCourseVideoNoWxOrAli(String vids, String pay_for, int coupon_id) {
        String en_params = "";
        try {
            if (coupon_id != -1)
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for, "coupon_id", coupon_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buCourseVideoNoWxOrAli(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<PayResponse> buyCourseLive(String live_id, String pay_for, int coupon_id) {
        String en_params = "";
        try {
            if (coupon_id != -1)
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("live_id", live_id, "pay_for", pay_for, "coupon_id", coupon_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("live_id", live_id, "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buCourseLive(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<PayResponse> buyCourseOffline(String vids, String pay_for, int coupon_id) {
        String en_params = "";
        try {
            if (coupon_id != -1)
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for, "coupon_id", coupon_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buCourseOffline(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> buyCourseOffline1(String vids, String pay_for, int coupon_id) {
        String en_params = "";
        try {
            if (coupon_id != -1)
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for, "coupon_id", coupon_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vids", vids, "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buCourseOffline1(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> addFreeOrder(String vid, int vtype, String pay_for, int coupon_id) {
        String en_params = "";
        try {
            if (coupon_id != -1)
                en_params = M.getEncryptData(
                        MApplication.getCodedLock()
                        , M.getMapString(
                                "vid", vid
                                , "vtype", vtype,
                                "pay_for", pay_for,
                                "coupon_id", coupon_id));
            else
                en_params = M.getEncryptData(
                        MApplication.getCodedLock(), M.getMapString(
                                "vid", vid
                                , "vtype", vtype
                                , "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .addFreeOrder(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<UserAccount> getUserAccount(boolean iscache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUserAccount(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<UserAccount>, ObservableSource<UserAccount>>() {
                             @Override
                             public ObservableSource<UserAccount> apply(Observable<UserAccount> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getUserAccount(dataBeanObservable
                                                 , new DynamicKey("HomeUserAccount")
                                                 , new EvictProvider(iscache))
                                         .map(new Function<UserAccount, UserAccount>() {
                                             @Override
                                             public UserAccount apply(UserAccount arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<PaySwitch> getPaySwitch() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("hextime", hex, "token", token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getPaySwitch(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<CouponBeans> getVideoCoupon(String id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("id", id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .getCourseCoupon(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<CouponBeans> getLiveCoupon(String live_id) {

        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("live_id", live_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .getLiveCoupon(en_params, Utils.getAouthToken(mApplication));
    }
}