package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Questionask;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class QuestionAskRecyclerAdapter extends BaseQuickAdapter<Questionask, BaseViewHolder> {

    public QuestionAskRecyclerAdapter() {
        super(R.layout.item_question_ask_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Questionask bean) {
//        int type = Integer.parseInt(bean.getType());
        viewHolder.setText(R.id.qa_name, bean.getUname());

        viewHolder.setText(R.id.qa_title, bean.getWd_description());
        viewHolder.setText(R.id.qa_time, bean.getCtime());//TimeUtils.stampToDate(bean.getCtime(), TimeUtils.Format_TIME3)
        viewHolder.setText(R.id.qa_zan, bean.getWd_browse_count());
        viewHolder.setText(R.id.qa_message, bean.getWd_comment_count());
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(),bean.getUserface(),(ImageView) viewHolder.getView(R.id.qa_cover));
    }

}
