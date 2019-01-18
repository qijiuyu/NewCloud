package com.seition.cloud.pro.newcloud.app.bean.library;

import com.jess.arms.base.bean.MBaseBean;

import java.io.Serializable;
import java.util.ArrayList;

public class LibraryCategoryBean extends MBaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String doc_category_id;
    private String title;
    private ArrayList<LibraryCategoryBean> childs;

    public ArrayList<LibraryCategoryBean> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<LibraryCategoryBean> childs) {
        this.childs = childs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoc_category_id() {
        return doc_category_id;
    }

    public void setDoc_category_id(String doc_category_id) {
        this.doc_category_id = doc_category_id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
