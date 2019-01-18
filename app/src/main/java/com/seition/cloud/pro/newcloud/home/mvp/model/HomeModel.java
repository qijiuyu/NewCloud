package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.ChangeFaceResponse;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.InitApp;
import com.seition.cloud.pro.newcloud.app.bean.MarketStatus;
import com.seition.cloud.pro.newcloud.app.bean.McryptKey;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSearch;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveBean;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOfflines;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organizations;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserCount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserMember;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CategoryService;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.CourseService;
import com.seition.cloud.pro.newcloud.home.api.service.HomeService;
import com.seition.cloud.pro.newcloud.home.api.service.LecturerService;
import com.seition.cloud.pro.newcloud.home.api.service.LiveService;
import com.seition.cloud.pro.newcloud.home.api.service.LoginService;
import com.seition.cloud.pro.newcloud.home.api.service.OfflineService;
import com.seition.cloud.pro.newcloud.home.api.service.OrganizationService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.HomeContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import okhttp3.MultipartBody;


@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.HomeModel {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;


    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<AdvertBean> getHomeBanner(String place, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("place", place));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(HomeService.class)
                .getBanners(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<AdvertBean>, ObservableSource<AdvertBean>>() {
                             @Override
                             public ObservableSource<AdvertBean> apply(Observable<AdvertBean> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.BannerCache.class)
                                         .getBanners(dataBeanObservable
                                                 , new DynamicKey("HomeBanner")
                                                 , new EvictProvider(iscache)
                                         )
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
    public Observable<CommonCategory> getHomeCategory(int count, boolean iscache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("count", count));

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getHomeCategory(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CommonCategory>, ObservableSource<CommonCategory>>() {
                             @Override
                             public ObservableSource<CommonCategory> apply(Observable<CommonCategory> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CategoryCache.class)
                                         .getHomeCategory(dataBeanObservable, count
                                                 , new DynamicKey("HomeCatgory")
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }



   /* @Override
    public Observable<CourseSearch> searchCourses(String keyword, String location, int page, int count, String cate_id, String order, boolean cache) throws Exception {
        String   en_params = M.getEncryptData(MApplication.getCodedLock(),M.getMapString("keyword",keyword,"location",location,"page",page,"count",count,"cate_id",cate_id,"order",order));
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .searchCourses( en_params))
                .flatMap(new Function<Observable<CourseSearch>, ObservableSource<CourseSearch>>() {
                             @Override
                             public ObservableSource<CourseSearch> apply(Observable<CourseSearch> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .searchCourses(dataBeanObservable
                                                 ,keyword,  location,  page,  count,  cate_id,  order
                                                 , new DynamicKey(order)
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }*/

    int count = 20;

    @Override
    public Observable<HomeLiveBean> getHomeLive(int count, boolean iscache) {

        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("count", count));

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LiveService.class)
                .getHomeLive(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<HomeLiveBean>, ObservableSource<HomeLiveBean>>() {
                             @Override
                             public ObservableSource<HomeLiveBean> apply(Observable<HomeLiveBean> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.LiveCache.class)
                                         .getHomeLive(dataBeanObservable
                                                 , count
                                                 , new DynamicKey("HomeLive")
                                                 , new EvictProvider(iscache)
                                         )
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<DataBean> modifierPassword(String oldpassword, String password, String repassword) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("oldpassword", oldpassword, "password", password, "repassword", repassword));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .modifierPassword(en_params, Utils.getAouthToken(mApplication));
    }


    @Override
    public Observable<CourseOfflines> getHomeOffline(int page, int count, String cateId, String orderBy, String school_id, String time, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("count", count));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OfflineService.class)
                .getHomeOfflines(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOfflines>, ObservableSource<CourseOfflines>>() {
                             @Override
                             public ObservableSource<CourseOfflines> apply(Observable<CourseOfflines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OfflineCache.class)
                                         .getOfflineCourses(dataBeanObservable
                                                 , new DynamicKey("HomeOffline")
                                                 , new EvictProvider(iscache)
                                         )
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<Lecturers> getHomeLectures(int count, boolean iscache) {


        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("count", count));
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(LecturerService.class)
                .getHomeLectures(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Lecturers>, ObservableSource<Lecturers>>() {
                    @Override
                    public ObservableSource<Lecturers> apply(Observable<Lecturers> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<Organizations> getHomeOrganization(int count, boolean iscache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("count", count));

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OrganizationService.class)
                .getHomeOrganization(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Organizations>, ObservableSource<Organizations>>() {
                             @Override
                             public ObservableSource<Organizations> apply(Observable<Organizations> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                         .getHomeOrganization(dataBeanObservable
                                                 , new DynamicKey("HomeOrganization")
                                                 , new EvictProvider(iscache)
                                         )
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }


    @Override
    public Observable<MessageUserInfo> getUserInfo(String user_id, boolean iscache) {
        String en_params = "";
        if ("".equals(user_id)) {
        } else {
            try {
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("user_id", user_id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUserInfo(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<UserAccount> getUserAccount(boolean iscache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUserAccount(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<UserAccount>, ObservableSource<UserAccount>>() {
                             @Override
                             public ObservableSource<UserAccount> apply(Observable<UserAccount> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getUserAccount(dataBeanObservable
                                                 , new DynamicKey("HomeUserAccount" + PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(iscache))
                                         .map(new Function<UserAccount, UserAccount>() {
                                             @Override
                                             public UserAccount apply(UserAccount arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<UserCount> getUserCount(boolean iscache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUserCount(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<UserCount>, ObservableSource<UserCount>>() {
                             @Override
                             public ObservableSource<UserCount> apply(Observable<UserCount> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getUserCount(dataBeanObservable
                                                 , new DynamicKey("HomeUserCount" + PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(iscache))
                                         .map(new Function<UserCount, UserCount>() {
                                             @Override
                                             public UserCount apply(UserCount arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<UserMember> getUserVip(String time, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("time", time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUserVip(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<UserMember>, ObservableSource<UserMember>>() {
                             @Override
                             public ObservableSource<UserMember> apply(Observable<UserMember> dataBeanObservable) {
                                 return dataBeanObservable;
                             }
                         }
                );
    }

    @Override
    public Observable<ChangeFaceResponse> setUserFace(MultipartBody.Part file) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .setUserFace(Utils.getAouthToken(mApplication), file);
    }

    @Override
    public Observable<DataBean> setUserInfo(String uname, int sex, String intro) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "uname", uname, "sex", sex, "intro", intro));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .setUserInfo(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<CourseSearch> searchCourses(String keyword, String location
            , int type, int page, int count, String cate_id, String order
            , String vip_id, boolean cache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("keyword", keyword, "location", location, "type", type, "page", page, "count", count, "cate_id", cate_id, "order", order));
        String token = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .searchCourses(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseSearch>, ObservableSource<CourseSearch>>() {
                             @Override
                             public ObservableSource<CourseSearch> apply(Observable<CourseSearch> dataBeanObservable) throws Exception {

                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .searchCourses(dataBeanObservable
                                                 , new DynamicKey(page + "")
                                                 , new EvictProvider(cache))
                                         .map(new Function<CourseSearch, CourseSearch>() {
                                             @Override
                                             public CourseSearch apply(CourseSearch arrayListDataBean) throws Exception {
                                                 System.out.println("<<<<<<<<<<<< -------");
                                                 return arrayListDataBean;
                                             }
                                         });
//                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOnlines> getHomePerfectCourses(int count, boolean iscache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("count", count));
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getHomePerfectCourses(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .getHomePerfectCourses(dataBeanObservable
                                                 , count
                                                 , new DynamicKey("HomePerCourses")
                                                 , new EvictProvider(iscache)
                                         )
                                         .map(listReply -> listReply);
                             }
                         }
                );*/

    }

    @Override
    public Observable<CourseOnlines> getHomeNewCourses(int count, boolean iscache) {
        String en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("count", count));
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getHomeNewCourses(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.CourseCache.class)
                                         .getHomeNewCourses(dataBeanObservable
                                                 , count
                                                 , new DynamicKey("HomeNewCourses")
                                                 , new EvictProvider(iscache)
                                         )
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<InitApp> getMcryptKey() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getMcryptKey(hex, token);
    }

    @Override
    public Observable<MarketStatus> getMarketStatus() {
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
                .getMarketStatus(en_params);
    }
}