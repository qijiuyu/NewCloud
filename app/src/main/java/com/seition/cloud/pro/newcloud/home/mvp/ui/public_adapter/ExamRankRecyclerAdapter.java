package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamRankUser;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class ExamRankRecyclerAdapter extends BaseQuickAdapter<ExamRankUser.RankUser, BaseViewHolder> {
    private int type;

    public ExamRankRecyclerAdapter() {
        super(R.layout.item_exam_rank);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ExamRankUser.RankUser bean) {

        viewHolder.setText(R.id.exam_rank, viewHolder.getAdapterPosition() + "");
        viewHolder.setText(R.id.exam_name, bean.getUsername());
        int totalTime = bean.getAnser_time();
        int hour = 0;
        int minute = 0;
        int second = 0;
        String time = "";

        if (totalTime >= 3600) {
            hour = totalTime / 3600;
            minute = (totalTime - 3600) / 60;
            second = (totalTime - 3600) % 60;
            time = hour + ":" + minute + "′" + second + "″";
        } else if (totalTime < 3600 && totalTime >= 60) {
            minute = totalTime / 60;
            second = totalTime % 60;
            time = minute + "′" + second + "″";
        } else if (totalTime < 60) {
//            second = (totalTime-3600)/60;
            time = totalTime + "″";
        }
        viewHolder.setText(R.id.exam_time, time);
        if (bean.getScore() == (int) bean.getScore())
            viewHolder.setText(R.id.exam_score, (int) bean.getScore() + "");
        else
            viewHolder.setText(R.id.exam_score, bean.getScore() + "");

        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(), bean.getUserface(), (ImageView) viewHolder.getView(R.id.exam_photo));
    }


}
