package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.config.CredPayConfig;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallListData;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallRankData;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_CreditDetails;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.HomeService;
import com.seition.cloud.pro.newcloud.home.api.service.MallService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class MallModel extends BaseModel implements MallContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MallModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CredPayConfig> getInitCredpayConfig() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getInitCredpayConfig(hex, token);
    }

    @Override
    public Observable<Data_CreditDetails> getCountConfig() {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getCreditConfig(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Data_CreditDetails>, ObservableSource<Data_CreditDetails>>() {
                             @Override
                             public ObservableSource<Data_CreditDetails> apply(Observable<Data_CreditDetails> dataBeanObservable) {
                                 return dataBeanObservable;
                             }
                         }
                );
    }


    @Override
    public Observable<MallCategory> getMallCategory(int goods_category_id, boolean iscache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("goods_category_id", goods_category_id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MallService.class)
                .getMallCategory(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<MallCategory>, ObservableSource<MallCategory>>() {
                             @Override
                             public ObservableSource<MallCategory> apply(Observable<MallCategory> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MallCache.class)
                                         .getMallCategory(dataBeanObservable
                                                 , new DynamicKey(goods_category_id)
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );


    }

    @Override
    public Observable<AdvertBean> getMallBanner(String place, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("place", place));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getBanners(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<AdvertBean>, ObservableSource<AdvertBean>>() {
                             @Override
                             public ObservableSource<AdvertBean> apply(Observable<AdvertBean> dataBeanObservable) {
                                 return mRepositoryManager.obtainCacheService(Cache.BannerCache.class)
                                         .getBanners(dataBeanObservable
                                                 , new DynamicKey("MallBanner")
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<MallRankData> getMallRankDatas(String type, String keyword, int page, int count, boolean iscache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type, "keyword", keyword, "page", page, "count", count));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MallService.class)
                .getMallRankDatas(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<MallRankData>, ObservableSource<MallRankData>>() {
                             @Override
                             public ObservableSource<MallRankData> apply(Observable<MallRankData> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MallCache.class)
                                         .getMallRankDatas(dataBeanObservable
                                                 , type, keyword, page, count
                                                 , new DynamicKey(page)
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<MallListData> getMallListDatas(String type, String goods_category, String keyword, int page, int count, boolean iscache) {
        System.out.println();
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type, "goods_category", goods_category, "keyword", keyword, "page", page, "count", count));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MallService.class)
                .getMallListDatas(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<MallListData>, ObservableSource<MallListData>>() {
                             @Override
                             public ObservableSource<MallListData> apply(Observable<MallListData> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MallCache.class)
                                         .getMallListDatas(dataBeanObservable
                                                 , type, goods_category, keyword, page, count
                                                 , new DynamicKey(page)
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );


    }

/*    @Override
    public Observable<ReceiveGoodsAddresss> getReceiveAddress(boolean iscache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MallService.class)
                .getReceiveAddress(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<ReceiveGoodsAddresss>, ObservableSource<ReceiveGoodsAddresss>>() {
                             @Override
                             public ObservableSource<ReceiveGoodsAddresss> apply(Observable<ReceiveGoodsAddresss> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getReceiveAddress(dataBeanObservable
                                                 , new DynamicKey("Address")
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }*/

    @Override
    public Observable<DataBean> exchangeMallGood(int goods_id, int num, String address_id) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("goods_id", goods_id, "num", num, "address_id", address_id));

        String outhToken = Utils.getAouthToken(mApplication);
        return mRepositoryManager
                .obtainRetrofitService(MallService.class)
                .exchangeMallGood(en_params, outhToken);
    }

    @Override
    public Observable<PayResponse> useAliPayOrWxPay(int goods_id, int num, String address_id, double price, String payStyle) {
        String en_params = M.getEncryptData(
                MApplication.getCodedLock()
                , M.getMapString(
                        "goods_id", goods_id
                        , "count", num
                        , "money", price
                        , "pay", payStyle
                        , "address_id", address_id));

        String outhToken = Utils.getAouthToken(mApplication);
        return mRepositoryManager
                .obtainRetrofitService(MallService.class)
                .useAliPayOrWxPay(en_params, outhToken);
    }

    @Override
    public Observable<ReceiveGoodsAddresss> getReceiveAddress(boolean iscache) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .receiveAddresses(Utils.getAouthToken(mApplication));
    }
}