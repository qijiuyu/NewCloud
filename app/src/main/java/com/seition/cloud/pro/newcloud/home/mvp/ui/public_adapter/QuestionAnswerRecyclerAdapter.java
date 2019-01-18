package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class QuestionAnswerRecyclerAdapter extends BaseQuickAdapter<Questionask, BaseViewHolder> {

    public QuestionAnswerRecyclerAdapter() {
        super(R.layout.item_question_answer_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Questionask bean) {
//        int type = Integer.parseInt(bean.getType());
        viewHolder.setText(R.id.name, bean.getUserinfo().getUname());
        viewHolder.setText(R.id.conent, bean.getDescription());
//        viewHolder.setText(R.id.zan_count, bean.getComment_count()+"评论");//TimeUtils.stampToDate(bean.getCtime(), TimeUtils.Format_TIME3)

        viewHolder.setText(R.id.time, TimeUtils.dateDiff(TimeUtils.stampToDate(bean.getCtime(),TimeUtils.Format_TIME8)));
//        viewHolder.setText(R.id.time, TimeUtils.stampToDate(bean.getCtime(),TimeUtils.Format_TIME3));
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(),bean.getUserface(),(ImageView) viewHolder.getView(R.id.qa_cover));
    }

}
