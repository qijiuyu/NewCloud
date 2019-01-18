package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveBean;
import com.seition.cloud.pro.newcloud.app.bean.course.HomeLiveCourse;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;

public class LiveHorRecyclerAdapter extends BaseQuickAdapter<HomeLiveCourse, BaseViewHolder> {

    public LiveHorRecyclerAdapter() {
        super(R.layout.item_live_horizontal_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, HomeLiveCourse bean) {
        viewHolder.setText(R.id.live_time, TimeUtils.stampToDate(bean.getTime() * 1000 + "", TimeUtils.Format_TIME3));
        viewHolder.setText(R.id.live_name, bean.getVideo_title());
//        viewHolder.setText(R.id.live_teacher, "" + bean.getT_price());
        ViewContentSettingUtils.priceSetting(mContext, viewHolder.getView(R.id.live_teacher), bean.getT_price());
        String count = "";
        if (PreferenceUtil.getInstance(viewHolder.itemView.getContext()).getInt(MyConfig.Config_MarketStatus, 0) == 0)
            count = bean.getVideo_order_count();
        else
            count = bean.getVideo_order_count_mark();
        viewHolder.setText(R.id.live_study_number, "" + count + "人在学");
//
        GlideLoaderUtil.LoadRoundImage1(viewHolder.itemView.getContext(), bean.getImageurl(), (ImageView) viewHolder.getView(R.id.live_cover));
    }
}
