package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class UserExchangeRecordRecyclerAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public UserExchangeRecordRecyclerAdapter() {
        super(R.layout.item_exchange_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Order bean) {
        viewHolder.setText(R.id.action_type, "支付");

        String title = "";
        switch (bean.getOrder_type()) {
            case 1:
                //文库
                title = bean.getDoc_info().getTitle();
                break;
            case 2:
                //商品
                title = bean.getGoods_info().getTitle();
                break;
        }
        viewHolder.setText(R.id.transaction_title, title);
        viewHolder.setText(R.id.transaction_time, TimeUtils.MyFormatTime3(TimeUtils.stringToLong(bean.getCtime()) * 1000));
        if (bean.getPay_price() == 0)
            viewHolder.setText(R.id.transaction_price, "-" + bean.getPrice() + "积分");
        else viewHolder.setText(R.id.transaction_price, "-" + bean.getPay_price() + "元");
    }
}