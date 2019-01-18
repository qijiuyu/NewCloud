package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageSystem;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class MessageSystemRecyclerAdapter extends BaseQuickAdapter<MessageSystem, BaseViewHolder> {

    public MessageSystemRecyclerAdapter() {
        super(R.layout.item_message_system_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MessageSystem bean) {
        viewHolder.setText(R.id.rl_message_system_title, Html.fromHtml(bean.getBody().replaceAll("</?[^>]+>", "")));
        viewHolder.setText(R.id.rl_message_system_time, TimeUtils.stampToDate(bean.getCtime(), TimeUtils.Format_TIME8));
    }


}
