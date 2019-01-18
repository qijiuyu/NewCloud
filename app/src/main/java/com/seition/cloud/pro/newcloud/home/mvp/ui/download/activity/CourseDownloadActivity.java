package com.seition.cloud.pro.newcloud.home.mvp.ui.download.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseCacheBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCourseComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CourseDownloadPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter.DownloadVideoAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CourseDownloadActivity extends BaseActivity<CourseDownloadPresenter> implements CourseContract.CourseDownloadView {
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.video_count)
    TextView video_count;
    @BindView(R.id.download_count)
    TextView download_count;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @Inject
    DownloadVideoAdapter adapter;

    @OnClick({R.id.course, R.id.download_all})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.course:
                if (from != null && from.equals(MessageConfig.FROM_COURSE))
                    killMyself();
                else
                    start(CourseDetailsFragment.newInstance(course.getCourseId(), false, "", MessageConfig.FROM_DOWNLOAD));
//                    launchActivity(new Intent(getApplicationContext()
//                            , CourseDetailsActivity.class)
//                            .putExtra("courseId", course.getCourseId())
//                            .putExtra("from", MessageConfig.FROM_DOWNLOAD)
//                    );
                break;
            case R.id.download_all:
                //下载全部
                break;
        }
    }

    private String from;
    private CourseCacheBean course;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCourseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_course_download; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.course_download);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        course = (CourseCacheBean) intent.getSerializableExtra("course");
        showCourseInfo();
        initList();
    }

    private void showCourseInfo() {
        title.setText(course.getTitle());
        video_count.setText(/*course.getVideo_order_count() + "人在学·共"*/"共" + course.getSection_count() + "节");
        download_count.setText("已下载" + 0 + "个任务");
        GlideLoaderUtil.LoadImage(this, course.getCover(), cover);
    }

    private void initList() {
        recycle_view.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        mPresenter.setRecycle_view(recycle_view);

        adapter.setOnItemClickListener(mPresenter);
        adapter.setOnItemChildClickListener(mPresenter);
        try {
            mPresenter.getCourseSeitionList(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void showSeition(ArrayList<MultiItemEntity> seitions) {
        adapter.setNewData(seitions);
        adapter.expandAll();
    }
}
