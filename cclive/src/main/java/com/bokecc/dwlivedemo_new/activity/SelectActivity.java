package com.bokecc.dwlivedemo_new.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.base.TitleActivity;
import com.bokecc.dwlivedemo_new.base.TitleOptions;
import com.bokecc.dwlivedemo_new.contract.SelectContract;
import com.bokecc.dwlivedemo_new.global.Config;
import com.bokecc.dwlivedemo_new.popup.TxtLoadingPopup;
import com.bokecc.dwlivedemo_new.presenter.SelectPresenter;
import com.bokecc.dwlivedemo_new.recycle.BaseOnItemTouch;
import com.bokecc.dwlivedemo_new.recycle.OnClickListener;
import com.bokecc.dwlivedemo_new.recycle.RecycleViewDivider;
import com.bokecc.dwlivedemo_new.recycle.SelectAdapter;
import com.bokecc.dwlivedemo_new.recycle.ServerRecycleAdapter;
import com.bokecc.dwlivedemo_new.recycle.StringRecycleAdapter;
import com.bokecc.dwlivedemo_new.util.DensityUtil;
import com.bokecc.sdk.mobile.push.core.DWPushSession;
import com.bokecc.sdk.mobile.push.entity.SpeedRtmpNode;

import java.util.ArrayList;

import butterknife.BindView;

public class SelectActivity extends TitleActivity<SelectActivity.SelectViewholder> implements SelectContract.View {

    private View mRoot;
    private int mType;

    private int mSelPosition = 0;
    private SelectAdapter mAdapter;

    private SelectPresenter mSelectPresenter;
    private TxtLoadingPopup mLoadingPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_select;
    }

    @Override
    public void onBindPresenter() {
        mSelectPresenter = new SelectPresenter(this, this);
    }

    @Override
    protected void onBindViewHolder() {
        mRoot = getWindow().getDecorView().findViewById(android.R.id.content);
        mViewHolder = new SelectViewholder(getContentView());
        initParams();
        mViewHolder.mDatas.setLayoutManager(new LinearLayoutManager(this));
        mViewHolder.mDatas.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.HORIZONTAL, DensityUtil.dp2px(this, 1), Color.parseColor("#E8E8E8"),
                DensityUtil.dp2px(this, 19), DensityUtil.dp2px(this, 19)));
        ArrayList<String> datas = new ArrayList<>();
        if (mType == Config.SELECT_TYPE_CAMERA) {
            mAdapter = new StringRecycleAdapter(this);
            datas.add("前置摄像头");
            datas.add("后置摄像头");
            mAdapter.bindDatas(datas);
        } else if (mType == Config.SELECT_TYPE_RESOLUTION) {
            mAdapter = new StringRecycleAdapter(this);
            datas.add("360P");
            datas.add("480P");
            datas.add("720P");
            mAdapter.bindDatas(datas);
        } else {
            mAdapter = new ServerRecycleAdapter(this);
            mAdapter.bindDatas(DWPushSession.getInstance().getRtmpNodes());
        }
        mAdapter.setSelPosition(mSelPosition); // 设置默认选中
        mViewHolder.mDatas.addOnItemTouchListener(new BaseOnItemTouch(mViewHolder.mDatas,
                new OnClickListener() {
                    @Override
                    public void onClick(RecyclerView.ViewHolder viewHolder) {
                        mSelPosition = mViewHolder.mDatas.getChildAdapterPosition(viewHolder.itemView);
                        mAdapter.setSelPosition(mSelPosition);
                        final Intent data = new Intent();
                        data.putExtra(Config.SELECT_TYPE, mType);
                        data.putExtra(Config.SELECT_POSITION, mSelPosition);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finishWithData(Config.SELECT_RESULT_CODE, data);
                            }
                        }, 300L);
                    }
                }));
        mViewHolder.mDatas.setAdapter(mAdapter);

        mLoadingPopup = new TxtLoadingPopup(this);
        mLoadingPopup.setKeyBackCancel(true);
        mLoadingPopup.setOutsideCancel(true);
        mLoadingPopup.setTipValue("正在测速...");
    }

    private void initParams() {
        mType = getIntent().getExtras().getInt(Config.SELECT_TYPE);
        mSelPosition = getIntent().getExtras().getInt(Config.SELECT_POSITION);
        String title;
        TitleOptions.Builder builder = new TitleOptions.Builder();
        builder = builder.leftStatus(TitleOptions.VISIBLE).
                leftResId(R.drawable.title_back).titleStatus(TitleOptions.VISIBLE);
        switch (mType) {
            case Config.SELECT_TYPE_CAMERA:
                mViewHolder.mTip.setText(getResources().getString(R.string.select_camera_tip));
                title = getResources().getString(R.string.camera);
                builder = builder.onTitleClickListener(new OnLeftClickListener() {
                    @Override
                    public void onLeft() {
                        returnBack();
                    }
                });
                break;
            case Config.SELECT_TYPE_RESOLUTION:
                mViewHolder.mTip.setText(getResources().getString(R.string.select_resolution_tip));
                title = getResources().getString(R.string.resolution);
                builder = builder.onTitleClickListener(new OnLeftClickListener() {
                    @Override
                    public void onLeft() {
                        returnBack();
                    }
                });
                break;
            case Config.SELECT_TYPE_SERVER:
                mViewHolder.mTip.setText(getResources().getString(R.string.select_server_tip));
                title = getResources().getString(R.string.server);
                builder = builder.rightStatus(TitleOptions.VISIBLE).
                        rightValue(getResources().getString(R.string.setting_server_test_speed)).
                        onTitleClickListener(new OnTitleClickListener() {
                    @Override
                    public void onLeft() {
                        returnBack();
                    }

                    @Override
                    public void onRight() {
                        mSelectPresenter.testSpeed();
                    }
                });
                break;
            default:
                throw new RuntimeException("SelectActivity error type");
        }
        builder = builder.title(title);
        TitleOptions options = builder.build();
        setTitleOptions(options);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        Intent data = new Intent();
        data.putExtra(Config.SELECT_TYPE, mType);
        data.putExtra(Config.SELECT_POSITION, mSelPosition);
        finishWithData(Config.SELECT_RESULT_CODE, data);
    }

    private void returnBack() {
        setResult(-1);
        exit();
    }

    @Override
    public void showLoading() {
        mLoadingPopup.show(mRoot);
    }

    @Override
    public void dismissLoading() {
        mLoadingPopup.dismiss();
    }

    @Override
    public void updateServers(ArrayList<SpeedRtmpNode> rtmpNodes) {
        // 查找推荐节点位置
        for (SpeedRtmpNode rtmp :
                rtmpNodes) {
            if (rtmp.isRecommend()) {
                mSelPosition = rtmp.getIndex();
                break;
            }
        }
        mAdapter.setSelPosition(mSelPosition);
        mAdapter.bindDatas(rtmpNodes);
        mAdapter.notifyDataSetChanged();
    }

    final class SelectViewholder extends TitleActivity.ViewHolder {

        @BindView(R2.id.id_select_tip)
        TextView mTip;
        @BindView(R2.id.id_select_datas)
        RecyclerView mDatas;

        SelectViewholder(View view) {
            super(view);
        }

    }

}
