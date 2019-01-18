package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecordContent;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecordSection;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<StudyRecordSection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final StudyRecordSection item) {
        helper.setText(R.id.time, item.header);
//        helper.setText(R.id.header, item.header);
//        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.time);
    }


    @Override
    protected void convert(BaseViewHolder helper, StudyRecordSection item) {
        StudyRecordContent content =  (StudyRecordContent)item.t;

        if(isDelete){
            helper.setVisible(R.id.cb,true);
            if(content.isSelect())
                helper.setBackgroundRes(R.id.cb,R.mipmap.choose);
            else
                helper.setBackgroundRes(R.id.cb,R.mipmap.unchoose);
        }else{
            helper.setVisible(R.id.cb,false);
        }

//        helper.setText(R.id.cb, item.header);
        helper.setText(R.id.courses_title, content.getVideo_info().getVideo_title());
        helper.setText(R.id.video_title, content.getVideo_section().getTitle());
        helper.setText(R.id.video_time, "上次播放记录时长:" + TimeUtils.timeLongToString(Long.parseLong(content.getTime())));
//        switch (helper.getLayoutPosition() %
//                2) {
//            case 0:
//                helper.setImageResource(R.id.iv, R.mipmap.m_img1);
//                break;
//            case 1:
//                helper.setImageResource(R.id.iv, R.mipmap.m_img2);
//                break;
//
//        }
//        helper.setText(R.id.tv, video.getName());
        helper.addOnClickListener(R.id.time);
    }

    private boolean isDelete = false;

    public void setDelete(boolean delete) {
        isDelete = delete;
        notifyDataSetChanged();
    }


}
