package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecord;

public class UserStudyRecordRecyclerAdapter extends BaseQuickAdapter<StudyRecord, BaseViewHolder> {

    public UserStudyRecordRecyclerAdapter() {
        super(R.layout.item_study_recode);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, StudyRecord bean) {


//        viewHolder.setText(R.id.time, bean.getTime());

//        ArrayList<StudyRecordContent> list ;//= bean.getList();
//
//        UserStudyContentRecyclerAdapter contentAdapter = new UserStudyContentRecyclerAdapter(list);
//        viewHolder.getV
//        viewHolder.setAdapter(R.id.listview,contentAdapter);

//        viewHolder.setText(R.id.intro, bean.getIntro());
//        viewHolder.setText(R.id.member_count, "成员:" + bean.getMemberCount());
//        viewHolder.setText(R.id.thread_count, "帖子:" + bean.getThreadCount());
//        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(),bean.getLogoUrl(),(ImageView) viewHolder.getView(R.id.group_img));
    }




}
