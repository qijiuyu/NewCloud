package com.seition.cloud.pro.newcloud.home.mvp.ui.lecture.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.utils.URLImageParser;

import static com.seition.cloud.pro.newcloud.widget.ExpandableTextView.cleanP;

/**
 * Created by addis on 2018/9/13.
 */
public class LectureHomeAdpater extends BaseQuickAdapter<Teacher, BaseViewHolder> {

    public LectureHomeAdpater() {
        super(R.layout.item_teacher_home);
    }

    @Override
    protected void convert(BaseViewHolder helper, Teacher item) {
        if (item.getInfo() == null || item.getInfo().trim().isEmpty() || "null".equals(item.getInfo()))
            helper.setText(R.id.teacher_info, R.string.teacher_default_info);
        else
            helper.setText(R.id.teacher_info
                    , Html.fromHtml(
                            /*cleanP(*/item.getInfo()/*)*/
                            , new URLImageParser(mContext, helper.getView(R.id.teacher_info))
                            , null));
    }
}
