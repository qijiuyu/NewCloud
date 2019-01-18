package com.seition.cloud.pro.newcloud.app.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.listener.ExamListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by addis on 2018/8/15.
 */
public class ExamInfoPopWindow extends BasePopWindow {
    private Context mContext;
    private PopupWindow popupWindow;
    private int Is_practice;
    private int animationStyle;
    private Unbinder mUnbinder;
    private ExamListener listener;
    private MExamBean meb;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.question_count)
    TextView question_count;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.exam_time)
    TextView exam_time;
    @BindView(R.id.test)
    TextView test;

    @OnClick({R.id.test, R.id.exam})
    void onClick(View view) {
        if (listener != null) listener.toExam(meb, view.getId());
        popupWindow.dismiss();
    }

    public ExamInfoPopWindow(Context context, int Is_practice, int animationStyle, ExamListener listener) {
        this.listener = listener;
        this.Is_practice = Is_practice;
        this.mContext = context;
        this.animationStyle = animationStyle;
        initPop();
    }

    public void setData(MExamBean meb) {
        this.meb = meb;
        title.setText(meb.getExams_paper_title());
        question_count.setText(meb.getQuestions_count() + "题");
        score.setText(meb.getScore() + "分");
        exam_time.setText(meb.getReply_time() == 0 ? "无限制" : meb.getReply_time() + "分钟");
        if (Is_practice == 0) test.setVisibility(View.GONE);
        else if (Is_practice == 1) test.setVisibility(View.VISIBLE);
    }

    private void initPop() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_exam_info, null);
        mUnbinder = ButterKnife.bind(this, view);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        if (animationStyle != 0) popupWindow.setAnimationStyle(animationStyle);
        view.findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void dissmiss(){
        popupWindow.dismiss();
    }

    public void show(Activity activity) {
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER_VERTICAL, 0, 0);
    }

    public void onDestory() {
        if (mUnbinder != null) mUnbinder.unbind();
    }
}
