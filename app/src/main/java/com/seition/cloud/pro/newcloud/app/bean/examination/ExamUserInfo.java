package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.MBaseBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/8/16.
 * 用户答题结果数据
 */
public class ExamUserInfo extends MBaseBean {
    private String exams_users_id;
    private String uid;
    private String exams_paper_id;
    private String status;
    private String exams_mode;
    private String completion_rate;
    private String pid;
    private String is_del;
    private String right_rate;
    private int score;
    private int right_count;
    private int wrong_count;
    private int progress;
    private int examiner_uid;
    private int anser_time;
    private long create_time;
    private long update_time;

    private ArrayList<ExamUserAnswer> content;

    public String getExams_users_id() {
        return exams_users_id;
    }

    public void setExams_users_id(String exams_users_id) {
        this.exams_users_id = exams_users_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getExams_paper_id() {
        return exams_paper_id;
    }

    public void setExams_paper_id(String exams_paper_id) {
        this.exams_paper_id = exams_paper_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExams_mode() {
        return exams_mode;
    }

    public void setExams_mode(String exams_mode) {
        this.exams_mode = exams_mode;
    }

    public String getCompletion_rate() {
        return completion_rate;
    }

    public void setCompletion_rate(String completion_rate) {
        this.completion_rate = completion_rate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getRight_rate() {
        return right_rate;
    }

    public void setRight_rate(String right_rate) {
        this.right_rate = right_rate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRight_count() {
        return right_count;
    }

    public void setRight_count(int right_count) {
        this.right_count = right_count;
    }

    public int getWrong_count() {
        return wrong_count;
    }

    public void setWrong_count(int wrong_count) {
        this.wrong_count = wrong_count;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getExaminer_uid() {
        return examiner_uid;
    }

    public void setExaminer_uid(int examiner_uid) {
        this.examiner_uid = examiner_uid;
    }

    public int getAnser_time() {
        return anser_time;
    }

    public void setAnser_time(int anser_time) {
        this.anser_time = anser_time;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public ArrayList<ExamUserAnswer> getContent() {
        return content;
    }

    public void setContent(ArrayList<ExamUserAnswer> content) {
        this.content = content;
    }
}
