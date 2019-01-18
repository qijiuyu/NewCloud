package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Hornor;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionasks;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */
public interface QuestionaskService {
    //问答-获取列表
    String QALIST = "wenda.getList" ;
    //问答-我的提问列表
    String MyQuestionList = "wenda.getMyQuestionList" ;
    //问答-我的回答列表
    String MyAnswerList = "wenda.getMyAnswerList" ;

    //	问答-获取详情
    String QADETAILS = "wenda.getInfo" ;

    //	问答-发布问答
    String QAPUBLISH = "wenda.release" ;

    //	问答-搜索
    String SearchQa = "wenda.search" ;
    //	回答列表
    String AnswerList = "wenda.getCommentList" ;

    //	回答列表
    String AnswerQuestion = "wenda.doComment" ;

    //	一周热门
    String WeekHot = "wenda.weekHot" ;
    //	光荣榜
    String HornorList = "wenda.gloryList" ;

    @POST(QALIST)
    Observable<Questionasks> getQuestionList(@Header("en-params") String en_params);

    @POST(MyQuestionList)
    Observable<Questionasks> getMyQuestionList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(MyAnswerList)
    Observable<Questionasks> getMyAnswerList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    @POST(QADETAILS)
    Observable<Questionask> getQuestionDetails(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(AnswerList)
    Observable<Questionasks> getQuestionAnswerList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(QAPUBLISH)
    Observable<DataBean> questionPublish(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(SearchQa)
    Observable<Questionasks> searchQuestion(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
    @POST(AnswerQuestion)
    Observable<DataBean> answerQuestion(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(WeekHot)
    Observable<Questionasks> getWeekHotQuestions( @Header("oauth-token") String oauthToken);
    @POST(HornorList)
    Observable<Hornor> getHornor( @Header("oauth-token") String oauthToken);

    @POST( "&mod=Wenda&act=getWenda")
    Observable<Questionasks> getMyQuestion();

    @POST( "&mod=Wenda&act=getAnswer")
    Observable<Questionasks> getMyAnswer();


}
