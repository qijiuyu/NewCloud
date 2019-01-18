package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;

public class CourseGridRecyclerAdapter extends BaseQuickAdapter<CourseOnline, BaseViewHolder> {

    public CourseGridRecyclerAdapter() {
        super(R.layout.item_courses_gridview);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CourseOnline bean) {
        viewHolder.setText(R.id.course_name, bean.getVideo_title());

        int count = 0;
        if( PreferenceUtil.getInstance(viewHolder.itemView.getContext()).getInt(MyConfig.Config_MarketStatus,0) == 0)
            count = bean.getVideo_order_count();
        else
            count = bean.getVideo_order_count_mark();

        viewHolder.setText(R.id.course_learn_count, count + "人在学习");
        ViewContentSettingUtils.priceSetting(mContext, viewHolder.getView(R.id.course_price), bean.getPrice());//设置价格
        String cover = bean.getCover();
        if (cover == null || cover.equals(""))
            cover = bean.getImageurl();
        if (!cover.contains("http"))
            cover = bean.getImageurl();
        GlideLoaderUtil.LoadRoundImage1(viewHolder.itemView.getContext(), cover, (ImageView) viewHolder.getView(R.id.course_cover));
    }
}
