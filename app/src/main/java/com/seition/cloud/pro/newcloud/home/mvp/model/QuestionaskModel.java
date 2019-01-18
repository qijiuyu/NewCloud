package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Hornor;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategorys;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionasks;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.CategoryService;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.QuestionaskService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;


@ActivityScope
public class QuestionaskModel extends BaseModel implements QuestionaskContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public QuestionaskModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CommentConfig> getInitReviewConfig() {
        long time = System.currentTimeMillis() / 1000;
        String hex = M.timeToHexTime(time);
        String token = M.getToken(time, hex);
        return mRepositoryManager
                .obtainRetrofitService(ConfigService.class)
                .getInitReviewConfig(hex, token);
    }

    @Override
    public Observable<Questionasks> getQuestionList(int page, int count, int wdtype, int type, boolean iscache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page",page ,"count",count ,"wdtype",wdtype ,"type",type ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getQuestionList(en_params))
                .flatMap(new Function<Observable<Questionasks>, ObservableSource<Questionasks>>() {
                             @Override
                             public ObservableSource<Questionasks> apply(Observable<Questionasks> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.QuestionaskCache.class)
                                         .getQuestionAsks(dataBeanObservable
                                                 , new DynamicKey(type+PreferenceUtil.getInstance(mApplication).getString("user_id",""))
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );

    }

    @Override
    public Observable<Questionasks> getMyQuestionList(int page, int count) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page",page ,"count",count ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getMyQuestionList(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<Questionasks> getMyAnswerList(int page, int count) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page",page ,"count",count ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getMyAnswerList(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<Questionask> getQuestionDetails(int wid, boolean iscache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("wid",wid ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getQuestionDetails(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Questionask>, ObservableSource<Questionask>>() {
                             @Override
                             public ObservableSource<Questionask> apply(Observable<Questionask> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.QuestionaskCache.class)
                                         .getQuestionDetails(dataBeanObservable
                                                 , new DynamicKey(wid+"")
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<Questionasks> getQuestionAnswerList(int page, int count, int wid, boolean iscache) {
        String en_params = null;
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("page",page ,"count",count ,"wid",wid  ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Observable.just(mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getQuestionAnswerList(en_params,Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Questionasks>, ObservableSource<Questionasks>>() {
                             @Override
                             public ObservableSource<Questionasks> apply(Observable<Questionasks> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.QuestionaskCache.class)
                                         .getQuestionAnswerList(dataBeanObservable
                                                 , new DynamicKey(page+""+wid)
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<QaCategorys> getQuestionCategoryList(boolean iscache) {


        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CategoryService.class)
                .getQuestionCategory( Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<QaCategorys>, ObservableSource<QaCategorys>>() {
                             @Override
                             public ObservableSource<QaCategorys> apply(Observable<QaCategorys> dataBeanObservable) throws Exception {
                                 return mRepositoryManager.obtainCacheService(Cache.QuestionaskCache.class)
                                         .getQuestionCategoryList(dataBeanObservable
                                                 , new DynamicKey("QACategory")
                                                 , new EvictProvider(iscache))
                                         .map(listReply -> listReply);
                             }
                         }
                );
    }

    @Override
    public Observable<DataBean> questionPublish(int typeid, String content) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("typeid",typeid ,"content",content));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .questionPublish(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<Questionasks> getMyQuestion(boolean iscache) {
        Observable<Questionasks> observable  = mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getMyQuestion();
        if (!iscache){
            return Observable.just(observable)
                    .flatMap(new Function<Observable<Questionasks>, ObservableSource<Questionasks>>() {
                                 @Override
                                 public ObservableSource<Questionasks> apply(Observable<Questionasks> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.QuestionaskCache.class)
                                             .getMyQuestion(dataBeanObservable
                                                     , new DynamicKey("MyQuestions")
                                                     , new EvictProvider(iscache))
                                             .map(listReply -> listReply);
                                 }
                             }
                    );
        }else
            return observable ;
    }

    @Override
    public Observable<Questionasks> getMyAnswer(boolean iscache) {
        Observable<Questionasks> observable  = mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getMyAnswer();
        if (!iscache){
            return Observable.just(observable)
                    .flatMap(new Function<Observable<Questionasks>, ObservableSource<Questionasks>>() {
                                 @Override
                                 public ObservableSource<Questionasks> apply(Observable<Questionasks> dataBeanObservable) throws Exception {
                                     return mRepositoryManager.obtainCacheService(Cache.QuestionaskCache.class)
                                             .getMyAnswer(dataBeanObservable
                                                     , new DynamicKey("MyAnswes")
                                                     , new EvictProvider(iscache))
                                             .map(listReply -> listReply);
                                 }
                             }
                    );
        }else
            return observable ;
    }

    @Override
    public Observable<Questionasks> searchQuestion(String str) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("str",str ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .searchQuestion(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<DataBean> answerQuestion(String wid, String content) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString("wid",wid ,"content",content));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .answerQuestion(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<Questionasks> getWeekHotQuestions(  ) {

        return mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getWeekHotQuestions( Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<Hornor> getHornor() {
        return mRepositoryManager
                .obtainRetrofitService(QuestionaskService.class)
                .getHornor( Utils.getAouthToken(mApplication));
    }
}