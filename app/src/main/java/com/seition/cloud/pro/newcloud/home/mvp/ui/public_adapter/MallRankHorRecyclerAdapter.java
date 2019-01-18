package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;

public class MallRankHorRecyclerAdapter extends BaseQuickAdapter<Mall, BaseViewHolder> {

    public MallRankHorRecyclerAdapter() {
        super(R.layout.item_mall_goods_rank_hor_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Mall bean) {
        viewHolder.setText(R.id.mall_name, bean.getTitle());
        viewHolder.setText(R.id.mall_stock, "库存" + bean.getStock());
        viewHolder.setText(R.id.mall_price, ViewContentSettingUtils.getMallPrice(bean.getPrice(), mContext, true, false));
        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(), bean.getCover(), (ImageView) viewHolder.getView(R.id.mall_img));
    }
}
