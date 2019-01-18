package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryCategorys;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.LibraryService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LibraryContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class LibraryModel extends BaseModel implements LibraryContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LibraryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Arr_Library> getLibraryList(int page, int count, String doc_category_id, String order, String cacheName, boolean updates) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "page", page
                    , "count", count
                    , "doc_category_id", doc_category_id
                    , "order", order
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LibraryService.class)
                .getLibraryList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Arr_Library>, ObservableSource<Arr_Library>>() {
                    @Override
                    public ObservableSource<Arr_Library> apply(Observable<Arr_Library> dataBeanObservable) throws Exception {
                        return mRepositoryManager
                                .obtainCacheService(Cache.LibraryCache.class)
                                .getLibraryList(dataBeanObservable, new DynamicKey(cacheName), new EvictProvider(updates));
                    }
                });
    }

    @Override
    public Observable<Arr_Library> getOwnerLibraryList(int page, int count, String cacheName, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "page", page
                    , "count", count
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LibraryService.class)
                .getOwnerLibraryList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Arr_Library>, ObservableSource<Arr_Library>>() {
                    @Override
                    public ObservableSource<Arr_Library> apply(Observable<Arr_Library> dataBeanObservable) throws Exception {
                        return mRepositoryManager
                                .obtainCacheService(Cache.LibraryCache.class)
                                .getLibraryList(dataBeanObservable, new DynamicKey(cacheName + PreferenceUtil.getInstance(mApplication).getString("user_id", "")), new EvictProvider(cache));
                    }
                });
    }

    @Override
    public Observable<DataBean> exchangeLibrary(int doc_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("doc_id", doc_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(LibraryService.class)
                .exchangeLibrary(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<LibraryCategorys> getCommonCategory() {
        return mRepositoryManager
                .obtainRetrofitService(LibraryService.class)
                .getCommonCategory();
    }
}