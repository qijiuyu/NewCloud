package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategory;

public class QuestionClassifyRecyclerAdapter extends BaseQuickAdapter<QaCategory, BaseViewHolder> {

    public QuestionClassifyRecyclerAdapter() {
        super(R.layout.item_qa_classify_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, QaCategory bean) {
        viewHolder.setText(R.id.classify_name, bean.getTitle());
    }

}
