package com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategory;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeQuestionaskClassify;

/**
 * Created by xzw on 2018/4/28.
 */

public class QuestionaskMainFragment extends BaseBackFragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;


    @OnClick({R.id.askAquestion, R.id.search_txt, R.id.search})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.askAquestion:
                //发问题
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
                    launchActivity(new Intent(_mActivity, LoginActivity.class).putExtra("SkipToHome", false));
                else
                    toWriteQuestion();
                break;
            case R.id.search_txt:
                toSearchQuestion();
                break;
            case R.id.search:
                startForResult(QuestionaskClassifyFragment.newInstance(), RequestCodeQuestionaskClassify);
                break;
        }
    }

    public void toWriteQuestion() {
        start(QuestionaskPublishFragment.newInstance());
    }

    public void toSearchQuestion() {
        start(QuestionaskSearchFragment.newInstance());
    }

    public static QuestionaskMainFragment newInstance() {

        Bundle args = new Bundle();

        QuestionaskMainFragment fragment = new QuestionaskMainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    VPFragmentAdapter adapter;

    public void setFragmenList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("热门", QuestionaskFragment.newInstance(2)));
        fragmenList.add(new FragmentBean("最新", QuestionaskFragment.newInstance(1)));
        fragmenList.add(new FragmentBean("待回复", QuestionaskFragment.newInstance(3)));
        adapter = new VPFragmentAdapter(getChildFragmentManager(), fragmenList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);

        tabs.setupWithViewPager(viewPager);
    }

/*
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        setFragmenList();
    }
*/

    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_questionask, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("我的学习记录");
        setFragmenList();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeQuestionaskClassify && resultCode == RESULT_OK && data != null) {
            QaCategory qaCategory = (QaCategory) data.getSerializable("QaCategory");
            int currentPosition = viewPager.getCurrentItem();
            QuestionaskFragment fragment = (QuestionaskFragment) adapter.getItem(currentPosition);//.ref
            fragment.reLoadData(qaCategory.getZy_wenda_category_id());
        }
    }
}
