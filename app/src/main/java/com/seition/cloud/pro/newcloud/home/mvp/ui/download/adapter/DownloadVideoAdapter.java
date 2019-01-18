package com.seition.cloud.pro.newcloud.home.mvp.ui.download.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionTitleDownloadItem;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionVideoDownloadItem;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.view_holder.DownloadViewHolder;

import java.util.List;

/**
 * Created by addis on 2018/4/9.
 */

public class DownloadVideoAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, DownloadViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DownloadVideoAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(MessageConfig.SEITION_ITEM, R.layout.item_course_seition);
        addItemType(MessageConfig.VIDEO_ITEM, R.layout.item_course_seition_download_video);
    }

    @Override
    protected void convert(DownloadViewHolder helper, MultiItemEntity item) {
        helper.setmContext(mContext);
        switch (item.getItemType()) {
            case MessageConfig.SEITION_ITEM:
                CourseSeitionTitleDownloadItem seition = (CourseSeitionTitleDownloadItem) item;
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (seition.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                helper.setText(R.id.seition_title, seition.mSeition.getTitle())
                        .setImageResource(R.id.next, seition.isExpanded() ? R.drawable.ic_arrow_down_gray : R.drawable.ic_arrow_next);
                break;
            case MessageConfig.VIDEO_ITEM:
                CourseSeitionVideoDownloadItem video = (CourseSeitionVideoDownloadItem) item;
                helper.setText(R.id.video_title, video.video.getTitle());
                helper.showBuy(video.video);
                break;
        }
    }
}

