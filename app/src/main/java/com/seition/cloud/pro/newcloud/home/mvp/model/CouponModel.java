package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBeans;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.service.CouponService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CouponContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


@ActivityScope
public class CouponModel extends BaseModel implements CouponContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CouponModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CouponBeans> getOrganizationCoupons(int type, String school_id, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString("type", type, "school_id", school_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .getOrganizationCoupon(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<CouponBeans>, ObservableSource<CouponBeans>>() {
                             @Override
                             public ObservableSource<CouponBeans> apply(Observable<CouponBeans> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CouponCache.class)
                                         .getOrganizationCoupons(dataBeanObservable
                                                 , new DynamicKey(type+school_id+ PreferenceUtil.getInstance(mApplication).getString("user_id",""))
                                                 , new EvictProvider(cache))

                                         .map(new Function<CouponBeans, CouponBeans>() {

                                             @Override
                                             public CouponBeans apply(CouponBeans dataBeanReply) throws Exception {
                                                 return dataBeanReply;
                                             }
                                         });
                             }
                         }
                );*/
    }

    @Override
    public Observable<CouponBeans> getCourseCoupon(int type, String courseId) {
        String en_params = "";
        try {
            if (type == 1)
                en_params = M.getEncryptData(
                        MApplication.getCodedLock(),
                        M.getMapString(
                                "id", courseId,
                                "canot", type));
            else en_params = M.getEncryptData(
                    MApplication.getCodedLock(),
                    M.getMapString(
                            "id", courseId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .getCourseCoupon(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CouponBeans>, ObservableSource<CouponBeans>>() {
                             @Override
                             public ObservableSource<CouponBeans> apply(Observable<CouponBeans> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }


    @Override
    public Observable<CouponBeans> getLiveCoupon(int type, String liveId) {
        String en_params = "";
        try {
            if (type == 1)
                en_params = M.getEncryptData(
                        MApplication.getCodedLock(),
                        M.getMapString(
                                "live_id", liveId,
                                "canot", type));
            else en_params = M.getEncryptData(
                    MApplication.getCodedLock(),
                    M.getMapString(
                            "live_id", liveId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .getLiveCoupon(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CouponBeans>, ObservableSource<CouponBeans>>() {
                             @Override
                             public ObservableSource<CouponBeans> apply(Observable<CouponBeans> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<CouponBeans> getMyCoupons(int type, int status, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "type", type
                            , "status", status));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return/* Observable.just(*/mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .getMyCoupon(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<CouponBeans>, ObservableSource<CouponBeans>>() {
                             @Override
                             public ObservableSource<CouponBeans> apply(Observable<CouponBeans> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CouponCache.class)
                                         .getOrganizationCoupons(dataBeanObservable
                                                 , new DynamicKey(type + PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(cache))

                                         .map(new Function<CouponBeans, CouponBeans>() {

                                             @Override
                                             public CouponBeans apply(CouponBeans dataBeanReply) throws Exception {
                                                 return dataBeanReply;
                                             }
                                         });
                             }
                         }
        );*/
    }

    @Override
    public Observable<DataBean> grantCoupon(String code) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("code", code));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .grantCoupon(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> useCoupon(String coupon_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(
                    MApplication.getCodedLock()
                    , M.getMapString("coupon_id", coupon_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .useCoupon(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> useVipCoupon(String coupon_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(
                    MApplication.getCodedLock()
                    , M.getMapString("coupon_id", coupon_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .useVipCoupon(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<CouponBeans> getCanUseCourseCouponWithMe(String courseId) {
//        String en_params = "";
//        try {
//            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type,"status",status));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Observable.just(mRepositoryManager
//                .obtainRetrofitService(CouponService.class)
//                .getMyCoupon(en_params, Utils.getAouthToken(mApplication)))
//                .flatMap(new Function<Observable<CouponBean>, ObservableSource<CouponBean>>() {
//                             @Override
//                             public ObservableSource<CouponBean> apply(Observable<CouponBean> dataBeanObservable) throws Exception {
//                                 return mRepositoryManager.obtainCacheService(Cache.CouponCache.class)
//                                         .getOrganizationCoupons(dataBeanObservable
//                                                 , new DynamicKey( type)
//                                                 , new EvictProvider(cache))
//                                         .map(new Function<CouponBean, CouponBean>() {
//                                             @Override
//                                             public CouponBean apply(CouponBean dataBeanReply) throws Exception {
//                                                 return dataBeanReply;
//                                             }
//                                         });
//                             }
//                         }
//                );
        return null;
    }

    @Override
    public Observable<CouponBeans> getCanUseLiveCouponWithMe(String courseId) {
        return null;
    }
}