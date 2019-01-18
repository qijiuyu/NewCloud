package com.seition.cloud.pro.newcloud.home.mvp.contract;

import android.content.DialogInterface;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRMoudles;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARR_TestClassify;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerOptionsItem;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamRankUser;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Moudles;
import com.seition.cloud.pro.newcloud.app.bean.examination.Pager;
import com.seition.cloud.pro.newcloud.app.bean.examination.TestClassify;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface ExamContract {
    //考试列表
    interface ExamListView extends IView {
        void setClassifyListData(ArrayList<TestClassify> classifyListData);

        void setExams(ArrayList<MExamBean> exams);

        void addExams(ArrayList<MExamBean> exams);

        void showExamInfo(MExamBean meb);

        void showFaceSaveStatus(FaceStatus status);
    }

    //进入考试列表前的类型选择
    interface ExamTypeMoudleView extends IView {
        void setData(ArrayList<Moudles> moudles);
    }

    //试题收藏
    interface ExamCollectView extends IView {

    }

    //考试
    interface ExaminationView extends IView {
        void showYesOrNoDialog(String title, String message, DialogInterface.OnClickListener left, DialogInterface.OnClickListener right);

        void showTime(String content);

        void setNowNumber(int numNumber);

        void answerShow(boolean isSelect, Pager pager, boolean isTest);

        void setData(String type, boolean isTest, ArrayList<AnswerOptionsItem> list);

        void showTopic(boolean isTest, Pager pager, int exams_type);

        void showAnswer(boolean isOpenAnalysis);

        void showFirstQuestion(boolean isFirst, boolean isTest);

        void showLastQuestion(boolean isLast, boolean isTest);

        void showAnswerSheet(ArrayList<MultiItemEntity> answerSheets, int typeIndex, int questionIndex, boolean isTest);
    }

    //我的考试
    interface ExamOwnerView extends MultiView {
        //        void setAdapter(ExamListRecyclerAdapter adapter);
        void delete(int type, int position);

        void emptyData(boolean isEmpty);
    }

    //考试结果
    interface ExamRestultView extends MultiView {
        void showExamUser(ExamRankUser.RankUser user);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface ExamModel extends IModel {
        Observable<ARRMoudles> examMoudlesList();//获取进入考试列表前的类型列表

        Observable<ARR_TestClassify> examClassifyList();//获取试卷分类列表

        Observable<DataBean> examCommit(MExamBean examBean, int examType, long time);//提交试卷信息

        Observable<DataBean> examSave(MExamBean examBean, int examType, long time);//保存练习进度

        Observable<ARRExamBean> examList(int page, int count, String subject_id, int level, String module_id);//获取试卷列表

        /**
         * 获取试题列表
         *
         * @param paper_id       试卷id
         * @param exams_type     考试类型 1练习模式 2 考试模式
         * @param exams_users_id 上次答卷的保存记录ID,用于继续考试或练习，可传空
         * @return 试卷数据
         */
        Observable<Examination> getExamInfo(int paper_id, int exams_type, String exams_users_id);

        Observable<Exam> getExamOwner(int log_type, int page, int count, boolean isache);

        Observable<CollectExam> getCollectExam(int page, int count, boolean isache);

        Observable<DataBean> collectExam(String source_id, int action);

        Observable<DataBean> deleteExamRecord(String exams_users_id);

        Observable<Examination> examinationWrongExam(String exams_users_id, int paper_id);

        Observable<ExamRankUser> getExamRankUser(String exams_users_id, int page, int count, boolean isache);

        Observable<Examination> getWrongExamData(int paper_id, String exams_users_id);

        Observable<Examination> getResult(int paper_id, String exams_users_id);

        Observable<FaceSence> getFaceSence();

        Observable<FaceStatus> getFaceSaveStatus();
    }

    interface ExaminationPagerModel extends IModel {
        void lastTopic();

        void nextTopic();

        void openTheAnalysis();

        void collectTheTopic();
    }
}
