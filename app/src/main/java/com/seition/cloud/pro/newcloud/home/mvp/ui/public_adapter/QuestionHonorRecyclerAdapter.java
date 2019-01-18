package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.Hornor;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class QuestionHonorRecyclerAdapter extends BaseQuickAdapter<Hornor, BaseViewHolder> {

    public QuestionHonorRecyclerAdapter() {
        super(R.layout.item_qa_honor_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Hornor bean) {

        switch (viewHolder.getAdapterPosition()) {
            case 0:
                viewHolder.setImageResource(R.id.star_level,R.mipmap.hornor_top);
                break;
            case 1:
                viewHolder.setImageResource(R.id.star_level,R.mipmap.hornor_second);
                break;
            case 2:
                viewHolder.setImageResource(R.id.star_level,R.mipmap.hornor_third);
                break;
        }

        viewHolder.setText(R.id.user_name, bean.getUserinfo().getUname());
        viewHolder.setText(R.id.answer_num, bean.getCount());
        viewHolder.setText(R.id.user_intro, bean.getUserinfo().getIntro());

        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(),bean.getUserinfo().getAvatar_small(),(ImageView) viewHolder.getView(R.id.user_img));
    }

}
