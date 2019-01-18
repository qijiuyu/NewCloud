package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.bind.Banks;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindAliAccount;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindBank;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.BindService;
import com.seition.cloud.pro.newcloud.home.api.service.UploadService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BindManageContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import okhttp3.MultipartBody;


@ActivityScope
public class BindManageModel extends BaseModel implements BindManageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BindManageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BindBank> getBindBanks(int limit, boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("limit", limit));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getBindBanks(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<BindBank>, ObservableSource<BindBank>>() {
                             @Override
                             public ObservableSource<BindBank> apply(Observable<BindBank> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.BindCache.class)
                                         .getBindBanks(dataBeanObservable
                                                 , new DynamicKey("BindBanks")
                                                 , new EvictProvider(isCache))
                                         .map(new Function<BindBank, BindBank>() {
                                             @Override
                                             public BindBank apply(BindBank arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );

    }

    @Override
    public Observable<Banks> getBanks(int area_id, boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("area_id", area_id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getBanks(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Banks>, ObservableSource<Banks>>() {
                             @Override
                             public ObservableSource<Banks> apply(Observable<Banks> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.BindCache.class)
                                         .getBanks(dataBeanObservable
                                                 , new DynamicKey("Banks")
                                                 , new EvictProvider(isCache))
                                         .map(new Function<Banks, Banks>() {
                                             @Override
                                             public Banks apply(Banks arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<DataBean> unbindBank(String id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("id", id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .unbindBank(en_params, Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<DataBean> addBindBank(String account, String accountmaster, String accounttype, String bankofdeposit, String location, String province, String city, String area, String tel_num) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(),
                    M.getMapString("account", account,
                            "accountmaster", accountmaster,
                            "accounttype", accounttype,
                            "bankofdeposit", bankofdeposit,
                            "location", location,
                            "province", province,
                            "city", city,
                            "area", area,
                            "tel_num", tel_num));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .addBindBank(en_params, Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<BindAliAccount> getAlipayInfo(boolean isCache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getAlipayInfo(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<BindAliAccount>, ObservableSource<BindAliAccount>>() {
                             @Override
                             public ObservableSource<BindAliAccount> apply(Observable<BindAliAccount> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.BindCache.class)
                                         .getAlipayInfo(dataBeanObservable
                                                 , new DynamicKey("BindAliAccount")
                                                 , new EvictProvider(isCache))
                                         .map(new Function<BindAliAccount, BindAliAccount>() {
                                             @Override
                                             public BindAliAccount apply(BindAliAccount arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<BindAliAccount> setAlipay(String real_name, String alipay_account) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("real_name", real_name, "alipay_account", alipay_account));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .setAlipay(en_params, Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<DataBean> unbindAlipay(String id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("id", id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .unbindAlipay(en_params, Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<FaceStatus> getFaceSaveStatus() {
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getFaceSaveStatus(Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<UploadResponse> uploadFile(MultipartBody.Part file) {
        return mRepositoryManager
                .obtainRetrofitService(UploadService.class)
                .uploadFile(file);
    }

    @Override
    public Observable<User> faceLogin(String attach_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("attach_id", attach_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .faceLogin(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> faceVerify(String attach_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("attach_id", attach_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .faceVerify(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> faceCreate(String attach_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("attach_id", attach_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .faceCreate(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> faceAdd(String attach_ids) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("attach_ids", attach_ids));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .faceAdd(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> getLoginBindStatus() {
        return mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getLoginBindStatus(Utils.getAouthToken(mApplication));
    }
}