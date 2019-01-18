package com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.event.GroupEvent;
import com.seition.cloud.pro.newcloud.app.utils.BlurUtils;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupTheme;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerGroupComponent;
import com.seition.cloud.pro.newcloud.home.di.module.GroupModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.GroupTopicPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupTopicRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.CustomShapeImageView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GroupTopicListActivity extends BaseActivity<GroupTopicPresenter> implements GroupContract.GroupTopicView,BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.group_topic_springView)SpringView springView;
    @BindView(R.id.topic_list)RecyclerView recyclerView;
    @BindView(R.id.group_details_lay)LinearLayout group_details_lay;
    @BindView(R.id.group_details_img)CustomShapeImageView group_details_img;
    @BindView(R.id.group_details_title)TextView group_details_title;
    @BindView(R.id.group_details_memmbercount)TextView group_details_memmbercount;
    @BindView(R.id.group_details_topiccount)TextView group_details_topiccount;
    @BindView(R.id.group_join)TextView group_join;
    @BindView(R.id.group_edit)TextView group_edit;
    @BindView(R.id.group_delete)TextView group_delete;

    @OnClick({R.id.group_details_img,R.id.group_join,R.id.group_edit,R.id.group_delete})
    void groupTopicOperate(View view){
        switch (view.getId())
        {
            case R.id.group_details_img:
                Intent intent = new Intent(GroupTopicListActivity.this, GroupDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("group",group);
                bundle.putParcelable("bitmap", bitmap);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.group_join:

                break;
            case R.id.group_edit:

                break;
            case R.id.group_delete:

                break;
        }

    }


    @Inject
    GroupTopicRecyclerAdapter adapter;

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
        return R.layout.activity_group_topic_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }
    private String  group_id="", dist ="", keysord=""; ;
    private int page = 1,   count= 10;

    private String  group_name, group_category;
    @Override
    public void initData(Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
        EventBus.getDefault().registerSticky(this);

        this.group =((Group)getIntent().getSerializableExtra("group"));
        group_id = group.getId();
        group_name = group.getName();
        group_category = (String) getIntent().getExtras().get("group_category");

        setTitle(group.getName());

        System.out.println("GroupEventGroupEventGroupEvent 1");
        mPresenter.getTopicList(group_id,page,count,dist,keysord,true,true);
        loadDetailstData();
        loadListData(page,true);
        System.out.println("GroupEventGroupEventGroupEvent 2");
        initView();
        System.out.println("GroupEventGroupEventGroupEvent 3");
    }

    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewAdapter = new RecyclerViewAdapter(this, mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                GroupTheme itemBean = (GroupTheme) adapter.getItem(position);
                switch(view.getId()){
                    case R.id.topic_more:
                        break;
                    case R.id.topic_count:
                        break;
                }
            }
        });

        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadListData(page,true);
            }

            @Override
            public void onLoadmore() {
                page++;
                loadListData(page,false);
            }
        });
        springView.setHeader(new DefaultHeader(getApplicationContext()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getApplicationContext()));
    }



    private void loadListData(int page,boolean pull){
        mPresenter.getTopicList(group_id,page,count,dist,keysord,pull,true);
    }
    private void loadDetailstData(){
        mPresenter.getGroupDetails(group_id,true);
    }



    // 一定要注册EventBus ，否则 下面的方法不会 执行
//    @Subscribe
    @Subscriber(tag = "Group")
    public void onGroupEvent(GroupEvent event) {
        System.out.println("GroupEventGroupEventGroupEvent");
        Group group = event.group;

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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    private Group group ;
    @Override
    public void showDetails(Group group) {
        this.group = group;
        getUrlBitmap(group.getLogoUrl());
        GlideLoaderUtil.LoadImage(getBaseContext(),group.getLogoUrl(),group_details_img);
        group_details_title.setText(group.getName());
        group_details_memmbercount.setText(group.getMemberCount()+"");
        group_details_topiccount.setText(group.getThreadCount()+"");
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap)msg.obj;
            Drawable drawable = new BitmapDrawable(BlurUtils.blurBitmap(GroupTopicListActivity.this, bitmap, 25));
            group_details_lay.setBackgroundDrawable(drawable);

        }
    };
    Bitmap bitmap = null;
    public void getUrlBitmap(String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<Bitmap> futureTargetBitmap =  Glide.with(GroupTopicListActivity.this).asBitmap().load(url).submit();

                try {
                    bitmap = futureTargetBitmap.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Message message = mHandler.obtainMessage();
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
