package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsClassify;
import com.seition.cloud.pro.newcloud.app.bean.news.ARRNewsItem;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.NewsContract;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.NewsService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class NewsModel extends BaseModel implements NewsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public NewsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ARRNewsClassify> getNewsClassifyList(String keysord, boolean cache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(NewsService.class)
                .getNewsClassifyList(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<ARRNewsClassify>, ObservableSource<ARRNewsClassify>>() {
                             @Override
                             public ObservableSource<ARRNewsClassify> apply(Observable<ARRNewsClassify> dataBeanObservable) {
                                 return mRepositoryManager.obtainCacheService(Cache.NewsCache.class)
                                         .cacheNewsClassifyList(dataBeanObservable
                                                 , new DynamicKey(keysord)
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<ARRNewsItem> getNewsList(int page, int count, String cid, String keysord, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(
                    MApplication.getCodedLock()
                    , M.getMapString(
                            "page", page,
                            "count", count,
                            "cid", cid
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(NewsService.class)
                .getNewsList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<ARRNewsItem>, ObservableSource<ARRNewsItem>>() {
                             @Override
                             public ObservableSource<ARRNewsItem> apply(Observable<ARRNewsItem> dataBeanObservable) {
                                 return mRepositoryManager.obtainCacheService(Cache.NewsCache.class)
                                         .cacheNewsList(dataBeanObservable
                                                 , page, count, cid
                                                 , new DynamicKey(keysord)
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }
}