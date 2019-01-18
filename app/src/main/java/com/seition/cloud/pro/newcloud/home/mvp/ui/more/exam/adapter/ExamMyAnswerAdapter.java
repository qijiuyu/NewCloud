package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerOptionsItem;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamConfig;
import com.seition.cloud.pro.newcloud.app.utils.HtmlUtils;
import com.seition.cloud.pro.newcloud.app.utils.URLImageParser;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.ExamGapFillingInContentListener;

/**
 * Created by addis on 2018/3/21.
 */

public class ExamMyAnswerAdapter extends BaseQuickAdapter<AnswerOptionsItem, BaseViewHolder> {
    private String type;
    private TextWatcher textWatcher;
    private ExamGapFillingInContentListener listener;
    private boolean isTest = true;//true为考试模式，默认为考试模式,否则不可答题，是查看答案模式

    public ExamMyAnswerAdapter() {
        super(R.layout.item_exam_select);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public void setListener(ExamGapFillingInContentListener listener) {
        this.listener = listener;
    }

    public void setTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addTextChangedListener(EditText editText, String content) {
        if (editText == null || textWatcher == null) return;
        if (editText.getTag() instanceof TextWatcher) {
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
        editText.setText(content);
        editText.addTextChangedListener(textWatcher);
        editText.setTag(textWatcher);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按钮弹起逻辑
                        editText.requestFocus();
                        break;
                }
                return false;
            }
        });
        editText.setFocusableInTouchMode(true);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerOptionsItem item) {
        helper.addOnClickListener(R.id.type_answer);
        helper.addOnClickListener(R.id.answer);
        switch (type) {
            case ExamConfig.COMPLETION:
                helper.getView(R.id.type_answer).setVisibility(View.GONE);
                helper.getView(R.id.type_gap).setVisibility(View.VISIBLE);
                helper.getView(R.id.type_select).setVisibility(View.GONE);
//                helper.setText(R.id.count, item.getAnswer_key());
                helper.setText(R.id.count, helper.getAdapterPosition() + "");
                helper.setText(R.id.answer, item.getAnswer_value());
                EditText answer = helper.getView(R.id.answer);
                if (isTest) {
                    addTextChangedListener(answer, item.getAnswer_value());
                    answer.setEnabled(true);
                } else answer.setEnabled(false);
                break;
            case ExamConfig.ESSAYS://论述
                helper.getView(R.id.type_answer).setVisibility(View.VISIBLE);
                helper.getView(R.id.type_gap).setVisibility(View.GONE);
                helper.getView(R.id.type_select).setVisibility(View.GONE);
                helper.setText(R.id.type_answer, item.getAnswer_value());
                EditText type_answer = helper.getView(R.id.type_answer);
                if (isTest) {
                    addTextChangedListener(type_answer, item.getAnswer_value());
                    type_answer.setEnabled(true);
                } else type_answer.setEnabled(false);
                break;
            default://默认选择题
                helper.getView(R.id.type_answer).setVisibility(View.GONE);
                helper.getView(R.id.type_gap).setVisibility(View.GONE);
                helper.getView(R.id.type_select).setVisibility(View.VISIBLE);
                helper.setText(R.id.select, item.getAnswer_key());
                helper.setText(R.id.content,
                        Html.fromHtml(
                                HtmlUtils.cleanP(item.getAnswer_value())
                                , new URLImageParser(mContext, helper.getView(R.id.content)), null));
                showSelect(item, helper.getView(R.id.select));
        }
    }

    private void showSelect(AnswerOptionsItem bean, View view) {
        switch (type) {
            case ExamConfig.JUDGE://判断题样式可能不一样
                if (bean.isSelector())
                    view.setBackgroundResource(R.drawable.exam_select1_y);
                else
                    view.setBackgroundResource(R.drawable.exam_select1_n);
                break;
            default:
                if (bean.isSelector())
                    view.setBackgroundResource(R.drawable.exam_select1_y);
                else
                    view.setBackgroundResource(R.drawable.exam_select1_n);
        }
    }
}
