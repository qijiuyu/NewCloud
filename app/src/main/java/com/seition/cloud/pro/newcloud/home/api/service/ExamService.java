package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRMoudles;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARR_TestClassify;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamRankUser;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface ExamService {

    String getMoudlesList = "exams.getMoudles";//获取模块
    String getCategory = "exams.getCategory";//获取考试分类
    String getPaperList = "exams.getPaperList";//获取列表
    String getPaperInfo = "exams.getPaperInfo";//获取试卷信息
    String submitExams = "exams.submitExams";//提交
    String saveExams = "exams.saveExams";//保存练习进度
    String getCollectList = "exams.getCollectList";//获取收藏的试题
    String collect = "exams.collect";//收藏试题
    String deleteExamsLog = "exams.deleteExamsLog";//删除记录
    String rank = "exams.rank";//成绩排名
    String result = "exams.result";//获取结果
    String wrongData = "exams.wrongData";//错题解析
    String wrongExams = "exams.wrongExams";//错题重练

    String getExamsLog = "exams.getExamsLog";//获取考试记录

    @POST(getMoudlesList)
    Observable<ARRMoudles> getMoudlesList(@Header("oauth-token") String oauthToken);

    @POST(getCategory)
    Observable<ARR_TestClassify> getClassifyList();

    @POST(submitExams)
    Observable<DataBean> examCommit(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(saveExams)
    Observable<DataBean> examSave(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(getPaperInfo)
    Observable<Examination> getExamInfo(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(getPaperList)
    Observable<ARRExamBean> getExamList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    @POST(getExamsLog)
    Observable<Exam> getExamOwner(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);//@Query("log_type") int log_type, @Query("page") int page, @Query("count") int count

    @POST(getCollectList)
    Observable<CollectExam> getCollectExam(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);//@Query("page") int page, @Query("count") int count

    @POST(collect)
    Observable<DataBean> collectExam(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(deleteExamsLog)
    Observable<DataBean> deleteExamRecord(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(wrongExams)
    Observable<Examination> examinationWrongExam(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(rank)
    Observable<ExamRankUser> getExamRankUser(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(wrongData)
    Observable<Examination> getWrongExamData(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(result)
    Observable<Examination> getResult(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
}