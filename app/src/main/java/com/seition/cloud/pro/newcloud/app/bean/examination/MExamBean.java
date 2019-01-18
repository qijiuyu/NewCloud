package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.MBaseBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by addis on 2017/11/7.
 */

public class MExamBean extends MBaseBean {
    private int wrong_exams_users_id;
    private int exams_paper_id;
    private int exams_subject_id;
    private int exams_module_id;
    private int exams_count;
    private int level;
    private int is_del;
    private int sort;
    private int exams_limit;
    private int questions_count;
    private String exams_paper_title;
    private String description;
    private String paper_subject;
    private String paper_subject_fullpath;
    private String exams_module_title;
    private String level_title;
    private double score;
    private long anser_time;//已使用时间
    private long reply_time;
    private long create_time;
    private long update_time;
    private long start_time;
    private long end_time;
    private Paper_options paper_options;
    private int exams_users_id;
    private int assembly_type;
    private ExamUserInfo exams_user_info;
    private ArrayList<ExamUserAnswer> user_answer_temp;

    public MExamBean() {
    }

    public void setUserAnswerTemp() {
        if (paper_options != null) {
            //在这里初始化更改写入用户答案
            setUserAnswer(user_answer_temp);
        }
    }

    public void setUserAnswer() {
        if (paper_options != null && exams_user_info != null
                && exams_user_info.getContent() != null && exams_user_info.getContent().size() > 0) {
            //在这里初始化更改写入用户答案
            setUserAnswer(exams_user_info.getContent());
        }
    }

    public void setUserAnswer(ArrayList<ExamUserAnswer> examUserAnswers) {
        HashMap<String, ArrayList<Pager>> arraypagers = paper_options.getOptions_questions_data();
        ArrayList<Options_type> options_type = paper_options.getOptions_type();
        if (examUserAnswers != null)
            for (ExamUserAnswer examUserAnswer : examUserAnswers) {
                if (options_type != null)
                    for (int i = 0; i < options_type.size(); i++) {
                        Options_type item = options_type.get(i);
                        if (item != null) {
                            ArrayList<Pager> questions = arraypagers.get(item.getQuestion_type());
                            if (questions != null)
                                for (int z = 0; z < questions.size(); z++) {
                                    Pager pager = questions.get(z);
                                    if (pager.getExams_question_id() == examUserAnswer.getExams_question_id()) {
                                        //同一题
                                        pager.setRight(examUserAnswer.getIs_right() == 1);
                                        switch (pager.getType_info().getQuestion_type_key()) {
                                            case ExamConfig.RADIO:
                                            case ExamConfig.JUDGE:
                                            case ExamConfig.MULTISELECT:
                                                for (AnswerOptionsItem answerOptionsItem : examUserAnswer.getUser_answer()) {
                                                    ArrayList<AnswerOptionsItem> answerOptionsItems = pager.getAnswer_options();
                                                    if (answerOptionsItems != null)
                                                        for (int j = 0; j < answerOptionsItems.size(); j++) {
                                                            if (answerOptionsItems.get(j).getAnswer_key().equals(answerOptionsItem.getAnswer_value()))
                                                                arraypagers.get(item.getQuestion_type())
                                                                        .get(z).getAnswer_options().get(j).setSelector(true);
                                                        }
                                                }
                                                break;
                                            case ExamConfig.COMPLETION:
                                            case ExamConfig.ESSAYS:
                                                arraypagers.get(item.getQuestion_type())
                                                        .get(z).setAnswer_options(examUserAnswer.getUser_answer());
                                                break;
                                        }
                                    }
                                }
                        }
                    }
            }
    }

    public ArrayList<ExamUserAnswer> getUser_answer_temp() {
        return user_answer_temp;
    }

    public void setUser_answer_temp(ArrayList<ExamUserAnswer> user_answer_temp) {
        this.user_answer_temp = user_answer_temp;
    }

    public ExamUserInfo getExams_user_info() {
        return exams_user_info;
    }

    public void setExams_user_info(ExamUserInfo exams_user_info) {
        this.exams_user_info = exams_user_info;
    }

    public int getAssembly_type() {
        return assembly_type;
    }

    public void setAssembly_type(int assembly_type) {
        this.assembly_type = assembly_type;
    }

    public void setWrong_exams_users_id(int wrong_exams_users_id) {
        this.wrong_exams_users_id = wrong_exams_users_id;
    }

    public int getWrong_exams_users_id() {
        return wrong_exams_users_id;
    }

    public long getAnser_time() {
        return anser_time;
    }

    public void setAnser_time(long anser_time) {
        this.anser_time = anser_time;
    }

    public void setExams_users_id(int exams_users_id) {
        this.exams_users_id = exams_users_id;
    }

    public int getExams_users_id() {
        return exams_users_id;
    }

    public void setPaper_options(Paper_options paper_options) {
        this.paper_options = paper_options;
    }

    public Paper_options getPaper_options() {
        return paper_options;
    }

    public void setExams_module_id(int exams_module_id) {
        this.exams_module_id = exams_module_id;
    }

    public int getExams_module_id() {
        return exams_module_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setExams_count(int exams_count) {
        this.exams_count = exams_count;
    }

    public int getExams_count() {
        return exams_count;
    }

    public void setExams_limit(int exams_limit) {
        this.exams_limit = exams_limit;
    }

    public int getExams_limit() {
        return exams_limit;
    }

    public void setExams_module_title(String exams_module_title) {
        this.exams_module_title = exams_module_title;
    }

    public String getExams_module_title() {
        return exams_module_title;
    }

    public void setExams_paper_id(int exams_paper_id) {
        this.exams_paper_id = exams_paper_id;
    }

    public int getExams_paper_id() {
        return exams_paper_id;
    }

    public void setExams_paper_title(String exams_paper_title) {
        this.exams_paper_title = exams_paper_title;
    }

    public String getExams_paper_title() {
        return exams_paper_title;
    }

    public void setExams_subject_id(int exams_subject_id) {
        this.exams_subject_id = exams_subject_id;
    }

    public int getExams_subject_id() {
        return exams_subject_id;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel_title(String level_title) {
        this.level_title = level_title;
    }

    public String getLevel_title() {
        return level_title;
    }

    public void setPaper_subject(String paper_subject) {
        this.paper_subject = paper_subject;
    }

    public String getPaper_subject() {
        return paper_subject;
    }

    public void setPaper_subject_fullpath(String paper_subject_fullpath) {
        this.paper_subject_fullpath = paper_subject_fullpath;
    }

    public String getPaper_subject_fullpath() {
        return paper_subject_fullpath;
    }

    public void setQuestions_count(int questions_count) {
        this.questions_count = questions_count;
    }

    public int getQuestions_count() {
        return questions_count;
    }

    public void setReply_time(long reply_time) {
        this.reply_time = reply_time;
    }

    public long getReply_time() {
        return reply_time;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getSort() {
        return sort;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public long getUpdate_time() {
        return update_time;
    }
}
