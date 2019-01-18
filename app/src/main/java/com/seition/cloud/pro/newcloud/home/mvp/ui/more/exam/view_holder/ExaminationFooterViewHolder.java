package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.view_holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by addis on 2018/3/22.
 */

public class ExaminationFooterViewHolder {

    private View footer;
    private Unbinder mUnbinder;
    ExamContract.ExaminationPagerModel epm;

    @BindView(R.id.open_the_answer)
    public TextView open_the_answer;

    @BindView(R.id.collect_the_exam)
    public TextView collect_the_exam;

    @BindView(R.id.analysis_ll)
    public LinearLayout analysis_ll;

    @BindView(R.id.answe_ll)
    public LinearLayout answe_ll;

    @BindView(R.id.answer_txt_lin)
    public LinearLayout answer_txt_lin;

    @BindView(R.id.answer_select_lin)
    public LinearLayout answer_select_lin;

    @BindView(R.id.answer_txt)
    public TextView answer_txt;

    @BindView(R.id.answer)
    public TextView answer;

    @BindView(R.id.select_answer_right)
    public LinearLayout select_answer_right;

    @BindView(R.id.me_answer)
    public TextView me_answer;

    @BindView(R.id.analysis)
    public TextView analysis;

    @BindView(R.id.next)
    public TextView next;
    @BindView(R.id.last)
    public TextView last;


    @OnClick({R.id.last, R.id.next, R.id.open_the_answer, R.id.collect_the_exam})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.last:
                epm.lastTopic();
                break;
            case R.id.next:
                epm.nextTopic();
                break;
            case R.id.open_the_answer:
                epm.openTheAnalysis();
                break;
            case R.id.collect_the_exam:
                epm.collectTheTopic();
                break;
        }
    }

    public ExaminationFooterViewHolder(Context context, ExamContract.ExaminationPagerModel epm) {
        this.footer = View.inflate(context, R.layout.view_footer_exam_footer, null);
        this.epm = epm;
        mUnbinder = ButterKnife.bind(this, footer);
    }

    public View getView() {
        return footer;
    }

    public void unBind() {
        mUnbinder.unbind();
    }
}
