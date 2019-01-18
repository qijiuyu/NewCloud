package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.LecturerService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LecturerContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class LecturerModel extends BaseModel implements LecturerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LecturerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Lecturers> getLecturers(int page, int count, String cateId, String orderBy, boolean cache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(
                    MApplication.getCodedLock()
                    , M.getMapString(
                            "page", page
                            , "count", count
                            , "cateId", cateId
                            , "orderBy", orderBy));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Observable<Lecturers> observable = mRepositoryManager
                .obtainRetrofitService(LecturerService.class)
                .getLecturers(en_params, Utils.getAouthToken(mApplication));
        if (cache) {
            return Observable.just(observable)
                    .flatMap(new Function<Observable<Lecturers>, ObservableSource<Lecturers>>() {
                                 @Override
                                 public ObservableSource<Lecturers> apply(Observable<Lecturers> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.LecturerCache.class)
                                             .getLecturers(dataBeanObservable
                                                     , page, count, cateId, orderBy
                                                     , new DynamicKey(cateId)
                                                     , new EvictProvider(true))
                                             .map(listReply -> listReply);
                                 }
                             }
                    );
        } else
            return observable;

    }

    @Override
    public Observable<Lecturer> getLectureDetails(int teacher_id, boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("teacher_id", teacher_id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LecturerService.class)
                .getLectureDetails(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Lecturer>, ObservableSource<Lecturer>>() {
                    @Override
                    public ObservableSource<Lecturer> apply(Observable<Lecturer> dataBeanObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(Cache.LecturerCache.class)
                                .getLectureDetails(dataBeanObservable
                                        , new DynamicKey(MessageConfig.TEACHER_GETINFO + "id=" + teacher_id)
                                        , new EvictProvider(isCache))
                                .map(listReply -> listReply);
                    }
                });
    }

    @Override
    public Observable<FollowState> doTeacherFollow(String user_id) {
        String en_params = "";
        try {
            en_params =
                    M.getEncryptData(MApplication.getCodedLock(), M.getMapString("user_id", user_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .doFollow(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<FollowState> cancelTeacherFollow(String user_id) {
        String en_params = "";
        try {
            en_params =
                    M.getEncryptData(MApplication.getCodedLock(), M.getMapString("user_id", user_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .cancelFollow(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<CommonCategory> getLectureCategory(boolean isCache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LecturerService.class)
                .getLectureCategory(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CommonCategory>, ObservableSource<CommonCategory>>() {
                             @Override
                             public ObservableSource<CommonCategory> apply(Observable<CommonCategory> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CategoryCache.class)
                                         .getCommonCategory(dataBeanObservable
                                                 , new DynamicKey("LectureCategory")
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }


    @Override
    public Observable<CourseOnlines> getTeacherCourse(int page, int count, int teacher_id, boolean isCache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count, "teacher_id", teacher_id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager.obtainRetrofitService(LecturerService.class)
                .getTeacherCourse(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.LecturerCache.class)
                                         .getTeacherCourse(dataBeanObservable
                                                 , new DynamicKey(page + "" + teacher_id)
                                                 , new EvictProvider(isCache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }
}