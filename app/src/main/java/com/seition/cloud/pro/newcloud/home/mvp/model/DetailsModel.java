package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Lecturer;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.service.LecturerService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.DetailsContract;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class DetailsModel extends BaseModel implements DetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Lecturer> getTeacher(int teacherId, boolean isCache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("teacher_id", teacherId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return/* Observable.just(*/mRepositoryManager
                .obtainRetrofitService(LecturerService.class)
                .getLectureDetails(en_params, Utils.getAouthToken(mApplication));/*)
                .flatMap(new Function<Observable<Lecturer>, ObservableSource<Lecturer>>() {
                    @Override
                    public ObservableSource<Lecturer> apply(Observable<Lecturer> dataBeanObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(Cache.LecturerCache.class)
                                .getLectureDetails(dataBeanObservable
                                        , new DynamicKey(MessageConfig.TEACHER_GETINFO + "id=" + teacherId)
                                        , new EvictProvider(isCache))
                                .map(listReply -> listReply);
                    }
                });*/
    }
}