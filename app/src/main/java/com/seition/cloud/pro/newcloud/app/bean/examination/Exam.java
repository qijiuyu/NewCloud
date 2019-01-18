package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class Exam extends DataBean<ArrayList<Exam>> implements  MultiItemEntity {

    private String exams_users_id;
    private String uid;
    private int exams_paper_id;
//    private String content;
    private int status;
    private String score;
    private String examiner_uid;
//    private String examiner_data;
    private String create_time;
    private int anser_time;
    private String update_time;
    private int right_count;
    private int wrong_count;
    private int progress;
    private String exams_mode;
    private String completion_rate;
    private int pid;
    private int is_del;
    private MExamBean paper_info;
    private String right_rate;



    /*收藏考试数据*/
    private String collection_id;
    private String source_id;
    private String source_table_name;
    private String ctime;
    private Pager question_info;


    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getSource_table_name() {
        return source_table_name;
    }

    public void setSource_table_name(String source_table_name) {
        this.source_table_name = source_table_name;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Pager getQuestion_info() {
        return question_info;
    }

    public void setQuestion_info(Pager question_info) {
        this.question_info = question_info;
    }

    public MExamBean getPaper_info() {
        return paper_info;
    }

    public void setPaper_info(MExamBean paper_info) {
        this.paper_info = paper_info;
    }




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

    public int getExams_paper_id() {
        return exams_paper_id;
    }

    public void setExams_paper_id(int exams_paper_id) {
        this.exams_paper_id = exams_paper_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExaminer_uid() {
        return examiner_uid;
    }

    public void setExaminer_uid(String examiner_uid) {
        this.examiner_uid = examiner_uid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getAnser_time() {
        return anser_time;
    }

    public void setAnser_time(int anser_time) {
        this.anser_time = anser_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getRight_rate() {
        return right_rate;
    }

    public void setRight_rate(String right_rate) {
        this.right_rate = right_rate;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
