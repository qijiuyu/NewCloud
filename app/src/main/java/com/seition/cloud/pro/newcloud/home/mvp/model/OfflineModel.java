package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchoolResponse;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OfflineContract;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CategoryService;
import com.seition.cloud.pro.newcloud.home.api.service.CourseService;
import com.seition.cloud.pro.newcloud.home.api.service.LiveService;
import com.seition.cloud.pro.newcloud.home.api.service.MessageService;
import com.seition.cloud.pro.newcloud.home.api.service.OfflineService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class OfflineModel extends BaseModel implements OfflineContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OfflineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CourseOfflines> getOfflineCourses(
            int page, int count, String cateId, String orderBy, String school_id, String time, boolean cache) {
        String en_params;
        if ("".equals(school_id))
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count, "cateId", cateId, "orderBy", orderBy, "time", time));
        else
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count, "cateId", cateId, "orderBy", orderBy, "time", time, "school_id", school_id));

        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OfflineService.class)
                .getOfflineList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOfflines>, ObservableSource<CourseOfflines>>() {
                             @Override
                             public ObservableSource<CourseOfflines> apply(Observable<CourseOfflines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OfflineCache.class)
                                         .getOfflineCourses(dataBeanObservable
                                                 , new DynamicKey(page + "")
                                                 , new EvictProvider(cache))

                                         .map(new Function<CourseOfflines, CourseOfflines>() {

                                             @Override
                                             public CourseOfflines apply(CourseOfflines dataBeanReply) throws Exception {
                                                 System.out.println("线下课列表缓存");
                                                 return dataBeanReply;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOffline> getOfflineCourseDetails(String id, boolean cache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("id", id));

        String outhToken = Utils.getAouthToken(mApplication);
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OfflineService.class)
                .getOfflineCourseDetails(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOffline>, ObservableSource<CourseOffline>>() {
                             @Override
                             public ObservableSource<CourseOffline> apply(Observable<CourseOffline> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OfflineCache.class)
                                         .getOfflineCourseDetails(dataBeanObservable
                                                 , id
                                                 , new DynamicKey("OfflineCourseDetails" + id)
                                                 , new EvictProvider(cache))
                                         .map(new Function<CourseOffline, CourseOffline>() {
                                             @Override
                                             public CourseOffline apply(CourseOffline dataBeanReply) throws Exception {
                                                 return dataBeanReply;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<CommonCategory> getOfflineCategory(boolean iscache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getCommonCategory(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CommonCategory>, ObservableSource<CommonCategory>>() {
                             @Override
                             public ObservableSource<CommonCategory> apply(Observable<CommonCategory> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CategoryCache.class)
                                         .getCommonCategory(dataBeanObservable
                                                 , new DynamicKey("OfflineCategory")
                                                 , new EvictProvider(iscache))

                                         .map(new Function<CommonCategory, CommonCategory>() {

                                             @Override
                                             public CommonCategory apply(CommonCategory dataBeanReply) throws Exception {
                                                 System.out.print("线下课缓存");
                                                 return dataBeanReply;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOfflines> getMyOfflineCourses(int page, int count, boolean cache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));

        String outhToken = Utils.getAouthToken(mApplication);
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OfflineService.class)
                .getMyOfflineCourses(en_params, outhToken))
                .flatMap(new Function<Observable<CourseOfflines>, ObservableSource<CourseOfflines>>() {
                             @Override
                             public ObservableSource<CourseOfflines> apply(Observable<CourseOfflines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OfflineCache.class)
                                         .getMyOfflineCourses(dataBeanObservable
                                                 , new DynamicKey(page + PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(cache))

                                         .map(new Function<CourseOfflines, CourseOfflines>() {

                                             @Override
                                             public CourseOfflines apply(CourseOfflines dataBeanReply) throws Exception {
                                                 return dataBeanReply;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOfflines> getMyCollectOfflineCourses(int page, int count) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));

        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OfflineService.class)
                .getMyCollectOfflineCourses(en_params, outhToken))
                .flatMap(new Function<Observable<CourseOfflines>, ObservableSource<CourseOfflines>>() {
                             @Override
                             public ObservableSource<CourseOfflines> apply(Observable<CourseOfflines> dataBeanObservable) {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOfflines> getMyTeachOfflineCourses(int page, int count) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));

        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OfflineService.class)
                .getMyTeachOfflineCourses(en_params, outhToken))
                .flatMap(new Function<Observable<CourseOfflines>, ObservableSource<CourseOfflines>>() {
                             @Override
                             public ObservableSource<CourseOfflines> apply(Observable<CourseOfflines> dataBeanObservable) {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<OfflineSchoolResponse> getOfflineSchools(int page, int count, boolean cache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count));

        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OfflineService.class)
                .getOfflineShools(en_params, outhToken))
                .flatMap(new Function<Observable<OfflineSchoolResponse>, ObservableSource<OfflineSchoolResponse>>() {
                             @Override
                             public ObservableSource<OfflineSchoolResponse> apply(Observable<OfflineSchoolResponse> dataBeanObservable) {
                                 return mRepositoryManager.obtainCacheService(Cache.OfflineCache.class)
                                         .getOfflineSchools(dataBeanObservable
                                                 , new DynamicKey("" + page)
                                                 , new EvictProvider(cache))
                                         .map(new Function<OfflineSchoolResponse, OfflineSchoolResponse>() {

                                             @Override
                                             public OfflineSchoolResponse apply(OfflineSchoolResponse dataBeanReply) {
                                                 return dataBeanReply;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<DataBean> collectOffine(boolean is_collect, String source_id) {
        int type = 0;
        type = is_collect ? 0 : 1;
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "type", type, "sctype", 7, "source_id", source_id)
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
    public Observable<DataBean> sendMessage(String content, int to) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("to", to, "content", content));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(MessageService.class)
                .sendMsg(en_params, Utils.getAouthToken(mApplication));
    }
}