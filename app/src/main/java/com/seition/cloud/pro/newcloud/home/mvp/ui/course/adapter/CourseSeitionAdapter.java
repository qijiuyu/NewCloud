package com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

import java.util.List;

/**
 * Created by addis on 2018/4/9.
 */

public class CourseSeitionAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CourseSeitionAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(MessageConfig.SEITION_ITEM, R.layout.item_course_seition);
        addItemType(MessageConfig.VIDEO_ITEM, R.layout.item_course_seition_video);
    }

    private boolean isBuy = false;
    private boolean isFree = false;

    public void setBuyOrFree(boolean buy, boolean free) {
        isBuy = buy;
        isFree = free;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case MessageConfig.SEITION_ITEM:
                CourseSeitionTitleItem seition = (CourseSeitionTitleItem) item;
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
                CourseSeitionVideoItem video = (CourseSeitionVideoItem) item;
                boolean isVideo = false;
                int drawableResources = R.drawable.ic_video;
                switch (video.video.getVideo_type()) {
                    case 1:
                        //视频
                        drawableResources = R.drawable.ic_video;
                        isVideo = true;
                        break;
                    case 2:
                        //音频
                        drawableResources = R.drawable.ic_voice;
                        isVideo = true;
                        break;
                    case 3:
                        //文本
                        drawableResources = R.drawable.ic_txt;
                        isVideo = false;
                        break;
                    case 4:
                        //文档
                        drawableResources = R.drawable.ic_word;
                        isVideo = false;
                        break;
                    case 5:
                        //考试
                        drawableResources = R.drawable.ic_test;
                        isVideo = false;
                        break;
                    default:
                        //视频
                        drawableResources = R.drawable.ic_video;
                        isVideo = true;
                        break;
                }
                helper.setText(R.id.video_title, video.video.getTitle())
                        .setText(R.id.video_time_count, video.video.getDuration())
                        .setVisible(R.id.video_time_count, isVideo)
                        .setBackgroundRes(R.id.seition_type, drawableResources)
                        .getView(R.id.video_title).setSelected(true);

                helper.setVisible(R.id.price, false);
                helper.setVisible(R.id.is_buy, false);
                helper.setVisible(R.id.is_free, false);
                if (video.video.getIs_free() == 1)
                    helper.setVisible(R.id.is_free, true);
                else if (!isFree && !isBuy) //课程价格不为0并且未购买状态才显示价格与购买状态
                    if (video.video.getIs_buy() == 1)
                        helper.setVisible(R.id.is_buy, true);
                    else {
                        if (video.video.getCourse_hour_price() > 0) {
                            helper.setVisible(R.id.price, true)
                                    .setText(R.id.price, video.video.getCourse_hour_price() + "");
                        }
                    }
//                if (video.video.getIs_shiting() == 1)
//                    helper.getView(R.id.is_audition).setVisibility(View.VISIBLE);
//                else
//                    helper.getView(R.id.is_audition).setVisibility(View.GONE);
                break;
        }
    }
}