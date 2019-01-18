package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.live.LiveTeacher;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LiveContract;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CategoryService;
import com.seition.cloud.pro.newcloud.home.api.service.CourseService;
import com.seition.cloud.pro.newcloud.home.api.service.LiveService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class LiveModel extends BaseModel implements LiveContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LiveModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CourseOnlines> getLives(
            String cate_id, String keyword, String begin_time, String end_time
            , String order, String teacher_id, int page, int count, String status, boolean cache) {
        String en_params = M.getEncryptData(
                MApplication.getCodedLock()
                , M.getMapString(
                        "cate_id", cate_id
                        , "keyword", keyword
                        , "begin_time", begin_time
                        , "end_time", end_time
                        , "order", order
                        , "teacher_id", teacher_id
                        , "page", page
                        , "count", count
                        , "status", status));
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .getLives(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) {
                                 return mRepositoryManager.obtainCacheService(Cache.LiveCache.class)
                                         .getLives(dataBeanObservable
                                                 , cate_id, keyword, begin_time, end_time, order, teacher_id, page, count, status
                                                 , new DynamicKey("LiveList")
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<CommonCategory> getCommonCategory(boolean iscache) {

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getCommonCategory(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CommonCategory>, ObservableSource<CommonCategory>>() {
                             @Override
                             public ObservableSource<CommonCategory> apply(Observable<CommonCategory> dataBeanObservable) {
                                 return mRepositoryManager.obtainCacheService(Cache.CategoryCache.class)
                                         .getCommonCategory(dataBeanObservable
                                                 , new DynamicKey("CommonCategory")
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<LiveTeacher> getLiveScreenTeacher(int page, int count, boolean cache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .getLiveScreenTeacher(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<LiveTeacher>, ObservableSource<LiveTeacher>>() {
                             @Override
                             public ObservableSource<LiveTeacher> apply(Observable<LiveTeacher> dataBeanObservable) {
                                 return mRepositoryManager.obtainCacheService(Cache.LiveCache.class)
                                         .getLiveScreenTeacher(dataBeanObservable
                                                 , new DynamicKey("" + page)
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOnlines> getMyLives(int page, int count) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));
        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .getMyLives(en_params, outhToken))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOnlines> getMyTeachLiveList(int page, int count) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));
        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .getMyTeachLiveList(en_params, outhToken))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOnlines> getMyTeachCourseList(int page, int count) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));
        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .getMyTeachCourseList(en_params, outhToken))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOnlines> getMyCourses(int page, int count, boolean cache) {

        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));
        String outhToken = Utils.getAouthToken(mApplication);
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getMyCourses(en_params, outhToken))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .getMyCourses(dataBeanObservable
                                                 , page, count
                                                 , new DynamicKey("MyCoursesList" + PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<CourseOnlines> getCollectCourse(int type, boolean cache) {

        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type));
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .getCollectCourse(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.LiveCache.class)
                                         .getCollectCourse(dataBeanObservable
                                                 , type
                                                 , new DynamicKey(type == 2 ? "MyCollectCourse" : "MyCollectLive" + PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }
}