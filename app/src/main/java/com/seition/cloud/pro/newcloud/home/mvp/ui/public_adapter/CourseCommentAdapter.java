package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.comment.CommentBean;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class CourseCommentAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> {

    public CourseCommentAdapter() {
        super(R.layout.item_comment_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CommentBean bean) {
        viewHolder.setText(R.id.comment_name, bean.getUsername());
        viewHolder.setText(R.id.comment_time, TimeUtils.stampToDate(bean.getCtime()+"",TimeUtils.Format_TIME3) + "");
        viewHolder.setText(R.id.comment_content, bean.getReview_description());
        GlideLoaderUtil.LoadCircleImage(mContext, bean.getUserface(), viewHolder.getView(R.id.comment_cover));
    }
}
