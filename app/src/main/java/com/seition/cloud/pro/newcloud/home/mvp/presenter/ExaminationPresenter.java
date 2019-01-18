package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerOptionsItem;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerSheet;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamConfig;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Options_type;
import com.seition.cloud.pro.newcloud.app.bean.examination.Pager;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.TimerUtils;
import com.seition.cloud.pro.newcloud.app.utils.YesOrNoDialog;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.ExamGapFillingInContentListener;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.AnswerSheetAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.ExamMyAnswerAdapter;
import com.umeng.commonsdk.debug.E;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ExaminationPresenter extends BasePresenter<ExamContract.ExamModel, ExamContract.ExaminationView>
        implements ExamContract.ExaminationPagerModel, ExamGapFillingInContentListener
        , BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, TextWatcher {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private MExamBean examBean;
    private int exams_type;
    boolean isOpenAnalysis = false;
    boolean isFrist = true;
    boolean isTest;
    private int itemPosition = 0;//用于填空题，须修改内容的下标
    private int questionTypeIndex = 0;//当前题所属类型下标
    private int questionIndex = 0;//当前类型下当前题下标
    private int questionIndexTemp = -1;
    TimerUtils timerUtils;
    private boolean isStartCommit = false;
    private long nowTimeCount;
    private long remainingTime;

    @Inject
    public ExaminationPresenter(ExamContract.ExamModel model, ExamContract.ExaminationView rootView) {
        super(model, rootView);
    }

    public void showTime(long nowTimeCount, long remainingTime) {
        this.nowTimeCount = nowTimeCount;
        this.remainingTime = remainingTime;
        if (mRootView == null) return;
        if (examBean.getReply_time() == 0)
            mRootView.showTime(TimeUtils.MyFormatTime11(nowTimeCount / 1000));
        else
            mRootView.showTime(TimeUtils.MyFormatTime11(remainingTime / 1000));
    }

    public void startTimer() {
        timerUtils = new TimerUtils(examBean.getReply_time() * 60 * 1000, 1000, examBean.getAnser_time() * 1000, new TimerUtils.TimerListener() {
            @Override
            public void TimerLastStart(long nowTimeCount, long remainingTime) {
                showTime(nowTimeCount, remainingTime);
//                pause_re.setVisibility(View.GONE);
            }

            @Override
            public void TimerStep(long nowTimeCount, long remainingTime) {
                showTime(nowTimeCount, remainingTime);
            }

            @Override
            public void TimerStop(long nowTimeCount, long remainingTime, boolean isCoerciveress) {
                //交卷
                showTime(nowTimeCount, remainingTime);
                if (mRootView == null) return;
                if (examBean.getReply_time() != 0 && nowTimeCount > remainingTime - 1)
                    mRootView.showMessage("考试时间结束,(正在自动交卷)");
//                    showYesDialog("考试时间结束", "(正在自动交卷)", null);
                if (((exams_type == 1 && examBean.getAssembly_type() == 0) || exams_type == 3) || isCoerciveress)
                    commitExamination(isCoerciveress);
                else {
                    isStartCommit = true;
                    mRootView.killMyself();
                }
            }

            @Override
            public void TimerPause(long nowTimeCount, long remainingTime) {
                //暂停
                showTime(nowTimeCount, remainingTime);
//                pause_re.setVisibility(View.VISIBLE);
            }
        });
        timerUtils.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void setExamBean(MExamBean examBean) {
        this.examBean = examBean;
        showNowPaper();
        if (isTest)
            startTimer();
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public void setExams_type(int exams_type) {
        this.exams_type = exams_type;
    }

    public String getMeAnswer(ArrayList<AnswerOptionsItem> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).isSelector())
                sb.append(list.get(i).getAnswer_key());
        if (sb.toString().trim().isEmpty()) return "未作答";
        return sb.toString();
    }

    private void checkIndex(boolean isNext) {
        ArrayList<Options_type> list = examBean.getPaper_options().getOptions_type();
        if (list == null || list.size() == 0) return;
        HashMap<String, ArrayList<Pager>> options_questions_data = examBean.getPaper_options().getOptions_questions_data();
        if (list == null) return;
        ArrayList<Pager> pagers = options_questions_data.get(list.get(questionTypeIndex).getQuestion_type());

        if (pagers == null || questionIndex < 0 || questionIndex > pagers.size() - 1) {
            if (isNext) {
                if (!isLastQuestion()) {
                    questionTypeIndex++;//该题型没有题了自动进入下个题型
                    questionIndex = 0;
                }
            } else {
                questionTypeIndex--;//该题型没有题了自动进入上个题型
                ArrayList<Pager> pagers2 = options_questions_data.get(list.get(questionTypeIndex).getQuestion_type());
                questionIndex = (pagers2 == null ? 0 : pagers2.size()) - 1;
            }
            checkIndex(isNext);
        }
    }

    public void showAnswerSheet() {
        ArrayList<MultiItemEntity> answerSheets = new ArrayList<>();
        ArrayList<Options_type> list = examBean.getPaper_options().getOptions_type();
        HashMap<String, ArrayList<Pager>> options_questions_data = examBean.getPaper_options().getOptions_questions_data();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Pager> pagers = options_questions_data.get(list.get(i).getQuestion_type());
            if (pagers == null || pagers.size() == 0) continue;//没有题的题型直接跳过
            else {
                for (int j = 0; j < pagers.size(); j++)
                    pagers.get(j).setNow(i == questionTypeIndex && j == questionIndex);
                answerSheets.add(new AnswerSheet(list.get(i).getType_info().getQuestion_type_title(), pagers));
            }
        }
        mRootView.showAnswerSheet(answerSheets, questionTypeIndex, questionIndex, isTest);
    }

    public void showNowPaper() {
        if (isFrist) if (initIndex()) return;
        checkIndex(true);
        showTopic(isTest, getNowPager());
        mRootView.showLastQuestion(isLastQuestion(), isTest);
        mRootView.showFirstQuestion(isFristQuestion(), isTest);
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                questionIndexTemp = questionIndex;
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    public Pager getNowPager() {
        return examBean
                .getPaper_options()
                .getOptions_questions_data()
                .get(examBean
                        .getPaper_options()
                        .getOptions_type()
                        .get(questionTypeIndex).getQuestion_type())
                .get(questionIndex);
    }

    public boolean isFristQuestion() {
        if (questionIndex != 0) return false;//题目下标不是0，就不是第一题
        ArrayList<Options_type> list = examBean.getPaper_options().getOptions_type();
        HashMap<String, ArrayList<Pager>> options_questions_data = examBean.getPaper_options().getOptions_questions_data();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Pager> pagers = options_questions_data.get(list.get(i).getQuestion_type());
            if (pagers == null || pagers.size() == 0)//类型下题目size = 0 跳过
                continue;
            if (questionTypeIndex == i) return true;
            else return false;
        }
        return true;
    }

    /**
     * 是否为最后一题
     *
     * @return
     */
    public boolean isLastQuestion() {
        ArrayList<Options_type> list = examBean.getPaper_options().getOptions_type();//题目类型列表
        HashMap<String, ArrayList<Pager>> options_questions_data = examBean.getPaper_options().getOptions_questions_data();//题目列表
        for (int i = list.size() - 1; i >= questionTypeIndex; i--) {
            ArrayList<Pager> pagers = options_questions_data.get(list.get(i).getQuestion_type());//对应题目类型下的题目列表
            int size = pagers == null ? 0 : pagers.size();//题目列表为空时数量为0，否则为题目数量
            if (size == 0)//为0跳过
                continue;
            if (questionTypeIndex == i)//是否为最后一个题目类型
                if (questionIndex > size - 2) {//选择下一题后下标超出重定
                    questionIndex = size - 1;
                    return true;
                } else if (questionIndex == size - 1) {
                    return true;
                } else return false;//最后一个类型但不是最后一题
            else return false;//不是最后一个类型
        }
        return true;
    }

    public boolean initIndex() {
        if (examBean.getQuestions_count() == 0
                || examBean.getPaper_options().getOptions_type() == null
                || examBean.getPaper_options().getOptions_type().size() == 0) {
            mRootView.showMessage(isTest ? "当前考试暂无试题！" : "当前试卷无错题！");
            mRootView.killMyself();
            return true;
        }
        isFrist = false;
        questionIndex = 0;
        questionTypeIndex = 0;
        return false;
    }

    public String getAnswer(ArrayList<String> list, boolean isSelect) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            if (isSelect)
                sb.append(list.get(i));
            else
                sb.append("  " + (i + 1) + "、 ").append(list.get(i));
        return sb.toString();
    }

    public void showTopic(boolean isTest, Pager pager) {
        mRootView.showTopic(isTest, pager, exams_type);
    }

    public int getNowQuestionIndex() {
        if (examBean == null) return 0;
        ArrayList<Options_type> list = examBean.getPaper_options().getOptions_type();//题目类型列表
        HashMap<String, ArrayList<Pager>> options_questions_data = examBean.getPaper_options().getOptions_questions_data();//题目列表
        int nowCount = 0;
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Pager> pagers = options_questions_data.get(list.get(i).getQuestion_type());
            if (pagers == null) continue;
            if (i < questionTypeIndex) nowCount += pagers.size();
            if (i == questionTypeIndex) nowCount += questionIndex;
        }
        return nowCount;
    }

    public int getAllQuestionCount() {
        int count = 0;
        if (examBean != null) {
            ArrayList<Options_type> list = examBean.getPaper_options().getOptions_type();//题目类型列表
            HashMap<String, ArrayList<Pager>> options_questions_data = examBean.getPaper_options().getOptions_questions_data();//题目列表
            int nowCount = 0;
            if (list == null || options_questions_data == null) return 0;
            for (int i = 0; i < list.size(); i++) {
                ArrayList<Pager> pagers = options_questions_data.get(list.get(i).getQuestion_type());
                if (pagers != null)
                    count += pagers.size();
            }
        }
//        return examBean.getQuestions_count();
        return count;
    }

    public void toExit() {
        //退出操作，须提示是否退出及其他操作
        mRootView.killMyself();
    }

    /**
     * @param isBack 是否为返回按钮调起提交
     */
    public void submitThePaper(boolean isBack) {
        //询问是否提交试卷，用于手动点击提交试卷
        if (isTest)
            mRootView.showYesOrNoDialog("退出考试", isBack ?
                    ((exams_type == 1 && examBean.getAssembly_type() == 0) || exams_type == 3) ?
                            "是否保存练习进度并退出？" : "是否退出考试？" : "是否退出考试并提交试卷？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (isBack) timerUtils.stop();//按返回键保存
                    else timerUtils.coercivenessStop();
                }
            }, null);
        else toExit();
    }

    public void pause() {
        if (timerUtils != null) timerUtils.pause();
    }

    public void start() {
        if (timerUtils != null) timerUtils.start();
    }

    /**
     * @param isCoerciveress true 提交   false 保存
     */
    public void commitExamination(boolean isCoerciveress) {
        //提交试卷数据
        mRootView.showLoading();
        ((exams_type == 1 || exams_type == 3) ?
                isCoerciveress ?
                        mModel.examCommit(examBean, exams_type, nowTimeCount / 1000)
                        : mModel.examSave(examBean, exams_type, nowTimeCount / 1000)
                : mModel.examCommit(examBean, exams_type, nowTimeCount / 1000))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null) mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        if (mRootView == null) return;
                        mRootView.showMessage(data.getMsg());
                        if (data.getCode() == 1) {
                            toExit();
                        }
                    }
                });
    }

    @Override
    public void lastTopic() {
        questionIndex--;
        if (questionIndex < 0)
            checkIndex(false);
        showNowPaper();
    }

    @Override
    public void nextTopic() {
        if (isLastQuestion()) {
            if (isTest)
                submitThePaper(false);
            else toExit();
            return;
        }
        questionIndex++;
        showNowPaper();
    }

    @Override
    public void openTheAnalysis() {
        mRootView.showAnswer(isOpenAnalysis = !isOpenAnalysis);
    }

    @Override
    public void collectTheTopic() {
        //收藏试题
        mRootView.showLoading();
        int typeIndex = questionTypeIndex;
        int qIndex = questionIndex;
        int status = getNowPager().getIs_collect() == 1 ? 0 : 1;
        mModel.collectExam(getNowPager().getExams_question_id() + "", status)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null) mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        if (examBean != null)
                            examBean.getPaper_options().getOptions_questions_data()
                                    .get(examBean.getPaper_options().getOptions_type().get(typeIndex).getQuestion_type())
                                    .get(qIndex).setIs_collect(status);
                        if (questionTypeIndex == typeIndex && qIndex == questionIndex)
                            showNowPaper();
                        if (mRootView == null) return;
                        mRootView.hideLoading();
                        mRootView.showMessage(status == 1 ? "收藏试题成功" : "取消收藏试题成功");
                    }
                });
    }

    @Override
    public void setContent(int position, String content) {
        //写入内容，填空
        ArrayList<Options_type> list = examBean.getPaper_options().getOptions_type();//题目类型列表
        examBean.getPaper_options().getOptions_questions_data().get(list.get(questionTypeIndex)
                .getQuestion_type()).get(questionIndex).getAnswer_options().get(position).setAnswer_value(content);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof AnswerSheetAdapter) {
            //答题卡点击事件
            if (((AnswerSheetAdapter) adapter).getItem(position) instanceof AnswerSheet)
                return;//选中题目类型暂不做操作
            List<MultiItemEntity> list = adapter.getData();
            int typePosition = -1;
            int questionPosition = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof AnswerSheet) {
                    typePosition++;
                    questionPosition = 0;
                    continue;
                }
                if (i == position) {
                    questionTypeIndex = typePosition;
                    questionIndex = questionPosition;
                    showNowPaper();
                    return;//选中并退出
                }
                questionPosition++;
            }
        } else if (adapter instanceof ExamMyAnswerAdapter) {
            //题目点击事件(选择题)
            if (!isTest) return;//查看答案模式直接跳出不修改任何数据
            //item点击事件判定
            Pager pager = getNowPager();
            switch (pager.getType_info().getQuestion_type_key()) {
                case ExamConfig.RADIO:
                case ExamConfig.JUDGE:
                    for (int i = 0; i < pager.getAnswer_options().size(); i++)
                        if (position == i && !pager.getAnswer_options().get(i).isSelector())
                            getNowPager().getAnswer_options().get(i).setSelector(true);
                        else
                            getNowPager().getAnswer_options().get(i).setSelector(false);
                    break;
                case ExamConfig.MULTISELECT://多选
                    getNowPager().getAnswer_options().get(position).setSelector();
                    break;
            }
            showTopic(isTest, getNowPager());
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.type_answer:
            case R.id.answer:
                itemPosition = position;
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * 输入框监听，在这里填入输入框的数据
     */
    @Override
    public void afterTextChanged(Editable editable) {
        if (isTest)
            if (questionIndexTemp == questionIndex)
                setContent(itemPosition, editable.toString());
    }
}
