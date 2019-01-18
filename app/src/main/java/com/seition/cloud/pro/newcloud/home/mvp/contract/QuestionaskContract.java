package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Hornor;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategory;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategorys;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionasks;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface QuestionaskContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends MultiView {

    }

    interface DetailsView extends MultiView {
        void showDetails(Questionask questionask);
    }

    interface PublishView extends IView {
        void showCategory(ArrayList<QaCategory> qaCategories);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<CommentConfig> getInitReviewConfig();

        Observable<Questionasks> getQuestionList(int page, int count, int wdtype, int type, boolean iscache);

        Observable<Questionasks> getMyQuestionList(int page, int count);

        Observable<Questionasks> getMyAnswerList(int page, int count);

        Observable<Questionask> getQuestionDetails(int wid, boolean iscache);

        Observable<Questionasks> getQuestionAnswerList(int page, int count, int wid, boolean iscache);

        Observable<QaCategorys> getQuestionCategoryList(boolean iscache);

        Observable<DataBean> questionPublish(int typeid, String content);

        Observable<Questionasks> getMyQuestion(boolean iscache);

        Observable<Questionasks> getMyAnswer(boolean iscache);

        Observable<Questionasks> searchQuestion(String str);

        Observable<DataBean> answerQuestion(String wid, String content);

        Observable<Questionasks> getWeekHotQuestions();

        Observable<Hornor> getHornor();
    }
}
