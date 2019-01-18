package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.bean.member.PaySwitch;
import com.seition.cloud.pro.newcloud.app.bean.member.VipUser;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.MemberService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class MemberModel extends BaseModel implements MemberContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MemberModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Member> getVipGrades(boolean iscache) {


        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MemberService.class)
                .getVipGrades(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Member>, ObservableSource<Member>>() {
                             @Override
                             public ObservableSource<Member> apply(Observable<Member> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MemberCache.class)
                                         .getMembers(dataBeanObservable
                                                 , new DynamicKey(PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(iscache))
                                         .map(new Function<Member, Member>() {
                                             @Override
                                             public Member apply(Member arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<VipUser> getNewMembers(int limit, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("limit", limit));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MemberService.class)
                .getNewMembers(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<VipUser>, ObservableSource<VipUser>>() {
                             @Override
                             public ObservableSource<VipUser> apply(Observable<VipUser> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MemberCache.class)
                                         .getNewMembers(dataBeanObservable
                                                 , limit
                                                 , new DynamicKey(PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(iscache))
                                         .map(new Function<VipUser, VipUser>() {
                                             @Override
                                             public VipUser apply(VipUser arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<CourseOnlines> getVipCourses(int vip_id, boolean iscache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(
                    MApplication.getCodedLock()
                    , M.getMapString(
                            "vip_id", vip_id
                    ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MemberService.class)
                .getVipCourses(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CourseOnlines>, ObservableSource<CourseOnlines>>() {
                             @Override
                             public ObservableSource<CourseOnlines> apply(Observable<CourseOnlines> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MemberCache.class)
                                         .getVipCourses(dataBeanObservable
                                                 , vip_id
                                                 , new DynamicKey("VipCourses" + vip_id + PreferenceUtil.getInstance(mApplication).getString("user_id", ""))
                                                 , new EvictProvider(iscache))
                                         .map(new Function<CourseOnlines, CourseOnlines>() {
                                             @Override
                                             public CourseOnlines apply(CourseOnlines arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
                             }
                         }
                );
    }

    @Override
    public Observable<PaySwitch> getPaySwitch() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("hextime", hex, "token", token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getPaySwitch(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<PayResponse> rechargeVip(String pay_for, int user_vip, String vip_type_time, int vip_time) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("pay_for", pay_for, "user_vip", user_vip, "vip_type_time", vip_type_time, "vip_time", vip_time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .rechargeVip(en_params, Utils.getAouthToken(mApplication));
    }
}