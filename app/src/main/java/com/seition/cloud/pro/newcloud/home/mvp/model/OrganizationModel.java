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
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturers;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.order.OrganizationOrder;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.bean.organization.OrganizationStatus;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organizations;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CategoryService;
import com.seition.cloud.pro.newcloud.home.api.service.CourseService;
import com.seition.cloud.pro.newcloud.home.api.service.LecturerService;
import com.seition.cloud.pro.newcloud.home.api.service.OrganizationService;
import com.seition.cloud.pro.newcloud.home.api.service.UploadService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrganizationContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import okhttp3.MultipartBody;


@ActivityScope
public class OrganizationModel extends BaseModel implements OrganizationContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrganizationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Organizations> getOrganizationList(int page, int count, String keyword, String user_id, String cateId, String orderBy, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count, "keyword", keyword, "user_id", user_id, "cateId", cateId, "orderBy", orderBy));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(OrganizationService.class)
                .getOrganizationList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Organizations>, ObservableSource<Organizations>>() {
                             @Override
                             public ObservableSource<Organizations> apply(Observable<Organizations> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                         .getOrganizationList(dataBeanObservable
                                                 , new DynamicKey(page)
                                                 , new EvictProvider(cache))
                                         .map(new Function<Organizations, Organizations>() {
                                             @Override
                                             public Organizations apply(Organizations arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );

    }


    @Override
    public Observable<CommonCategory> getOrganizationCategory(boolean iscache) {


        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getOrganizationCategory(Utils.getAouthToken(mApplication));/*)
                    .flatMap(new Function<Observable<CommonCategory>, ObservableSource<CommonCategory>>() {
                                 @Override
                                 public ObservableSource<CommonCategory> apply(Observable<CommonCategory> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                             .getOrganizationCategory(dataBeanObservable
                                                     , new DynamicKey("OrganizationCategory")
                                                     , new EvictProvider(iscache))
                                             .map(listReply -> listReply);
                                 }
                             }
                    );*/


    }

    @Override
    public Observable<Organization> getOrganizationDetails(String school_id, boolean iscache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("school_id", school_id));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(OrganizationService.class)
                .getOrganizationDetails(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Organization>, ObservableSource<Organization>>() {
                             @Override
                             public ObservableSource<Organization> apply(Observable<Organization> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                         .getOrganizationDetails(dataBeanObservable
                                                 , new DynamicKey(school_id)
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );*/
    }

    @Override
    public Observable<FollowState> doOrganizationFollow(String user_id) {
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
    public Observable<FollowState> cancelOrganizationFollow(String user_id) {
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
    public Observable<CourseOnlines> getOrganizationCourses(int page, int count, int school_id, boolean cache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "page", page, "count",
                    count, "school_id", school_id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager.obtainRetrofitService(CourseService.class)
                .getCourses(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                         .getOrganizationCourses(dataBeanObservable
                                                 , new DynamicKey(page + "" + school_id)
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<Lecturers> getOrganizationTeacher(int page, int count, int school_id, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count, "school_id", school_id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager.obtainRetrofitService(LecturerService.class)
                .getLecturers(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Lecturers>, ObservableSource<Lecturers>>() {
                             @Override
                             public ObservableSource<Lecturers> apply(Observable<Lecturers> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                         .getOrganizationTeachers(dataBeanObservable
                                                 , new DynamicKey(page + "" + school_id)
                                                 , new EvictProvider(cache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<OrganizationStatus> getMyOrganizationStatus() {
        return mRepositoryManager.obtainRetrofitService(OrganizationService.class)
                .getMyOrganizationStatue(Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> uploadFiles(List<MultipartBody.Part> files) {
        return mRepositoryManager
                .obtainRetrofitService(UploadService.class)
                .uploadFiles(files);
    }

    @Override
    public Observable<UploadResponse> uploadFile(MultipartBody.Part file) {
        return mRepositoryManager
                .obtainRetrofitService(UploadService.class)
                .uploadFile(file);
    }

    @Override
    public Observable<Organization> applyOrganization(String title, String cate_id, String idcard, String phone, String province, String city, String area,
                                                      String attach_id, String reason, String location, String address, String identity_id) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "title", title
                            , "cate_id", cate_id
                            , "idcard", idcard
                            , "phone", phone
                            , "province", province
                            , "city", city
                            , "area", area
                            , "attach_id", attach_id
                            , "reason", reason
                            , "location", location
                            , "address", address
                            , "identity_id", identity_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(OrganizationService.class)
                .applyOrganization(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<OrganizationOrder> getOrganizationOrderList(int page, int count, String school_id, String pay_status, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock()
                    , M.getMapString(
                            "page", page
                            , "limit", count
                            , "school_id", school_id
                            , "pay_status", pay_status));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return /*Observable.just(*/mRepositoryManager.obtainRetrofitService(OrganizationService.class)
                .getOrganizationOrderList(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Orders>, ObservableSource<Orders>>() {
                             @Override
                             public ObservableSource<Orders> apply(Observable<Orders> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                         .getOrganizationOrderList(dataBeanObservable
                                                 , new DynamicKey(page+""+school_id)
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );*/
    }

    @Override
    public Observable<Lecturers> getOrganizationLectures(int page, int count, String school_id, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page", page, "count", count, "school_id", school_id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager.obtainRetrofitService(LecturerService.class)
                .getLecturers(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Lecturers>, ObservableSource<Lecturers>>() {
                             @Override
                             public ObservableSource<Lecturers> apply(Observable<Lecturers> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.OrganizationCache.class)
                                         .getOrganizationLecturers(dataBeanObservable
                                                 , new DynamicKey(page + "" + school_id)
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
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
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CourseService.class)
                .getShareUrl(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Share>, ObservableSource<Share>>() {
                    @Override
                    public ObservableSource<Share> apply(Observable<Share> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }
}