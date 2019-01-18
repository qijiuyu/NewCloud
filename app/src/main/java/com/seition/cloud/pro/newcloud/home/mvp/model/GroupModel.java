package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.bean.group.Category;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupData;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupMember;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupTheme;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.GroupService;
import com.seition.cloud.pro.newcloud.home.api.service.UploadService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import okhttp3.MultipartBody;


@ActivityScope
public class GroupModel extends BaseModel implements GroupContract.Model, GroupContract.fModel, GroupContract.GroupOperationModel, GroupContract.GroupTopicModel {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GroupModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Category> getGroupCategory(String cacheName, boolean cache) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存

        return mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupCategory();
      /*  return Observable.just(mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupCategory())
                .flatMap(new Function<Observable<Category>, ObservableSource<Category>>() {
                             @Override
                             public ObservableSource<Category> apply(Observable<Category> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.GroupCache.class)
                                         .getGroupCategory(dataBeanObservable
                                                 , new DynamicKey(cacheName)
                                                 , new EvictProvider(cache));
                             }
                         }
                );*/

        /*Observable<Category> observable = mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupCategory();

        if (cache) {
            return observable;
        } else {
            return Observable.just(observable)
                    .flatMap(new Function<Observable<Category>, ObservableSource<Category>>() {
                                 @Override
                                 public ObservableSource<Category> apply(Observable<Category> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.GroupCache.class)
                                             .getGroupCategory(dataBeanObservable
                                                     , new DynamicKey(cacheName)
                                                     , new EvictProvider(cache));
                                 }
                             }
                    );
        }*/

    }


    @Override
    public Observable<GroupData> getGroupListNew(int vPage, int page, int count, String cate_id, boolean cache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupList(page, count, cate_id))
                .flatMap(new Function<Observable<GroupData>, ObservableSource<GroupData>>() {
                             @Override
                             public ObservableSource<GroupData> apply(Observable<GroupData> dataBeanObservable) throws Exception {
                                 Observable<GroupData> observableSource =mRepositoryManager.obtainCacheService(Cache.GroupCache.class)
                                         .getGroupList(dataBeanObservable
                                                 , page
                                                 , count
                                                 , cate_id
                                                 , new DynamicKey(page + "")
                                                 , new EvictProvider(cache));

                                 return observableSource
                                         .map(new Function<GroupData, GroupData>() {
                                             @Override
                                             public GroupData apply(GroupData arrayListDataBean) throws Exception {
                                                 return arrayListDataBean;
                                             }
                                         });
//                                         .map(listReply -> listReply);
                             }
                         }
                );
    }


    @Override
    public Observable<DataBean> createGroup(String name, int group_logo, String cate_id, String type, String intro, String announce) {
        return mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .createGroup(name, group_logo, cate_id, type, intro, announce);
    }

    @Override
    public Observable<DataBean> editGroup(int group_id, String name, int group_logo, String cate_id, String intro, String announce) {
        return mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .editGroup(group_id, name, group_logo, cate_id, intro, announce);
    }

    @Override
    public Observable<DataBean> uploadFile(List<MultipartBody.Part> files) {
        return mRepositoryManager
                .obtainRetrofitService(UploadService.class)
                .uploadFiles(files);
    }

    @Override
    public Observable<DataBean<Group>> getGroupDetals(String group_id, boolean cache) {
        Observable<DataBean<Group>> observable = mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupDetails(group_id);
        if (cache) {
            return Observable.just(observable)
                    .flatMap(new Function<Observable<DataBean<Group>>, ObservableSource<DataBean<Group>>>() {
                                 @Override
                                 public ObservableSource<DataBean<Group>> apply(Observable<DataBean<Group>> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.GroupCache.class)
                                             .getGroupDetails(dataBeanObservable
                                                     , group_id
                                                     , new DynamicKey(group_id)
                                                     , new EvictProvider(cache))
                                             .map(listReply -> listReply);
                                 }
                             }
                    );
        } else
            return observable;

    }

    @Override
    public Observable<DataBean<ArrayList<GroupTheme>>> getTopicList(String group_id, int page, int count, String dist, String keysord, boolean cache) {
        Observable<DataBean<ArrayList<GroupTheme>>> observable = mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getTopicList(group_id, page, count, dist, keysord);
        if (cache) {
            return Observable.just(observable)
                    .flatMap(new Function<Observable<DataBean<ArrayList<GroupTheme>>>, ObservableSource<DataBean<ArrayList<GroupTheme>>>>() {
                                 @Override
                                 public ObservableSource<DataBean<ArrayList<GroupTheme>>> apply(Observable<DataBean<ArrayList<GroupTheme>>> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.GroupCache.class)
                                             .getTopicList(dataBeanObservable
                                                     , group_id
                                                     , page
                                                     , count
                                                     , dist
                                                     , keysord
                                                     , new DynamicKey(page)
                                                     , new EvictProvider(cache))
                                             .map(listReply -> listReply);
                                 }
                             }
                    );
        } else
            return observable;

    }

    @Override
    public Observable<DataBean> deleteGroup(String group_id) {
        return null;
    }

    @Override
    public Observable<DataBean> quitGroup(String group_id) {
        return null;
    }

    @Override
    public Observable<DataBean> joinGroup(String group_id) {
        return null;
    }

    @Override
    public Observable<DataBean> themeOperate(String tid, int action, String type) {
        return null;
    }

    @Override
    public Observable<DataBean> themeDelete(String tid, String group_id) {
        return null;
    }

    @Override
    public Observable<DataBean> themeReply(String tid, String group_id) {
        return null;
    }

    @Override
    public Observable<DataBean<ArrayList<GroupMember>>> getGroupMemberList(String group_id, int page, int count, String type, boolean cache) {
        Observable<DataBean<ArrayList<GroupMember>>> observable = mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupMemberList(group_id, page, count, type);
        if (cache) {
            return Observable.just(observable)
                    .flatMap(new Function<Observable<DataBean<ArrayList<GroupMember>>>, ObservableSource<DataBean<ArrayList<GroupMember>>>>() {
                                 @Override
                                 public ObservableSource<DataBean<ArrayList<GroupMember>>> apply(Observable<DataBean<ArrayList<GroupMember>>> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.GroupCache.class)
                                             .getGroupMemberList(dataBeanObservable
                                                     , group_id
                                                     , page
                                                     , count
                                                     , type
                                                 /*, new DynamicKey(page)
                                                 , new EvictProvider(cache)*/)
                                             .map(listReply -> listReply);
                                 }
                             }
                    );
        } else
            return observable;

    }

    @Override
    public Observable<DataBean<ArrayList<GroupMember>>> getApplyGroupMemberList(String group_id, int page, int count, String type, boolean cache) {
        Observable<DataBean<ArrayList<GroupMember>>> observable = mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupMemberList(group_id, page, count, type);

        if (cache) {
            return Observable.just(observable)
                    .flatMap(new Function<Observable<DataBean<ArrayList<GroupMember>>>, ObservableSource<DataBean<ArrayList<GroupMember>>>>() {
                                 @Override
                                 public ObservableSource<DataBean<ArrayList<GroupMember>>> apply(Observable<DataBean<ArrayList<GroupMember>>> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.GroupCache.class)
                                             .getApplyGroupMemberList(dataBeanObservable
                                                     , group_id
                                                     , page
                                                     , count
                                                     , type
                                                 /*, new DynamicKey(page)
                                                 , new EvictProvider(cache)*/)
                                             .map(listReply -> listReply);
                                 }
                             }
                    );
        } else
            return observable;

    }
}