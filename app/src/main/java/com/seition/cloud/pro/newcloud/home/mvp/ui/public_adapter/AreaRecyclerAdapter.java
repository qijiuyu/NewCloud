package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.AreaInfo;

public class AreaRecyclerAdapter extends BaseQuickAdapter<AreaInfo, BaseViewHolder> {

    public AreaRecyclerAdapter() {
        super(R.layout.item_area_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, AreaInfo bean) {
        viewHolder.setText(R.id.area_name, bean.getTitle());
    }
}
