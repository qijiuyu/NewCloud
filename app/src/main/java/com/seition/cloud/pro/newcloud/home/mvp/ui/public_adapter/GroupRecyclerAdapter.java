package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;

public class GroupRecyclerAdapter extends BaseQuickAdapter<Group, BaseViewHolder> {

    public GroupRecyclerAdapter() {
        super(R.layout.item_group_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Group bean) {
        viewHolder.setText(R.id.name, bean.getName());
        viewHolder.setText(R.id.intro, bean.getIntro());
        viewHolder.setText(R.id.member_count, "成员:" + bean.getMemberCount());
        viewHolder.setText(R.id.thread_count, "帖子:" + bean.getThreadCount());
        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getLogoUrl(),(ImageView) viewHolder.getView(R.id.group_img));
    }


}
