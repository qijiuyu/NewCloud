package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class MemberTypeRecyclerAdapter extends BaseQuickAdapter<Member, BaseViewHolder> {

    public MemberTypeRecyclerAdapter() {
        super(R.layout.item_member_type);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Member bean) {
        viewHolder.setText(R.id.member_name, bean.getTitle());
        viewHolder.setText(R.id.member_month_price, bean.getVip_month());
        viewHolder.setText(R.id.member_year_price, bean.getVip_year());
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(),bean.getCover(),(ImageView) viewHolder.getView(R.id.member_type_img));
    }


}
