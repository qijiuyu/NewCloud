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

public class CourseLiveRecyclerAdapter extends BaseQuickAdapter<CourseOnline, BaseViewHolder> {

    public CourseLiveRecyclerAdapter() {
        super(R.layout.item_courses_list);
    }

    private boolean isShowBuy = true;

    public void setShowBuy(boolean showBuy) {
        isShowBuy = showBuy;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CourseOnline bean) {
        int type = Integer.parseInt(bean.getType());
        viewHolder.setText(R.id.course_title, bean.getVideo_title());

        int count = 0;
        if (PreferenceUtil.getInstance(viewHolder.itemView.getContext()).getInt(MyConfig.Config_MarketStatus, 0) == 0)
            count = bean.getVideo_order_count();
        else
            count = bean.getVideo_order_count_mark();
        if (type == 1) {
            int seitionCount = bean.getSection_count();
            if (seitionCount == 0)
                seitionCount = bean.getVideo_section_count();
            viewHolder.setText(R.id.course_apply_num, count + "人在学.共" + seitionCount + "节");
            viewHolder.setBackgroundRes(R.id.course_type, R.mipmap.course_video);
        } else if (type == 2) {
            viewHolder.setText(R.id.course_apply_num, count + "人报名");
            viewHolder.setBackgroundRes(R.id.course_type, R.mipmap.course_live);
        }
        if (isShowBuy)
            ViewContentSettingUtils.priceSetting(mContext, viewHolder.getView(R.id.course_price), bean.getPrice());
        else viewHolder.setText(R.id.course_price, bean.getTime_limit());

        if (/*bean.getIsBuy() == 1 || */isShowBuy && bean.getIs_buy() > 0)
            viewHolder.setVisible(R.id.course_isbuy, true);
        else
            viewHolder.setVisible(R.id.course_isbuy, false);
        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(), bean.getImageurl(), (ImageView) viewHolder.getView(R.id.course_cover));
    }

}
