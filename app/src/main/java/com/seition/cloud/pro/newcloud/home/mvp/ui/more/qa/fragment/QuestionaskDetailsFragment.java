package com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerQuestionaskComponent;
import com.seition.cloud.pro.newcloud.home.di.module.QuestionaskModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.QuestionaskDetailsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QuestionAnswerRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.ExpandableTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class QuestionaskDetailsFragment extends BaseBackFragment<QuestionaskDetailsPresenter> implements QuestionaskContract.DetailsView,
        BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.commit_ll)
    LinearLayout commit_ll;
    @Inject
    QuestionAnswerRecyclerAdapter adapter;

    Questionask questionask;

    @OnClick(R.id.commit)
    void commit() {
        onClick(answer);
    }

    public static QuestionaskDetailsFragment newInstance(Questionask questionask) {
        Bundle args = new Bundle();
        args.putSerializable("QA", questionask);
        QuestionaskDetailsFragment fragment = new QuestionaskDetailsFragment();
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_questionask_details, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getInitReviewConfig(commit_ll);
        loadData(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        questionask = (Questionask) getArguments().getSerializable("QA");
        setTitle("问题详情");
        initViews();
    }

    private void loadData(boolean pull) {
        mPresenter.getQuestionDetails(Integer.parseInt(questionask.getId()), pull, false);
        mPresenter.getQuestionAnswerList(Integer.parseInt(questionask.getId()), pull, false);
    }

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
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

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    ImageView qa_cover;

    TextView name;
    TextView classify;

    TextView answer_cout;
    ExpandableTextView content;

    TextView time;
    TextView comment;
    TextView answer;

    View headerView;

    private void initViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);// 布局管理器
        headerView = getLayoutInflater().inflate(R.layout.fragment_questionask_details_header, recyclerView, false);
        qa_cover = (ImageView) headerView.findViewById(R.id.qa_cover);
        name = (TextView) headerView.findViewById(R.id.name);
        classify = (TextView) headerView.findViewById(R.id.classify);
        answer_cout = (TextView) headerView.findViewById(R.id.answer_cout);
        content = (ExpandableTextView) headerView.findViewById(R.id.content);
        time = (TextView) headerView.findViewById(R.id.time);
        comment = (TextView) headerView.findViewById(R.id.comment);
        answer = (TextView) headerView.findViewById(R.id.answer);
        comment.setOnClickListener(this);
        answer.setOnClickListener(this);
        adapter.addHeaderView(headerView);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(getActivity(),5), Utils.dip2px(getActivity(),10)/*,R.color.color_e5*/));
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
        springView.setHeader(new DefaultHeader(getContext()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getContext()));
        springView.setEnableFooter(false);
        loadData(true);
    }


    @Override
    public void showDetails(Questionask bean) {

        GlideLoaderUtil.LoadCircleImage(_mActivity, questionask.getUserface(), qa_cover);
        name.setText(questionask.getUname());
        answer_cout.setText((questionask.getWd_comment_count() == null ? "0" : questionask.getWd_comment_count()) + "个回答");

//        if (bean.getWd_description().length() > 40) {
//            SpannableStringBuilder span = new SpannableStringBuilder("缩进" + bean.getWd_description());
//            span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
//                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            content.setText(span);
//        } else
        content.setText(_mActivity, bean.getWd_description());
        time.setText(bean.getCtime());
        // TODO 问答分类 需修改,无数据
        classify.setVisibility(View.GONE);
        classify.setText(bean.getType());
    }

    @Override
    public void showStateViewState(int state) {
        showStateViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment:
                break;
            case R.id.answer:
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
                    launchActivity(new Intent(_mActivity, LoginActivity.class).putExtra("SkipToHome", false));
                else
                    start(QuestionaskAnswerFragment.newInstance(questionask));
                break;
        }
    }
}