package com.seition.cloud.pro.newcloud.app.bean.examination;


import java.io.Serializable;
import java.util.ArrayList;

public class TestClassify implements Serializable {
    private String subject_id;// 考试分类ID

    private String title;// 考试分类名称

    private ArrayList<TestClassify> child;

    public void setChild(ArrayList<TestClassify> child) {
        this.child = child;
    }

    public ArrayList<TestClassify> getChild() {
        return child;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}
