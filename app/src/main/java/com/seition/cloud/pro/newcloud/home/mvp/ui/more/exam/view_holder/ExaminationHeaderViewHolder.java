package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.view_holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by addis on 2018/3/22.
 */

public class ExaminationHeaderViewHolder {
    private View header;
    private Unbinder mUnbinder;
    @BindView(R.id.now_number)
    public TextView now_number;
    @BindView(R.id.all_number)
    public TextView all_number;
    @BindView(R.id.exam_type)
    public TextView exam_type;
    @BindView(R.id.topic)
    public TextView topic;

    public ExaminationHeaderViewHolder(Context context) {
        this.header = View.inflate(context, R.layout.view_header_exam_topic, null);
        mUnbinder = ButterKnife.bind(this, header);
    }

    public View getView() {
        return header;
    }

    public void unBind() {
        mUnbinder.unbind();
    }
}
