package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.text.SpannableString;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageLetter;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.widget.emoji.ExpressionUtil;

public class MessagePrivateRecyclerAdapter extends BaseQuickAdapter<MessageLetter, BaseViewHolder> {

    public MessagePrivateRecyclerAdapter() {
        super(R.layout.item_message_private_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MessageLetter bean) {
        String timeStr = "";
        MessageUserInfo userInfo = bean.getAnd_user_info();
        String userId = PreferenceUtil.getInstance(viewHolder.itemView.getContext()).getString("user_id", "");

        viewHolder.setText(R.id.message_send_time, TimeUtils.stampToDate(bean.getList_ctime(), TimeUtils.Format_TIME3));
//        viewHolder.setText(R.id.message_send_name, userId.equals(bean.getLast_message().getFrom_uid() + "") ? bean.getTo_user_info().getUname() + "" : bean.getLast_message().getUser_from_info().getUname());
        viewHolder.setText(R.id.message_send_name, bean.getAnd_user_info().getUname());
        // 表情处理
        String faceString = bean.getLast_message().getContent(); // -------------要转换的文字
        if (faceString == null) faceString = "";
        SpannableString spannableString = ExpressionUtil.getExpressionString(mContext, faceString, "\\[[a-z_]{1,11}]",
                faceString.length());
        viewHolder.setText(R.id.message_send_content, spannableString);// -------暂时取消表情解析
//        viewHolder.setText(R.id.message_send_content, bean.getLast_message().getContent());
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(), bean.getAnd_user_info().getAvatar_big(), (ImageView) viewHolder.getView(R.id.message_send_cover));
//        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(), userId.equals(bean.getLast_message().getFrom_uid() + "") ? bean.getTo_user_info().getAvatar_big() : bean.getLast_message().getUser_from_info().getAvatar_big(), (ImageView) viewHolder.getView(R.id.message_send_cover));

    }
}
