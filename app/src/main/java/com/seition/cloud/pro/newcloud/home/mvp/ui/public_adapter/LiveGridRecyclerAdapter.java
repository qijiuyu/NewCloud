package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;

public class LiveGridRecyclerAdapter extends BaseQuickAdapter<CourseOnline, BaseViewHolder> {

    public LiveGridRecyclerAdapter() {
        super(R.layout.item_live_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CourseOnline bean) {

        double price = bean.getPrice();

        if (price == 0) {
            viewHolder.setTextColor(R.id.live_price,viewHolder.itemView.getContext().getResources().getColor(R.color.green));
            viewHolder.setText(R.id.live_price,"免费");
        } else {
            viewHolder.setText(R.id.live_price,"¥" + price);
            viewHolder.setTextColor(R.id.live_price,viewHolder.itemView.getContext().getResources().getColor(R.color.color_1F68CE));
        }
//        viewHolder.setText(R.id.live_state, bean.getName());
        viewHolder.setText(R.id.live_name, bean.getVideo_title());

        int count = 0;
        if( PreferenceUtil.getInstance(viewHolder.itemView.getContext()).getInt(MyConfig.Config_MarketStatus,0) == 0)
            count = bean.getVideo_order_count();
        else
            count = bean.getVideo_order_count_mark();
        viewHolder.setText(R.id.live_learn_count, ""+count + "人在学习");
        GlideLoaderUtil.LoadRoundImage1(viewHolder.itemView.getContext(),bean.getCover(),(ImageView) viewHolder.getView(R.id.live_cover));
    }


}
