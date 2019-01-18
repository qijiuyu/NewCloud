package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.config.FreeCourseNotLoginWatchVideo;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseVideoFreeTime;
import com.seition.cloud.pro.newcloud.app.bean.course.SeitionDetailsBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.VH;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.BindService;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.CouponService;
import com.seition.cloud.pro.newcloud.home.api.service.CourseService;
import com.seition.cloud.pro.newcloud.home.api.service.ExamService;
import com.seition.cloud.pro.newcloud.home.api.service.LiveService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class CourseModel extends BaseModel implements CourseContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CourseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Examination> getCourseExamInfo(int paper_id, int exams_type) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "paper_id", paper_id
                    , "exams_type", exams_type
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getExamInfo(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<CourseOnline> getLiveDetails(String courseId, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString("live_id", courseId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getLiveDetails(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOnline>, ObservableSource<CourseOnline>>() {
                             @Override
                             public ObservableSource<CourseOnline> apply(Observable<CourseOnline> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .getCourseNow(dataBeanObservable
                                                 , new DynamicKey(MessageConfig.LIVE_INFO + courseId)
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<FreeCourseNotLoginWatchVideo> getNotLoginWatchFreeVideo() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("hextime", hex, "token", token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getNotLoginWatchFreeVideo(en_params))
                .flatMap(new Function<Observable<FreeCourseNotLoginWatchVideo>, ObservableSource<FreeCourseNotLoginWatchVideo>>() {
                             @Override
                             public ObservableSource<FreeCourseNotLoginWatchVideo> apply(Observable<FreeCourseNotLoginWatchVideo> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOnline> getCourseDetails(String courseId, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString("id", courseId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getCourseDetails(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<SeitionDetailsBean> getLiveSeitionDetails(String courseId, String seitionId) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "live_id", courseId
                            , "section_id", seitionId
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getLiveSeitionDetails(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<SeitionDetailsBean>, ObservableSource<SeitionDetailsBean>>() {
                             @Override
                             public ObservableSource<SeitionDetailsBean> apply(Observable<SeitionDetailsBean> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<VH> getVHId() {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getVHId(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<VH>, ObservableSource<VH>>() {
                             @Override
                             public ObservableSource<VH> apply(Observable<VH> dataBeanObservable) throws Exception {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<CourseSeition> getCourseSeitionList(String courseId, boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString("id", courseId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getCourseSeitionList(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<CourseSeition>, ObservableSource<CourseSeition>>() {
                             @Override
                             public ObservableSource<CourseSeition> apply(Observable<CourseSeition> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .getCourseSeitionList(dataBeanObservable, courseId, new DynamicKey(MessageConfig.COURSE_SEITION_LIST)
                                                 , new EvictProvider(isCache))
                                         .map(listReply -> listReply);
                             }
                         }
        );*/
    }

    @Override
    public Observable<DataBean> collectCourse(int type, String source_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type, "sctype", 2, "source_id", source_id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .collectCourse(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<DataBean>, ObservableSource<DataBean>>() {
                    @Override
                    public ObservableSource<DataBean> apply(Observable<DataBean> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<Share> getShare(String type, String vid, String mhm_id) {
        String en_params = "";
        try {
            if (mhm_id != null && !mhm_id.trim().isEmpty())
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type, "vid", vid, "mhm_id", mhm_id));
            else
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type, "vid", vid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getShareUrl(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Share>, ObservableSource<Share>>() {
                    @Override
                    public ObservableSource<Share> apply(Observable<Share> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });*/
    }

    @Override
    public Observable<CouponBean> getExchangeCard(String coupon_code, int mhm_id, double price, String vid) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(
                    MApplication.getCodedLock()
                    , M.getMapString("coupon_code", coupon_code
                            , "vid", vid
                            , "mhm_id", mhm_id
                            , "price", price));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .getExchangeCard(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<DataBean> cancelExchangeCard(String coupon_code, int mhm_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(
                    MApplication.getCodedLock()
                    , M.getMapString(
                            "coupon_code", coupon_code
                            , "mhm_id", mhm_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(CouponService.class)
                .cancelExchangeCard(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<PayResponse> rechargeCardUse(String pay_for, String card_number) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("pay_for", pay_for, "card_number", card_number));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return  mRepositoryManager
//                .obtainRetrofitService(UserService.class)
//                .rechargeBanlance(en_params, Utils.getAouthToken(mApplication));
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .rechargeBanlance(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> addStudyRecord(String vid, String sid, long time) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("vid", vid, "sid", sid, "time", time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .addStudyRecord(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<CourseVideoFreeTime> getVideoFreeTime(String id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "id", id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getVideoFreeTime(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<FaceSence> getFaceSence() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("hextime", hex, "token", token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = Utils.getAouthToken(mApplication);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getFaceSence(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<FaceStatus> getFaceSaveStatus() {
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getFaceSaveStatus(Utils.getAouthToken(mApplication)));
    }
}