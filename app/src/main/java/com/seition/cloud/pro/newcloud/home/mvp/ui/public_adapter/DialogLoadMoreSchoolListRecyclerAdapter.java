package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.offline.OfflineSchool;

public class DialogLoadMoreSchoolListRecyclerAdapter extends BaseQuickAdapter<OfflineSchool, BaseViewHolder> {

    public DialogLoadMoreSchoolListRecyclerAdapter() {
        super(R.layout.item_load_morer_teacher_dialog_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, OfflineSchool bean) {
        viewHolder.setText(R.id.name, bean.getTitle());
    }


}
