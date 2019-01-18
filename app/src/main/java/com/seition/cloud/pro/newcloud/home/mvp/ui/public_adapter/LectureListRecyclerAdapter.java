package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class LectureListRecyclerAdapter extends BaseQuickAdapter<Teacher, BaseViewHolder> {

    public LectureListRecyclerAdapter() {
        super(R.layout.item_lecturer_list);
    }

    private boolean isShowOgranization = true;

    public void setIsShowOrganization(boolean isShowOgranization) {
        this.isShowOgranization = isShowOgranization;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Teacher bean) {
        if (MyConfig.isOpenAboutSchool && isShowOgranization) {
            viewHolder.setText(R.id.organization_name, bean.getSchool_info().getTitle());
        } else viewHolder.setVisible(R.id.organization, false);
        viewHolder.setText(R.id.lecturer_name, bean.getName());
        viewHolder.setText(R.id.lecturer_intro, Html.fromHtml(bean.getInfo() == null ? "这个老师太懒,暂时没有简介!" : bean.getInfo()));
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(), bean.getHeadimg(), (ImageView) viewHolder.getView(R.id.lecturer_cover));
    }
}
