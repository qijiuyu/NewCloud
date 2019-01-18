package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.BindBank;

public class BindBankRecyclerAdapter extends BaseQuickAdapter<BindBank, BaseViewHolder> {

    public BindBankRecyclerAdapter() {
        super(R.layout.item_bind_bank);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BindBank bean) {
        viewHolder.setText(R.id.bank_name, bean.getAccounttype());
        viewHolder.setText(R.id.bank_acount, "****  ****  ****  "+bean.getAccount().substring(bean.getAccount().length()-4,bean.getAccount().length()));
//        viewHolder.setText(R.id.comment_time, "成员:" + bean.getMemberCount());
//        viewHolder.setText(R.id.comment_content, "帖子:" + bean.getThreadCount());
//        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getLogoUrl(),(ImageView) viewHolder.getView(R.id.comment_cover));
    }


}
