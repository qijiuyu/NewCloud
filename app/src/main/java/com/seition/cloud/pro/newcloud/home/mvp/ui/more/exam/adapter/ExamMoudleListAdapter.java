package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.Moudles;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

/**
 * Created by addis on 2018/3/21.
 */

public class ExamMoudleListAdapter extends BaseQuickAdapter<Moudles, BaseViewHolder> {

    public ExamMoudleListAdapter() {
        super(R.layout.item_exam_moudle_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Moudles item) {
        helper.setText(R.id.item_title, item.getTitle());
        helper.setText(R.id.description, item.getDescription());
        String url = item.getIcon();
        try {
            if (Long.parseLong(item.getIcon()) > 0)
                url = "";
        } catch (Exception e) {
        }
        GlideLoaderUtil.LoadImage(helper.itemView.getContext(), url, helper.getView(R.id.icon));
    }
}
