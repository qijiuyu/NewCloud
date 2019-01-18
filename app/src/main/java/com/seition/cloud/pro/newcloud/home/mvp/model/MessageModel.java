package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageComment;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatter;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatterItem;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageSystem;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.MessageService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MessageContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class MessageModel extends BaseModel implements MessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Arr_MessageLatter> getMessagePrivateLetters() {
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(MessageService.class)
                .getMessagePrivateLetters(Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Arr_MessageLatter>, ObservableSource<Arr_MessageLatter>>() {
                             @Override
                             public ObservableSource<Arr_MessageLatter> apply(Observable<Arr_MessageLatter> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MessageCache.class)
                                         .getMessagePrivateLetters(dataBeanObservable
                                                 , new DynamicKey(MessageList+ PreferenceUtil.getInstance(mApplication).getString("user_id",""))
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );*/
    }

    @Override
    public Observable<Arr_MessageComment> getMessageComment() {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MessageService.class)
                .getMessageComment(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Arr_MessageComment>, ObservableSource<Arr_MessageComment>>() {
                             @Override
                             public ObservableSource<Arr_MessageComment> apply(Observable<Arr_MessageComment> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MessageCache.class)
                                         .getMessageComment(dataBeanObservable
                                                 , new DynamicKey(PreferenceUtil.getInstance(mApplication).getString("user_id",""))
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<Arr_MessageSystem> getMessageSystems() {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MessageService.class)
                .getMessageSystems(Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Arr_MessageSystem>, ObservableSource<Arr_MessageSystem>>() {
                             @Override
                             public ObservableSource<Arr_MessageSystem> apply(Observable<Arr_MessageSystem> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MessageCache.class)
                                         .getMessageSystems(dataBeanObservable
                                                 , new DynamicKey(PreferenceUtil.getInstance(mApplication).getString("user_id",""))
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<Arr_MessageLatterItem> getChatList(String id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "list_id", id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
       return  mRepositoryManager
                .obtainRetrofitService(MessageService.class)
                .getChatList(en_params, Utils.getAouthToken(mApplication));
               /* .flatMap(new Function<Observable<Arr_MessageLatterItem>, ObservableSource<Arr_MessageLatterItem>>() {
                             @Override
                             public ObservableSource<Arr_MessageLatterItem> apply(Observable<Arr_MessageLatterItem> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.MessageCache.class)
                                         .getChatList(dataBeanObservable, id
                                                 , new DynamicKey(MessageInfo)
                                                 , new EvictProvider(true))
                                         .map(listReply -> listReply);
                             }
                         }
                );*/
    }

    @Override
    public Observable<DataBean> replyMsg(String content, String id, String uid) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "id", id
                    , "reply_content", content
                    , "to", uid
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(MessageService.class)
                .replyMsg(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<DataBean>, ObservableSource<DataBean>>() {
                             @Override
                             public ObservableSource<DataBean> apply(Observable<DataBean> dataBeanObservable) {
                                 return dataBeanObservable;
                             }
                         }
                );
    }
}