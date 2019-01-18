package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSearch;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.SearchContract;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CategoryService;
import com.seition.cloud.pro.newcloud.home.api.service.CourseService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class SearchModel extends BaseModel implements SearchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CourseSearch> searchCourses(String keyword, String location, int type, int page, int count, String cate_id, String order, String vip_id, boolean cache) throws Exception {
        System.out.println(keyword + "," + location + "," + page + "," + count + "," + cate_id + "," + order);
        String en_params = "";
        if ("".equals(vip_id))
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "keyword", keyword
                            , "location", location
                            , "type", type
                            , "page", page
                            , "count", count
                            , "cate_id", cate_id
                            , "order", order));
        else
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("keyword", keyword, "location", location, "type", type, "page", page, "count", count, "cate_id", cate_id, "order", order, "vip_id", vip_id));
        String token = Utils.getAouthToken(mApplication);

        return mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .searchCourses(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<CommonCategory> getCommonCategory(boolean iscache) throws Exception {

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getCommonCategory(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CommonCategory>, ObservableSource<CommonCategory>>() {
                             @Override
                             public ObservableSource<CommonCategory> apply(Observable<CommonCategory> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CategoryCache.class)
                                         .getCommonCategory(dataBeanObservable
                                                 , new DynamicKey("CommonCategory")
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }


}