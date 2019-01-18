package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindBank;

public class BindBankManageRecyclerAdapter extends BaseQuickAdapter<BindBank, BaseViewHolder> {

    public BindBankManageRecyclerAdapter() {
        super(R.layout.item_bank_manage_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BindBank bean) {
        viewHolder.setText(R.id.bank_name, bean.getAccounttype());

        viewHolder.addOnClickListener(R.id.bank_delete);
    }


}
