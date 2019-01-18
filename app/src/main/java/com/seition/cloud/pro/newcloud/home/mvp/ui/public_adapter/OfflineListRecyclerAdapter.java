package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;

public class OfflineListRecyclerAdapter extends BaseQuickAdapter<CourseOffline, BaseViewHolder> {

    public OfflineListRecyclerAdapter() {
        super(R.layout.item_offline_recycler_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CourseOffline bean) {
        viewHolder.setText(R.id.offline_title, bean.getCourse_name());
//        viewHolder.setText(R.id.offline_price, "¥"+bean.getPrice());
        try {
            ViewContentSettingUtils.priceSetting(mContext, viewHolder.getView(R.id.offline_price), Double.parseDouble(bean.getPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.setText(R.id.offline_time, "" + TimeUtils.stampToDate(bean.getListingtime(), TimeUtils.Format_TIME3) + "~"
                + TimeUtils.stampToDate(bean.getUctime(), TimeUtils.Format_TIME3));
        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(), bean.getImageurl(), (ImageView) viewHolder.getView(R.id.offline_cover));
    }
}
