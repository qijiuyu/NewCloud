package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class OrganizationGridRecyclerAdapter extends BaseQuickAdapter<Organization, BaseViewHolder> {

    public OrganizationGridRecyclerAdapter() {
        super(R.layout.item_organization_horizontal_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Organization bean) {

        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getCover(),(ImageView) viewHolder.getView(R.id.organization_cover));
    }


}
