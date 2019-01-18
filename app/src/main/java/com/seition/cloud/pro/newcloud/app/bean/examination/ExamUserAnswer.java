package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.MBaseBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/8/16.
 * 用户某题的回答信息
 */
public class ExamUserAnswer extends MBaseBean {
    private int exams_question_id;
    private int is_right;
    private ArrayList<AnswerOptionsItem> user_answer;

    public int getExams_question_id() {
        return exams_question_id;
    }

    public void setExams_question_id(int exams_question_id) {
        this.exams_question_id = exams_question_id;
    }

    public int getIs_right() {
        return is_right;
    }

    public void setIs_right(int is_right) {
        this.is_right = is_right;
    }

    public ArrayList<AnswerOptionsItem> getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(ArrayList<AnswerOptionsItem> user_answer) {
        this.user_answer = user_answer;
    }
}
