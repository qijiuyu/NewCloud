package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;

/**
 * Created by addis on 2018/3/21.
 */

public class ExamListAdapter extends BaseQuickAdapter<MExamBean, BaseViewHolder> {

    public ExamListAdapter() {
        super(R.layout.item_exam_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MExamBean item) {
        helper.setText(R.id.item_title, item.getExams_paper_title());
        helper.setText(R.id.question_number, item.getQuestions_count() + "");
        helper.setText(R.id.join_test_number, item.getExams_count() + "");
    }
}
