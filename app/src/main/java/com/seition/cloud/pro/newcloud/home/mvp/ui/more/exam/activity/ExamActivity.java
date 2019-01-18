package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamLevel;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Moudles;
import com.seition.cloud.pro.newcloud.app.bean.examination.TestClassify;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ExamCategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ExamInfoPopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ExamLevelPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerExamComponent;
import com.seition.cloud.pro.newcloud.home.di.module.ExamModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.ExamPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter.ExamListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindFaceChedkActivity;
import com.seition.cloud.pro.newcloud.widget.TopBar;
import com.seition.cloud.pro.newcloud.widget.TopBarTab;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeFace;


public class ExamActivity extends BaseActivity<ExamPresenter> implements ExamContract.ExamListView {
    @Inject
    public ExamListAdapter adapter;
    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @BindView(R.id.topBar)
    TopBar mTobBar;
    private Moudles moudles;
    private ExamInfoPopWindow popWindow;
    ArrayList<TestClassify> classifyListData = new ArrayList<>();
    ExamLevelPopWindow examLevelPopWindow;
    ExamCategoryPickPopupWindow categoryPickPopupWindow;
    String cate_id;

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
        return R.layout.activity_exam; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mPresenter.setMoudle(moudles = (Moudles) getIntent().getSerializableExtra("moudle"));
        setTitle(moudles.getTitle());
        popWindow = new ExamInfoPopWindow(this, moudles.getIs_practice(), 0, mPresenter);
        mTobBar.addItem(new TopBarTab(this, R.drawable.ic_arrow_down_gray, "分类"))
                .addItem(new TopBarTab(this, R.drawable.ic_arrow_down_gray, "筛选条件"));
        initTabBar();
        initExamSelectData();
        initList();
    }

    private void initExamSelectData() {
        examLevelPopWindow = new ExamLevelPopWindow(this);
        examLevelPopWindow.setOnDialogItemClickListener(mPresenter);
        examLevelPopWindow.addItemDatas(new ExamLevel(0, "全部"));
        examLevelPopWindow.addItemDatas(new ExamLevel(1, "简单"));
        examLevelPopWindow.addItemDatas(new ExamLevel(2, "普通"));
        examLevelPopWindow.addItemDatas(new ExamLevel(3, "困难"));
    }

    private void initTabBar() {
        mTobBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition,TopBarTab tab) {
                switch (position) {
                    case 0:
                        if (classifyListData == null || classifyListData.size() == 0)
                            try {
                                mPresenter.examClassifyList();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        else
                            categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                    case 1:
                        examLevelPopWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void initList() {
        recycle_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);
        springview.setListener(mPresenter);//上拉下拉监听
        mPresenter.getExamList(true);
        mPresenter.getFaceSence();
    }

    MaterialDialog materialDialog;

    @Override
    public void showLoading() {
        popWindow.dissmiss();
        materialDialog = new MaterialDialog.Builder(this)
//                .title("正在请求数据")
                .content("正在请求数据")
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
    public void setClassifyListData(ArrayList<TestClassify> classifyListData) {
        this.classifyListData = classifyListData;
        categoryPickPopupWindow = new ExamCategoryPickPopupWindow(this, classifyListData);
        if (classifyListData.size() == 0)
            categoryPickPopupWindow.setDialogEmptyViewVisibility(true);
        else
            categoryPickPopupWindow.setDialogEmptyViewVisibility(false);
        categoryPickPopupWindow.setOnDialogItemClickListener(mPresenter);
        categoryPickPopupWindow.showPopAsDropDown(mTobBar, 0, 0, Gravity.BOTTOM);
    }

    @Override
    public void setExams(ArrayList<MExamBean> exams) {
        adapter.setNewData(exams);
        adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(this));
    }

    @Override
    public void addExams(ArrayList<MExamBean> exams) {
        adapter.addData(exams);
    }

    @Override
    public void showExamInfo(MExamBean meb) {
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(this).getString("oauth_token", null)))
            launchActivity(new Intent(this, LoginActivity.class));
        else {
            if (popWindow == null) return;
            popWindow.setData(meb);
            popWindow.show(this);
        }
    }

    int operationType = -1;

    @Override
    public void showFaceSaveStatus(FaceStatus status) {
        switch (status.getStatus()) {
            case 0:
                operationType = 3;
                break;
            case 1:
                operationType = 2;
                break;
            case 2:
                operationType = 4;
                break;

        }
        showFaceDialog(operationType);
    }

    private void showFaceDialog(int operationType) {
        new MaterialDialog.Builder(ExamActivity.this)
                .content(operationType == 2 ? "登录需要扫脸验证，是否开始扫脸验证？" : "登录需要完善个人人脸信息，是否立即去完善？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        startActivityForResult(new Intent(ExamActivity.this, BindFaceChedkActivity.class).putExtra("OperationType", operationType), RequestCodeFace);
//                        startForResult(BindFaceChedkActivity.newInstance(operationType),RequestCodeFace);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MyConfig.ResultCodeFaceCheck)
            mPresenter.getExamInfoAndStartExam(mPresenter.getMeb(), 2);
    }
}
