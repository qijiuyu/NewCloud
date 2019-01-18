package com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategory;
import com.seition.cloud.pro.newcloud.app.popupwindow.QaCategoryListPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerQuestionaskComponent;
import com.seition.cloud.pro.newcloud.home.di.module.QuestionaskModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.QuestionaskContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.QuestionPublishPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class QuestionaskPublishFragment extends BaseBackFragment<QuestionPublishPresenter> implements QuestionaskContract.PublishView,QaCategoryListPopWindow.OnDialogItemClickListener {

    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.qa_content)
    EditText qa_content;
    @BindView(R.id.cate_name)
    TextView cate_name;


    private int typeId = -1;
    private String content = "";

    public static QuestionaskPublishFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putSerializable("QA",questionask);
        QuestionaskPublishFragment fragment = new QuestionaskPublishFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.qa_cateSelect_rl)
    RelativeLayout qaCateSelectRl;

    @OnClick({R.id.qa_cateSelect_rl, R.id.toolbar_right_text})
    void openDialog(View v) {
        switch (v.getId()) {
            case R.id.qa_cateSelect_rl:
                if (categories.size() == 0) {
                    mPresenter.getQuestionCategoryList(false);
                } else
                    listPopWindow.showPopAsDropDown(qaCateSelectRl, 0, 0, Gravity.BOTTOM);
                break;
            case R.id.toolbar_right_text:
                content =   qa_content.getText().toString().trim();
                if (typeId < 0) {
                    Utils.showToast(_mActivity, "请选择分类");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    Utils.showToast(_mActivity, "请输入问题内容");
                    return;
                }
                mPresenter.questionPublish(typeId, content);
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
        return inflater.inflate(R.layout.fragment_questionask_pubish, container, false);
    }

    QaCategoryListPopWindow listPopWindow;

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("发布问题");
        toolbar_right_text.setText("发布");
//        initListWindow();
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

    ArrayList<QaCategory> categories = new ArrayList<>();

    @Override
    public void showCategory(ArrayList<QaCategory> qaCategories) {
        categories = qaCategories;
        listPopWindow = new QaCategoryListPopWindow(_mActivity, null, 0, qaCateSelectRl);
        listPopWindow.setOnDialogItemClickListener(this);
        for (QaCategory category : qaCategories) {
            listPopWindow.addItemDatas(category);
        }

        listPopWindow.showPopAsDropDown(qaCateSelectRl, 0, 0, Gravity.BOTTOM);
    }

    @Override
    public void onWindowItemClick(Object p) {
        QaCategory category = (QaCategory) p;
        typeId = category.getZy_wenda_category_id();
        cate_name.setText(category.getTitle());
        listPopWindow.hide();
    }
}
