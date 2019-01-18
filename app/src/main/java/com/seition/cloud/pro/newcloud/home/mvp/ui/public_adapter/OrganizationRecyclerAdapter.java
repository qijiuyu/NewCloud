package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.organization.Organization;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class OrganizationRecyclerAdapter extends BaseQuickAdapter<Organization, BaseViewHolder> {

    public OrganizationRecyclerAdapter() {
        super(R.layout.item_organization_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Organization bean) {
        viewHolder.setText(R.id.organization_name, bean.getTitle())
                .setText(R.id.organization_course_count, bean.getCount().getVideo_count() + "课程")
                .setText(R.id.organization_student_count, bean.getCount().getTeacher_count() + "讲师")
                .setText(R.id.organization_info, bean.getInfo())
                .getView(R.id.organization_name).setSelected(true);
        GlideLoaderUtil.LoadRoundImage1(viewHolder.itemView.getContext(), bean.getCover(), (ImageView) viewHolder.getView(R.id.organization_cover));
    }
}
