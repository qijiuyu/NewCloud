/**
 * Copyright 2017 bejson.com
 */
package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Auto-generated: 2017-11-14 10:39:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Type_info extends MBaseBean {

    private int exams_question_type_id;
    private String question_type_title;
    private String question_type_key;

    public void setExams_question_type_id(int exams_question_type_id) {
        this.exams_question_type_id = exams_question_type_id;
    }

    public int getExams_question_type_id() {
        return exams_question_type_id;
    }

    public void setQuestion_type_title(String question_type_title) {
        this.question_type_title = question_type_title;
    }

    public String getQuestion_type_title() {
        return question_type_title;
    }

    public void setQuestion_type_key(String question_type_key) {
        this.question_type_key = question_type_key;
    }

    public String getQuestion_type_key() {
        return question_type_key;
    }

}