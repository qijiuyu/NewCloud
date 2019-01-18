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
public class Options_type extends MBaseBean {

    private String question_type;
    private double score;
    private String question_type_key;
    private String desc;
    private Type_info type_info;

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setQuestion_type_key(String question_type_key) {
        this.question_type_key = question_type_key;
    }

    public String getQuestion_type_key() {
        return question_type_key;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setType_info(Type_info type_info) {
        this.type_info = type_info;
    }

    public Type_info getType_info() {
        return type_info;
    }

}