package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMessageComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MessageModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MessageContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MessagePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.fragment.MessageCommentFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.fragment.MessagePrivateFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.fragment.MessageSystemFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.VPFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;

    @OnClick({R.id.rl_message_private,R.id.rl_message_coment,R.id.rl_message_system})
    void toMsgBlock(View view){
        switch (view.getId()){
            case R.id.rl_message_private:
                launchActivity(new Intent(MessageActivity.this,MessagePrivateActivity.class));
                break;
            case R.id.rl_message_coment:
                launchActivity(new Intent(MessageActivity.this,MessageCommentActivity.class));
                break;
            case R.id.rl_message_system:
                launchActivity(new Intent(MessageActivity.this,MessageSystemActivity.class));
                break;
        }
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageModule(new MessageModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_message; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.message);
        setFragmentList();
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


    public void setFragmentList() {
        ArrayList<FragmentBean> fragmenList = new ArrayList<FragmentBean>();
        fragmenList.add(new FragmentBean("私信", MessagePrivateFragment.newInstance()));
        fragmenList.add(new FragmentBean("评论", MessageCommentFragment.newInstance()));
        fragmenList.add(new FragmentBean("通知", MessageSystemFragment.newInstance()));
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            PreferenceUtil.getInstance(MessageActivity.this).saveInt("messageCommentCount",0);
                            break;
                        case 1:
                            PreferenceUtil.getInstance(MessageActivity.this).saveInt("messagePrivateCount",0);
                            break;
                        case 2:
                            PreferenceUtil.getInstance(MessageActivity.this).saveInt("messageSystemCount",0);
                            break;

                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
