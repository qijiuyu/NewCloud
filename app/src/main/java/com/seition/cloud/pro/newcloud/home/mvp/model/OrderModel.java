package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.config.RefundConfig;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.order.OrderRefund;
import com.seition.cloud.pro.newcloud.app.bean.order.Orders;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.UploadService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrderContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;


@ActivityScope
public class OrderModel extends BaseModel implements OrderContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<RefundConfig> getInitRefundConfig() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getInitRefundConfig(hex, token);
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
    public Observable<Orders> getOrders(String type, String pay_status, String orderType, String schoolId, int page, int count, boolean isCache) {
        String en_params = "";
        if (schoolId == null || schoolId.isEmpty())
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type,
                    "pay_status", pay_status,
                    "order_type", orderType,
                    "page", page,
                    "count", count));
        else en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type,
                "pay_status", pay_status,
                "school_id", schoolId,
                "order_type", orderType,
                "page", page,
                "count", count));
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getOrders(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Orders>, ObservableSource<Orders>>() {
                             @Override
                             public ObservableSource<Orders> apply(Observable<Orders> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getOrders(dataBeanObservable
                                                 , new DynamicKey(type+page+pay_status)
                                                 , new EvictProvider(isCache))
                                         .map(listReply -> listReply);
                             }
                         }
                );*/

    }

    @Override
    public Observable<OrderRefund> refundOrderInfo(int order_type, int order_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "order_type", order_type,
                    "order_id", order_id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .refundOrderInfo(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> orderRefund(int order_type, int order_id, String refund_reason, String refund_note, String voucher) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "order_type", order_type,
                    "order_id", order_id,
                    "refund_reason", refund_reason,
                    "refund_note", refund_note,
                    "voucher", voucher
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .orderRefund(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> orderCancel(int order_type, int order_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "order_type", order_type,
                            "order_id", order_id
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .orderCancel(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> cancelApplicationForDrawbackOrder(int order_type, int order_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "order_type", order_type,
                            "order_id", order_id
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .cancelApplicationForDrawbackOrder(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<PayResponse> orderPay(int order_type, int order_id, int coupon_id, String pay_for) {
        String token = Utils.getAouthToken(mApplication);
        String en_params = "";
        try {
            if (coupon_id != 0)
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("order_type", order_type, "order_id", order_id, "coupon_id", coupon_id, "pay_for", pay_for));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("order_type", order_type, "order_id", order_id, "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .orderPay(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<Data_BalanceDetails> getBanlanceConfig() {
        String token = Utils.getAouthToken(mApplication);
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getBanlanceConfig(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Data_BalanceDetails>, ObservableSource<Data_BalanceDetails>>() {
                             @Override
                             public ObservableSource<Data_BalanceDetails> apply(Observable<Data_BalanceDetails> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
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
        String token = Utils.getAouthToken(mApplication);
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buCourseVideo(en_params, Utils.getAouthToken(mApplication));
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
        String token = Utils.getAouthToken(mApplication);
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .buCourseLive(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<PayResponse> buyCourseOffline(String vids, String pay_for, int coupon_id) {
        String en_params = "";
        try {
            if (coupon_id != -1 && coupon_id != 0)
                en_params = M.getEncryptData(MApplication.getCodedLock()
                        , M.getMapString(
                                "vids", vids
                                , "pay_for", pay_for
                                , "coupon_id", coupon_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock()
                        , M.getMapString(
                                "vids", vids
                                , "pay_for", pay_for));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = Utils.getAouthToken(mApplication);
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
    public Observable<UploadResponse> uploadFile(MultipartBody.Part file) {
        return mRepositoryManager
                .obtainRetrofitService(UploadService.class)
                .uploadFile(file);
    }
}