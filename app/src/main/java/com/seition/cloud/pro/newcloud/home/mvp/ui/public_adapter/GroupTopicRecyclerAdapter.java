package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupTheme;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupTopicListActivity;

/**
 * author: xzw
 */

public class GroupTopicRecyclerAdapter extends BaseQuickAdapter<GroupTheme, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {


    public GroupTopicRecyclerAdapter() {
        super(R.layout.item_group_topic_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, GroupTheme bean) {
        viewHolder.setText(R.id.topic_title, bean.getTitle());
        viewHolder.setText(R.id.topic_content, bean.getContent());
        viewHolder.setText(R.id.topic_auther, bean.getName());
        viewHolder.setText(R.id.topic_time,  bean.getReplytime());
        viewHolder.setText(R.id.topic_count,  bean.getReplaycount());

//        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),group.getLogoUrl(),(ImageView) viewHolder.getView(R.id.group_img));
//        ImageLoaderUtil.LoadImage(mContext, group.getCustom_fields().getThumb_c().get(0), (ImageView) viewHolder.getView(R.id.iv_logo));
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Intent intent = new Intent(view.getContext(), GroupTopicListActivity.class);
        ((Activity)(view.getContext())).startActivityForResult(intent, 0);
    }
}
