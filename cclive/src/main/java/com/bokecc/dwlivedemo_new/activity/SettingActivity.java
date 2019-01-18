package com.bokecc.dwlivedemo_new.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.base.TitleActivity;
import com.bokecc.dwlivedemo_new.base.TitleOptions;
import com.bokecc.dwlivedemo_new.global.Config;
import com.bokecc.dwlivedemo_new.util.SPUtil;
import com.bokecc.dwlivedemo_new.view.ItemLayout;
import com.bokecc.dwlivedemo_new.view.ToggleButton;
import com.bokecc.sdk.mobile.push.core.DWPushSession;
import com.bokecc.sdk.mobile.push.entity.SpeedRtmpNode;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends TitleActivity<SettingActivity.SettingViewHolder> {

    private boolean isBeauty = true, isVertical = true;

    // 选中相机 分辨率 服务器的索引
    private int mCameraPos = Config.SELECT_POSITION_DEFAULT,
            mResolutionPos = Config.SELECT_POSITION_DEFAULT, mServerPos;
    private int mFpsValue = Config.SETTING_DEFAULT_FPS, mBitrateValue = Config.SETTING_DEFAULT_BITRATE;

    private SPUtil mSPUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onBindViewHolder() {
        mSPUtil = SPUtil.getIntsance();
        mViewHolder = new SettingViewHolder(getContentView());
        TitleOptions.Builder builder = new TitleOptions.Builder();
        TitleOptions options = builder.leftStatus(TitleOptions.VISIBLE).
                leftResId(R.drawable.title_back).
                rightStatus(TitleOptions.GONE).
                titleStatus(TitleOptions.VISIBLE).title(DWPushSession.getInstance().getLiveRoomName()).
                onTitleClickListener(new OnLeftClickListener() {
                    @Override
                    public void onLeft() {
                        finish();
                    }
                }).
                build();
        setTitleOptions(options);
        mViewHolder.mOrientation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isVertical = !isChecked;
                mSPUtil.put(Config.SETTING_ORIENTATION, isVertical);
            }
        });
        mViewHolder.mBeauty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBeauty = isChecked;
                mSPUtil.put(Config.SETTING_BEAUTY, isBeauty);
            }
        });
        initDefValue();
    }

    private void initDefValue() {
        isVertical = mSPUtil.getBoolean(Config.SETTING_ORIENTATION, true);
        isBeauty = mSPUtil.getBoolean(Config.SETTING_BEAUTY, true);
        mCameraPos = mSPUtil.getInt(Config.SETTING_CAMERA, 0);
        mResolutionPos = mSPUtil.getInt(Config.SETTING_RESOLUTION, 0);
        mFpsValue = mSPUtil.getInt(Config.SETTING_FPS, Config.SETTING_DEFAULT_FPS);
        mBitrateValue = mSPUtil.getInt(Config.SETTING_BITRATE, Config.SETTING_DEFAULT_BITRATE);
        mViewHolder.mOrientation.setCheckedImmediately(!isVertical);
        mViewHolder.mBeauty.setCheckedImmediately(isBeauty);
        mViewHolder.mCamera.setValue(mCameraPos == 0 ? "前置摄像头" : "后置摄像头");
        mViewHolder.mResolution.setValue(mResolutionPos == 0 ?
                "360P" : (mResolutionPos == 1) ? "480P" : "720P");
        mViewHolder.mFps.setValue(String.valueOf(mFpsValue) + "帧/秒");
        mViewHolder.mBitrate.setValue(String.valueOf(mBitrateValue) + "kbs");
        mServerPos = DWPushSession.getInstance().getRecommendIndex();
        SpeedRtmpNode rtmpNode = DWPushSession.getInstance().getRtmpNodes().get(mServerPos);
        mViewHolder.mServer.setValue(rtmpNode.getDesc());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 || data == null) {
            return;
        }
        int selType = data.getIntExtra(Config.SELECT_TYPE, -1);
        int seekType = data.getIntExtra(Config.SEEK_TYPE, -1);
        if (selType != -1) {
            if (selType == Config.SELECT_TYPE_CAMERA) {
                mCameraPos = data.getIntExtra(Config.SELECT_POSITION, 0);
                mViewHolder.mCamera.setValue(mCameraPos == 0 ? "前置摄像头" : "后置摄像头");
                mSPUtil.put(Config.SETTING_CAMERA, mCameraPos);
            } else if (selType == Config.SELECT_TYPE_RESOLUTION) {
                mResolutionPos = data.getIntExtra(Config.SELECT_POSITION, 0);
                mViewHolder.mResolution.setValue(mResolutionPos == 0 ?
                        "360P" : (mResolutionPos == 1) ? "480P" : "720P");
                mSPUtil.put(Config.SETTING_RESOLUTION, mResolutionPos);
            } else {
                mServerPos = data.getIntExtra(Config.SELECT_POSITION, mServerPos);
                SpeedRtmpNode rtmpNode = DWPushSession.getInstance().getRtmpNodes().get(mServerPos);
                mViewHolder.mServer.setValue(rtmpNode.getDesc());
            }
        }
        if (seekType != -1) {
            if (seekType == Config.SEEK_TYPE_FPS) {
                mFpsValue = data.getIntExtra(Config.SEEK_PROGRESS, Config.SETTING_DEFAULT_FPS);
                mViewHolder.mFps.setValue(String.valueOf(mFpsValue) + "帧/秒");
                mSPUtil.put(Config.SETTING_FPS, mFpsValue);
            } else {
                mBitrateValue = data.getIntExtra(Config.SEEK_PROGRESS, Config.SETTING_DEFAULT_BITRATE);
                mViewHolder.mBitrate.setValue(String.valueOf(mBitrateValue) + "kbs");
                mSPUtil.put(Config.SETTING_BITRATE, mBitrateValue);
            }
        }
    }

    final class SettingViewHolder extends TitleActivity.ViewHolder {

        @BindView(R2.id.id_item_orientation)
        ToggleButton mOrientation;
        @BindView(R2.id.id_item_beauty)
        ToggleButton mBeauty;
        @BindView(R2.id.id_setting_camera)
        ItemLayout mCamera;
        @BindView(R2.id.id_setting_resolution)
        ItemLayout mResolution;
        @BindView(R2.id.id_setting_bitrate)
        ItemLayout mBitrate;
        @BindView(R2.id.id_setting_fps)
        ItemLayout mFps;
        @BindView(R2.id.id_setting_server)
        ItemLayout mServer;

        SettingViewHolder(View view) {
            super(view);
        }

        @OnClick({R2.id.id_setting_camera, R2.id.id_setting_resolution, R2.id.id_setting_server})
        void jumpSelect(View v) {
            Bundle bundle = new Bundle();
            int i = v.getId();
            if (i == R.id.id_setting_camera) {
                bundle.putInt(Config.SELECT_TYPE, Config.SELECT_TYPE_CAMERA);
                bundle.putInt(Config.SELECT_POSITION, mCameraPos);

            } else if (i == R.id.id_setting_resolution) {
                bundle.putInt(Config.SELECT_TYPE, Config.SELECT_TYPE_RESOLUTION);
                bundle.putInt(Config.SELECT_POSITION, mResolutionPos);

            } else if (i == R.id.id_setting_server) {
                bundle.putInt(Config.SELECT_TYPE, Config.SELECT_TYPE_SERVER);
                bundle.putInt(Config.SELECT_POSITION, mServerPos);

            } else {
                return;
            }
            goForResult(SelectActivity.class, Config.SETTING_REQUEST_CODE, bundle);
        }

        @OnClick({R2.id.id_setting_fps, R2.id.id_setting_bitrate})
        void jumpSeek(View v) {
            Bundle bundle = new Bundle();
            int i = v.getId();
            if (i == R.id.id_setting_fps) {
                bundle.putInt(Config.SEEK_MIN, Config.SETTING_MIN_FPS);
                bundle.putInt(Config.SEEK_MAX, Config.SETTING_MAX_FPS);
                bundle.putInt(Config.SEEK_DEFAULT, mFpsValue);
                bundle.putInt(Config.SEEK_TYPE, Config.SEEK_TYPE_FPS);

            } else if (i == R.id.id_setting_bitrate) {
                bundle.putInt(Config.SEEK_MIN, Config.SETTING_MIN_BITRATE);
                bundle.putInt(Config.SEEK_MAX, DWPushSession.getInstance().getMaxBitrate());
                bundle.putInt(Config.SEEK_DEFAULT, mBitrateValue);
                bundle.putInt(Config.SEEK_TYPE, Config.SEEK_TYPE_BITRATE);

            } else {
                return;
            }
            goForResult(SeekActivity.class, Config.SETTING_REQUEST_CODE, bundle);
        }

        @OnClick(R2.id.id_setting_start_btn)
        void jumpPush() {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Config.PUSH_ORIENTATION, isVertical);
            bundle.putBoolean(Config.PUSH_BEAUTY, isBeauty);
            bundle.putInt(Config.PUSH_CAMERA, mCameraPos);
            bundle.putInt(Config.PUSH_RESOLUTION, mResolutionPos);
            bundle.putInt(Config.PUSH_BITRATE, mBitrateValue);
            bundle.putInt(Config.PUSH_FPS, mFpsValue);
            bundle.putInt(Config.PUSH_SERVER, mServerPos);
            go(PushActivity.class, bundle);
        }

    }

}
