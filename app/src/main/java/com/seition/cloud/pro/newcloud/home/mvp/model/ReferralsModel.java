package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;
import com.seition.cloud.pro.newcloud.app.bean.library.Arr_Library;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Hornor;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategorys;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionasks;
import com.seition.cloud.pro.newcloud.app.bean.referrals.Arr_Referrals;
import com.seition.cloud.pro.newcloud.app.bean.referrals.OwnerQRCode;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CategoryService;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.LibraryService;
import com.seition.cloud.pro.newcloud.home.api.service.QuestionaskService;
import com.seition.cloud.pro.newcloud.home.api.service.ReferralsService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ReferralsContract;

import javax.inject.Inject;

import freemarker.template.utility.StringUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;

/**
 * Created by addis on 2018/11/23.
 */
@ActivityScope
public class ReferralsModel extends BaseModel implements ReferralsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ReferralsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Arr_Referrals> getReferralsList(int page, int count, String uid) {
        String en_params = "";
        try {
            if (uid != null && !uid.isEmpty())
                en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                        "page", page
                        , "count", count
                        , "uid", uid
                ));
            else en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "page", page
                    , "count", count
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ReferralsService.class)
                .getReferralsList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Arr_Referrals>, ObservableSource<Arr_Referrals>>() {
                    @Override
                    public ObservableSource<Arr_Referrals> apply(Observable<Arr_Referrals> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<OwnerQRCode> getOwnerQRCode() {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ReferralsService.class)
                .getQRCode(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<OwnerQRCode>, ObservableSource<OwnerQRCode>>() {
                    @Override
                    public ObservableSource<OwnerQRCode> apply(Observable<OwnerQRCode> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }
}