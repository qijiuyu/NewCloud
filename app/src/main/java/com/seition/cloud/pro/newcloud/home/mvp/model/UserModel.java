package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.AreaInfo;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.VersionInfo;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComment;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComments;
import com.seition.cloud.pro.newcloud.app.bean.note.Notes;
import com.seition.cloud.pro.newcloud.app.bean.order.Orders;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecord;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.LoginService;
import com.seition.cloud.pro.newcloud.home.api.service.UserService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import okhttp3.ResponseBody;


@ActivityScope
public class UserModel extends BaseModel implements UserContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<StudyRecord> getStudyRecord(boolean iscache) {
        String outhToken = Utils.getAouthToken(mApplication);

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getStudyRecord(outhToken))
                .flatMap(new Function<Observable<StudyRecord>, ObservableSource<StudyRecord>>() {
                             @Override
                             public ObservableSource<StudyRecord> apply(Observable<StudyRecord> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getStudyRecord(dataBeanObservable
                                                 , new DynamicKey("StudyRecord"+PreferenceUtil.getInstance(mApplication).getString("user_id",""))
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<DataBean> deleteStudyRecord(String sid) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("sid", sid));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .deleteStudyRecord(en_params,Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<Orders> getExchangeRecord(String type,boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("type", type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getExchangeRecord(en_params,Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Orders>, ObservableSource<Orders>>() {
                             @Override
                             public ObservableSource<Orders> apply(Observable<Orders> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getExchangeRecord(dataBeanObservable
                                                 , new DynamicKey("ExchangeRecord"+PreferenceUtil.getInstance(mApplication).getString("user_id",""))
                                                 , new EvictProvider(isCache))
                                         .map(listReply -> listReply);
                             }
                         }
                );*/

    }

    @Override
    public Observable<ReceiveGoodsAddresss> getReceiveAddress(boolean iscache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .receiveAddresses( Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<ReceiveGoodsAddresss>, ObservableSource<ReceiveGoodsAddresss>>() {
                             @Override
                             public ObservableSource<ReceiveGoodsAddresss> apply(Observable<ReceiveGoodsAddresss> dataBeanObservable) throws Exception {


                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getReceiveAddress(dataBeanObservable, new DynamicKey(PreferenceUtil.getInstance(mApplication).getString("user_id","")), new EvictProvider(iscache))
                                         .map(new Function<ReceiveGoodsAddresss, ReceiveGoodsAddresss>() {
                                             @Override
                                             public ReceiveGoodsAddresss apply(ReceiveGoodsAddresss arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
//                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<ReceiveGoodsAddress> addReceiveAddress(String province,String city,String area,String location,String address,String name,String phone,String is_default) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("province",province,"city",city,"area",area,"location",location,"address",address,"name",name,"phone",phone,"is_default",is_default));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .addReceiveAddress(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<ReceiveGoodsAddress> changeReceiveAddress(String province,String city,String area,String location,String address,String name,String phone,String address_id,String is_default) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("province",province,"city",city,"area",area/*,"location",location*/,"address",address,"name",name,"phone",phone,"address_id", address_id,"is_default",is_default));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .changeReceiveAddress(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<ReceiveGoodsAddress> deleteReceiveAddress(String address_id ) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("address_id",address_id ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .deleteReceiveAddress( en_params,Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<ReceiveGoodsAddress> setDefaultReceiveAddress(String address_id ) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("address_id",address_id ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .setDefaultReceiveAddress( en_params,Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<AreaInfo> getArea(int area_id, boolean cache) {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("area_id",area_id,"hextime",hex, "token",token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s =Utils.getAouthToken(mApplication);
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getArea( en_params,Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<AreaInfo>, ObservableSource<AreaInfo>>() {
                             @Override
                             public ObservableSource<AreaInfo> apply(Observable<AreaInfo> dataBeanObservable) throws Exception {


                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getArea(dataBeanObservable, new DynamicKey(area_id+""), new EvictProvider(cache))
                                         .map(new Function<AreaInfo, AreaInfo>() {
                                             @Override
                                             public AreaInfo apply(AreaInfo arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
//                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<Notes> getMyNotes( int page,int count) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page",page,"count",count));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getMyNotes(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Notes>, ObservableSource<Notes>>() {
                             @Override
                             public ObservableSource<Notes> apply(Observable<Notes> dataBeanObservable) throws Exception {


                                 return mRepositoryManager.obtainCacheService(Cache.UserCache.class)
                                         .getMynotes(dataBeanObservable, new DynamicKey("MyNotes"+ PreferenceUtil.getInstance(mApplication).getString("user_id","")), new EvictProvider(cache))
                                         .map(new Function<Notes, Notes>() {
                                             @Override
                                             public Notes apply(Notes arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
//                                         .map(listReply -> listReply);
                             }
                         }
                );*/
    }

    @Override
    public Observable<NoteComments> getMyNoteComments(int pid, int ntype, boolean cache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("pid",pid,"ntype",ntype));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getMyNoteComments( en_params,Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<NoteComment> addNoteComments(int pid, String content, int kzid) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("is_open",1,"kztype",1,"pid",pid,"content",content,"kzid",kzid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .addNoteComments( en_params,Utils.getAouthToken(mApplication)));
    }

    @Override
    public Observable<VersionInfo> getVersionInfo() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("hextime",hex, "token",token));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getVersionInfo( en_params,Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> getPasswordBackVerifyCode(String  phone_number) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(),
                    M.getMapString("phone", phone_number));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .getPasswordBackVerifyCode(en_params);
    }

    @Override
    public Observable<DataBean> checkVerifyCode(String  phone,String code) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(),
                    M.getMapString("phone", phone,"code",code));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .checkPhoneCode(en_params);
    }

    @Override
    public Observable<DataBean> resetPassword(String phone, String code, String pwd, String repwd) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(),
                    M.getMapString("phone", phone,"code",code,"pwd",pwd,"repwd",repwd));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .resetPssword(en_params);
    }

    @Override
    public Observable<DataBean> getPasswordByEmail(String email) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(),
                    M.getMapString("email", email));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(LoginService.class)
                .getPasswordByEmail(en_params);
    }
}