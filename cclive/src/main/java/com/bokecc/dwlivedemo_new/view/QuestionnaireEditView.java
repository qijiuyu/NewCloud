package com.bokecc.dwlivedemo_new.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bokecc.dwlivedemo_new.R;

/**
 * 问卷问答题编辑控件
 * Created by renhui on 2017/8/15.
 */
public class QuestionnaireEditView extends LinearLayout {

    private Context mContext;
    private EditText mSubjectEdit;

    public QuestionnaireEditView(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    public QuestionnaireEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.questionnaire_edit_layout, this, true);
        mSubjectEdit = (EditText) findViewById(R.id.subject_edit);
    }

    /**
     * 问答题是否做答了
     */
    public boolean isAnswered() {
        return !TextUtils.isEmpty(getAnswer().trim());
    }

    /**
     * 获取问答内容
     */
    public String getAnswer() {
        return mSubjectEdit.getText().toString();
    }

    /**
     * 清除焦点
     */
    public void removeFocus() {
        mSubjectEdit.clearFocus();
    }
}
