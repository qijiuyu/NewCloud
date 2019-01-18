package com.seition.cloud.pro.newcloud.app.listener;

import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;

/**
 * Created by addis on 2018/8/15.
 */
public interface ExamListener {
    void toExam(MExamBean meb, int type);
}
