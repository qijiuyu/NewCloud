package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

public class CollectExam extends DataBean<ArrayList<CollectExam>> {

    private String collection_id;
    private String uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }





}
