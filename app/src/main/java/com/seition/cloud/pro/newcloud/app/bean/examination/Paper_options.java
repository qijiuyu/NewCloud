/**
 * Copyright 2017 bejson.com
 */
package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.MBaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Auto-generated: 2017-11-14 10:39:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Paper_options extends MBaseBean {

    private int exams_paper_options_id;
    private int exams_paper_id;
    private ArrayList<Options_type> options_type;
    private int questions_count;
    private double score;
    private HashMap<String, List<String>> options_questions;
    private HashMap<String, ArrayList<Pager>> options_questions_data;

    public HashMap<String, ArrayList<Pager>> getOptions_questions_data() {
        return options_questions_data;
    }

    public void setOptions_questions_data(HashMap<String, ArrayList<Pager>> options_questions_data) {
        this.options_questions_data = options_questions_data;
    }

    public void setOptions_questions(HashMap<String, List<String>> options_questions) {
        this.options_questions = options_questions;
    }

    public HashMap<String, List<String>> getOptions_questions() {
        return options_questions;
    }

    public void setExams_paper_options_id(int exams_paper_options_id) {
        this.exams_paper_options_id = exams_paper_options_id;
    }

    public int getExams_paper_options_id() {
        return exams_paper_options_id;
    }

    public void setExams_paper_id(int exams_paper_id) {
        this.exams_paper_id = exams_paper_id;
    }

    public int getExams_paper_id() {
        return exams_paper_id;
    }

    public void setOptions_type(ArrayList<Options_type> options_type) {
        this.options_type = options_type;
    }

    public ArrayList<Options_type> getOptions_type() {
        return options_type;
    }

    public void setQuestions_count(int questions_count) {
        this.questions_count = questions_count;
    }

    public int getQuestions_count() {
        return questions_count;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}