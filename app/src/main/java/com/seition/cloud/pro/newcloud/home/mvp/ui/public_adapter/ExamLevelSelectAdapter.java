package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamLevel;

/**
 * Created by addis on 2018/9/8.
 */
public class ExamLevelSelectAdapter extends BaseQuickAdapter<ExamLevel, BaseViewHolder> {

    public ExamLevelSelectAdapter() {
//        super(R.layout.item_exam_level);
        super(R.layout.item_dialog_list);
    }

    private int selectId = -1;

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ExamLevel level) {
//        viewHolder.setText(R.id.level_title, level.getLevelTitle());
//        viewHolder.setChecked(R.id.level_title, selectId == level.getLevelId());
        viewHolder.setText(R.id.name, level.getLevelTitle());
    }
}
