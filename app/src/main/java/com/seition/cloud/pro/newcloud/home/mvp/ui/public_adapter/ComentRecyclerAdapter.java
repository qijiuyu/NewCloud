package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;

public class ComentRecyclerAdapter extends BaseQuickAdapter<Organization, BaseViewHolder> {

    public ComentRecyclerAdapter() {
        super(R.layout.item_comment_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Organization bean) {
//        viewHolder.setText(R.id.comment_name, bean.getInfo());
//        viewHolder.setText(R.id.comment_star, bean.getIntro());
//        viewHolder.setText(R.id.comment_time, "成员:" + bean.getMemberCount());
//        viewHolder.setText(R.id.comment_content, "帖子:" + bean.getThreadCount());
//        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getLogoUrl(),(ImageView) viewHolder.getView(R.id.comment_cover));
    }


}
