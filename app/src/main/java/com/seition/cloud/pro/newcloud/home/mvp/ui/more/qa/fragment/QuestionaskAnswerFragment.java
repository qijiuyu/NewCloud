package com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerQuestionaskComponent;
import com.seition.cloud.pro.newcloud.home.di.module.QuestionaskModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.QuestionaskPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class QuestionaskAnswerFragment extends BaseBackFragment<QuestionaskPresenter> implements QuestionaskContract.View{

    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.qa_content)
    EditText qa_content;
    @BindView(R.id.qa_description)
    TextView qa_description;

    private String content = "";

    public static QuestionaskAnswerFragment newInstance(Questionask questionask) {
        Bundle args = new Bundle();
        args.putSerializable("QA",questionask);
        QuestionaskAnswerFragment fragment = new QuestionaskAnswerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick({ R.id.toolbar_right_text})
    void openDialog(View v) {
        switch (v.getId()) {

            case R.id.toolbar_right_text:
                content =   qa_content.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {
                    Utils.showToast(_mActivity, "请输入问题内容");
                    return;
                }
                mPresenter.answerQuestion(questionask.getId(), content);
                break;
        }
    }



    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerQuestionaskComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .questionaskModule(new QuestionaskModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer_question, container, false);
    }


    Questionask questionask;
    @Override
    public void initData(Bundle savedInstanceState) {
         questionask = (Questionask)getArguments().getSerializable("QA");
        setTitle("回答问题");
        qa_description.setText(questionask.getWd_description());
        toolbar_right_text.setBackgroundResource(R.drawable.ic_question_answer);
        qa_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()>0){
                    toolbar_right_text.setClickable(true);
                    toolbar_right_text.setBackgroundResource(R.drawable.ic_question_answer_ok);
                }else {
                    toolbar_right_text.setClickable(false);
                    toolbar_right_text.setBackgroundResource(R.drawable.ic_question_answer);
                }

            }
        });
    }


    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        pop();
    }


    @Override
    public void showStateViewState(int state) {

    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {

    }
}
