package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.member.VipUser;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class MemberUserRecyclerAdapter extends BaseQuickAdapter<VipUser, BaseViewHolder> {

    public MemberUserRecyclerAdapter() {
        super(R.layout.item_new_member);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, VipUser bean) {
        viewHolder.setText(R.id.vip_user_name, bean.getUname());
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(),bean.getUser_head_portrait(),(ImageView) viewHolder.getView(R.id.item_grida_image));
    }


}
