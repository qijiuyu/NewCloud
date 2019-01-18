package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.Moudles;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerExamComponent;
import com.seition.cloud.pro.newcloud.home.di.module.ExamModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.ExamTypeListPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.ExamMoudleListAdapter;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ExamTypeListActivity extends BaseActivity<ExamTypeListPresenter> implements ExamContract.ExamTypeMoudleView {
    @Inject
    ExamMoudleListAdapter adapter;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

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
        return R.layout.activity_exam_type_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.exam);
        initList();
    }

    private void initList() {
        recycle_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);

        mPresenter.examMoudlesList("getExamMoudlesList", true);
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
    public void setData(ArrayList<Moudles> moudles) {
        //拿到列表数据进行数据显示
        adapter.addData(moudles);
    }
}
