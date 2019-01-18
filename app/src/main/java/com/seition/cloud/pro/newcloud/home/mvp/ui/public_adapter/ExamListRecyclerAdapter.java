package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

import java.util.ArrayList;

public class ExamListRecyclerAdapter extends BaseItemDraggableAdapter<Exam, BaseViewHolder> {

    private int type;

    public ExamListRecyclerAdapter(ArrayList<Exam> data,int layoutId,int type) {
        super(layoutId,data);
        this.type = type;
    }

    public void setType(int type){
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Exam bean) {
        String name ="";
        if(type!=4) {
            viewHolder.setText(R.id.exam_name, Html.fromHtml(bean.getPaper_info().getExams_paper_title()));
            viewHolder.setText(R.id.exam_time, TimeUtils.stampToDate(bean.getUpdate_time(),TimeUtils.Format_TIME8));
            if (bean.getProgress() < 100) {
                viewHolder.setVisible(R.id.exam_price, false);
                viewHolder.setVisible(R.id.exam_statue, true);

            } else {
                viewHolder.setVisible(R.id.exam_price, true);
                viewHolder.setVisible(R.id.exam_statue, false);

                if (bean.getStatus() == 0) {
                    viewHolder.setText(R.id.exam_price, "正在阅卷");
                } else if (bean.getStatus() == 1) {
                    if (type == 3)
                        viewHolder.setText(R.id.exam_price, bean.getWrong_count() + "道错题");
                    else
                        viewHolder.setText(R.id.exam_price, bean.getScore() + "分");
                }
            }
        }
        else{
            viewHolder.setText(R.id.exam_name, Html.fromHtml(bean .getQuestion_info().getContent()));
            viewHolder.setText(R.id.exam_time, TimeUtils.stampToDate(bean.getCtime(),TimeUtils.Format_TIME8));
        }

    }


}
