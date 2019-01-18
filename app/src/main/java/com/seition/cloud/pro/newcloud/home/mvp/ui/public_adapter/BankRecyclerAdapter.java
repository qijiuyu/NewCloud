package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;

public class BankRecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BankRecyclerAdapter() {
        super(R.layout.item_area_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String bean) {
        viewHolder.setText(R.id.area_name, bean);
//        viewHolder.setText(R.id.comment_time, "成员:" + bean.getMemberCount());
//        viewHolder.setText(R.id.comment_content, "帖子:" + bean.getThreadCount());
//        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getLogoUrl(),(ImageView) viewHolder.getView(R.id.comment_cover));
    }


}
