package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerOptionsItem;
import com.seition.cloud.pro.newcloud.app.bean.examination.AnswerSheet;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamConfig;
import com.seition.cloud.pro.newcloud.app.bean.examination.Pager;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

import java.util.ArrayList;

/**
 * Created by addis on 2018/3/21.
 */

public class AnswerSheetAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private int index = 0;

    public AnswerSheetAdapter() {
        super(new ArrayList<>());
        addItemType(MessageConfig.ANSWER_SHEET_TYPE, R.layout.item_examination_answer_sheet_type);
        addItemType(MessageConfig.ANSWER_SHEET_QUESTION, R.layout.item_examination_answer_sheet_question);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case MessageConfig.ANSWER_SHEET_TYPE:
                helper.setText(R.id.title, ((AnswerSheet) item).getTitle());
                index = 0;
                break;
            case MessageConfig.ANSWER_SHEET_QUESTION:
                helper.setText(R.id.item_index, ++index + "");
                Pager pager = (Pager) item;
                if (pager.isNow())
                    helper.getView(R.id.item_index).setBackgroundResource(R.drawable.shape_answer_sheet_selected);
                else {
                    for (AnswerOptionsItem answer : pager.getAnswer_options()) {
                        switch (pager.getType_info().getQuestion_type_key()) {
                            case ExamConfig.RADIO://选择
                            case ExamConfig.JUDGE:
                            case ExamConfig.MULTISELECT:
                                if (answer.isSelector()) {
                                    helper.getView(R.id.item_index).setBackgroundResource(R.drawable.shape_answer_sheet_gone);
                                    return;
                                }
                                break;
                            case ExamConfig.COMPLETION://填空
                            case ExamConfig.ESSAYS:
                                if (answer.getAnswer_value() != null && !answer.getAnswer_value().trim().isEmpty()) {
                                    helper.getView(R.id.item_index).setBackgroundResource(R.drawable.shape_answer_sheet_gone);
                                    return;
                                }
                                break;
                        }
                    }
                    helper.getView(R.id.item_index).setBackgroundResource(R.drawable.shape_answer_sheet_unfinished);
                }
                break;
        }
    }
}
