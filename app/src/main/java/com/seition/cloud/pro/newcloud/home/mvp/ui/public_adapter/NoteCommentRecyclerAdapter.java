package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComment;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class NoteCommentRecyclerAdapter extends BaseQuickAdapter<NoteComment, BaseViewHolder> {

    public NoteCommentRecyclerAdapter() {
        super(R.layout.item_second_comment_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, NoteComment bean) {
        viewHolder.setText(R.id.user_name, bean.getUserinfo().getUname());
        viewHolder.setText(R.id.time, TimeUtils.stampToDate(bean.getCtime(),TimeUtils.Format_TIME5));
        viewHolder.setText(R.id.comment, bean.getNote_description());
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(),bean.getUserinfo().getAvatar_small(),(ImageView) viewHolder.getView(R.id.user_photo));
    }


}
