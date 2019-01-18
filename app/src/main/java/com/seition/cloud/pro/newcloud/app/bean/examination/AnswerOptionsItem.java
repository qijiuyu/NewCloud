package com.seition.cloud.pro.newcloud.app.bean.examination;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2017/11/14.
 */

public class AnswerOptionsItem extends MBaseBean {

    private String answer_key;
    private String answer_value;

    public AnswerOptionsItem() {
    }

    public AnswerOptionsItem(String answer_value) {
        setAnswer_value(answer_value);
    }

    private boolean isSelector = false;

    public void setSelector(boolean selector) {
        isSelector = selector;
    }

    public void setSelector() {
        isSelector = !isSelector;
    }

    public boolean isSelector() {
        return isSelector;
    }

    public String getAnswer_key() {
        return answer_key;
    }

    public void setAnswer_key(String answer_key) {
        this.answer_key = answer_key;
    }

    public String getAnswer_value() {
        return answer_value;
    }

    public void setAnswer_value(String answer_value) {
        this.answer_value = answer_value;
    }
}
