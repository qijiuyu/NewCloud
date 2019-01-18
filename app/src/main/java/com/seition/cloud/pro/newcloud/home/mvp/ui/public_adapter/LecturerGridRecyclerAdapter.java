package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.lecturer.Teacher;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class LecturerGridRecyclerAdapter extends BaseQuickAdapter<Teacher, BaseViewHolder> {

    public LecturerGridRecyclerAdapter() {
        super(R.layout.item_lecturer_horizontal_lis);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Teacher bean) {
        viewHolder.setText(R.id.lecture_name, bean.getName());
        GlideLoaderUtil.LoadCircleImage(viewHolder.itemView.getContext(),bean.getHeadimg(),(ImageView) viewHolder.getView(R.id.lecture_cover));
    }


}
