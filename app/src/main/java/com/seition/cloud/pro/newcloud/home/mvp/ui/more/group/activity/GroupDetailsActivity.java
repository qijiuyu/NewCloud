package com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.BlurUtils;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerGroupComponent;
import com.seition.cloud.pro.newcloud.home.di.module.GroupModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.GroupDetailsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupApplyMemberHorizontalListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupMemberHorizontalListAdapter;
import com.seition.cloud.pro.newcloud.widget.CustomShapeImageView;
import com.seition.cloud.pro.newcloud.widget.HorizontalListView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GroupDetailsActivity extends BaseActivity<GroupDetailsPresenter> implements GroupContract.GroupMemberView {
    @BindView(R.id.group_details_name)
    TextView group_details_name;
    @BindView(R.id.group_details_memmbercount)
    TextView group_details_memmbercount;
    @BindView(R.id.group_details_topiccount)
    TextView group_details_topiccount;
    @BindView(R.id.group_details_instro)
    TextView group_details_instro;
    @BindView(R.id.group_details_announce)
    TextView group_details_announce;
    @BindView(R.id.group_details_img)
    CustomShapeImageView group_details_img;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.group_member_hlist)
    HorizontalListView group_member_hlist;
    @BindView(R.id.group_member_apply_hlist)
    HorizontalListView group_member_apply_hlist;

    @BindView(R.id.TomemberList)
    TextView TomemberList;
    @BindView(R.id.applyTomemberList)
    TextView applyTomemberList;

    @Inject
    GroupMemberHorizontalListAdapter adapter;
//    @Inject
//    GroupMemberHorizontalListAdapter applyAdapter;
    @Inject
    GroupApplyMemberHorizontalListAdapter applyAdapter;

    private Group group ;
    private Bitmap bitmap;

    private String group_id ="";
    private String type     = "";
    private int page  = 1;
    private int count = 10;

    @OnClick({R.id.TomemberList,R.id.applyTomemberList})
    void toMemberList(View view){
        Intent intent =new Intent(GroupDetailsActivity.this,GroupMemberListActivity.class);
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.TomemberList:
                bundle.putString("type","");
                break;
            case R.id.applyTomemberList:
                bundle.putString("type","apply");

                break;
        }
        bundle.putSerializable("group",group);
        intent.putExtras(bundle);
        launchActivity(intent);
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerGroupComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .groupModule(new GroupModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_group_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        group = (Group)getIntent().getExtras().getSerializable("group");
        bitmap = getIntent().getExtras().getParcelable("bitmap");
        group_id = group.getId();

        setUIData();
    }

    private void setUIData(){
        setTitle(group.getName());
        GlideLoaderUtil.LoadImage(this,group.getLogoUrl(),group_details_img);
        Drawable drawable = new BitmapDrawable(BlurUtils.blurBitmap(GroupDetailsActivity.this, bitmap, 25));
        rl.setBackgroundDrawable(drawable);
        group_details_name.setText(group.getName());
        group_details_memmbercount.setText(group.getMemberCount());
        group_details_topiccount.setText(group.getThreadCount());
        group_details_instro.setText(group.getIntro());
        group_details_announce.setText(group.getAnnounce());
        mPresenter.getGroupMemberList(group_id,page,count,type,true);
        mPresenter.getGroupApplyMemberList(group_id,page,count,"apply",true);
        group_member_hlist.setAdapter(adapter);
        group_member_apply_hlist.setAdapter(applyAdapter);

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

}
