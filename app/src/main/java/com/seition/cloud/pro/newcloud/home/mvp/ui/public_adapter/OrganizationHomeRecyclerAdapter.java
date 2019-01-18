package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;

public class OrganizationHomeRecyclerAdapter extends BaseQuickAdapter<Organization, BaseViewHolder> {

    public OrganizationHomeRecyclerAdapter() {
        super(R.layout.item_organization_home_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Organization bean) {
        viewHolder.setText(R.id.organization_info, Html.fromHtml(bean.getInfo()));
        viewHolder.addOnClickListener(R.id.organization_discounts);
//        viewHolder.setText(R.id.intro, group.getIntro());
//        viewHolder.setText(R.id.member_count, "成员:" + group.getMemberCount());
//        viewHolder.setText(R.id.thread_count, "帖子:" + group.getThreadCount());
//        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),group.getLogoUrl(),(ImageView) viewHolder.getView(R.id.group_img));
    }



}
