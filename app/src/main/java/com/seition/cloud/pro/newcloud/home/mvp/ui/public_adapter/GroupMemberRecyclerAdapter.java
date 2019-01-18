package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupMember;
import com.seition.cloud.pro.newcloud.widget.CustomShapeImageView;

/**
 * author: xzw
 */

public class GroupMemberRecyclerAdapter extends BaseQuickAdapter<GroupMember, BaseViewHolder> {

    public GroupMemberRecyclerAdapter() {
        super(R.layout.item_group_member_list);

    }

    @Override
    protected void convert(BaseViewHolder viewHolder, GroupMember bean) {

        viewHolder.setText(R.id.group_member_name, bean.getName());

        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getAvatar_big(),(CustomShapeImageView) viewHolder.getView(R.id.group_member_img));

    }

}
