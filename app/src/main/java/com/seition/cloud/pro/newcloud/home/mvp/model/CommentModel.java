package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.comment.Comments;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CommentContract;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CourseService;
import com.seition.cloud.pro.newcloud.home.api.service.LecturerService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class CommentModel extends BaseModel implements CommentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CommentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Comments> getTeacherComment(int page, int count, String teacherId, boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "page", page
                            , "count", count
                            , "teacher_id", teacherId
                            , "type", 2
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(LecturerService.class)
                .getComment(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Comments>, ObservableSource<Comments>>() {
                             @Override
                             public ObservableSource<Comments> apply(Observable<Comments> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .getTeacherComment(dataBeanObservable
                                                 , new DynamicKey(teacherId+MessageConfig.COURSE_INFO)
                                                 , new EvictProvider(isCache))
                                         .map(listReply -> listReply);
                             }
                         }
                );*/
    }

    @Override
    public Observable<Comments> getComment(int page, int count, String courseId, int type, boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "page", page
                            , "count", count
                            , "kzid", courseId
                            , "kztype", type
                            , "type", 2
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getComment(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Comments>, ObservableSource<Comments>>() {
                             @Override
                             public ObservableSource<Comments> apply(Observable<Comments> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .getComment(dataBeanObservable
                                                 , new DynamicKey(courseId + type + MessageConfig.COURSE_INFO)
                                                 , new EvictProvider(isCache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<DataBean> commentCourse(String kzid, int kztype, String content, int score) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "kzid", kzid
                            , "kztype", kztype
                            , "content", content
                            , "score", score
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .commentCourse(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<CommentConfig> getInitReviewConfig() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getInitReviewConfig(hex, token);
    }

    @Override
    public Observable<DataBean> commentTeacher(String teacher_id, int kztype, String content, int score) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "teacher_id", teacher_id
                            , "kztype", kztype
                            , "content", content
                            , "score", score
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mRepositoryManager
                .obtainRetrofitService(LecturerService.class)
                .commentTeacher(en_params, Utils.getAouthToken(mApplication));
    }
}