package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;

public class OfflineGridRecyclerAdapter extends BaseQuickAdapter<CourseOffline, BaseViewHolder> {

    public OfflineGridRecyclerAdapter() {
        super(R.layout.item_courses_gridview);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CourseOffline bean) {
        viewHolder.setText(R.id.course_name, bean.getCourse_name());

        int count = 0;
        if( PreferenceUtil.getInstance(viewHolder.itemView.getContext()).getInt(MyConfig.Config_MarketStatus,0) == 0)
            count = Integer.parseInt(bean.getCourse_order_count());
        else
            count = Integer.parseInt(bean.getCourse_order_count_mark());
        viewHolder.setText(R.id.course_learn_count, count+"人报名");
//        viewHolder.setText(R.id.course_price, "¥" + bean.getPrice());

        ViewContentSettingUtils.priceSetting(mContext, viewHolder.getView(R.id.course_price), Double.parseDouble(bean.getPrice()));
//        ViewContentSettingUtils.priceSetting(mContext,  viewHolder.getView(R.id.course_price), Double.parseDouble(bean.getPrice()));
        GlideLoaderUtil.LoadRoundImage1(viewHolder.itemView.getContext(),bean.getImageurl(),(ImageView) viewHolder.getView(R.id.course_cover));
    }


}
