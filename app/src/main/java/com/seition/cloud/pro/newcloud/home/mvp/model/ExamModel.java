package com.seition.cloud.pro.newcloud.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRMoudles;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARR_TestClassify;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerOptionsItem;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamConfig;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamRankUser;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Options_type;
import com.seition.cloud.pro.newcloud.app.bean.examination.Pager;
import com.seition.cloud.pro.newcloud.app.bean.examination.Paper_options;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.api.Cache;
import com.seition.cloud.pro.newcloud.home.api.service.BindService;
import com.seition.cloud.pro.newcloud.home.api.service.ConfigService;
import com.seition.cloud.pro.newcloud.home.api.service.ExamService;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;

/**
 * 考试相关model
 */
@ActivityScope
public class ExamModel extends BaseModel implements ExamContract.ExamModel {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ExamModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ARRMoudles> examMoudlesList() {
        return /*Observable.just(*/mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getMoudlesList(Utils.getAouthToken(mApplication))/*)
                .flatMap(new Function<Observable<ARRMoudles>, ObservableSource<ARRMoudles>>() {
                    @Override
                    public ObservableSource<ARRMoudles> apply(Observable<ARRMoudles> dataBeanObservable) {
                        return mRepositoryManager
                                .obtainCacheService(Cache.ExamCache.class)
                                .examMoudleList(dataBeanObservable
                                        , new DynamicKey(cacheName)
                                        , new EvictProvider(cache));
                    }
                })*/;
    }

    @Override
    public Observable<ARRExamBean> examList(int page, int count, String subject_id, int level, String module_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "page", page
                    , "count", count
                    , "level", level
                    , "subject_id", subject_id
                    , "module_id", module_id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getExamList(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<ARRExamBean>, ObservableSource<ARRExamBean>>() {
                    @Override
                    public ObservableSource<ARRExamBean> apply(Observable<ARRExamBean> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<Examination> getExamInfo(int paper_id, int exams_type, String exams_users_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "paper_id", paper_id
                    , "exams_type", exams_type
                    , "exams_users_id", exams_users_id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getExamInfo(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Examination>, ObservableSource<Examination>>() {
                    @Override
                    public ObservableSource<Examination> apply(Observable<Examination> dataBeanObservable) {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<Exam> getExamOwner(int log_type, int page, int count, boolean isache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "log_type", log_type
                    , "page", page
                    , "count", count
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getExamOwner(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Exam>, ObservableSource<Exam>>() {
                    @Override
                    public ObservableSource<Exam> apply(Observable<Exam> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<CollectExam> getCollectExam(int page, int count, boolean isache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "page", page
                    , "count", count
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getCollectExam(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<CollectExam>, ObservableSource<CollectExam>>() {
                    @Override
                    public ObservableSource<CollectExam> apply(Observable<CollectExam> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<ARR_TestClassify> examClassifyList() {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getClassifyList())
                .flatMap(new Function<Observable<ARR_TestClassify>, ObservableSource<ARR_TestClassify>>() {
                    @Override
                    public ObservableSource<ARR_TestClassify> apply(Observable<ARR_TestClassify> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<DataBean> examSave(MExamBean examBean, int examType, long time) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    inputExamData(examBean, time, examType)//试题数据
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .examSave(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<DataBean>, ObservableSource<DataBean>>() {
                    @Override
                    public ObservableSource<DataBean> apply(Observable<DataBean> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<DataBean> examCommit(MExamBean examBean, int examType, long time) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    inputExamData(examBean, time, examType)//试题数据
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .examCommit(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<DataBean>, ObservableSource<DataBean>>() {
                    @Override
                    public ObservableSource<DataBean> apply(Observable<DataBean> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    private Object[] inputExamData(MExamBean examBean, long time, int examType) {
        Paper_options option = examBean.getPaper_options();
        ArrayList<Object> list = new ArrayList<>();
        if (examType == 3 && examBean.getWrong_exams_users_id() > 0) {
            list.add("wrong_exams_users_id");
            list.add(examBean.getWrong_exams_users_id() + "");//错题答卷ID,用于错题再练
        } else if (examBean.getExams_users_id() > 0) {
            list.add("exams_users_id");
            list.add(examBean.getExams_users_id() + "");//上次答卷的保存记录ID,用于继续考试或练习
        }
        if (examType == 3) examType = 1;
        list.add("paper_options_id");
        list.add(examBean.getPaper_options().getExams_paper_options_id() + "");
        list.add("paper_id");
        list.add(examBean.getExams_paper_id() + "");
        list.add("anser_time");
        list.add(time + "");
        list.add("exams_type");
        list.add(examType + "");
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, ArrayList<Pager>> options_questions_data = option.getOptions_questions_data();
        ArrayList<Options_type> options_type = option.getOptions_type();
        for (int i = 0; i < options_type.size(); i++) {
            Options_type item = options_type.get(i);
            if (item != null) {
                ArrayList<Pager> questions = options_questions_data.get(item.getQuestion_type());
                if (questions != null)
                    for (int j = 0; j < questions.size(); j++) {
                        Pager pager = questions.get(j);
                        ArrayList<AnswerOptionsItem> aoiList = pager.getAnswer_options();
                        ArrayList<String> answer = new ArrayList<>();
                        for (int k = 0; k < aoiList.size(); k++) {
                            AnswerOptionsItem aoi = aoiList.get(k);
                            switch (item.getQuestion_type_key()) {
                                case ExamConfig.RADIO:
                                case ExamConfig.JUDGE:
                                    if (aoi.isSelector())
                                        hashMap.put(pager.getExams_question_id() + "", aoi.getAnswer_key());
                                    break;
                                case ExamConfig.MULTISELECT:
                                    if (aoi.isSelector()) {
                                        answer.add(aoi.getAnswer_key());
                                    }
                                    break;
                                case ExamConfig.COMPLETION:
                                    answer.add(aoi.getAnswer_value());
//                                    hashMap.put(pager.getExams_question_id() + "", aoi.getAnswer_value());
                                    break;
                                case ExamConfig.ESSAYS:
                                    if (aoi.getAnswer_value() != null && !aoi.getAnswer_value().trim().isEmpty()) {
                                        hashMap.put(pager.getExams_question_id() + "", aoi.getAnswer_value());
//                                        answer.add(aoi.getAnswer_value());
                                    }
                                    break;
                            }
                        }
                        if (answer.size() > 0)//多选和填空题在这里添加数据
                            hashMap.put(pager.getExams_question_id() + "", answer);
                    }
            }
        }
        list.add("user_answer");
        list.add(hashMap);
        return list.toArray();
    }

    @Override
    public Observable<DataBean> collectExam(String source_id, int action) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "source_id", source_id
                    , "action", action
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .collectExam(en_params, Utils.getAouthToken(mApplication));

    }

    @Override
    public Observable<DataBean> deleteExamRecord(String exams_users_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "exams_users_id", exams_users_id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .deleteExamRecord(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<Examination> examinationWrongExam(String exams_users_id, int paper_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "exams_users_id", exams_users_id
                    , "paper_id", paper_id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .examinationWrongExam(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<ExamRankUser> getExamRankUser(String exams_users_id, int page, int count, boolean isache) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "exams_users_id", exams_users_id
                    , "page", page
                    , "count", count
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getExamRankUser(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<ExamRankUser>, ObservableSource<ExamRankUser>>() {
                    @Override
                    public ObservableSource<ExamRankUser> apply(Observable<ExamRankUser> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<Examination> getWrongExamData(int paper_id, String exams_users_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "paper_id", paper_id
                    , "exams_users_id", exams_users_id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getWrongExamData(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Examination>, ObservableSource<Examination>>() {
                    @Override
                    public ObservableSource<Examination> apply(Observable<Examination> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<Examination> getResult(int paper_id, String exams_users_id) {
        String en_params = "";
        try {
            en_params = M.getEncryptData(MApplication.getCodedLock(), M.getMapString(
                    "paper_id", paper_id
                    , "exams_users_id", exams_users_id
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ExamService.class)
                .getResult(en_params, Utils.getAouthToken(mApplication)))
                .flatMap(new Function<Observable<Examination>, ObservableSource<Examination>>() {
                    @Override
                    public ObservableSource<Examination> apply(Observable<Examination> dataBeanObservable) throws Exception {
                        return dataBeanObservable;
                    }
                });
    }

    @Override
    public Observable<FaceSence> getFaceSence() {
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
                .getFaceSence(en_params, Utils.getAouthToken(mApplication));
    }

    @Override
    public Observable<FaceStatus> getFaceSaveStatus() {
        return (mRepositoryManager
                .obtainRetrofitService(BindService.class)
                .getFaceSaveStatus(Utils.getAouthToken(mApplication)));
    }
}