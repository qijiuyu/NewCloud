package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;

public class DialogListRecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DialogListRecyclerAdapter() {
        super(R.layout.item_dialog_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String string) {
        viewHolder.setText(R.id.name, string);
    }


}
