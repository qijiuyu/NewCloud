package com.bokecc.dwlivedemo_new.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.base.TitleActivity;
import com.bokecc.dwlivedemo_new.base.TitleOptions;
import com.bokecc.dwlivedemo_new.global.Config;

import butterknife.BindView;

public class SeekActivity extends TitleActivity<SeekActivity.SeekViewHolder> {

    private int mType;
    private String mUnit; // 单位
    // 最小值 用于实时计算
    private int mMin, mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_seek;
    }

    @Override
    protected void onBindViewHolder() {
        mViewHolder = new SeekViewHolder(getContentView());
        initParams();
        mViewHolder.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mViewHolder.mValueView.setText(String.valueOf(progress + mMin) + mUnit);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mProgress = seekBar.getProgress() + mMin;
            }
        });
    }

    private void initParams() {
        mType = getIntent().getExtras().getInt(Config.SEEK_TYPE);
        mMin = getIntent().getExtras().getInt(Config.SEEK_MIN);
        int max = getIntent().getExtras().getInt(Config.SEEK_MAX);
        int defValue = getIntent().getExtras().getInt(Config.SEEK_DEFAULT);
        mProgress = defValue;
        mViewHolder.mMinView.setText(String.format("%s", mMin));
        mViewHolder.mMaxView.setText(String.format("%s", max));
        mViewHolder.mSeekBar.setMax(max - mMin);
        mViewHolder.mSeekBar.setProgress(defValue - mMin);
        String title;
        switch (mType) {
            case Config.SEEK_TYPE_FPS:
                mViewHolder.mTipView.setText(getResources().getString(R.string.seek_fps_tip));
                title =  getResources().getString(R.string.fps);
                mUnit = "帧/秒";
                break;
            case Config.SEEK_TYPE_BITRATE:
                mViewHolder.mTipView.setText(getResources().getString(R.string.seek_bitrate_tip));
                title =  getResources().getString(R.string.bitrate);
                mUnit = "kbs";
                break;
            default:
                throw new RuntimeException("SeekActivity error type");
        }
        mViewHolder.mValueView.setText(mProgress + mUnit);
        TitleOptions.Builder builder = new TitleOptions.Builder();
        TitleOptions options = builder.leftStatus(TitleOptions.VISIBLE).
                leftResId(R.drawable.title_back).
                rightStatus(TitleOptions.INVISIBLE).
                titleStatus(TitleOptions.VISIBLE).title(title).
                onTitleClickListener(new OnLeftClickListener() {
                    @Override
                    public void onLeft() {
                        exit();
                    }
                }).
                build();
        setTitleOptions(options);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        Intent data = new Intent();
        data.putExtra(Config.SEEK_TYPE, mType);
        data.putExtra(Config.SEEK_PROGRESS, mProgress);
        finishWithData(Config.SEEK_RESULT_CODE, data);
    }

    final class SeekViewHolder extends TitleActivity.ViewHolder {

        @BindView(R2.id.id_seek_tip)
        TextView mTipView;
        @BindView(R2.id.id_seek_min_value)
        TextView mMinView;
        @BindView(R2.id.id_seek_max_value)
        TextView mMaxView;
        @BindView(R2.id.id_seek_value)
        TextView mValueView;
        @BindView(R2.id.id_seek_bar)
        SeekBar mSeekBar;

        SeekViewHolder(View view) {
            super(view);
        }

    }

}
