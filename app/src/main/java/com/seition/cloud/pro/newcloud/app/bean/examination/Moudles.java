package com.seition.cloud.pro.newcloud.app.bean.examination;


import com.jess.arms.base.bean.MBaseBean;

/**
 * 考试列表信息
 *
 * @author HJ
 *         2017.3.30 修改 addis
 */
public class Moudles extends MBaseBean {
    private String exams_module_id;
    private String title;
    private String icon;
    private String description;
    private String btn_text;
    private int is_practice;//是否开启练习模式	1 是 0 :否 ,为0时,只有考试模式

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setIs_practice(int is_practice) {
        this.is_practice = is_practice;
    }

    public int getIs_practice() {
        return is_practice;
    }

    public void setBtn_text(String btn_text) {
        this.btn_text = btn_text;
    }

    public String getBtn_text() {
        return btn_text;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setExams_module_id(String exams_module_id) {
        this.exams_module_id = exams_module_id;
    }

    public String getExams_module_id() {
        return exams_module_id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}