package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageComment;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class MessageCommentRecyclerAdapter extends BaseQuickAdapter<MessageComment, BaseViewHolder> {

    public MessageCommentRecyclerAdapter() {
        super(R.layout.item_message_comment_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MessageComment bean) {
        viewHolder.addOnClickListener(R.id.message_send_back);
        viewHolder.setText(R.id.message_send_time, TimeUtils.stampToDate(bean.getCtime(), TimeUtils.Format_TIME8));
        viewHolder.setText(R.id.message_send_name, bean.getUidinfo().getUname());
        viewHolder.setText(R.id.message_send_content, bean.getTo_comment());
        viewHolder.setText(R.id.last_message_send_name, bean.getFidinfo().getUname());
        viewHolder.setText(R.id.last_message_send_content, "：" + bean.getInfo());
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(), bean.getUidinfo().getAvatar_big(), (ImageView) viewHolder.getView(R.id.message_send_cover));
    }
}
