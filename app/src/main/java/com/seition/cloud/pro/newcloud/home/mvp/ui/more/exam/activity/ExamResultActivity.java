package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamRankUser;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerExamComponent;
import com.seition.cloud.pro.newcloud.home.di.module.ExamModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.ExamResultPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamRankRecyclerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ExamResultActivity extends BaseActivity<ExamResultPresenter> implements ExamContract.ExamRestultView {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    //    @BindView(R.id.test_rank)
    TextView test_rank;
    //    @BindView(R.id.test_username)
    TextView test_username;
    //    @BindView(R.id.test_time)
    TextView test_time;
    //    @BindView(R.id.test_score_s)
    TextView test_score_s;
    //    @BindView(R.id.test_userphoto)
    ImageView test_userphoto;

    TextView test_score;
    TextView test_instro;
    TextView test_again;

    @Inject
    ExamRankRecyclerAdapter adapter;

    private String exam_User_Id, exams_Paper_Id;
    private int exam_type;
    private int pid;
    private boolean isDelect;

    @OnClick({R.id.all_explain, R.id.wrong_explain})
    void doSomething(View view) {
        switch (view.getId()) {
            case R.id.all_explain:
                mPresenter.getResult(exam_User_Id, Integer.parseInt(exams_Paper_Id), exam_type);
                break;
            case R.id.wrong_explain:
                mPresenter.getWrongExamData(exam_User_Id, Integer.parseInt(exams_Paper_Id), exam_type);
                break;

        }

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
        return R.layout.activity_exam_result_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        exam_User_Id = getIntent().getStringExtra("Exam_User_Id");
        exams_Paper_Id = getIntent().getStringExtra("Exams_Paper_Id");
        isDelect = getIntent().getBooleanExtra("isDelect", false);
        exam_type = getIntent().getIntExtra("Exams_Type", 0);
        pid = getIntent().getIntExtra("pid", 0);
        initView();
        loadData(true);
    }

    //activity_exam_result_header
    private void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
//        recyclerView.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(_mActivity,0), Utils.dip2px(_mActivity,0.3f),R.color.color_e5));

  /*      adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData(cateId,false,false);
            }
        },recyclerView);
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(new CustomLoadMoreView());*/

        View headerView = getLayoutInflater().inflate(R.layout.activity_exam_result_header, recyclerView, false);
        test_score_s = (TextView) headerView.findViewById(R.id.test_score_s);
        test_time = (TextView) headerView.findViewById(R.id.test_time);
        test_rank = (TextView) headerView.findViewById(R.id.test_rank);
        test_username = (TextView) headerView.findViewById(R.id.test_username);
        test_userphoto = (ImageView) headerView.findViewById(R.id.test_userphoto);
        test_score = (TextView) headerView.findViewById(R.id.test_score);
        test_instro = (TextView) headerView.findViewById(R.id.test_instro);
        test_again = (Button) headerView.findViewById(R.id.test_again);
        test_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDelect) showMessage("该试卷已不可练习");
                else if (pid == 0)
                    //重练不需要传examsUsersId，模式强制为练习模式
                    mPresenter.getExamInfo("", Integer.parseInt(exams_Paper_Id), 1);
                else //上一级通过错题重练生成的数据应再次调错题重练接口进入练习
                    mPresenter.examinationWrongExam(pid + "", Integer.parseInt(exams_Paper_Id));
            }
        });
        adapter.addHeaderView(headerView);

        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);

        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                loadData(true);
            }

            @Override
            public void onLoadmore() {

                loadData(false);
            }
        });
        springView.setHeader(new DefaultHeader(this));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(this));
        springView.setEnableFooter(false);
    }

    private void loadData(boolean pull) {
        mPresenter.getExamRankUser(exam_User_Id, pull, false);
    }

    MaterialDialog materialDialog;

    @Override
    public void showLoading() {
        materialDialog = new MaterialDialog.Builder(this)
                .title("正在请求数据")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .canceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null)
            materialDialog.dismiss();
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
    public void showStateViewState(int state) {
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnable(enabled);
    }

    @Override
    public void showExamUser(ExamRankUser.RankUser now) {
        test_rank.setText(now.getRank_nomber());

        GlideLoaderUtil.LoadCircleImage(this, now.getUserface(), test_userphoto);

        test_username.setText(now.getUsername());

        int totalTime = now.getAnser_time();
        int hour = 0;
        int minute = 0;
        int second = 0;
        String time = "";

        if (totalTime >= 3600) {
            hour = totalTime / 3600;
            minute = (totalTime - 3600) / 60;
            second = (totalTime - 3600) % 60;
            time = hour + ":" + minute + "′" + second + "″";
        } else if (totalTime < 3600 && totalTime >= 60) {
            minute = totalTime / 60;
            second = totalTime % 60;
            time = minute + "′" + second + "″";
        } else if (totalTime < 60) {
            time = totalTime + "″";
        }

        test_time.setText(time + "");
        if (now.getScore() == (int) now.getScore()) {
            test_score_s.setText((int) now.getScore() + "");
            test_score.setText((int) now.getScore() + "");
        } else {
            test_score_s.setText(now.getScore() + "");
            test_score.setText(now.getScore() + "");
        }
    }
}
