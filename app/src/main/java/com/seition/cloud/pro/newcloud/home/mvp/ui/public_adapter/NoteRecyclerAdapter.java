package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.note.Note;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class NoteRecyclerAdapter extends BaseQuickAdapter<Note, BaseViewHolder> {

    public NoteRecyclerAdapter() {
        super(R.layout.item_note_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Note bean) {
        viewHolder.setText(R.id.list_item_title, bean.getNote_title());

        viewHolder.setText(R.id.list_item_txt, bean.getNote_description());
//        viewHolder.setText(R.id.note_source, "源自：" + bean.getVideo_title());
        viewHolder.setText(R.id.comment_time, TimeUtils.stampToDate(bean.getCtime(), TimeUtils.Format_TIME9));
        viewHolder.setText(R.id.comment_count, bean.getNote_comment_count() + "");
        viewHolder.setText(R.id.zan_count, bean.getNote_help_count() + "");
    }
}
