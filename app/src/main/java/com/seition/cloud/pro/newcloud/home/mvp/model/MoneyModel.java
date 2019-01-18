package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_SpiltDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.MoneyDetailResponse;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MoneyContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class MoneyModel extends BaseModel implements MoneyContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MoneyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Data_BalanceDetails> getBanlanceConfig(boolean iscache) {
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
    public Observable<Data_SpiltDetails> getIncomeConfig(boolean iscache) {
       String s =  Utils.getAouthToken(mApplication);
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getSpiltConfig(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Data_SpiltDetails>, ObservableSource<Data_SpiltDetails>>() {
                             @Override
                             public ObservableSource<Data_SpiltDetails> apply(Observable<Data_SpiltDetails> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<Data_CreditDetails> getCountConfig(boolean iscache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getCreditConfig(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Data_CreditDetails>, ObservableSource<Data_CreditDetails>>() {
                             @Override
                             public ObservableSource<Data_CreditDetails> apply(Observable<Data_CreditDetails> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<PayResponse> rechargeBanlance(String pay_for, String money) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("pay_for",pay_for,"money",money));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .rechargeBanlance(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> recharge(String type, String exchange_score) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type",type,"exchange_score",exchange_score));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .rechargeJifen(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<DataBean>, ObservableSource<DataBean>>() {
                    @Override
                    public ObservableSource<DataBean> apply(Observable<DataBean> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<DataBean> incomeToWithdraw(String exchange_balance, String type, int card_id) {
        String en_params = "";
        try {
            if(type.equals("unionpay"))
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("exchange_balance",exchange_balance,"type",type,"card_id",card_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("exchange_balance",exchange_balance,"type",type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .incomeToWithdraw(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<DataBean>, ObservableSource<DataBean>>() {
                    @Override
                    public ObservableSource<DataBean> apply(Observable<DataBean> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }


    @Override
    public Observable<MoneyDetailResponse> getBalanceList(int limit, int page, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("limit",limit,"page",page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getBalanceList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<MoneyDetailResponse>, ObservableSource<MoneyDetailResponse>>() {
                    @Override
                    public ObservableSource<MoneyDetailResponse> apply(Observable<MoneyDetailResponse> dataBeanObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                .getBalanceList(dataBeanObservable
                                        , new DynamicKey(page)
                                        , new EvictProvider(iscache))
                                .map(new Function<MoneyDetailResponse, MoneyDetailResponse>() {
                                    @Override
                                    public MoneyDetailResponse apply(MoneyDetailResponse arrayListDataBean) throws Exception {

                                        return arrayListDataBean;
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<MoneyDetailResponse> getSpiltList(int limit, int page, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("limit",limit,"page",page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getSpiltList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<MoneyDetailResponse>, ObservableSource<MoneyDetailResponse>>() {
                    @Override
                    public ObservableSource<MoneyDetailResponse> apply(Observable<MoneyDetailResponse> dataBeanObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                .getSpiltList(dataBeanObservable
                                        , new DynamicKey(page)
                                        , new EvictProvider(iscache))
                                .map(new Function<MoneyDetailResponse, MoneyDetailResponse>() {
                                    @Override
                                    public MoneyDetailResponse apply(MoneyDetailResponse arrayListDataBean) throws Exception {

                                        return arrayListDataBean;
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<MoneyDetailResponse> getCreditList(int limit, int page, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("limit",limit,"page",page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getCreditList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<MoneyDetailResponse>, ObservableSource<MoneyDetailResponse>>() {
                    @Override
                    public ObservableSource<MoneyDetailResponse> apply(Observable<MoneyDetailResponse> dataBeanObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                .getCreditList(dataBeanObservable
                                        , new DynamicKey(page)
                                        , new EvictProvider(iscache))
                                .map(new Function<MoneyDetailResponse, MoneyDetailResponse>() {
                                    @Override
                                    public MoneyDetailResponse apply(MoneyDetailResponse arrayListDataBean) throws Exception {

                                        return arrayListDataBean;
                                    }
                                });
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}