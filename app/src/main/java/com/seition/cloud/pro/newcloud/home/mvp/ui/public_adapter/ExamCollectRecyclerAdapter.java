package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class ExamCollectRecyclerAdapter extends BaseQuickAdapter<CollectExam, BaseViewHolder> {
    private int type;
    public ExamCollectRecyclerAdapter() {
        super(R.layout.item_exam_collect_swip);
    }
    public void setType(int type){
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CollectExam bean) {
        viewHolder.setText(R.id.exam_name, Html.fromHtml(bean .getQuestion_info().getContent()));
        viewHolder.setText(R.id.exam_time, TimeUtils.stampToDate(bean.getCtime(),TimeUtils.Format_TIME8));
    }


}
