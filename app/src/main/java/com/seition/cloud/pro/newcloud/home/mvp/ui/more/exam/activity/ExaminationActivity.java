package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerOptionsItem;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerSheet;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamConfig;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Pager;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.HtmlUtils;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.URLImageParser;
import com.seition.cloud.pro.newcloud.app.utils.YesOrNoDialog;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerExamComponent;
import com.seition.cloud.pro.newcloud.home.di.module.ExamModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.ExaminationPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.AnswerSheetAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.ExamMyAnswerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.view_holder.ExaminationFooterViewHolder;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.view_holder.ExaminationHeaderViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * by Addis
 * 考试或查看解析界面
 */
public class ExaminationActivity extends BaseActivity<ExaminationPresenter> implements ExamContract.ExaminationView {

    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.pause)
    LinearLayout pause;
    ExaminationHeaderViewHolder header;
    ExaminationFooterViewHolder footer;
    @Inject
    ExamMyAnswerAdapter adapter;
    @Inject
    AnswerSheetAdapter answerSheetAdapter;
    private boolean isShowAnswerSheet = false;

    @OnClick({R.id.toolbar_right_text, R.id.toolbar_back, R.id.start, R.id.exit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_text:
                if (isShowAnswerSheet)
                    mPresenter.submitThePaper(false);
                else
                    mPresenter.showAnswerSheet();
                break;
            case R.id.exit:
            case R.id.toolbar_back:
                mPresenter.submitThePaper(true);
                break;
            case R.id.start:
                pause.setVisibility(View.GONE);
                mPresenter.start();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isTest) {
            getMenuInflater().inflate(R.menu.exam_start_menu, menu);
            menu.getItem(0).setOnMenuItemClickListener(clickListener);
        }
        return true;
    }

    MenuItem.OnMenuItemClickListener clickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.pause://因为使用android.support.v7.widget.SearchView类，可以在onCreateOptionsMenu(Menu menu)中直接设置监听事件
                    mPresenter.pause();
                    pause.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        }
    };

    @Override
    public void onBackPressedSupport() {
//        super.onBackPressedSupport();//拦截返回事件
        if (!isShowAnswerSheet)
            mPresenter.submitThePaper(true);
        else {
            mPresenter.showNowPaper();
        }
    }

    YesOrNoDialog.Builder yesOrNoDialog;

    public void showYesOrNoDialog(String title, String message, DialogInterface.OnClickListener left, DialogInterface.OnClickListener right) {
        if (yesOrNoDialog == null)
            yesOrNoDialog = new YesOrNoDialog.Builder(this);
        yesOrNoDialog.setTitle(title);
        yesOrNoDialog.setMessage(message);
        yesOrNoDialog.setPositiveButton("确定", left != null ? left : new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                timerUtils.coercivenessStop();
            }
        });
        yesOrNoDialog.setNegativeButton("取消", right != null ? right : new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        yesOrNoDialog.create().show();
    }


    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerExamComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .examModule(new ExamModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_examination; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private void initHeader() {
        header = new ExaminationHeaderViewHolder(this);
        footer = new ExaminationFooterViewHolder(this, mPresenter);
    }

    boolean isTest = false;

    @Override
    public void initData(Bundle savedInstanceState) {
        initHeader();
        initView();
        setTitle(R.string.exam);
        Intent intent = getIntent();
        mPresenter.setExams_type(intent.getIntExtra(MessageConfig.START_EXAMINATION_TYPE, 1));//考试类型 1练习模式 2 考试模式 3 错题重练 默认练习模式
        mPresenter.setTest(isTest = intent.getBooleanExtra(MessageConfig.START_EXAMINATION_IS_TEST, true));//是否为考试模式，默认为考试模式
        mPresenter.setExamBean((MExamBean) intent.getSerializableExtra(MessageConfig.START_EXAMINATION));
        header.all_number.setText("/" + mPresenter.getAllQuestionCount());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        recycle_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);
        answerSheetAdapter.setOnItemClickListener(mPresenter);
        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setTextChangedListener(mPresenter);
        adapter.addHeaderView(header.getView());
        adapter.addFooterView(footer.getView());
    }

    @Override
    public SpringView.DragHander getRefreshHeaderView() {
        return null;
    }

    @Override
    public SpringView.DragHander getLoadMoreFooterView() {
        return null;
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
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        header.unBind();
        footer.unBind();
    }

    public void answerShow(boolean isSelect, Pager pager, boolean isTest) {
        footer.answe_ll.setVisibility(View.VISIBLE);
        if (isSelect) {
            footer.answer_txt_lin.setVisibility(View.GONE);
            footer.answer_select_lin.setVisibility(View.VISIBLE);
            if (isTest) {
                footer.select_answer_right.setVisibility(View.GONE);
            } else {
                footer.select_answer_right.setVisibility(View.VISIBLE);
                footer.me_answer.setText(mPresenter.getMeAnswer(pager.getAnswer_options()));
            }
            footer.answer.setText(mPresenter.getAnswer(pager.getAnswer_true_option(), isSelect));
        } else {
            footer.answer_txt_lin.setVisibility(View.VISIBLE);
            footer.answer_select_lin.setVisibility(View.GONE);
            footer.answer_txt.setText(mPresenter.getAnswer(pager.getAnswer_true_option(), false));
        }
    }

    @Override
    public void setData(String type, boolean isTest, ArrayList<AnswerOptionsItem> list) {
        adapter.setType(type);
        adapter.setTest(isTest);
        if (adapter.getData() == null) adapter.setNewData(list);
        else
            adapter.replaceData(list);
        recycle_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// 布局管理器
        if (isShowAnswerSheet) {
            recycle_view.setAdapter(adapter);
            isShowAnswerSheet = false;
        }
    }

    @Override
    public void showAnswerSheet(ArrayList<MultiItemEntity> answerSheets, int typeIndex, int questionIndex, boolean isTest) {
        isShowAnswerSheet = true;
        recycle_view.setAdapter(answerSheetAdapter);
        answerSheetAdapter.setNewData(answerSheets);
        answerSheetAdapter.expandAll();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);//默认五条每行
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (answerSheets.get(position) instanceof AnswerSheet)
                    return gridLayoutManager.getSpanCount();//独占一行
                else
                    return 1;

            }
        });
        recycle_view.setLayoutManager(gridLayoutManager);// 布局管理器
        if (isTest)
            toolbar_right_text.setText("交卷");
        else
            toolbar_right_text.setText("");
    }

    @Override
    public void showTopic(boolean isTest, Pager pager, int exams_type) {
        toolbar_right_text.setText("答题卡");
        switch (pager.getType_info().getQuestion_type_key()) {
            case ExamConfig.RADIO:
            case ExamConfig.MULTISELECT:
            case ExamConfig.JUDGE:
                answerShow(true, pager, isTest && exams_type == 2);
                break;
            case ExamConfig.COMPLETION:
                answerShow(false, pager, isTest && exams_type == 2);
                if (pager.getAnswer_options() == null || pager.getAnswer_options().size() == 0) {//没有记录则根据答案数量新增默认记录用于存放答案
                    if (pager.getAnswer_true_option() != null) {
                        ArrayList<AnswerOptionsItem> list = new ArrayList<>();
                        for (int i = 0; i < pager.getAnswer_true_option().size(); i++) {
                            AnswerOptionsItem answerOptionsItem = new AnswerOptionsItem();
                            answerOptionsItem.setAnswer_key(i + 1 + "");
                            answerOptionsItem.setAnswer_value("");
                            list.add(answerOptionsItem);
                        }
                        pager.setAnswer_options(list);
                    }
                }
                break;
            case ExamConfig.ESSAYS:
                footer.answe_ll.setVisibility(View.GONE);
                if (pager.getAnswer_options() == null || pager.getAnswer_options().size() == 0) {//没有记录则新增一条默认记录用于存放答案
                    ArrayList<AnswerOptionsItem> list = new ArrayList<>();
                    AnswerOptionsItem answerOptionsItem = new AnswerOptionsItem();
                    answerOptionsItem.setAnswer_key("");
                    answerOptionsItem.setAnswer_value("");
                    list.add(answerOptionsItem);
                    pager.setAnswer_options(list);
                }
                break;
        }
        setData(pager.getType_info().getQuestion_type_key(), isTest, pager.getAnswer_options());
        if (!isTest || (exams_type == 1 || exams_type == 3))
            footer.open_the_answer.setVisibility(View.VISIBLE);
        else
            footer.open_the_answer.setVisibility(View.GONE);
        showCollect(pager.getIs_collect() == 1);
        header.exam_type.setText(pager.getType_info().getQuestion_type_title());
        header.topic.setText(Html.fromHtml(HtmlUtils.cleanP(pager.getContent().trim().intern())
                , new URLImageParser(getApplication(), header.topic), null));
        setNowNumber(mPresenter.getNowQuestionIndex() + 1);
        footer.analysis.setText(Html.fromHtml(HtmlUtils.cleanP(pager.getAnalyze().trim())
                , new URLImageParser(getApplication(), footer.analysis), null));
    }

    @Override
    public void showAnswer(boolean isOpenAnalysis) {
        if (isOpenAnalysis)
            footer.analysis_ll.setVisibility(View.VISIBLE);
        else
            footer.analysis_ll.setVisibility(View.GONE);
        footer.open_the_answer.setText((isOpenAnalysis ? "关闭" : "展开") + "解析");
    }

    @Override
    public void showFirstQuestion(boolean isFirst, boolean isTest) {
        if (isFirst) footer.last.setVisibility(View.GONE);
        else footer.last.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLastQuestion(boolean isLast, boolean isTest) {
        if (isLast) {
            if (isTest)
                footer.next.setText("交卷");
            else
                footer.next.setText("退出");
            footer.next.setBackgroundResource(R.drawable.shape_exam_commit_pager);
        } else {
            footer.next.setText("下一题");
            footer.next.setBackgroundResource(R.drawable.exam_next_pager);
        }
    }

    @Override
    public void showTime(String content) {
//        setTitle(content);
        toolbar_title.setText(content);
    }

    @Override
    public void setNowNumber(int numNumber) {
        header.now_number.setText(numNumber + "");
    }

    private void showCollect(boolean isCollect) {
        Drawable img;
        if (isCollect)
            img = getResources().getDrawable(R.mipmap.ic_collect_press);
        else
            img = getResources().getDrawable(R.mipmap.ic_collect);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        footer.collect_the_exam.setCompoundDrawables(img, null, null, null);
    }
}
